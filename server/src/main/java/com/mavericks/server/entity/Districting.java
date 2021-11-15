package com.mavericks.server.entity;

import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;

import javax.persistence.*;
import java.util.List;

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
    private double populationEquality;
    private double polsbyPopper;
    private int numberOfOpportunity;
    private String imageUrl; // used by SeaWulf districtings, is the preview image filepath

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

    public double getPopulationEquality() {
        return populationEquality;
    }
    public void setPopulationEquality(double populationEquality) {
        this.populationEquality = populationEquality;
    }

    public double getPolsbyPopper() {
        return polsbyPopper;
    }
    public void setPolsbyPopper(double polsbyPopper) {
        this.polsbyPopper = polsbyPopper;
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

    @Override
    public String toString() {
        return "Districting{" +
                "id=" + id +
                ", state=" + state.getId() +
                ", geometry=" + geometries.toString() +
                ", populationEquality=" + populationEquality +
                ", polsbyPopper=" + polsbyPopper +
                ", numberOfOpportunity=" + numberOfOpportunity +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
