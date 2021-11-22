package com.mavericks.server.entity;

import com.mavericks.server.enumeration.Demographic;
import com.mavericks.server.enumeration.PopulationMeasure;
import com.mavericks.server.enumeration.Region;

import javax.persistence.*;

@Entity
@Table(name = "Populations")
public class Population {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "regionId", length = 50, nullable = false)
    private String regionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "populationMeasureType", nullable = false)
    private PopulationMeasure populationMeasureType;

    @Enumerated(EnumType.STRING)
    @Column(name = "demographicType", nullable = false)
    private Demographic demographicType;

    @Column(name = "value", nullable = false)
    private int value;

    public Population() {}

    public Population(String regionId, PopulationMeasure populationMeasureType, Demographic demographicType, int value) {
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

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
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
