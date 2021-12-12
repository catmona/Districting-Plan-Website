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
    private double upperExtreme;

    @Column(name = "upperQuartile", nullable = false)
    private double upperQuartile;

    @Column(name = "median", nullable = false)
    private double median;

    @Column(name = "lowerQuartile", nullable = false)
    private double lowerQuartile;

    @Column(name = "lowerExtreme", nullable = false)
    private double lowerExtreme;

    public Box() {}

    public Box(long id, String boxWhiskerId, int districtNumber, double upperExtreme, double upperQuartile, double median, double lowerQuartile, double lowerExtreme) {
        this.id = id;
        this.boxWhiskerId = boxWhiskerId;
        this.districtNumber = districtNumber;
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

    public double getUpperExtreme() {
        return upperExtreme;
    }

    public void setUpperExtreme(double upperExtreme) {
        this.upperExtreme = upperExtreme;
    }

    public double getUpperQuartile() {
        return upperQuartile;
    }

    public void setUpperQuartile(double upperQuartile) {
        this.upperQuartile = upperQuartile;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public double getLowerQuartile() {
        return lowerQuartile;
    }

    public void setLowerQuartile(double lowerQuartile) {
        this.lowerQuartile = lowerQuartile;
    }

    public double getLowerExtreme() {
        return lowerExtreme;
    }

    public void setLowerExtreme(double lowerExtreme) {
        this.lowerExtreme = lowerExtreme;
    }
}
