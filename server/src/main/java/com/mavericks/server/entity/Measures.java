package com.mavericks.server.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Measures {

    private double populationEqualityScore;



    public  Measures() {}

    public Measures(double populationEqualityScore) {
        this.populationEqualityScore = populationEqualityScore;
    }

    public double getPopulationEqualityScore() {
        return populationEqualityScore;
    }

    public void setPopulationEqualityScore(double populationEqualityScore) {
        this.populationEqualityScore = populationEqualityScore;
    }

    public Measures clone(){
        Measures m = new Measures();
        //m.setPolsbyPopperScore(this.polsbyPopperScore);
        m.setPopulationEqualityScore(this.populationEqualityScore);
        return m;
    }
}
