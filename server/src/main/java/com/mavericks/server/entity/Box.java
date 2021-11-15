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

    public double[] getUpperExtreme() {
        return upperExtreme;
    }

    public void setUpperExtreme(double[] upperExtreme) {
        this.upperExtreme = upperExtreme;
    }

    public double[] getUpperQuartile() {
        return upperQuartile;
    }

    public void setUpperQuartile(double[] upperQuartile) {
        this.upperQuartile = upperQuartile;
    }

    public double[] getMedian() {
        return median;
    }

    public void setMedian(double[] median) {
        this.median = median;
    }

    public double[] getLowerQuartile() {
        return lowerQuartile;
    }

    public void setLowerQuartile(double[] lowerQuartile) {
        this.lowerQuartile = lowerQuartile;
    }

    public double[] getLowerExtreme() {
        return lowerExtreme;
    }

    public void setLowerExtreme(double[] lowerExtreme) {
        this.lowerExtreme = lowerExtreme;
    }
}
