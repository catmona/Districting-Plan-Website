package com.mavericks.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PopulationsCopy")
public class PopulationCopy {
    @Id
    @Column(name = "regionId", length = 50, nullable = false)
    private String regionId;

    @Column(name = "republicanVotes", nullable = false)
    private double republicanVotes;

    @Column(name = "democraticVotes", nullable = false)
    private double democraticVotes;

    @Column(name = "populations", nullable = false)
    private int populationTotal;

    @Column(name = "white", nullable = false)
    private int white;

    @Column(name = "black", nullable = false)
    private int black;

    @Column(name = "asian", nullable = false)
    private int asian;

    public PopulationCopy() {}

    public PopulationCopy(String regionId, double republicanVotes,
                          double democraticVotes, int populationTotal,
                          int white, int black, int asian) {
        this.regionId = regionId;
        this.republicanVotes = republicanVotes;
        this.democraticVotes = democraticVotes;
        this.populationTotal = populationTotal;
        this.white = white;
        this.black = black;
        this.asian = asian;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public double getRepublicanVotes() {
        return republicanVotes;
    }

    public void setRepublicanVotes(double republicanVotes) {
        this.republicanVotes = republicanVotes;
    }

    public double getDemocraticVotes() {
        return democraticVotes;
    }

    public void setDemocraticVotes(double democraticVotes) {
        this.democraticVotes = democraticVotes;
    }

    public int getPopulationTotal() {
        return populationTotal;
    }

    public void setPopulationTotal(int populationTotal) {
        this.populationTotal = populationTotal;
    }

    public int getWhite() {
        return white;
    }

    public void setWhite(int white) {
        this.white = white;
    }

    public int getBlack() {
        return black;
    }

    public void setBlack(int black) {
        this.black = black;
    }

    public int getAsian() {
        return asian;
    }

    public void setAsian(int asian) {
        this.asian = asian;
    }

    public PopulationCopy clone(){
        PopulationCopy clone = new PopulationCopy(regionId,republicanVotes,democraticVotes,
                populationTotal,white,black,asian);
        return clone;
    }
}
