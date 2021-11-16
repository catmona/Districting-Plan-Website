package com.mavericks.server.entity;

import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.Feature;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Districts")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int districtNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtingId")
    private Districting districtingPlan;
    @Transient
    private Geometry geometry;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "DistrictNeighbors", joinColumns = {@JoinColumn(name = "districtId")}, inverseJoinColumns = {@JoinColumn(name = "neighborId")})
    private List<District> neighbors; // should be a Set or Hashset type to reduce overhead
    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
    private List<Precinct> precincts;

    @Transient
    private List<CensusBlock> blocks;
    @Transient
    private Measures measures;

    @Transient
    private Population population;

    public District() {}

    public District(long id, Districting districtingPlan, Geometry geometry) {
        this.id = id;
        this.districtingPlan = districtingPlan;
        this.geometry = geometry;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Districting getDistrictingPlan() {
        return districtingPlan;
    }
    public void setDistrictingPlan(Districting districtingPlan) {
        this.districtingPlan = districtingPlan;
    }

    public Geometry getGeometry() {
        return geometry;
    }
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public District getRandNeighbor(){
        return neighbors.get((int)(Math.random()*neighbors.size()));
    }

    /*
    public CensusBlock getRandCensusBlock(){
        return borderBlocks.get((int)(Math.random()*borderBlocks.size()));
    }*/

    public Population computePopulation() {
        // get population by aggregating the plan's District populations
        Population res = new Population();
        for (Precinct p: precincts) {
            res.combinePopulations(p.getPopulation());
        }
        return res;
    }

    public Population getPopulation(){
        return this.population;
    }



    public void removeCensusBlock(CensusBlock cb){

    }

    public void addCensusBlock(CensusBlock cb){

    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public List<CensusBlock> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<CensusBlock> blocks) {
        this.blocks = blocks;
    }

    public Measures getMeasures() {
        return measures;
    }

    public void setMeasures(Measures measures) {
        this.measures = measures;
    }

    public CensusBlock getRandCensusBlock(){
        return blocks.get((int)(Math.random()*blocks.size()));
    }

    public int getDistrictNumber() {
        return districtNumber;
    }

    public void setDistrictNumber(int districtNumber) {
        this.districtNumber = districtNumber;
    }
}
