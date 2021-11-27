package com.mavericks.server.dto;

import com.mavericks.server.entity.BoxWhisker;
import com.mavericks.server.entity.Population;

import java.util.ArrayList;
import java.util.List;

public class BoxWhiskerPlotDTO {
    private BoxWhisker boxWhisker;
    private List<Integer> enactedPoints = new ArrayList<>();
    private List<Integer> selectedPoints = new ArrayList<>();
    private List<Integer> equalizedPoints = new ArrayList<>();

    public BoxWhiskerPlotDTO() {}

    public BoxWhiskerPlotDTO(BoxWhisker boxWhisker, List<Integer> enactedPoints, List<Integer> selectedPoints, List<Integer> equalizedPoints) {
        this.boxWhisker = boxWhisker;
        this.enactedPoints = enactedPoints;
        this.selectedPoints = selectedPoints;
        this.equalizedPoints = equalizedPoints;
    }

    public BoxWhisker getBoxWhisker() {
        return boxWhisker;
    }

    public void setBoxWhisker(BoxWhisker boxWhisker) {
        this.boxWhisker = boxWhisker;
    }

    public List<Integer> getEnactedPoints() {
        return enactedPoints;
    }

    public void setEnactedPoints(List<Integer> enactedPoints) {
        this.enactedPoints = enactedPoints;
    }

    public List<Integer> getSelectedPoints() {
        return selectedPoints;
    }

    public void setSelectedPoints(List<Integer> selectedPoints) {
        this.selectedPoints = selectedPoints;
    }

    public List<Integer> getEqualizedPoints() {
        return equalizedPoints;
    }

    public void setEqualizedPoints(List<Integer> equalizedPoints) {
        this.equalizedPoints = equalizedPoints;
    }
}
