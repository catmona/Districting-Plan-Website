package com.mavericks.server.dto;

import com.mavericks.server.entity.*;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.FeatureCollection;

import java.util.List;

public class StateDTO {

    private String enactedId;
    private Point center;
    private List<PopulationCopy> districtPopulations;
    private FeatureCollection featureCollection;
    private double populationEquality;
    private double polsbyPopper;
    private int majorityMinority;
    private int splitCounty;
    private double efficiencyGap;

    public StateDTO() {}

    public StateDTO(String enactedId, Point center, List<PopulationCopy> districtPopulations, FeatureCollection featureCollection, double populationEquality, double polsbyPopper, int majorityMinority, int splitCounty, double efficiencyGap) {
        this.enactedId = enactedId;
        this.center = center;
        this.districtPopulations = districtPopulations;
        this.featureCollection = featureCollection;
        this.populationEquality = populationEquality;
        this.polsbyPopper = polsbyPopper;
        this.majorityMinority = majorityMinority;
        this.splitCounty = splitCounty;
        this.efficiencyGap = efficiencyGap;
    }

    public String getEnactedId() {
        return enactedId;
    }

    public void setEnactedId(String enactedId) {
        this.enactedId = enactedId;
    }

    public List<PopulationCopy> getDistrictPopulations() {
        return districtPopulations;
    }

    public void setDistrictPopulations(List<PopulationCopy> districtPopulations) {
        this.districtPopulations = districtPopulations;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public FeatureCollection getFeatureCollection() {
        return featureCollection;
    }

    public void setFeatureCollection(FeatureCollection featureCollection) {
        this.featureCollection = featureCollection;
    }

    public double getPopulationEquality() {
        return populationEquality;
    }

    public void setPopulationEquality(double populationEquality) {
        this.populationEquality = populationEquality;
    }

    public double getPolsbyPopper() {
        return polsbyPopper;
    }

    public void setPolsbyPopper(double polsbyPopper) {
        this.polsbyPopper = polsbyPopper;
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

    public double getEfficiencyGap() {
        return efficiencyGap;
    }

    public void setEfficiencyGap(double efficiencyGap) {
        this.efficiencyGap = efficiencyGap;
    }
}
