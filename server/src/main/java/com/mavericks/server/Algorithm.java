package com.mavericks.server;

import com.mavericks.server.dto.AlgorithmDTO;
import com.mavericks.server.entity.*;
import com.mavericks.server.enumeration.Demographic;
import com.mavericks.server.enumeration.PopulationMeasure;
import com.mavericks.server.repository.CensusBlockRepository;
import com.mavericks.server.repository.DistrictingRepository;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.operation.union.UnaryUnionOp;
import org.locationtech.jts.simplify.TopologyPreservingSimplifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.wololo.geojson.Feature;
import org.wololo.geojson.GeoJSON;
import org.wololo.geojson.GeoJSONFactory;

import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *TODO:
 * 1.update district populations with the removal/addition of each census block(Done but test later)
 * 2.Track which precincts have changed (Done but test later)
 * 3. Implement a copy method for the districting class
 *
 *
 */

public class Algorithm{

    @Autowired
    public DistrictingRepository districtingRepo;

    //user set flag to continue running the algorithm
    private boolean running;
    private boolean flag;
    private int iterations;
    private final int max_iterations;
    //measure constraints
    private double minPopulationEquality;
    private double populationEquality;
    //number of failed consecutive block moves
    private int failedCbMoves;
    private final int maxFaildCbMoves;
    private Districting inProgressPlan;
    //change later
    private PopulationMeasure populationMeasure;
    private final int REDRAW_CONST=5;
    private Districting bestDistricting;
    private int movesMade;
    private String inProgressPlanId;


    public Algorithm() {
        this.max_iterations=5000;
        this.maxFaildCbMoves=1000;
        running=true;
        flag=true;
    }


    @Async
    public void run() {
        setInProgressPlan(inProgressPlan.clone());

        while(iterations!=max_iterations && failedCbMoves!=maxFaildCbMoves && flag
                && (populationEquality>minPopulationEquality)){
            District d1=inProgressPlan.getRandDistrict();
            CensusBlock cb = d1.getRandCensusBlock();
            if(cb.isMoved() ||cb.getPopulation().get(0).getPopulationTotal()==0 ){
                iterations++;
                continue;
            }
            String oldPrecinct=cb.getPrecinctId();
            List<CensusBlock> neighbors = inProgressPlan.getNeighbors(cb);
            District d2=findNeighboringDistrict(neighbors,d1,inProgressPlan);
            if(d2==null){
                iterations++;
                continue;
            }
            boolean added=d2.addCensusBlock(cb,inProgressPlan,neighbors,populationMeasure,false);
            boolean removed=d1.removeCensusBlock(cb,neighbors,populationMeasure,false);
            Measures newMeasures=inProgressPlan.computeMeasures(populationMeasure);
            if(added&&removed&&newMeasures.getPopulationEquality()<populationEquality){
                inProgressPlan.setMeasures(newMeasures);
                populationEquality=newMeasures.getPopulationEquality();
                if(!oldPrecinct.equals(cb.getPrecinctId())){
                    inProgressPlan.addPrecinct(oldPrecinct);
                    inProgressPlan.addPrecinct(cb.getPrecinctId());
                }
                failedCbMoves=0;
                movesMade++;
            }
            else {

                d1.addCensusBlock(cb,inProgressPlan,neighbors,populationMeasure,true);
                d2.removeCensusBlock(cb,neighbors,populationMeasure,true);
                failedCbMoves++;
            }

//            if(iterations%REDRAW_CONST==0 && iterations>0){
//                inProgressPlan.processMovedBlocks();
//            }
            iterations++;
            System.out.println("iteration:"+iterations+"popEqu:"+populationEquality);
        }
        System.out.println("final population equ:"+populationEquality+"cbMovesfailed"+failedCbMoves);
        running=false;
    }

    public District findNeighboringDistrict(List<CensusBlock>neighbors,District currentDistrict,Districting plan){
        for(CensusBlock cb:neighbors){
            if(!cb.getDistrictId().equals(currentDistrict.getId())){
                return plan.getDistrict(cb.getDistrictId());
            }
        }
        return null;
    }

    private double acceptanceProbability(double oldScore, double newScore, double temp){
        if(oldScore>newScore){
            return 1.0;
        }
        else{
            return Math.exp((oldScore-newScore)/temp);
        }
    }

    public AlgorithmDTO getProgress(){
        return new AlgorithmDTO(inProgressPlan.getMeasures().getPopulationEquality(),iterations,running,null,null,-1);
    }


    public AlgorithmDTO getResults(){
        List<Feature> features = new ArrayList<>();
        GeoJSONWriter writer = new GeoJSONWriter();
        GeoJSONReader reader = new GeoJSONReader();
        GeometryFactory gf = new GeometryFactory();
        List<PopulationCopy>pops=new ArrayList<>();
        int index =1;
        for(District d:inProgressPlan.getDistricts()){
            Map<String,Object> properties = new HashMap<>();
            properties.put("District",index);
            properties.put("District_Name",""+index);
            Geometry geo=d.getGeometry();
            GeoJSON json = writer.write(geo);
            features.add(new Feature((org.wololo.geojson.Geometry)json ,properties));
            pops.add(d.getPopulation().get(0));
            index++;
        }
        AlgorithmDTO dto=new AlgorithmDTO(inProgressPlan.getMeasures().getPopulationEquality(),iterations,running,writer.write(features)
                ,pops,inProgressPlan.getPrecinctsChanged().size());
        dto.setMajorityMinority(inProgressPlan.majorityMinority());
        dto.setEfficiencyGap(inProgressPlan.efficiencyGap());
        dto.setPolsbyPopper(inProgressPlan.computePolsbyPopper());
        dto.setSplitCounty(inProgressPlan.getSplitCounty());
        return dto;
    }


    public void setInProgressPlan(Districting inProgressPlan) {
        this.inProgressPlan = inProgressPlan;
        this.populationEquality = inProgressPlan.getMeasures().getPopulationEquality();
    }

    public Districting getInProgressPlan() {
        return inProgressPlan;
    }

    public boolean isRunning() {
        return running;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public double getMinPopulationEquality() {
        return minPopulationEquality;
    }

    public void setMinPopulationEquality(double minPopulationEquality) {
        this.minPopulationEquality = minPopulationEquality;
    }



    public int getFailedCbMoves() {
        return failedCbMoves;
    }

    public void setFailedCbMoves(int failedCbMoves) {
        this.failedCbMoves = failedCbMoves;
    }

    public void setPopulationMeasure(PopulationMeasure populationMeasure) {
        this.populationMeasure = populationMeasure;
    }

    public String getInProgressPlanId() {
        return inProgressPlanId;
    }

    public void setInProgressPlanId(String inProgressPlanId) {
        this.inProgressPlanId = inProgressPlanId;
    }
}
