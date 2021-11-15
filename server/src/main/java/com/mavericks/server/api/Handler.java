package com.mavericks.server.api;

import com.mavericks.server.dto.StateDTO;
import com.mavericks.server.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.server.ResponseStatusException;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSONFactory;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Component
public class Handler {

    private final Map<Long,Object[]> jobs;

    @Autowired
    public Handler(Map<Long, Object[]> jobs) {
        this.jobs = jobs;
    }

    public String readFile(String path){
        try{
            //read file and return
            InputStream in = new ClassPathResource(path).getInputStream();
            String data = StreamUtils.copyToString(in, Charset.defaultCharset());
            in.close();
            return data;
        }catch (IOException e){
            return  "";
        }
    }

    public StateDTO getStateSummary(String stateName, HttpSession session){

        State state= new State("NV","Nevada",4);
        String data = readFile("data/NV/2012/State.geojson");
        FeatureCollection featureCollection = (FeatureCollection) GeoJSONFactory.create(data);
        List<Districting> districtings= new ArrayList<>();
        int[] aa = new int[]{88601, 16972, 72744, 131912};
        int[] white = new int[]{347382, 582218, 523798, 435644};
        int[] asian = new int[]{60582, 32304, 126960, 47066};
        int[] hispanic = new int[]{343864, 183123, 166285, 238360};
        int[] all = new int[]{723705, 766064, 853240, 786739};

        for(int i=0;i<30;i++){
            Districting dist = new Districting(state,featureCollection);
            List<District> districts = new ArrayList<>();
            Feature[]fs=featureCollection.getFeatures();
            List<Population> distPopulations= new ArrayList<>();
            for(int j=0;j<fs.length;j++){
                Population population= new Population();
                population.setPopulation(PopulationMeasure.TOTAL,Demographic.ALL,all[j]);
                population.setPopulation(PopulationMeasure.TOTAL,Demographic.AFRICAN_AMERICAN,aa[j]);
                population.setPopulation(PopulationMeasure.TOTAL,Demographic.ASIAN,asian[j]);
                population.setPopulation(PopulationMeasure.TOTAL,Demographic.HISPANIC,hispanic[j]);
                population.setPopulation(PopulationMeasure.TOTAL,Demographic.WHITE,white[j]);
                District district= new District(i,dist,fs[0]);
                districts.add(district);
                district.setPopulation(population);
            }

            dist.setDistricts(districts);

            districtings.add(dist);
        }

        state.setEnacted(districtings.get(0));


        state.setDistrictings(districtings);
        session.setAttribute("state",state);
        StateDTO dto =state.makeDTO();
        return dto;
    }


    public Map<String,Object> getDistrictings(long stateId, HttpSession session){
        return new Hashtable<>();
    }

    public Map<String,Object> getDistrictingSummary(long districtingId, HttpSession session){
        return new Hashtable<>();
    }

    public Map<String,Object> getBoxWhisker(long stateId,long districtingId, long demographicId,boolean enacted,
                                               boolean current, HttpSession session){
        return new Hashtable<>();
    }

    /**
     * Sets the algorithm constraints
     * @param minPopulationEquality deviation from ideal population
     * @param minCompactness the geometric compactness computed by polsby popper
     * @return the thread id
     */
    public long setLimits(double minPopulationEquality, double minCompactness){
//        Algorithm alg = new Algorithm(minPopulationEquality,minCompactness,100000);
//        Thread t = new Thread(alg);
//        Object[] pair={t,alg};
//        jobs.put(t.getId(),pair);
//        return t.getId();
        return 0;
    }

    public Map<String,Object> startAlgorithm(long threadId){
        return new Hashtable<>();
    }

    public Map<String,Object> getProgress(long threadId){
        return new Hashtable<>();
    }


    public Map<String,Object> getResults(long threadId){
        return new Hashtable<>();
    }

    public Map<String,Object> stopAlgorithm(long threadId){
        return new Hashtable<>();
    }



}
