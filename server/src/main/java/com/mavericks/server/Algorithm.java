package com.mavericks.server;

import com.mavericks.server.entity.District;
import com.mavericks.server.entity.Districting;

public class Algorithm implements Runnable{

    //user set flag to continue running the algorithm
    private boolean running;
    private int iterations;
    private int max_iterations;
    //measure constraints
    private double minPopulationEquality;
    private double minCompactness;
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
        while(iterations!=max_iterations && failedCbMoves!=maxFaildCbMoves){
            District d1=inProgressPlan.getRandDistrict();
            District d2=d1.getRandNeighbor();
        }
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
