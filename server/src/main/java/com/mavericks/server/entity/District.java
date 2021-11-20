package com.mavericks.server.entity;

import com.mavericks.server.SetCustom;
import com.mavericks.server.converter.GeometryConverterString;
import com.mavericks.server.enumeration.Region;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.Feature;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Districts")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private long id;

    @Convert(converter = GeometryConverterString.class)
    @Column(name="geometry")
    private Geometry geometry;

    @Column(name="districtingId", nullable=false)
    private long districtingId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "DistrictNeighbors", joinColumns = {@JoinColumn(name = "districtId")}, inverseJoinColumns = {@JoinColumn(name = "neighborId")})
    private List<District> neighbors;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtId")
    private List<Precinct> precincts;

    public District() {}

    public District(long districtingId) {
        this.districtingId = districtingId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public long getDistrictingId() {
        return districtingId;
    }

    public void setDistrictingId(long districtingId) {
        this.districtingId = districtingId;
    }

    public List<District> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<District> neighbors) {
        this.neighbors = neighbors;
    }

    public List<Precinct> getPrecincts() {
        return precincts;
    }

    public void setPrecincts(List<Precinct> precincts) {
        this.precincts = precincts;
    }

    public Region getRegion() {
        return Region.DISTRICT;
    }

    /* Other class methods below */

    public District getRandNeighbor(){
        return neighbors.get((int)(Math.random()*neighbors.size()));
    }

    public void removeCensusBlock(CensusBlock cb){

    }

    public void addCensusBlock(CensusBlock cb){

    }
}
