package com.mavericks.server.entity;

import com.mavericks.server.converter.GeometryConverterString;
import com.mavericks.server.enumeration.Region;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CensusBlocks")
public class CensusBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private long id;

    @Column(name="districtId", nullable=false)
    private long districtId;

    @Column(name="precinctNumber", nullable=false)
    private int precinctNumber;

    @Convert(converter = GeometryConverterString.class)
    @Column(name="geometry")
    private Geometry geometry;

    @Column(name="isBorderBlock", nullable=false)
    private boolean isBorderBlock;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CensusBlockNeighbors", joinColumns = {@JoinColumn(name = "censusBlockId")}, inverseJoinColumns = {@JoinColumn(name = "neighborId")})
    private List<CensusBlock> neighbors;

    public CensusBlock() {}

    public CensusBlock(long districtId, int precinctNumber, boolean isBorderBlock) {
        this.districtId = districtId;
        this.precinctNumber = precinctNumber;
        this.isBorderBlock = isBorderBlock;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(long districtId) {
        this.districtId = districtId;
    }

    public int getPrecinctNumber() {
        return precinctNumber;
    }

    public void setPrecinctNumber(int precinctNumber) {
        this.precinctNumber = precinctNumber;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public boolean isBorderBlock() {
        return isBorderBlock;
    }

    public void setBorderBlock(boolean borderBlock) {
        isBorderBlock = borderBlock;
    }

    public List<CensusBlock> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<CensusBlock> neighbors) {
        this.neighbors = neighbors;
    }

    public Region getRegion() {
        return Region.CENSUS_BLOCK;
    }
}
