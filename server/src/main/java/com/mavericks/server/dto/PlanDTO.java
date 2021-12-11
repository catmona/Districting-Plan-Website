package com.mavericks.server.dto;

import com.mavericks.server.entity.Election;
import com.mavericks.server.entity.PopulationCopy;
import org.wololo.geojson.FeatureCollection;

import java.util.List;

public class PlanDTO {
    private List<PopulationCopy> districtPopulations;
    private FeatureCollection featureCollection;

    public PlanDTO() {}

    public PlanDTO(List<PopulationCopy> districtPopulations, FeatureCollection featureCollection) {
        this.districtPopulations = districtPopulations;
        this.featureCollection = featureCollection;
    }

    public List<PopulationCopy> getDistrictPopulations() {
        return districtPopulations;
    }

    public void setDistrictPopulations(List<PopulationCopy> districtPopulations) {
        this.districtPopulations = districtPopulations;
    }

    public FeatureCollection getFeatureCollection() {
        return featureCollection;
    }

    public void setFeatureCollection(FeatureCollection featureCollection) {
        this.featureCollection = featureCollection;
    }
}
