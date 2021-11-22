package com.mavericks.server.dto;

import com.mavericks.server.entity.*;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.FeatureCollection;

import java.util.List;

public class StateDTO {

    private Point center;
    private List<List<Integer>> districtPopulations;
    private Election elections;
    private String featureCollection;

    public StateDTO(Point center, String collection,
                    List<List<Integer>> districtPopulations, Election elections) {
        this.center = center;
        this.featureCollection = collection;
        this.districtPopulations = districtPopulations;
        this.elections = elections;
    }

    public StateDTO(){}

    public List<List<Integer>> getDistrictPopulations() {
        return districtPopulations;
    }

    public void setDistrictPopulations(List<List<Integer>> districtPopulations) {
        this.districtPopulations = districtPopulations;
    }

    public String getFeatureCollection() {
        return featureCollection;
    }

    public void setFeatureCollection(String featureCollection) {
        this.featureCollection = featureCollection;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Election getElections() {
        return elections;
    }

    public void setElections(Election elections) {
        this.elections = elections;
    }
}
