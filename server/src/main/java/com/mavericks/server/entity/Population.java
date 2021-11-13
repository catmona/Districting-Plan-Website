package com.mavericks.server.entity;

import java.util.List;

public class Population {

    private List<List<Integer>> populations;



    public Integer getPopulations(PopulationMeasure popMeasure, Demographic demg) {
        return populations.get(popMeasure.ordinal()).get(demg.ordinal());
    }

}
