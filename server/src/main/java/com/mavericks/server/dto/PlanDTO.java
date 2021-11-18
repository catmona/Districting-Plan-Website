package com.mavericks.server.dto;

import com.mavericks.server.entity.Election;
import com.mavericks.server.entity.Population;

import java.util.List;

public class PlanDTO {
    private int numDistricts;
    private double PolsbyPopper;
    private double populationEquality;
    private Election election;
    private List<Population> districtPopulations;

    public PlanDTO(int numDistricts, double polsbyPopper, double populationEquality, Election election, List<Population> districtPopulations) {
        this.numDistricts = numDistricts;
        PolsbyPopper = polsbyPopper;
        this.populationEquality = populationEquality;
        this.election = election;
        this.districtPopulations = districtPopulations;
    }

    public int getNumDistricts() {
        return numDistricts;
    }

    public void setNumDistricts(int numDistricts) {
        this.numDistricts = numDistricts;
    }

    public double getPolsbyPopper() {
        return PolsbyPopper;
    }

    public void setPolsbyPopper(double polsbyPopper) {
        PolsbyPopper = polsbyPopper;
    }

    public double getPopulationEquality() {
        return populationEquality;
    }

    public void setPopulationEquality(double populationEquality) {
        this.populationEquality = populationEquality;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public List<Population> getDistrictPopulations() {
        return districtPopulations;
    }

    public void setDistrictPopulations(List<Population> districtPopulations) {
        this.districtPopulations = districtPopulations;
    }
}
