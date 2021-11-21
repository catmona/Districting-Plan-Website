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
    @Column(name="id", nullable=false)
    private long id;

    @Column(name="districtId", nullable=false)
    private long districtId;

    @Column(name="precinctNumber", nullable=false)
    private int precinctNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "geometryId", referencedColumnName = "id")
    private CensusBlockGeometry cbGeometry;

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

    public CensusBlockGeometry getCbGeometry() {
        return cbGeometry;
    }

    public void setCbGeometry(CensusBlockGeometry cbGeometry) {
        this.cbGeometry = cbGeometry;
    }

    public Geometry getGeometry() {
        return cbGeometry.getGeometry();
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
