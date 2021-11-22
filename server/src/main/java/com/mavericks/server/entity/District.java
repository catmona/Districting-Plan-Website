package com.mavericks.server.entity;

import com.mavericks.server.SetCustom;
import com.mavericks.server.converter.GeometryConverterString;
import com.mavericks.server.enumeration.Basis;
import com.mavericks.server.enumeration.Demographic;
import com.mavericks.server.enumeration.PopulationMeasure;
import com.mavericks.server.enumeration.Region;
import org.hibernate.annotations.Where;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.Feature;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "DistrictNeighbors", joinColumns = {@JoinColumn(name = "districtId")}, inverseJoinColumns = {@JoinColumn(name = "neighborId")})
    private List<District> neighbors;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtId")
    private List<CensusBlock> censusBlocks;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtId")
    @Where(clause = "isBorderBlock='1'")
    private List<CensusBlock> borderBlocks;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "regionId")
    @OrderBy("populationMeasureType, demographicType")
    private List<Population> populations;

    @Transient
    private int districtNumber;

    public District() {}

    public District(String districtingId) {
        this.districtingId = districtingId;
    }

    public int getDistrictNumber(){
        return districtNumber;
    }

    public void removeCensusBlock(CensusBlock cb){
        cb.getNeighbors().stream().forEach(c->c.setBorderBlock(true));
        geometry=geometry.difference(cb.getGeometry());
    }

    public void addCensusBlock(District oldDistrict,CensusBlock cb,Districting plan){
        List<Geometry>districtGeoms=plan.getDistricts().stream().filter(d->d.districtingId!=oldDistrict.id)
                .map(d->d.geometry).collect(Collectors.toList());
        Geometry cutDistrict = oldDistrict.geometry.difference(cb.getGeometry());
        districtGeoms.add(cutDistrict);
        List<CensusBlock>neighbors=cb.getNeighbors().stream().filter(c->c.getDistrictId()!=oldDistrict.districtingId)
                .collect(Collectors.toList());
        cb.setDistrictId(districtingId);
        for(CensusBlock neigh:neighbors){
            boolean border=false;
            for(Geometry geom:districtGeoms){
                if(neigh.getGeometry().touches(geom)){
                    border=true;
                }
            }
            neigh.setBorderBlock(border);
        }
        geometry=geometry.union(cb.getGeometry());

    }


    public CensusBlock getRandCensusBlock(){
        return borderBlocks.get((int)(Math.random()*populations.size()));
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

    public List<District> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<District> neighbors) {
        this.neighbors = neighbors;
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

}
