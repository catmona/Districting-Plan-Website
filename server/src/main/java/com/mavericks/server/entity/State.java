package com.mavericks.server.entity;
import com.mavericks.server.dto.StateDTO;

import java.util.ArrayList;
import java.util.List;
import org.wololo.geojson.FeatureCollection;

import javax.persistence.*;

@Entity
@Table(name = "States")
public class State {
    @Id
    private String id;
    private String name;
    @Transient
    private Point center;
    //private BoxWhisker boxWhisker;
    private int numberOfDistricts;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enactedDistricting")
    private Districting enacted;

    @OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
    private List<Districting> districtings;



    public State() {}

    public State(String id, String name, int numberOfDistricts) {
        this.id = id;
        this.name = name;
        this.numberOfDistricts = numberOfDistricts;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    public List<Districting> getDistrictings() {
        return districtings;
    }
    public void setDistrictings(List<Districting> districtings) {
        this.districtings = districtings;
    }

    public Population getPopulation() {
        return getEnacted().getPopulation();
    }

    public StateDTO makeDTO(){
        //dummy value; replace later
        Districting districting= this.getEnacted();
        FeatureCollection collection = districting.getGeometry();
        List<Population> distPopulations= new ArrayList<>();
        List<District>districts=districting.getDistricts();
        for(District d:districts){
            distPopulations.add(d.getPopulation());
        }
        return new StateDTO(districting.getPopulation().getPopulation(PopulationMeasure.TOTAL,Demographic.ALL),this.center,collection,distPopulations, districting.getElection());
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
    }
}