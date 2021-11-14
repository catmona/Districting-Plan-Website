package com.mavericks.server.entity;

import com.mavericks.server.entity.Demographic;
import com.mavericks.server.entity.PopulationMeasure;

import java.util.ArrayList;
import java.util.List;

public class Population {

    // PopulationMeasure[Demographics[]]
    private List<List<Integer>> populations;

    public Population() {
        // instantiate empty populations var
        for (int i = 0; i < PopulationMeasure.values().length; i++) {
            List<Integer> popMeasure = new ArrayList<Integer>();
            for (int j = 0; j < Demographic.values().length; j++) {
                popMeasure.add(j, 0);
            }
            populations.set(i, popMeasure);
        }

    }

    public Integer getPopulation(PopulationMeasure popMeasure, Demographic demo) {
        return populations.get(popMeasure.ordinal()).get(demo.ordinal());
    }

    public void setPopulation(PopulationMeasure popMeasure, Demographic demo, int value) {
        populations.get(popMeasure.ordinal()).set(demo.ordinal(), value);
    }

    public void combinePopulations(Population p1) {
        PopulationMeasure popMeasureEnum;
        Demographic demoEnum;
        for (int i = 0; i < PopulationMeasure.values().length; i++) {
            for (int j = 0; j < Demographic.values().length; j++) {
                popMeasureEnum = PopulationMeasure.values()[i];
                demoEnum = Demographic.values()[j];
                this.setPopulation(popMeasureEnum, demoEnum,
                        this.getPopulation(popMeasureEnum, demoEnum) + p1.getPopulation(popMeasureEnum, demoEnum));
            }
        }
    }

    // made this in case
    public static Population combinePopulations(Population p1, Population p2) {
        Population sumPop = new Population();
        PopulationMeasure popMeasureEnum;
        Demographic demoEnum;
        for (int i = 0; i < PopulationMeasure.values().length; i++) {
            for (int j = 0; j < Demographic.values().length; j++) {
                popMeasureEnum = PopulationMeasure.values()[i];
                demoEnum = Demographic.values()[j];
                sumPop.setPopulation(popMeasureEnum, demoEnum,
                        p1.getPopulation(popMeasureEnum, demoEnum) + p2.getPopulation(popMeasureEnum, demoEnum));
            }
        }

        return sumPop;
    }

}
