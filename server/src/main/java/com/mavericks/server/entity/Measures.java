package com.mavericks.server.entity;

public class Measures {

    private double populationEqualityScore;
    private double polsbyPopperScore;

    public Measures(double populationEqualityScore, double polsbyPopperScore) {
        this.populationEqualityScore = populationEqualityScore;
        this.polsbyPopperScore = polsbyPopperScore;
    }

    public double getPopulationEqualityScore() {
        return populationEqualityScore;
    }

    public void setPopulationEqualityScore(double populationEqualityScore) {
        this.populationEqualityScore = populationEqualityScore;
    }

    public double getPolsbyPopperScore() {
        return polsbyPopperScore;
    }

    public void setPolsbyPopperScore(double polsbyPopperScore) {
        this.polsbyPopperScore = polsbyPopperScore;
    }
}
