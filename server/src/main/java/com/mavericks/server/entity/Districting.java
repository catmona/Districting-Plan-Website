package com.mavericks.server.entity;

import com.mavericks.server.dto.DistrictingDTO;
import com.mavericks.server.dto.DistrictingDTO;
import com.mavericks.server.dto.PlanDTO;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Districtings")
public class Districting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stateId")
    private State state;
    @Transient
    private FeatureCollection geometries;
    @Transient
    private Measures measures;
    private int numberOfOpportunity;
    private String imageUrl; // used by SeaWulf districtings, is the preview image filepath
    @Transient
    private Election election;

    @OneToMany(mappedBy = "districtingPlan", fetch = FetchType.LAZY)
    private List<District> districts;

    @Transient
    private Population population;

    public Districting() {}

    public Districting(State state, FeatureCollection geometries) {
        this.state = state;
        this.geometries=geometries;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }

    public FeatureCollection getGeometry() {
        return geometries;
    }
    public void setGeometry(FeatureCollection geometries) {
        this.geometries = geometries;
    }

    public Measures getMeasures() {
        return measures;
    }

    public void setMeasures(Measures measures) {
        this.measures = measures;
    }

    public int getNumberOfOpportunity() {
        return numberOfOpportunity;
    }
    public void setNumberOfOpportunity(int numberOfOpportunity) {
        this.numberOfOpportunity = numberOfOpportunity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<District> getDistricts() {
        return districts;
    }
    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public District getRandDistrict(){
        return districts.get((int)(Math.random()*districts.size()));
    }


    //replace stuff in other areas.
    public Population computePopulation() {
        // get population by aggregating the plan's District populations
        Population res = new Population();
        for (District d: districts) {
            res.combinePopulations(d.getPopulation());
        }
        return res;
    }

    public Population getPopulation(){
        return this.population;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public DistrictingDTO makeDistrictDTO(){
        return new DistrictingDTO(this.measures.getPolsbyPopperScore(),this.measures.getPopulationEqualityScore(),this.election);
    }

    public PlanDTO makePlanDTO(){
        List<Population>distPopulations = new ArrayList<>();
        for(District d:districts){
            distPopulations.add(d.getPopulation());
        }
        return new PlanDTO(districts.size(),this.measures.getPolsbyPopperScore(),
                this.measures.getPopulationEqualityScore(),this.election,distPopulations);
    }

    public double computePolsbyPopper(District removedDistrict,District addedDistrict){
        List<District> otherDistricts = districts.stream().filter(d->d.getId()!=removedDistrict.getId()
                &&d.getId()!=addedDistrict.getId()).collect(Collectors.toList());
        double removedPolsby=polsbyHelper(removedDistrict);
        double addedPolsby=polsbyHelper(addedDistrict);
        return (otherDistricts.stream().mapToDouble(d->d.getMeasures().getPolsbyPopperScore())
                .reduce(0,(a,b)->a+b)+removedPolsby+addedPolsby)/districts.size();

    }

    private double polsbyHelper(District district){
        double area = district.getGeometry().getArea();
        double perimeter = district.getGeometry().getLength();

        return (Math.PI*4*area)/(Math.pow(perimeter,2));
    }

    @Override
    public String toString() {
        return "Districting{" +
                "id=" + id +
                ", state=" + state.getId() +
                ", geometry=" + geometries.toString() +
                ", populationEquality=" + this.measures.getPopulationEqualityScore() +
                ", polsbyPopper=" + this.measures.getPolsbyPopperScore() +
                ", numberOfOpportunity=" + numberOfOpportunity +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
