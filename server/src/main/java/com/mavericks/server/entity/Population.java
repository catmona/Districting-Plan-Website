package com.mavericks.server.entity;

import com.mavericks.server.enumeration.Demographic;
import com.mavericks.server.enumeration.PopulationMeasure;
import com.mavericks.server.enumeration.Region;

import javax.persistence.*;

@Entity
@Table(name = "Population")
public class Population {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private long id;

    @Column(name="regionType", nullable=false)
    private Region regionType;

    @Column(name="regionId", nullable=false)
    private long regionId;

    @Column(name="populationMeasureType", nullable=false)
    private PopulationMeasure populationMeasureType;

    @Column(name="demographicType", nullable=false)
    private Demographic demographicType;

    @Column(name="value", nullable=false)
    private int value;

    public Population() {}

    public Population(Region regionType, long regionId, PopulationMeasure populationMeasureType, Demographic demographicType, int value) {
        this.regionType = regionType;
        this.regionId = regionId;
        this.populationMeasureType = populationMeasureType;
        this.demographicType = demographicType;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Region getRegionType() {
        return regionType;
    }

    public void setRegionType(Region regionType) {
        this.regionType = regionType;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public PopulationMeasure getPopulationMeasureType() {
        return populationMeasureType;
    }

    public void setPopulationMeasureType(PopulationMeasure populationMeasureType) {
        this.populationMeasureType = populationMeasureType;
    }

    public Demographic getDemographicType() {
        return demographicType;
    }

    public void setDemographicType(Demographic demographicType) {
        this.demographicType = demographicType;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
