package com.mavericks.server.entity;

import com.mavericks.server.enumeration.Basis;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Boxes")
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "boxWhiskerId", length = 50, nullable = false)
    private String boxWhiskerId;

    @Column(name = "districtNumber", nullable = false)
    private int districtNumber;

    @Column(name = "upperExtreme", nullable = false)
    private int upperExtreme;

    @Column(name = "upperQuartile", nullable = false)
    private int upperQuartile;

    @Column(name = "median", nullable = false)
    private int median;

    @Column(name = "lowerQuartile", nullable = false)
    private int lowerQuartile;

    @Column(name = "lowerExtreme", nullable = false)
    private int lowerExtreme;

    public Box() {}

    public Box(String boxWhiskerId, int districtNumber, int upperExtreme, int upperQuartile, int median, int lowerQuartile, int lowerExtreme) {
        this.boxWhiskerId = boxWhiskerId;
        this.districtNumber= districtNumber;
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

    public String getBoxWhiskerId() {
        return boxWhiskerId;
    }

    public void setBoxWhiskerId(String boxWhiskerId) {
        this.boxWhiskerId = boxWhiskerId;
    }

    public int getDistrictNumber() {
        return districtNumber;
    }

    public void setDistrictNumber(int districtNumber) {
        this.districtNumber = districtNumber;
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
