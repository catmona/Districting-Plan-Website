package com.mavericks.server.dto;

import com.mavericks.server.entity.Election;

public class DistricitingDTO {

    private double polsbyPopper;
    private double populationEquality;
    private Election election;

    public DistricitingDTO(double polsbyPopper, double populationEquality, Election election) {
        this.polsbyPopper = polsbyPopper;
        this.populationEquality = populationEquality;
        this.election = election;
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

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }
}
