package com.mavericks.server.dto;

import com.mavericks.server.entity.Election;

import java.util.List;

public class PlanDTO {
    private List<List<Integer>> districtPopulations;
    private List<Election> districtElections;

    public PlanDTO() {}

    public PlanDTO(List<List<Integer>> districtPopulations, List<Election> districtElections) {
        this.districtPopulations = districtPopulations;
        this.districtElections = districtElections;
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
