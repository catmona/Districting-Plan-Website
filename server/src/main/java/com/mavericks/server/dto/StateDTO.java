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

    public StateDTO() {}

    public StateDTO(String enactedId, Point center,
                    List<PopulationCopy> districtPopulations, FeatureCollection
                            featureCollection) {
        this.enactedId = enactedId;
        this.center = center;
        this.districtPopulations = districtPopulations;
        this.featureCollection = featureCollection;
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
}
