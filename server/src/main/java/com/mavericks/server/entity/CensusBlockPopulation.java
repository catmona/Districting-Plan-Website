package com.mavericks.server.entity;

import javax.persistence.*;

@Entity
@Table(name = "CensusBlockPopulations")
public class CensusBlockPopulation {
    @EmbeddedId
    private CBPopKey key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "censusBlockId", insertable = false, updatable = false)
    private CensusBlock censusBlock;

    @Enumerated(EnumType.STRING)
    private PopulationMeasure populationMeasure;

    @Enumerated(EnumType.STRING)
    private Demographic demographic;

    public CensusBlockPopulation() {}
    public CensusBlockPopulation(CBPopKey key, CensusBlock censusBlock, PopulationMeasure populationMeasure, Demographic demographic) {
        this.key = key;
        this.censusBlock = censusBlock;
        this.populationMeasure = populationMeasure;
        this.demographic = demographic;
    }

    public CBPopKey getKey() {
        return key;
    }

    public void setKey(CBPopKey key) {
        this.key = key;
    }

    public CensusBlock getCensusBlock() {
        return censusBlock;
    }

    public void setCensusBlock(CensusBlock censusBlock) {
        this.censusBlock = censusBlock;
    }

    public PopulationMeasure getPopulationMeasure() {
        return populationMeasure;
    }

    public void setPopulationMeasure(PopulationMeasure populationMeasure) {
        this.populationMeasure = populationMeasure;
    }

    public Demographic getDemographic() {
        return demographic;
    }

    public void setDemographic(Demographic demographic) {
        this.demographic = demographic;
    }
}