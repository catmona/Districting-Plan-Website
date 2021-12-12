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
import org.wololo.geojson.Feature;
import org.wololo.geojson.GeoJSON;
import org.wololo.jts2geojson.GeoJSONWriter;

import javax.persistence.*;
import java.util.*;
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

    @Column(name = "previewImageUrl")
    private String previewImageUrl; // used by SeaWulf districtings, is the preview image filepath

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name="populationEqualityScore", column=@Column(name = "populationEqualityScore"))
    })
    private Measures measures;

    @Column(name = "geometricCompactness")
    private double geometricCompactness;

    @Column(name = "majorityMinority")
    private int majorityMinority;

    @Column(name = "efficiencyGap")
    private double efficiencyGap;

    @Column(name = "devFromEnactedPopulation")
    private double devFromEnactedPopulation;

    @Column(name = "devFromEnactedWhite")
    private double devFromEnactedWhite;

    @Column(name = "devFromEnactedBlack")
    private double devFromEnactedBlack;

    @Column(name = "devFromEnactedAsian")
    private double devFromEnactedAsian;

    @Column(name = "devFromEnactedDemographics")
    private double devFromEnactedDemographics;

    @Column(name = "devFromAveragePopulation")
    private double devFromAveragePopulation;

    @Column(name = "devFromAverageWhite")
    private double devFromAverageWhite;

    @Column(name = "devFromAverageBlack")
    private double devFromAverageBlack;

    @Column(name = "devFromAverageAsian")
    private double devFromAverageAsian;

    @Column(name = "devFromAverageDemographics")
    private double devFromAverageDemographics;

    @Column(name = "splitCounty")
    private int splitCounty;

    @Column(name = "score")
    private double score;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "districtingId")
    private List<District> districts;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "regionId")
    @OrderBy("populationMeasureType, demographicType")
    private List<Population> populations;

    @Transient
    private List<String>precinctsChanged;

    @Transient
    private District maxPop;

    @Transient
    private District minPop;

    public Districting() {
        precinctsChanged=new ArrayList<>();
    }

    public Districting(String stateId, Measures measures) {
        this.stateId = stateId;
        this.measures = measures;
        precinctsChanged=new ArrayList<>();
    }

//    public void processMovedBlocks(){
//        for(District d: districts){
//            d.processMovedBlocks();
//        }
//    }

    public District getRandDistrict(){
        maxPop=districts.get(0);
        minPop=districts.get(0);
        District dist=districts.get(0);
        for(District d:districts){
            if(d.getPopulation(PopulationMeasure.TOTAL,Demographic.ALL)>
                    dist.getPopulation(PopulationMeasure.TOTAL,Demographic.ALL)){
                dist=d;
            }
//            else if (d.getPopulation(PopulationMeasure.TOTAL,Demographic.ALL)>
//                    minPop.getPopulation(PopulationMeasure.TOTAL,Demographic.ALL)){
//                minPop=d;
//            }

        }

//        Random rand = new Random();
//        District dist = districts.get(rand.nextInt(districts.size()));
//        while(dist.getBorderBlocks().size()==0){
//            dist = districts.get(rand.nextInt(districts.size()));
//        }
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

    public double getGeometricCompactness() {
        return geometricCompactness;
    }

    public void setGeometricCompactness(double geometricCompactness) {
        this.geometricCompactness = geometricCompactness;
    }

    public int getMajorityMinority() {
        return majorityMinority;
    }

    public void setMajorityMinority(int majorityMinority) {
        this.majorityMinority = majorityMinority;
    }

    public double getEfficiencyGap() {
        return efficiencyGap;
    }

    public void setEfficiencyGap(double efficiencyGap) {
        this.efficiencyGap = efficiencyGap;
    }

    public double getDevFromEnactedPopulation() {
        return devFromEnactedPopulation;
    }

    public void setDevFromEnactedPopulation(double devFromEnactedPopulation) {
        this.devFromEnactedPopulation = devFromEnactedPopulation;
    }

    public double getDevFromEnactedWhite() {
        return devFromEnactedWhite;
    }

    public void setDevFromEnactedWhite(double devFromEnactedWhite) {
        this.devFromEnactedWhite = devFromEnactedWhite;
    }

    public double getDevFromEnactedBlack() {
        return devFromEnactedBlack;
    }

    public void setDevFromEnactedBlack(double devFromEnactedBlack) {
        this.devFromEnactedBlack = devFromEnactedBlack;
    }

    public double getDevFromEnactedAsian() {
        return devFromEnactedAsian;
    }

    public void setDevFromEnactedAsian(double devFromEnactedAsian) {
        this.devFromEnactedAsian = devFromEnactedAsian;
    }

    public double getDevFromEnactedDemographics() {
        return devFromEnactedDemographics;
    }

    public void setDevFromEnactedDemographics(double devFromEnactedDemographics) {
        this.devFromEnactedDemographics = devFromEnactedDemographics;
    }

    public double getDevFromAveragePopulation() {
        return devFromAveragePopulation;
    }

    public void setDevFromAveragePopulation(double devFromAveragePopulation) {
        this.devFromAveragePopulation = devFromAveragePopulation;
    }

    public double getDevFromAverageWhite() {
        return devFromAverageWhite;
    }

    public void setDevFromAverageWhite(double devFromAverageWhite) {
        this.devFromAverageWhite = devFromAverageWhite;
    }

    public double getDevFromAverageBlack() {
        return devFromAverageBlack;
    }

    public void setDevFromAverageBlack(double devFromAverageBlack) {
        this.devFromAverageBlack = devFromAverageBlack;
    }

    public double getDevFromAverageAsian() {
        return devFromAverageAsian;
    }

    public void setDevFromAverageAsian(double devFromAverageAsian) {
        this.devFromAverageAsian = devFromAverageAsian;
    }

    public double getDevFromAverageDemographics() {
        return devFromAverageDemographics;
    }

    public void setDevFromAverageDemographics(double devFromAverageDemographics) {
        this.devFromAverageDemographics = devFromAverageDemographics;
    }

    public int getSplitCounty() {
        return splitCounty;
    }

    public void setSplitCounty(int splitCounty) {
        this.splitCounty = splitCounty;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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

    public List<Population> getPopulations() {
        return populations;
    }

    public void setPopulations(List<Population> populations) {
        this.populations = populations;
    }

    public Region getRegion() {
        return Region.DISTRICTING;
    }

    public String getDistrictGeoJSON() {
        return districtGeoJSON;
    }

    public void setDistrictGeoJSON(String districtGeoJSON) {
        this.districtGeoJSON = districtGeoJSON;
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
        return new DistrictingDTO(this.getId(), this.score, this.geometricCompactness,
                this.measures.getPopulationEquality(), this.majorityMinority, this.splitCounty,
                this.devFromAveragePopulation, this.devFromEnactedPopulation);
    }

    public PlanDTO makePlanDTO(PopulationMeasure popType){
        PlanDTO dto = new PlanDTO();

        List<PopulationCopy> populations = new ArrayList<>();
        for (District d : districts) {
            populations.add(d.getPopulation().get(0));
        }
        dto.setDistrictPopulations(populations);

        List<Feature> features = new ArrayList<>();
        GeoJSONWriter writer = new GeoJSONWriter();
        int i = 1;
        for(District d:this.getDistricts()){
            Map<String,Object> properties = new HashMap<>();
            properties.put("District", i);
            properties.put("District_Name","" + i);
            Geometry geo=d.getGeometry();
            GeoJSON json = writer.write(geo);
            features.add(new Feature((org.wololo.geojson.Geometry)json ,properties));
            i++;
        }
        dto.setFeatureCollection(writer.write(features));
        dto.setPopulationEquality(measures.getPopulationEquality());
        dto.setPolsbyPopper(geometricCompactness);
        dto.setMajorityMinority(majorityMinority);
        dto.setSplitCounty(splitCounty);
        dto.setEfficiencyGap(efficiencyGap);

        return dto;
    }

    //work in progress
    public double computePopulationEquality(PopulationMeasure measure){
        double max=0;
        double min=Double.MAX_VALUE;
        for(District d:districts){
            int value=d.getPopulation().get(0).getPopulationTotal();
            min=Math.min(value,min);
            max=Math.max(max,value);
        }
        return (max-min)/((min+max)/2);
    }

    //stubbed
    public Measures computeMeasures(PopulationMeasure measure){
        //double polsby=computePolsbyPopper();
        double popEquality = computePopulationEquality(measure);
        return new Measures(popEquality);
    }

    private double polsbyHelper(Geometry geom){
        double area = geom.getArea();
        double perimeter = geom.getLength();

        return (Math.PI*4)*(area/Math.pow(perimeter,2));
    }

    public Districting clone(){
        Districting plan= new Districting();
        List<District>distCopys=new ArrayList<>();
        plan.setMeasures(this.measures.clone());
        for(District d: this.districts){
            distCopys.add(d.clone());
        }
        plan.setDistricts(distCopys);
        return plan;
    }

    public void addPrecinct(String precinctId){
        precinctsChanged.add(precinctId);
    }

    public List<String> getPrecinctsChanged() {
        return precinctsChanged;
    }

    public void setPrecinctsChanged(List<String> precinctsChanged) {
        this.precinctsChanged = precinctsChanged;
    }

    public District getMaxPop() {
        return maxPop;
    }

    public void setMaxPop(District maxPop) {
        this.maxPop = maxPop;
    }

    public District getMinPop() {
        return minPop;
    }

    public void setMinPop(District minPop) {
        this.minPop = minPop;
    }
}
