package com.mavericks.server.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Measures {

    private double populationEquality;



    public  Measures() {}

    public Measures(double populationEquality) {
        this.populationEquality = populationEquality;
    }

    public double getPopulationEquality() {
        return populationEquality;
    }

    public void setPopulationEquality(double populationEquality) {
        this.populationEquality = populationEquality;
    }

    public Measures clone(){
        Measures m = new Measures();
        //m.setPolsbyPopperScore(this.polsbyPopperScore);
        m.setPopulationEquality(this.populationEquality);
        return m;
    }
}
