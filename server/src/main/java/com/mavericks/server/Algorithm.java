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

    public Algorithm(double minPopulationEquality, double minCompactness, int max_iterations) {
        this.minPopulationEquality = minPopulationEquality;
        this.minCompactness = minCompactness;
        this.max_iterations=max_iterations;
        running=true;
    }


    @Override
    public void run() {
        while(iterations!=max_iterations && failedCbMoves!=maxFaildCbMoves){
            District d1=inProgressPlan.getRandDistrict();
            District d2=d1.getRandNeighbor();
        }
    }
}
