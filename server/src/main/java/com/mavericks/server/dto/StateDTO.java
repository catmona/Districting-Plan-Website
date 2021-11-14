package com.mavericks.server.dto;

import com.mavericks.server.entity.Point;
import com.mavericks.server.entity.Population;
import org.locationtech.jts.geom.Geometry;

public class StateDTO {

    private Integer population;
    private Geometry geometry;
    private Point center;
    private Population demographics;

    public StateDTO(Integer population, Geometry geometry, Point center, Population demographics) {
        this.population = population;
        this.geometry = geometry;
        this.center = center;
        this.demographics = demographics;
    }

    public StateDTO(){

    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public Population getDemographics() {
        return demographics;
    }

    public void setDemographics(Population demographics) {
        this.demographics = demographics;
    }
}
