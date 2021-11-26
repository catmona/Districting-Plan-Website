package com.mavericks.server;

import com.mavericks.server.dto.AlgorithmDTO;
import com.mavericks.server.entity.CensusBlock;
import com.mavericks.server.entity.District;
import com.mavericks.server.entity.Districting;
import com.mavericks.server.entity.Measures;
import com.mavericks.server.enumeration.Demographic;
import com.mavericks.server.enumeration.PopulationMeasure;
import com.mavericks.server.repository.CensusBlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.wololo.geojson.Feature;
import org.wololo.geojson.GeoJSONFactory;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Algorithm{

    //user set flag to continue running the algorithm
    private boolean running;
    private int iterations;
    private final int max_iterations;
    //measure constraints
    private double minPopulationEquality;
    private double minCompactness;
    private double compactness;
    private double populationEquality;
    //number of failed consecutive block moves
    private int failedCbMoves;
    private final int maxFaildCbMoves;
    private Districting inProgressPlan;
    //change later
    private PopulationMeasure populationMeasure;
    private final int REDRAW_CONST=5;


    public Algorithm() {
        this.max_iterations=100000;
        this.maxFaildCbMoves=1000;
        running=true;
    }


    @Async
    public void run() {
        System.out.println("entered");
        populationMeasure=PopulationMeasure.TOTAL;
        while(iterations!=max_iterations && failedCbMoves!=maxFaildCbMoves && running
                && (compactness<minCompactness || populationEquality>minPopulationEquality)){
            District d1=inProgressPlan.getRandDistrict();
            CensusBlock cb = d1.getRandCensusBlock();
            List<CensusBlock> neighbors = inProgressPlan.getNeighbors(cb);
            District d2=findNeighboringDistrict(neighbors,d1,inProgressPlan);
            if(d2==null){
                iterations++;
                continue;
            }
            d2.addCensusBlock(d1,cb,inProgressPlan,neighbors,populationMeasure);
            d1.removeCensusBlock(cb,neighbors,populationMeasure);
            Measures newMeasures=inProgressPlan.computeMeasures(populationMeasure);
            if(newMeasures.getPolsbyPopperScore()<=compactness &&
                    newMeasures.getPopulationEqualityScore()>=populationEquality){
                d1.addCensusBlock(d2,cb,inProgressPlan,neighbors,populationMeasure);
                d2.removeCensusBlock(cb,neighbors,populationMeasure);
                failedCbMoves++;
            }
            else {
                inProgressPlan.setMeasures(newMeasures);
                compactness=newMeasures.getPolsbyPopperScore();
                populationEquality=newMeasures.getPopulationEqualityScore();
                failedCbMoves=0;
            }


            iterations++;

        }
    }

    public District findNeighboringDistrict(List<CensusBlock>neighbors,District currentDistrict,Districting plan){
        for(CensusBlock cb:neighbors){
            if(!cb.getDistrictId().equals(currentDistrict.getId())){
                return plan.getDistrict(cb.getDistrictId());
            }
        }
        return null;
    }

    private double acceptanceProbability(int oldScore, int newScore, int temp){
        if(oldScore>newScore){
            return 1.0;
        }
        else{
            return Math.exp((newScore-oldScore)/temp);
        }
    }



    public AlgorithmDTO getProgress(){
        return new AlgorithmDTO(inProgressPlan.getMeasures(),iterations,running,null,null);
    }


    public AlgorithmDTO getResults(){
        List<Feature> features = new ArrayList<>();
        GeoJSONWriter writer = new GeoJSONWriter();
        for(District d:inProgressPlan.getDistricts()){
            Map<String,Object> properties = new HashMap<>();
            properties.put("District",d.getDistrictNumber());
            properties.put("District_Name",""+d.getDistrictNumber());
            features.add(new Feature(((Feature)GeoJSONFactory.create(writer.write(d.getGeometry()).toString())).getGeometry(),properties));
        }
        return new AlgorithmDTO(inProgressPlan.getMeasures(),iterations,running,writer.write(features)
                ,inProgressPlan.getPopulation());
    }


    public void setInProgressPlan(Districting inProgressPlan) {
        this.inProgressPlan = inProgressPlan;
        this.compactness=inProgressPlan.getMeasures().getPolsbyPopperScore();
        this.populationEquality=inProgressPlan.getMeasures().getPopulationEqualityScore();
    }

    public Districting getInProgressPlan() {
        return inProgressPlan;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
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

    public double getMinCompactness() {
        return minCompactness;
    }

    public void setMinCompactness(double minCompactness) {
        this.minCompactness = minCompactness;
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
}
