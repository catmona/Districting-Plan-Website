package com.mavericks.server.entity;

import com.mavericks.server.SetCustom;
import com.mavericks.server.converter.GeometryConverterString;
import com.mavericks.server.converter.ObjectConverterJson;
import com.mavericks.server.enumeration.Region;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "CensusBlocks")
public class CensusBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private long id;

    @Column(name="precinctId", nullable=false)
    private long precinctId;

    @Convert(converter = GeometryConverterString.class)
    @Column(name="geometry")
    private Geometry geometry;

    @Column(name="isBorderBlock", nullable=false)
    private boolean isBorderBlock;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "CensusBlockNeighbors", joinColumns = {@JoinColumn(name = "censusBlockId")}, inverseJoinColumns = {@JoinColumn(name = "neighborId")})
    private List<CensusBlock> neighbors;

    public CensusBlock() {}

    public CensusBlock(long precinctId, boolean isBorderBlock) {
        this.precinctId = precinctId;
        this.isBorderBlock = isBorderBlock;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPrecinctId() {
        return precinctId;
    }

    public void setPrecinctId(long precinctId) {
        this.precinctId = precinctId;
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
