package com.mavericks.server.dto;

import com.mavericks.server.entity.Measures;
import com.mavericks.server.entity.Population;
import com.mavericks.server.entity.PopulationCopy;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSON;

import java.util.List;

public class AlgorithmDTO {
    private double populationEquality;
    private int iterations;
    private boolean isRunning;
    private FeatureCollection featureCollection;
    private List<PopulationCopy> districtPopulations;
    private int precinctsChanged;
    private double polsbyPopper;
    private double efficiencyGap;
    private double majorityMinority;
    private double splitCounty;

    public AlgorithmDTO(double populationEquality, int iterations, boolean isRunning, FeatureCollection geometry,
                        List<PopulationCopy> population,int precinctsChanged) {
        this.populationEquality =populationEquality;
        this.iterations = iterations;
        this.isRunning = isRunning;
        this.featureCollection=geometry;
        this.districtPopulations=population;
        this.precinctsChanged=precinctsChanged;
    }

    public double getPopulationEquality() {
        return populationEquality;
    }

    public void setPopulationEquality(double populationEquality) {
        this.populationEquality = populationEquality;
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

    public double getPolsbyPopper() {
        return polsbyPopper;
    }

    public void setPolsbyPopper(double polsbyPopper) {
        this.polsbyPopper = polsbyPopper;
    }

    public double getEfficiencyGap() {
        return efficiencyGap;
    }

    public void setEfficiencyGap(double efficiencyGap) {
        this.efficiencyGap = efficiencyGap;
    }

    public double getMajorityMinority() {
        return majorityMinority;
    }

    public void setMajorityMinority(double majorityMinority) {
        this.majorityMinority = majorityMinority;
    }

    public double getSplitCounty() {
        return splitCounty;
    }

    public void setSplitCounty(double splitCounty) {
        this.splitCounty = splitCounty;
    }
}
