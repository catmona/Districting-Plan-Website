package com.mavericks.server.entity;

import com.mavericks.server.converter.GeometryConverterString;
import com.mavericks.server.enumeration.Demographic;
import com.mavericks.server.enumeration.PopulationMeasure;
import com.mavericks.server.enumeration.Region;
import org.hibernate.annotations.Where;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wololo.geojson.Feature;

import javax.persistence.*;
import java.util.*;
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
    @Autowired
    private List<Geometry>cbToAdd;

    @Transient
    @Autowired
    private List<Geometry>cbToRemove;

    @Transient
    private int districtNumber;

    public District() {
        this.cbToRemove=new ArrayList<>();
        this.cbToAdd=new ArrayList<>();
    }

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

    public void removeCensusBlock(CensusBlock cb,List<CensusBlock>neighbors,PopulationMeasure measure){
        List<CensusBlock>cbBorders=neighbors.stream().filter(c->c.getDistrictId().equals(this.id))
                .collect(Collectors.toList());
        cbBorders.forEach(c->c.setBorderBlock(true));
        for(CensusBlock block:cbBorders){
            borderBlocks.putIfAbsent(block.getId(),block);
            innerBlocks.remove(block.getId());
        }

        if(this.cbToAdd.contains(cb.getGeometry())){
            this.cbToAdd.remove(cb.getGeometry());
        }
        else{
            this.cbToRemove.add(cb.getGeometry());
        }

        this.borderBlocks.remove(cb.getId());
        Population distPop=this.getPopulationObj(measure,Demographic.ALL);
        distPop.setValue(distPop.getValue()-cb.getPopulation(measure,Demographic.ALL));


    }


    public void addCensusBlock(District oldDistrict,CensusBlock cb,Districting plan,List<CensusBlock>neighbors
            ,PopulationMeasure measure){
        List<CensusBlock>newDistrictNeighbors= neighbors.stream().
                filter(c->!c.getDistrictId().equals(oldDistrict.getId())).collect(Collectors.toList());

        for(CensusBlock newDistNeighbor: newDistrictNeighbors){
            List<CensusBlock>otherDistNeighbors=plan.getNeighbors(newDistNeighbor);
            boolean borderBlock=false;
            for(CensusBlock block:otherDistNeighbors){
                if(!block.getDistrictId().equals(newDistNeighbor.getDistrictId())
                        && !block.equals(cb)){
                    borderBlock=true;
                    this.borderBlocks.putIfAbsent(newDistNeighbor.getId(), newDistNeighbor);
                    this.innerBlocks.remove(newDistNeighbor.getId());
                    break;
                }
            }
            newDistNeighbor.setBorderBlock(borderBlock);
            if(!borderBlock){
                this.innerBlocks.putIfAbsent(newDistNeighbor.getId(),newDistNeighbor);
                this.borderBlocks.remove(newDistNeighbor.getId());
            }
        }

        cb.setDistrictId(this.id);
        this.cbToAdd.add(cb.getGeometry());
        this.borderBlocks.put(cb.getId(),cb);
        Population distPop=this.getPopulationObj(measure,Demographic.ALL);
        distPop.setValue(distPop.getValue()+cb.getPopulation(measure,Demographic.ALL));

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
        Object[]blocks =borderBlocks.values().toArray();
        Random rand = new Random();
        return (CensusBlock)blocks[rand.nextInt(blocks.length)];
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

    public Population getPopulationObj(PopulationMeasure measure, Demographic demg){
        return populations.get(measure.ordinal() + demg.ordinal());
    }

    public DistrictElection getElectionDataByElection(String electionId) {
        Optional<DistrictElection> data = electionData.stream().filter(e -> e.getElectionId() == electionId).findFirst();
        return data.isPresent() ? data.get() : null;
    }

    public void setDistrictNumber(int districtNumber) {
        this.districtNumber = districtNumber;
    }

    public List<Geometry> getCbToAdd() {
        return cbToAdd;
    }

    public void setCbToAdd(List<Geometry> cbToAdd) {
        this.cbToAdd = cbToAdd;
    }

    public List<Geometry> getCbToRemove() {
        return cbToRemove;
    }

    public void setCbToRemove(List<Geometry> cbToRemove) {
        this.cbToRemove = cbToRemove;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        District district = (District) o;
        return this.id.equals(district.getId());
    }


}
