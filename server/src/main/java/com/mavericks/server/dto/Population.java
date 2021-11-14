package com.mavericks.server.dto;

import com.mavericks.server.entity.Demographic;
import com.mavericks.server.entity.PopulationMeasure;

import java.util.List;

public class Population {

    // Demographics[PopulationMeasure[]]
    private List<List<Integer>> populations;

    public Integer getPopulations(PopulationMeasure popMeasure, Demographic demg) {
        return populations.get(popMeasure.ordinal()).get(demg.ordinal());
    }

    public List<Integer> getDemographic(PopulationMeasure popMeasure){
        return populations.get(popMeasure.ordinal());
    }

}
