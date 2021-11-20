package com.mavericks.server.entity;
import com.mavericks.server.converter.ObjectConverterJson;
import com.mavericks.server.dto.StateDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mavericks.server.enumeration.Demographic;
import com.mavericks.server.enumeration.PopulationMeasure;
import org.wololo.geojson.FeatureCollection;

import javax.persistence.*;

@Entity
@Table(name = "States")
public class State {
    @Id
    @Column(name="id", length=2, nullable=false)
    private String id;

    @Column(name="fullName", length=50, nullable=false)
    private String fullName;

    @Convert(converter = ObjectConverterJson.class)
    @Column(name="center")
    private Point center;

    @Column(name="numberOfDistricts", nullable=false)
    private int numberOfDistricts;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enactedId", referencedColumnName = "id")
    @Column(name="enactedId")
    private Districting enacted;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "stateId")
    private Set<Districting> districtings;

    public State() {}

    public State(String id, String fullName, int numberOfDistricts) {
        this.id = id;
        this.fullName = fullName;
        this.numberOfDistricts = numberOfDistricts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public int getNumberOfDistricts() {
        return numberOfDistricts;
    }

    public void setNumberOfDistricts(int numberOfDistricts) {
        this.numberOfDistricts = numberOfDistricts;
    }

    public Districting getEnacted() {
        return enacted;
    }

    public void setEnacted(Districting enacted) {
        this.enacted = enacted;
    }

    public Set<Districting> getDistrictings() {
        return districtings;
    }

    public void setDistrictings(Set<Districting> districtings) {
        this.districtings = districtings;
    }

    /* Other class methods below */

    public StateDTO makeDTO(){
        //dummy value; replace later
        Districting districting= this.getEnacted();
        FeatureCollection collection = districting.getGeometry();
        List<Population> distPopulations= new ArrayList<>();
        List<District>districts=districting.getDistricts();
        for(District d:districts){
            distPopulations.add(d.getPopulation());
        }
<<<<<<< HEAD
        return new StateDTO(145,this.center,collection,distPopulations, districting.getElection());
    }

    @Override
    public String toString() {
        return "State{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", center=" + center.toString() +
                ", numberOfDistricts=" + numberOfDistricts +
                ", enacted=" + enacted.getId() +
                '}';
=======
        return new StateDTO(districting.getPopulation().getPopulation(PopulationMeasure.TOTAL, Demographic.ALL),this.center,collection,distPopulations, districting.getElection());
>>>>>>> origin/entities
    }
}