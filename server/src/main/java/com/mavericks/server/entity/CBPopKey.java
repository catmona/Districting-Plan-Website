package com.mavericks.server.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class CBPopKey implements Serializable {
    @Column(name = "censusBlockId")
    private long censusBlockId;

    @Enumerated(EnumType.STRING)
    @Column(name = "popMeasureEnum")
    private PopulationMeasure popMeasureEnum;

    @Enumerated(EnumType.STRING)
    @Column(name = "demographicEnum")
    private Demographic demographicEnum;

    public CBPopKey() {}
    public CBPopKey(long censusBlockId, PopulationMeasure popMeasureEnum, Demographic demographicEnum) {
        this.censusBlockId = censusBlockId;
        this.popMeasureEnum = popMeasureEnum;
        this.demographicEnum = demographicEnum;
    }

    public long getCensusBlockId() {
        return censusBlockId;
    }

    public void setCensusBlockId(long censusBlockId) {
        this.censusBlockId = censusBlockId;
    }

    public PopulationMeasure getPopMeasureEnum() {
        return popMeasureEnum;
    }

    public void setPopMeasureEnum(PopulationMeasure popMeasureEnum) {
        this.popMeasureEnum = popMeasureEnum;
    }

    public Demographic getDemographicEnum() {
        return demographicEnum;
    }

    public void setDemographicEnum(Demographic demographicEnum) {
        this.demographicEnum = demographicEnum;
    }
}