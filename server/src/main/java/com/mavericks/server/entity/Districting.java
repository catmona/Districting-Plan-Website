package com.mavericks.server.entity;

import com.mavericks.server.converter.FeatureCollectionConverterString;
import com.mavericks.server.dto.DistrictingDTO;
import com.mavericks.server.dto.PlanDTO;
import com.mavericks.server.enumeration.Basis;
import com.mavericks.server.enumeration.Demographic;
import com.mavericks.server.enumeration.PopulationMeasure;
import com.mavericks.server.enumeration.Region;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.operation.union.UnaryUnionOp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Entity
@Table(name = "Districtings")
public class Districting {
    @Id
    @Column(name = "id", length = 50, nullable = false)
    private String id;

    @Column(name = "stateId", length = 2, nullable = false)
    private String stateId;

    @Column(name = "districtGeoJSON")
    private String districtGeoJSON;

    @Column(name = "precinctGeoJSON")
    private String precinctGeoJSON;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name="populationEqualityScore", column=@Column(name = "populationEqualityScore")),
            @AttributeOverride( name="polsbyPopperScore", column=@Column(name = "polsbyPopperScore"))
    })
    private Measures measures;

    @Column(name = "previewImageUrl")
    private String previewImageUrl; // used by SeaWulf districtings, is the preview image filepath

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtingId")
    private List<District> districts;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "regionId")
    @OrderBy("populationMeasureType, demographicType")
    private List<Population> populations;

    public Districting() {}

    public Districting(String stateId, Measures measures) {
        this.stateId = stateId;
        this.measures = measures;
    }

    public District getRandDistrict(){
        Random rand = new Random();
        District dist = districts.get(rand.nextInt(districts.size()));
        while(dist.getBorderBlocks().size()==0){
            dist = districts.get(rand.nextInt(districts.size()));
        }
        return dist;
    }

    public District getDistrict(String id){
        for(District d:districts){
            if(id.equals(d.getId())){
                return d;
            }
        }
        return null;
    }


    public List<CensusBlock> getNeighbors(CensusBlock cb){
        List<CensusBlock> neighbors=new ArrayList<>();
        for(String id:cb.getNeighborIds()){
            for(District d:this.getDistricts()){
                CensusBlock neighbor=d.getCb(id);
                if(neighbor!=null){
                    neighbors.add(d.getCb(id));
                    break;
                }
            }
        }

        return neighbors;
    }





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public Measures getMeasures() {
        return measures;
    }

    public void setMeasures(Measures measures) {
        this.measures = measures;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public String getDistrictGeoJSON() {
        return districtGeoJSON;
    }

    public void setDistrictGeoJSON(String districtGeoJSON) {
        this.districtGeoJSON = districtGeoJSON;
    }

    public String getPrecinctGeoJSON() {
        return precinctGeoJSON;
    }

    public void setPrecinctGeoJSON(String precinctGeoJSON) {
        this.precinctGeoJSON = precinctGeoJSON;
    }

    public List<Population> getPopulations() {
        return populations;
    }

    public void setPopulations(List<Population> populations) {
        this.populations = populations;
    }

    public Region getRegion() {
        return Region.DISTRICTING;
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

    public List<Population>getPopulation(){
        return populations;
    }


    public DistrictingDTO makeDistrictDTO(){
        return new DistrictingDTO(this.getId(), this.measures.getPolsbyPopperScore(),
                this.measures.getPopulationEqualityScore());
    }

    public PlanDTO makePlanDTO(PopulationMeasure popType){
        PlanDTO dto = new PlanDTO();
        List<Election> elections = new ArrayList<Election>();
        List<List<Integer>> populations = new ArrayList<>();
        for (District d : districts) {
            elections.add(d.getElection());
            populations.add(d.getPopulation(popType));
        }
        dto.setDistrictElections(elections);
        dto.setDistrictPopulations(populations);
        return dto;
    }

    public double computePolsbyPopper(){
//        List<District> otherDistricts = districts.stream().filter(d->!d.equals(removedDistrict)
//                && !d.equals(addedDistrict)).collect(Collectors.toList());
//        double removedPolsby=polsbyHelper(removedDistrict.getGeometry().difference(block.getGeometry()));
//        double addedPolsby=polsbyHelper(addedDistrict.getGeometry().union(block.getGeometry()));
        double polsbySum=0;
        for(District d:districts){
            polsbySum+=polsbyHelper(d.getGeometry());
        }
        return polsbySum/districts.size();

    }

    //work in progress
    public double computePopulationEquality(PopulationMeasure measure){
        double max=0;
        double min=Double.MAX_VALUE;
        for(District d:districts){
            int value=d.getPopulation(measure,Demographic.ALL);
            min=Math.min(value,min);
            max=Math.max(max,value);
        }
        return (max-min)/((min+max)/2);

    }

    //stubbed
    public Measures computeMeasures(PopulationMeasure measure){
        double polsby=computePolsbyPopper();
        double popEquality =computePopulationEquality(measure);
        return new Measures(popEquality,polsby);
    }

    private double polsbyHelper(Geometry geom){
        double area = geom.getArea();
        double perimeter = geom.getLength();

        return (Math.PI*4)*(area/Math.pow(perimeter,2));
    }

}
