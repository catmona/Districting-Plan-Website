package com.mavericks.server.entity;

import com.mavericks.server.converter.GeometryConverterString;
import com.mavericks.server.enumeration.Demographic;
import com.mavericks.server.enumeration.PopulationMeasure;
import com.mavericks.server.enumeration.Region;
import org.hibernate.annotations.Where;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.wololo.geojson.Feature;

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

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "districtId")
//    @MapKey(name = "id")
//    private Map<String, CensusBlock> censusBlocks;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtId")
    @Where(clause = "isBorderBlock = '1'")
    @MapKey(name = "id")
    private Map<String, CensusBlock> borderBlocks;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtId")
    @Where(clause = "isBorderBlock = '0'")
    @MapKey(name = "id")
    private Map<String, CensusBlock> innerBlocks;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "regionId")
    @OrderBy("populationMeasureType, demographicType")
    private List<Population> populations;

    @Transient
    private List<Geometry>cbToAdd;

    @Transient
    private List<Geometry>cbToRemove;

    @Transient
    private int districtNumber;

    public District() {}

    public District(String districtingId) {
        this.districtingId = districtingId;
    }

    public int getDistrictNumber(){
        return districtNumber;
    }

    public void redraw(){
        this.geometry=new GeometryCollection((Geometry[]) cbToAdd.toArray(),new GeometryFactory()).union();
        for(Geometry geo:cbToRemove){
            this.geometry=geometry.difference(geo);
        }

    }

    public void removeCensusBlock(CensusBlock cb,List<CensusBlock>neighbors){
        List<CensusBlock>cbBorders=neighbors.stream().filter(c->c.getDistrictId()!=this.districtingId)
                .collect(Collectors.toList());
        cbBorders.forEach(c->c.setBorderBlock(true));
        for(CensusBlock block:cbBorders){
            if(!block.isBorderBlock()){
                borderBlocks.put(block.getId(),block);
                innerBlocks.remove(block.getId());
            }
        }

        if(this.cbToAdd.contains(cb.getGeometry())){
            this.cbToAdd.remove(cb.getGeometry());
        }
        else{
            this.cbToRemove.add(cb.getGeometry());
        }
        //geometry=geometry.difference(cb.getGeometry());

    }


    public void addCensusBlock(District oldDistrict,CensusBlock cb,Districting plan,List<CensusBlock>neighbors){
        List<CensusBlock>newDistrictNeighbors= neighbors.stream().
                filter(c->!c.getDistrictId().equals(oldDistrict.getDistrictingId())).collect(Collectors.toList());

        for(CensusBlock newDistNeighbor: newDistrictNeighbors){
            List<CensusBlock>otherDistNeighbors=plan.getNeighbors(cb);
            boolean borderBlock=false;
            for(CensusBlock block:otherDistNeighbors){
                if(!block.getDistrictId().equals(newDistNeighbor.getDistrictId())
                        && block.getId().equals(cb.getId())){
                    borderBlock=true;
                    this.borderBlocks.putIfAbsent(newDistNeighbor.getId(), newDistNeighbor);
                    this.innerBlocks.remove(newDistNeighbor.getId());
                }
            }
            newDistNeighbor.setBorderBlock(borderBlock);
        }

        cb.setDistrictId(this.id);
        this.cbToAdd.add(cb.getGeometry());
        //geometry=geometry.union(cb.getGeometry());

    }

    public CensusBlock getCb(String id){
        if(borderBlocks.get(id)==null){
            return innerBlocks.get(id);
        }
        else{
            return borderBlocks.get(id);
        }
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

    public Map<String, CensusBlock> getBorderBlocks() {
        return borderBlocks;
    }

    public void setBorderBlocks(Map<String, CensusBlock> borderBlocks) {
        this.borderBlocks = borderBlocks;
    }

    public Map<String, CensusBlock> getInnerBlocks() {
        return innerBlocks;
    }

    public void setInnerBlocks(Map<String, CensusBlock> innerBlocks) {
        this.innerBlocks = innerBlocks;
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

    public void setDistrictNumber(int districtNumber) {
        this.districtNumber = districtNumber;
    }
}
