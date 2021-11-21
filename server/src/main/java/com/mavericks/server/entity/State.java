package com.mavericks.server.entity;
import com.mavericks.server.converter.PointConverterString;
import com.mavericks.server.dto.StateDTO;
import org.wololo.geojson.FeatureCollection;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "States")
public class State {
    @Id
    @Column(name="id", length=2, nullable=false)
    private String id;

    @Column(name="fullName", length=50, nullable=false)
    private String fullName;

    @Convert(converter = PointConverterString.class)
    @Column(name="center")
    private Point center;

    @Column(name="numberOfDistricts", nullable=false)
    private int numberOfDistricts;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enactedId", referencedColumnName = "id")
    private Districting enacted;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "stateId")
    private List<Districting> districtings;

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

    public List<Districting> getDistrictings() {
        return districtings;
    }

    public void setDistrictings(List<Districting> districtings) {
        this.districtings = districtings;
    }

    /* Other class methods below */

    public StateDTO makeDTO(){
        // TODO
        List<Population> p = new ArrayList<>();
        Districting e = enacted;
        FeatureCollection fc = e.getDistrictGeoJSON();
        return new StateDTO(center, fc, p, null);
    }
}