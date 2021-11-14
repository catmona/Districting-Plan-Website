package com.mavericks.server.entity;

public class Box {
    private double[] upperExtreme;
    private double[] upperQuartile;
    private double[] median;
    private double[] lowerQuartile;
    private double[] lowerExtreme;

    public Box(double[] upperExtreme, double[] upperQuartile, double[] median, double[] lowerQuartile, double[] lowerExtreme) {
        this.upperExtreme = upperExtreme;
        this.upperQuartile = upperQuartile;
        this.median = median;
        this.lowerQuartile = lowerQuartile;
        this.lowerExtreme = lowerExtreme;
    }
}
