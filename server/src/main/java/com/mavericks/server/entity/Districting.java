package com.mavericks.server.entity;

import com.mavericks.server.SetCustom;
import com.mavericks.server.dto.DistrictingDTO;
import com.mavericks.server.dto.DistrictingDTO;
import com.mavericks.server.dto.PlanDTO;
import com.mavericks.server.enumeration.Region;
import org.locationtech.jts.geom.Geometry;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "Districtings")
public class Districting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private long id;

    @Column(name="stateId", length=2, nullable=false)
    private String stateId;

    @Column(name="geoJSON")
    private String geoJSON;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name="populationEqualityScore", column=@Column(name = "populationEqualityScore")),
            @AttributeOverride( name="polsbyPopperScore", column=@Column(name = "polsbyPopperScore"))
    })
    private Measures measures;

    @Column(name="previewImageUrl")
    private String previewImageUrl; // used by SeaWulf districtings, is the preview image filepath

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtingId")
    private List<District> districts;

    @Transient
    private FeatureCollection geometries;

    public Districting() {}

    public Districting(String stateId, Measures measures) {
        this.stateId = stateId;
        this.measures = measures;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getGeoJSON() {
        return geoJSON;
    }

    public void setGeoJSON(String geoJSON) {
        this.geoJSON = geoJSON;
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

    public FeatureCollection getGeometries() {
        return geometries;
    }

    public void setGeometries(FeatureCollection geometries) {
        this.geometries = geometries;
    }

    public Region getRegion() {
        return Region.DISTRICTING;
    }

    /* Other class methods below */

    public District getRandDistrict(){
        return districts.get((int)(Math.random()*districts.size()));
    }

    public DistrictingDTO makeDistrictDTO(){
        return null;
//        return new DistrictingDTO(this.measures.getPolsbyPopperScore(),this.measures.getPopulationEqualityScore(),this.election);
    }

    public PlanDTO makePlanDTO(){
        return null;
//        List<Population>distPopulations = new ArrayList<>();
//        for(District d:districts){
//            distPopulations.add(d.getPopulation());
//        }
//        return new PlanDTO(districts.size(),this.measures.getPolsbyPopperScore(),
//                this.measures.getPopulationEqualityScore(),this.election,distPopulations);
    }

    public double computePolsbyPopper(District removedDistrict,District addedDistrict){
        List<District> otherDistricts = districts.stream().filter(d->d.getId()!=removedDistrict.getId()
                &&d.getId()!=addedDistrict.getId()).collect(Collectors.toList());
        double removedPolsby=polsbyHelper(removedDistrict);
        double addedPolsby=polsbyHelper(addedDistrict);
        return 0;
//        return (otherDistricts.stream().mapToDouble(d->d.getMeasures().getPolsbyPopperScore())
//                .reduce(0,(a,b)->a+b)+removedPolsby+addedPolsby)/districts.size();

    }

    //stubbed
    public Measures computeMeasures(){
        return null;
    }

    private double polsbyHelper(District district){
        double area = district.getGeometry().getArea();
        double perimeter = district.getGeometry().getLength();

        return (Math.PI*4*area)/(Math.pow(perimeter,2));
    }

}
