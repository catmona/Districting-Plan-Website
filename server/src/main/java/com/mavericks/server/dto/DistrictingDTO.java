package com.mavericks.server.dto;

import com.mavericks.server.entity.Election;

public class DistrictingDTO {

    private String districtingId;
    private double polsbyPopper;
    private double populationEquality;
    private double repPercent;
    private double demPercent;

    public DistrictingDTO(String districtingId, double polsbyPopper, double populationEquality, double repPercent, double demPercent) {
        this.districtingId = districtingId;
        this.polsbyPopper = polsbyPopper;
        this.populationEquality = populationEquality;
        this.repPercent = repPercent;
        this.demPercent = demPercent;
    }

    public String getDistrictingId() {
        return districtingId;
    }

    public void setDistrictingId(String districtingId) {
        this.districtingId = districtingId;
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
}
