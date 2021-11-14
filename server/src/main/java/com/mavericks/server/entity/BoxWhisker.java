package com.mavericks.server.entity;

import java.util.List;

public class BoxWhisker {

    private List<Box> boxes;

    public BoxWhisker(List<Box> boxes) {
        this.boxes = boxes;
    }

    public Box getBox(int districtNum){
        return boxes.get(districtNum);
    }

}
