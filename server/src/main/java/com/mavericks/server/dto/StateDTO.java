package com.mavericks.server.dto;

import com.mavericks.server.entity.*;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.FeatureCollection;

import java.util.List;

public class StateDTO {

    private String enactedId;
    private Point center;
    private List<List<Integer>> districtPopulations;
    private List<Election> districtElections;
    private String featureCollection;

    public StateDTO() {}

    public StateDTO(String enactedId, Point center, String collection,
                    List<List<Integer>> districtPopulations, List<Election> districtElections) {
        this.enactedId = enactedId;
        this.center = center;
        this.featureCollection = collection;
        this.districtPopulations = districtPopulations;
        this.districtElections = districtElections;
    }

    public String getEnactedId() {
        return enactedId;
    }

    public void setEnactedId(String enactedId) {
        this.enactedId = enactedId;
    }

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
