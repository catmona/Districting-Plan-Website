package com.mavericks.server.entity;

import java.util.List;

public class BoxWhisker {

    //each box is a demographic
    private List<Box> boxes;

    public BoxWhisker(List<Box> boxes) {
        this.boxes = boxes;
    }

    public Box getBox(int districtNum){
        return boxes.get(districtNum);
    }

    public void addBox(Box box){
        boxes.add(box);
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
}
