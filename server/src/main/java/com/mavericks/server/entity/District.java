package com.mavericks.server.entity;

import com.mavericks.server.converter.GeometryConverterString;
import com.mavericks.server.enumeration.Basis;
import com.mavericks.server.enumeration.Demographic;
import com.mavericks.server.enumeration.PopulationMeasure;
import com.mavericks.server.enumeration.Region;
import org.hibernate.annotations.Where;
import org.locationtech.jts.dissolve.LineDissolver;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.TopologyException;
import org.locationtech.jts.operation.union.UnaryUnionOp;
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

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "districtId")
//    private List<DistrictElection> electionData;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "regionId")
    private List<PopulationCopy> population;

    @Transient
    @Autowired
    private List<Geometry>cbToAdd;

    @Transient
    @Autowired
    private List<Geometry>cbToRemove;

    @Transient
    private int districtNumber;

    @Transient
    private Geometry prevGeometry;

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

    public void processMovedBlocks(){
        cbToAdd.add(this.geometry);
        this.geometry=UnaryUnionOp.union(cbToAdd);
        cbToAdd.clear();

        for(Geometry geom:cbToRemove){
            this.geometry=this.geometry.difference(geom);
        }
        cbToRemove.clear();

    }


    public boolean removeCensusBlock(CensusBlock cb,List<CensusBlock>neighbors,PopulationMeasure measure, boolean revert){
        List<CensusBlock>cbBorders=neighbors.stream().filter(c->c.getDistrictId().equals(this.id))
                .collect(Collectors.toList());
        cbBorders.forEach(c->c.setBorderBlock(true));
        for(CensusBlock block:cbBorders){
            borderBlocks.putIfAbsent(block.getId(),block);
            innerBlocks.remove(block.getId());
        }

        this.borderBlocks.remove(cb.getId());
        int diffMultiplier=-1;
        combinePops(this.population.get(0),cb.getPopulation().get(0),diffMultiplier);
        if(revert){
            this.geometry=this.prevGeometry;
//            cbToRemove.remove(cb.getGeometry());
            cb.setMoved(false);
        }
        else{
            this.prevGeometry=this.geometry;
            try{
                this.geometry=this.geometry.difference(cb.getGeometry());
            }catch (Exception e){
                return false;
            }
//            cbToRemove.add(cb.getGeometry());
            cb.setMoved(true);
        }

        return true;

    }

    public boolean addCensusBlock(CensusBlock cb,Districting plan,List<CensusBlock>neighbors
            ,PopulationMeasure measure, boolean revert){
        List<CensusBlock>newDistrictNeighbors= neighbors.stream().
                filter(c->c.getDistrictId().equals(this.id)).collect(Collectors.toList());
        if(newDistrictNeighbors.size()!=0){
            cb.setPrecinctId(newDistrictNeighbors.get(0).getPrecinctId());
        }
        adjNewDistNeighbors(cb,plan,newDistrictNeighbors);
        cb.setDistrictId(this.id);
        this.borderBlocks.put(cb.getId(),cb);
        int addMultiplier=1;
        combinePops(this.population.get(0),cb.getPopulation().get(0),addMultiplier);
        if(revert){
            this.geometry=this.prevGeometry;
            cb.setMoved(false);
        }
        else{
            this.prevGeometry=this.geometry;
            try{
                this.geometry= this.geometry.union(cb.getGeometry());
            }catch(Exception e){
                return false;
            }
            cb.setMoved(true);
        }

        return true;
    }

    public void combinePops(PopulationCopy distPop, PopulationCopy cbPop, int multiplier){
//        for(int i=0;i<distPops.size();i++){
//            distPops.get(i).setValue(distPops.get(i).getValue()+cbPops.get(i).getValue()*multiplier);
//        }

        distPop.setAsian(distPop.getAsian()+cbPop.getAsian()*multiplier);
        distPop.setBlack(distPop.getBlack()+cbPop.getBlack()*multiplier);
        distPop.setWhite(distPop.getWhite()+cbPop.getWhite()*multiplier);
        double repVotes=distPop.getPopulationTotal()*distPop.getRepublicanVotes()+
                cbPop.getPopulationTotal()*cbPop.getRepublicanVotes()*multiplier;
        double demVotes=distPop.getPopulationTotal()*distPop.getDemocraticVotes()+
                cbPop.getPopulationTotal()*cbPop.getDemocraticVotes()*multiplier;
        distPop.setDemocraticVotes((demVotes)/(repVotes+demVotes));
        distPop.setRepublicanVotes((repVotes)/(repVotes+demVotes));
        distPop.setPopulationTotal(distPop.getPopulationTotal()+cbPop.getPopulationTotal()*multiplier);
    }


    private void adjNewDistNeighbors(CensusBlock cb,Districting plan,List<CensusBlock>neighbors){
        for(CensusBlock neighbor: neighbors){
            List<CensusBlock>otherNeighbors=plan.getNeighbors(neighbor);
            boolean borderBlock=false;
            for(CensusBlock block:otherNeighbors){
                if(!block.getDistrictId().equals(neighbor.getDistrictId())
                        && !block.equals(cb)){
                    borderBlock=true;
                    break;
                }
            }
            neighbor.setBorderBlock(borderBlock);
            if(!borderBlock){
                this.innerBlocks.put(neighbor.getId(),neighbor);
                this.borderBlocks.remove(neighbor.getId());
            }
        }
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

//    public List<DistrictElection> getElectionData() {
//        return electionData;
//    }

//    public void setElectionData(List<DistrictElection> electionData) {
//        this.electionData = electionData;
//    }

//    public List<Population> getPopulations() {
//        return populations;
//    }

//    public void setPopulations(List<Population> populations) {
//        this.populations = populations;
//    }

    public List<PopulationCopy> getPopulation() {
        return population;
    }

    public void setPopulation(List<PopulationCopy> population) {
        this.population = population;
    }

    //    public List<Election> getElection() {
//        return election;
//    }
//
//    public void setElection(List<Election> election) {
//        this.election = election;
//    }

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

//    public DistrictElection getElectionDataByElection(String electionId) {
//        Optional<DistrictElection> data = electionData.stream().filter(e -> e.getElectionId() == electionId).findFirst();
//        return data.isPresent() ? data.get() : null;
//    }

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

    public District clone(){
        District d = new District();
        d.setGeometry(this.geometry.copy());
        List<PopulationCopy>popCopy=new ArrayList<>();
        popCopy.add(population.get(0).clone());
        d.setId(this.id);
        d.setDistrictingId(this.districtingId);
        return d;
    }




}
