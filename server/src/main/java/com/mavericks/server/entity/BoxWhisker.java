package com.mavericks.server.entity;

import com.mavericks.server.enumeration.Basis;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BoxWhisker")
public class BoxWhisker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private long id;

    @Column(name="districtId", nullable=false)
    private long districtId;

    @Column(name="basisType", nullable=false)
    private Basis basisType;

    @Column(name="upperExtreme", nullable=false)
    private int upperExtreme;

    @Column(name="upperQuartile", nullable=false)
    private int upperQuartile;

    @Column(name="median", nullable=false)
    private int median;

    @Column(name="lowerQuartile", nullable=false)
    private int lowerQuartile;

    @Column(name="lowerExtreme", nullable=false)
    private int lowerExtreme;

    public BoxWhisker() {}

    public BoxWhisker(long districtId, Basis basisType, int upperExtreme, int upperQuartile, int median, int lowerQuartile, int lowerExtreme) {
        this.districtId = districtId;
        this.basisType = basisType;
        this.upperExtreme = upperExtreme;
        this.upperQuartile = upperQuartile;
        this.median = median;
        this.lowerQuartile = lowerQuartile;
        this.lowerExtreme = lowerExtreme;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(long districtId) {
        this.districtId = districtId;
    }

    public Basis getBasisType() {
        return basisType;
    }

    public void setBasisType(Basis basisType) {
        this.basisType = basisType;
    }

    public int getUpperExtreme() {
        return upperExtreme;
    }

    public void setUpperExtreme(int upperExtreme) {
        this.upperExtreme = upperExtreme;
    }

    public int getUpperQuartile() {
        return upperQuartile;
    }

    public void setUpperQuartile(int upperQuartile) {
        this.upperQuartile = upperQuartile;
    }

    public int getMedian() {
        return median;
    }

    public void setMedian(int median) {
        this.median = median;
    }

    public int getLowerQuartile() {
        return lowerQuartile;
    }

    public void setLowerQuartile(int lowerQuartile) {
        this.lowerQuartile = lowerQuartile;
    }

    public int getLowerExtreme() {
        return lowerExtreme;
    }

    public void setLowerExtreme(int lowerExtreme) {
        this.lowerExtreme = lowerExtreme;
    }
}
