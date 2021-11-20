package com.mavericks.server;

import com.mavericks.server.dto.AlgorithmDTO;
import com.mavericks.server.entity.CensusBlock;
import com.mavericks.server.entity.District;
import com.mavericks.server.entity.Districting;
import com.mavericks.server.entity.Measures;
import org.wololo.geojson.Feature;
import org.wololo.geojson.GeoJSONFactory;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Algorithm implements Runnable{

    //user set flag to continue running the algorithm
    private boolean running;
    private int iterations;
    private int max_iterations;
    //measure constraints
    private double minPopulationEquality;
    private double minCompactness;
    private double compactness;
    private double populationEquality;
    //number of failed consecutive block moves
    private int failedCbMoves;
    private int maxFaildCbMoves;
    private Districting inProgressPlan;

    public Algorithm(double minPopulationEquality, double minCompactness) {
        this.minPopulationEquality = minPopulationEquality;
        this.minCompactness = minCompactness;
        this.max_iterations=100000;
        this.maxFaildCbMoves=1000;
        running=true;
    }


    @Override
    public void run() {
        while(iterations!=max_iterations && failedCbMoves!=maxFaildCbMoves && !running){
            District d1=inProgressPlan.getRandDistrict();
            District d2=d1.getRandNeighbor();
            CensusBlock cb = d1.getRandCensusBlock();
            d1.addCensusBlock(cb);
            d2.removeCensusBlock(cb);
            Measures newMeasures=inProgressPlan.computeMeasures();
            if(newMeasures.getPolsbyPopperScore()<=compactness &&
                    newMeasures.getPopulationEqualityScore()<=populationEquality){
                d1.addCensusBlock(cb);
                d2.removeCensusBlock(cb);
                failedCbMoves++;
            }
            else {
                failedCbMoves=0;
            }

            iterations++;

        }
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
}
