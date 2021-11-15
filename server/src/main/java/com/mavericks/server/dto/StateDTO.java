package com.mavericks.server.dto;

import com.mavericks.server.entity.Demographic;
import com.mavericks.server.entity.Point;
import com.mavericks.server.entity.Population;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.FeatureCollection;

public class StateDTO {

    private Integer population;
    private Point center;
    private FeatureCollection featureCollection;

    public StateDTO(Integer population, Point center, FeatureCollection collection) {
        this.population = population;
        this.center = center;
        this.featureCollection= collection;
    }

    public StateDTO(){

    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
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

}
