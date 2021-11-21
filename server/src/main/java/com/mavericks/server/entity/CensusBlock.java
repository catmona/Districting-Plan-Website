package com.mavericks.server.entity;

import com.mavericks.server.enumeration.Region;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CensusBlocks")
public class CensusBlock {
    @Id
    @Column(name = "id", length = 50, nullable = false)
    private String id;

    @Column(name = "districtId", length = 50, nullable = false)
    private String districtId;

    @Column(name = "precinctId", length = 50, nullable = false)
    private String precinctId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "geometryId", referencedColumnName = "id")
    private CensusBlockGeometry cbGeometry;

    @Column(name = "isBorderBlock", nullable = false)
    private boolean isBorderBlock;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CensusBlockNeighbors", joinColumns = {@JoinColumn(name = "censusBlockId")}, inverseJoinColumns = {@JoinColumn(name = "neighborId")})
    private List<CensusBlock> neighbors;

    public CensusBlock() {}

    public CensusBlock(String districtId, String precinctId, boolean isBorderBlock) {
        this.districtId = districtId;
        this.precinctId = precinctId;
        this.isBorderBlock = isBorderBlock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getPrecinctId() {
        return precinctId;
    }

    public void setPrecinctId(String precinctId) {
        this.precinctId = precinctId;
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
