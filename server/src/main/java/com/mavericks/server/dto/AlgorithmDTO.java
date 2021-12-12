package com.mavericks.server.dto;

import com.mavericks.server.entity.Measures;
import com.mavericks.server.entity.Population;
import com.mavericks.server.entity.PopulationCopy;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSON;

import java.util.List;

public class AlgorithmDTO {
    private Measures measures;
    private int iterations;
    private boolean isRunning;
    private FeatureCollection geometry;
    private List<PopulationCopy> population;
    private int precinctsChanged;

    public AlgorithmDTO(Measures measures, int iterations, boolean isRunning, FeatureCollection geometry,
                        List<PopulationCopy> population,int precinctsChanged) {
        this.measures = measures;
        this.iterations = iterations;
        this.isRunning = isRunning;
        this.geometry=geometry;
        this.population=population;
        this.precinctsChanged=precinctsChanged;
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

    public List<PopulationCopy> getPopulation() {
        return population;
    }

    public void setPopulation(List<PopulationCopy> population) {
        this.population = population;
    }

    public int getPrecinctsChanged() {
        return precinctsChanged;
    }

    public void setPrecinctsChanged(int precinctsChanged) {
        this.precinctsChanged = precinctsChanged;
    }
}
