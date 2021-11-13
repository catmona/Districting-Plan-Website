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

}
