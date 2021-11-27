package com.mavericks.server.dto;

import com.mavericks.server.entity.*;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.FeatureCollection;

import java.util.List;

public class StateDTO {

    private Point center;
    private List<List<Integer>> districtPopulations;
    private List<Election> districtElections;
    private String featureCollection;

    public StateDTO(Point center, String collection,
                    List<List<Integer>> districtPopulations, List<Election> districtElections) {
        this.center = center;
        this.featureCollection = collection;
        this.districtPopulations = districtPopulations;
        this.districtElections = districtElections;
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

    public List<Election> getDistrictElections() {
        return districtElections;
    }

    public void setDistrictElections(List<Election> districtElections) {
        this.districtElections = districtElections;
    }
}
