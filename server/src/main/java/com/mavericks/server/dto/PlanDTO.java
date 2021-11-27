package com.mavericks.server.dto;

import com.mavericks.server.entity.Election;

import java.util.List;

public class PlanDTO {
    private double repPercent;
    private double demPercent;
    private List<List<Integer>> districtPopulations;
    private List<Election> districtElections;

    public PlanDTO() {}

    public PlanDTO(double repPercent, double dempPercent, List<List<Integer>> districtPopulations, List<Election> districtElections) {
        this.repPercent = repPercent;
        this.demPercent = dempPercent;
        this.districtPopulations = districtPopulations;
        this.districtElections = districtElections;
    }

    public double getRepPercent() {
        return repPercent;
    }

    public void setRepPercent(double repPercent) {
        this.repPercent = repPercent;
    }

    public double getDemPercent() {
        return demPercent;
    }

    public void setDemPercent(double demPercent) {
        this.demPercent = demPercent;
    }

    public List<List<Integer>> getDistrictPopulations() {
        return districtPopulations;
    }

    public void setDistrictPopulations(List<List<Integer>> districtPopulations) {
        this.districtPopulations = districtPopulations;
    }

    public List<Election> getDistrictElections() {
        return districtElections;
    }

    public void setDistrictElections(List<Election> districtElections) {
        this.districtElections = districtElections;
    }
}
