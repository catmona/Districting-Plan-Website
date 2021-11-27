package com.mavericks.server.dto;

import com.mavericks.server.entity.Election;

public class DistrictingDTO {

    private String districtingId;
    private double polsbyPopper;
    private double populationEquality;

    public DistrictingDTO(String districtingId, double polsbyPopper, double populationEquality) {
        this.districtingId = districtingId;
        this.polsbyPopper = polsbyPopper;
        this.populationEquality = populationEquality;
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
}
