package com.mavericks.server.dto;

import com.mavericks.server.entity.*;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.FeatureCollection;

import java.util.List;

public class StateDTO {

    private Point center;
    private List<Population> districtPopulations;
    private Election elections;
    private FeatureCollection featureCollection;

    public StateDTO(Point center, FeatureCollection collection,
                    List<Population> districtPopulations, Election elections) {
        this.center = center;
        this.featureCollection= collection;
        this.districtPopulations=districtPopulations;
        this.elections = elections;
    }

    public StateDTO(){

    }

    public List<Population> getDistrictPopulations() {
        return districtPopulations;
    }

    public void setDistrictPopulations(List<Population> districtPopulations) {
        this.districtPopulations = districtPopulations;
    }

    public FeatureCollection getFeatureCollection() {
        return featureCollection;
    }

    public void setFeatureCollection(FeatureCollection featureCollection) {
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
