package com.mavericks.server.dto;

import com.mavericks.server.entity.Measures;
import com.mavericks.server.entity.Population;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSON;

public class AlgorithmDTO {
    private Measures measures;
    private int iterations;
    private boolean isRunning;
    private FeatureCollection geometry;
    private Population population;

    public AlgorithmDTO(Measures measures, int iterations, boolean isRunning, FeatureCollection geometry, Population population) {
        this.measures = measures;
        this.iterations = iterations;
        this.isRunning = isRunning;
        this.geometry=geometry;
        this.population=population;
    }

    public Measures getMeasures() {
        return measures;
    }

    public void setMeasures(Measures measures) {
        this.measures = measures;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public FeatureCollection getGeometry() {
        return geometry;
    }

    public void setGeometry(FeatureCollection geometry) {
        this.geometry = geometry;
    }
}
