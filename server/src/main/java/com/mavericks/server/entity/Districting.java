package com.mavericks.server.entity;

import com.mavericks.server.converter.FeatureCollectionConverterString;
import com.mavericks.server.dto.DistrictingDTO;
import com.mavericks.server.dto.PlanDTO;
import com.mavericks.server.enumeration.Region;
import org.hibernate.annotations.Where;
import org.wololo.geojson.FeatureCollection;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Districtings")
public class Districting {
    @Id
    @Column(name = "id", length = 50, nullable = false)
    private String id;

    @Column(name = "stateId", length = 2, nullable = false)
    private String stateId;

    @Convert(converter = FeatureCollectionConverterString.class)
    @Column(name = "districtGeoJSON")
    private FeatureCollection districtGeoJSON;

    @Convert(converter = FeatureCollectionConverterString.class)
    @Column(name = "precinctGeoJSON")
    private FeatureCollection precinctGeoJSON;

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
    private List<Population> populations;

    public Districting() {}

    public Districting(String stateId, Measures measures) {
        this.stateId = stateId;
        this.measures = measures;
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

    public FeatureCollection getDistrictGeoJSON() {
        return districtGeoJSON;
    }

    public void setDistrictGeoJSON(FeatureCollection districtGeoJSON) {
        this.districtGeoJSON = districtGeoJSON;
    }

    public FeatureCollection getPrecinctGeoJSON() {
        return precinctGeoJSON;
    }

    public void setPrecinctGeoJSON(FeatureCollection precinctGeoJSON) {
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
