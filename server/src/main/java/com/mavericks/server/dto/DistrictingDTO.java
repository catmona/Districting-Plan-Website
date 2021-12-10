package com.mavericks.server.dto;

import com.mavericks.server.entity.Election;

public class DistrictingDTO {

    private String districtingId;
    private double score;
    private double polsbyPopper;
    private double populationEquality;
    private int majorityMinority;
    private int splitCounty;
    private double devFromAveragePopulation;
    private double devFromEnactedPopulation;

    public DistrictingDTO(String districtingId, double score, double polsbyPopper, double populationEquality,
                          int majorityMinority, int splitCounty,
                          double devFromAveragePopulation, double devFromEnactedPopulation) {
        this.districtingId = districtingId;
        this.score = score;
        this.polsbyPopper = polsbyPopper;
        this.populationEquality = populationEquality;
        this.majorityMinority = majorityMinority;
        this.splitCounty = splitCounty;
        this.devFromAveragePopulation = devFromAveragePopulation;
        this.devFromEnactedPopulation = devFromEnactedPopulation;
    }

    public String getDistrictingId() {
        return districtingId;
    }

    public void setDistrictingId(String districtingId) {
        this.districtingId = districtingId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getPolsbyPopper() {
        return polsbyPopper;
    }

    public void setPolsbyPopper(double polsbyPopper) {
        this.polsbyPopper = polsbyPopper;
    }

    public double getPopulationEquality() {
        return populationEquality;
    }

    public void setPopulationEquality(double populationEquality) {
        this.populationEquality = populationEquality;
    }

    public int getMajorityMinority() {
        return majorityMinority;
    }

    public void setMajorityMinority(int majorityMinority) {
        this.majorityMinority = majorityMinority;
    }

    public int getSplitCounty() {
        return splitCounty;
    }

    public void setSplitCounty(int splitCounty) {
        this.splitCounty = splitCounty;
    }

    public double getDevFromAveragePopulation() {
        return devFromAveragePopulation;
    }

    public void setDevFromAveragePopulation(double devFromAveragePopulation) {
        this.devFromAveragePopulation = devFromAveragePopulation;
    }

    public double getDevFromEnactedPopulation() {
        return devFromEnactedPopulation;
    }

    public void setDevFromEnactedPopulation(double devFromEnactedPopulation) {
        this.devFromEnactedPopulation = devFromEnactedPopulation;
    }
}
