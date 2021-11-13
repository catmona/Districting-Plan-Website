package com.mavericks.server.entity;

import org.locationtech.jts.geom.Geometry;

import java.util.List;

public class District {

    private long districtId;
    private List<District> neighbors;
    private List<CensusBlock> borderBlocks;
    private List<CensusBlock> innerBlocks;
    private Measures measures;
    private Geometry geometry;

    public District getRandNeighbor(){
        return neighbors.get((int)(Math.random()*neighbors.size()));
    }

    public CensusBlock getRandCensusBlock(){
        return borderBlocks.get((int)(Math.random()*borderBlocks.size()));
    }

    public void removeCensusBlock(CensusBlock cb){

    }

    public void addCensusBlock(){

    }






}
