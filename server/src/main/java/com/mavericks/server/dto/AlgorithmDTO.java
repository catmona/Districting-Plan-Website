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
    private FeatureCollection featureCollection;
    private List<PopulationCopy> districtPopulations;
    private int precinctsChanged;

    public AlgorithmDTO(Measures measures, int iterations, boolean isRunning, FeatureCollection geometry,
                        List<PopulationCopy> population,int precinctsChanged) {
        this.measures =measures;
        this.iterations = iterations;
        this.isRunning = isRunning;
        this.featureCollection=geometry;
        this.districtPopulations=population;
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

    public FeatureCollection getFeatureCollection() {
        return featureCollection;
    }

    public void setFeatureCollection(FeatureCollection featureCollection) {
        this.featureCollection = featureCollection;
    }

    public List<PopulationCopy> getDistrictPopulations() {
        return districtPopulations;
    }

    public void setDistrictPopulations(List<PopulationCopy> districtPopulations) {
        this.districtPopulations = districtPopulations;
    }

    public int getPrecinctsChanged() {
        return precinctsChanged;
    }

    public void setPrecinctsChanged(int precinctsChanged) {
        this.precinctsChanged = precinctsChanged;
    }
}
