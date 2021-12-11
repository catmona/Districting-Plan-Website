package com.mavericks.server.dto;

import com.mavericks.server.entity.BoxWhisker;
import com.mavericks.server.entity.Population;
import com.mavericks.server.entity.PopulationCopy;

import java.util.ArrayList;
import java.util.List;

public class BoxWhiskerPlotDTO {
    private BoxWhisker boxWhisker;
    private List<PopulationCopy> enactedPoints = new ArrayList<>();
    private List<PopulationCopy> selectedPoints = new ArrayList<>();
    private List<PopulationCopy> equalizedPoints = new ArrayList<>();

    public BoxWhiskerPlotDTO() {}

    public BoxWhiskerPlotDTO(BoxWhisker boxWhisker, List<PopulationCopy> enactedPoints, List<PopulationCopy> selectedPoints, List<PopulationCopy> equalizedPoints) {
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

    public List<PopulationCopy> getEnactedPoints() {
        return enactedPoints;
    }

    public void setEnactedPoints(List<PopulationCopy> enactedPoints) {
        this.enactedPoints = enactedPoints;
    }

    public List<PopulationCopy> getSelectedPoints() {
        return selectedPoints;
    }

    public void setSelectedPoints(List<PopulationCopy> selectedPoints) {
        this.selectedPoints = selectedPoints;
    }

    public List<PopulationCopy> getEqualizedPoints() {
        return equalizedPoints;
    }

    public void setEqualizedPoints(List<PopulationCopy> equalizedPoints) {
        this.equalizedPoints = equalizedPoints;
    }
}
