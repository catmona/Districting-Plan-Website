package com.mavericks.server.entity;

import com.mavericks.server.enumeration.Demographic;
import com.mavericks.server.enumeration.PopulationMeasure;
import com.mavericks.server.enumeration.Region;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geometryId", referencedColumnName = "id")
    private CensusBlockGeometry cbGeometry;

    @Column(name = "isBorderBlock", nullable = false)
    private boolean isBorderBlock;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "regionId")
    @OrderBy("populationMeasureType, demographicType")
    private List<Population> populations;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "CensusBlockNeighbors",
            joinColumns = @JoinColumn(name = "censusBlockId", referencedColumnName = "id"))
    @Column(name = "neighborId")
    private List<String> neighborIds;

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

    public List<Population> getPopulations() {
        return populations;
    }

    public void setPopulations(List<Population> populations) {
        this.populations = populations;
    }

    public List<String> getNeighborIds() {
        return neighborIds;
    }

    public void setNeighborIds(List<String> neighborIds) {
        this.neighborIds = neighborIds;
    }

    public Region getRegion() {
        return Region.CENSUS_BLOCK;
    }

    public Integer getPopulation(PopulationMeasure measure, Demographic demg) {
        return populations.get(measure.ordinal() + demg.ordinal()).getValue();
    }

    public List<Integer> getPopulation(PopulationMeasure measure) {
        return populations.stream().filter(p -> p.getPopulationMeasureType() == measure)
                .map(p -> p.getValue())
                .collect(Collectors.toList());
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CensusBlock that = (CensusBlock) o;
        return that.getId().equals(id);
    }

}
