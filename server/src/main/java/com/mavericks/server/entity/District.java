package com.mavericks.server.entity;

import com.mavericks.server.converter.GeometryConverterString;
import com.mavericks.server.enumeration.Demographic;
import com.mavericks.server.enumeration.PopulationMeasure;
import com.mavericks.server.enumeration.Region;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "Districts")
public class District {
    @Id
    @Column(name = "id", length = 50, nullable = false)
    private String id;

    @Column(name = "districtingId", length = 50, nullable = false)
    private String districtingId;

    @Convert(converter = GeometryConverterString.class)
    @Column(name = "geometry")
    private Geometry geometry;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtId")
    private List<DistrictElection> electionData;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtId")
    @MapKey(name = "id")
    private Map<String, CensusBlock> censusBlocks;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "regionId")
    @OrderBy("populationMeasureType, demographicType")
    private List<Population> populations;

    public District() {}

    public District(String districtingId) {
        this.districtingId = districtingId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getDistrictingId() {
        return districtingId;
    }

    public void setDistrictingId(String districtingId) {
        this.districtingId = districtingId;
    }

    public Map<String, CensusBlock> getCensusBlocks() {
        return censusBlocks;
    }

    public void setCensusBlocks(Map<String, CensusBlock> censusBlocks) {
        this.censusBlocks = censusBlocks;
    }

    public List<DistrictElection> getElectionData() {
        return electionData;
    }

    public void setElectionData(List<DistrictElection> electionData) {
        this.electionData = electionData;
    }

    public List<Population> getPopulations() {
        return populations;
    }

    public void setPopulations(List<Population> populations) {
        this.populations = populations;
    }

    public Region getRegion() {
        return Region.DISTRICT;
    }

    /* Other class methods below */

    public Integer getPopulation(PopulationMeasure measure, Demographic demg) {
        return populations.get(measure.ordinal() + demg.ordinal()).getValue();
    }

    public List<Integer> getPopulation(PopulationMeasure measure) {
        return populations.stream().filter(p -> p.getPopulationMeasureType() == measure)
                .map(p -> p.getValue())
                .collect(Collectors.toList());
    }

    public DistrictElection getElectionDataByElection(String electionId) {
        Optional<DistrictElection> data = electionData.stream().filter(e -> e.getElectionId() == electionId).findFirst();
        return data.isPresent() ? data.get() : null;
    }

    public void removeCensusBlock(CensusBlock cb){

    }

    public void addCensusBlock(CensusBlock cb){

    }
}
