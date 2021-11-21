package com.mavericks.server.entity;

import com.mavericks.server.SetCustom;
import com.mavericks.server.converter.GeometryConverterString;
import com.mavericks.server.enumeration.Basis;
import com.mavericks.server.enumeration.Region;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.Feature;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "Districts")
public class District {
    @Id
    @Column(name="id", nullable=false)
    private long id;

    @Column(name="districtingId", nullable=false)
    private long districtingId;

    @Column(name="number", nullable=false)
    private int number;

    @Convert(converter = GeometryConverterString.class)
    @Column(name="geometry")
    private Geometry geometry;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtId")
    private List<DistrictElection> electionData;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtId")
    private List<BoxWhisker> boxWhiskers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "DistrictNeighbors", joinColumns = {@JoinColumn(name = "districtId")}, inverseJoinColumns = {@JoinColumn(name = "neighborId")})
    private List<District> neighbors;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtId")
    private List<CensusBlock> censusBlocks;

    public District() {}

    public District(long districtingId, int number) {
        this.districtingId = districtingId;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public long getDistrictingId() {
        return districtingId;
    }

    public void setDistrictingId(long districtingId) {
        this.districtingId = districtingId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<CensusBlock> getCensusBlocks() {
        return censusBlocks;
    }

    public void setCensusBlocks(List<CensusBlock> censusBlocks) {
        this.censusBlocks = censusBlocks;
    }

    public List<DistrictElection> getElectionData() {
        return electionData;
    }

    public void setElectionData(List<DistrictElection> electionData) {
        this.electionData = electionData;
    }

    public List<BoxWhisker> getBoxWhiskers() {
        return boxWhiskers;
    }

    public void setBoxWhiskers(List<BoxWhisker> boxWhiskers) {
        this.boxWhiskers = boxWhiskers;
    }

    public List<District> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<District> neighbors) {
        this.neighbors = neighbors;
    }

    public Region getRegion() {
        return Region.DISTRICT;
    }

    /* Other class methods below */

    public DistrictElection getElectionDataByElection(long electionId) {
        Optional<DistrictElection> data = electionData.stream().filter(e -> e.getElectionId() == electionId).findFirst();
        return data.isPresent() ? data.get() : null;
    }

    public BoxWhisker getBoxWhiskersByBasis(Basis basis) {
        Optional<BoxWhisker> box = boxWhiskers.stream().filter(bw -> bw.getBasisType() == basis).findFirst();
        return box.isPresent() ? box.get() : null;
    }

    public District getRandomNeighbor(){
        return neighbors.get((int)(Math.random()*neighbors.size()));
    }

    public void removeCensusBlock(CensusBlock cb){

    }

    public void addCensusBlock(CensusBlock cb){

    }
}
