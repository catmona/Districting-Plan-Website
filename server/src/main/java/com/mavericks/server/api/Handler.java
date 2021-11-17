package com.mavericks.server.api;

import com.mavericks.server.Algorithm;
import com.mavericks.server.dto.DistrictingDTO;
import com.mavericks.server.dto.DistrictingDTO;
import com.mavericks.server.dto.PlanDTO;
import com.mavericks.server.dto.StateDTO;
import com.mavericks.server.entity.*;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.server.ResponseStatusException;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSONFactory;

import org.wololo.jts2geojson.GeoJSONReader;

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
        int[] democratic = new int[]{137868, 155780, 203421, 168457};
        int[] republican = new int[]{74490, 216078, 190975, 152284};
        GeoJSONReader reader = new GeoJSONReader();
        for(int i=0;i<30;i++){
            Districting dist = new Districting(state,featureCollection);
            List<District> districts = new ArrayList<>();
            Feature[]fs=featureCollection.getFeatures();
            List<Population> distPopulations= new ArrayList<>();
            List<DistrictElection>voteData=new ArrayList<>();
            Election election = new Election(665526,633827);
            for(int j=0;j<fs.length;j++){
                Population population= new Population();
                population.setPopulation(PopulationMeasure.TOTAL,Demographic.ALL,all[j]);
                population.setPopulation(PopulationMeasure.TOTAL,Demographic.AFRICAN_AMERICAN,aa[j]);
                population.setPopulation(PopulationMeasure.TOTAL,Demographic.ASIAN,asian[j]);
                population.setPopulation(PopulationMeasure.TOTAL,Demographic.HISPANIC,hispanic[j]);
                population.setPopulation(PopulationMeasure.TOTAL,Demographic.WHITE,white[j]);
                DistrictElection districtVoteData= new DistrictElection(republican[j],democratic[j]);
                District district= new District(i,dist,reader.read(fs[0].getGeometry()));
                districts.add(district);
                district.setPopulation(population);
                voteData.add(districtVoteData);
            }

            dist.setDistricts(districts);
            election.setDistrictElections(voteData);
            dist.setElection(election);
            districtings.add(dist);
        }

        state.setEnacted(districtings.get(0));
        Districting enacted =districtings.get(0);
        Measures m = new Measures(0.0413883162478257,0.07725808180925772);
        enacted.setMeasures(m);




        state.setDistrictings(districtings);
        session.setAttribute("state",state);
        StateDTO dto =state.makeDTO();

        return dto;
    }


    public List<DistrictingDTO> getDistrictings(HttpSession session){
        State state = (State) session.getAttribute("state");
        List<DistrictingDTO> plansPreview= new ArrayList<>();
        Districting enacted = state.getEnacted();
        for(int i=0;i<30;i++){
            plansPreview.add(enacted.makeDistrictDTO());
        }

        return plansPreview;
    }

    public PlanDTO getDistrictingSummary(long districtingId, HttpSession session){
        State state = (State) session.getAttribute("state");
        Districting districting = state.getDistrictings().get((int)districtingId);
        PlanDTO planDTO= districting.makePlanDTO();
        return planDTO;
    }

    public List<PlanDTO> getDistrictingSummaries(HttpSession session){
        State state = (State) session.getAttribute("state");
        List<Districting> districtings = state.getDistrictings();
        List<PlanDTO> planDTOs = new ArrayList<PlanDTO>();
        for (int i = 0; i < districtings.size(); i++) {
            planDTOs.add(i, districtings.get(i).makePlanDTO());
        }

        return planDTOs;
    }

    public Box getBoxWhisker(long districtingId, Basis basis,boolean enacted,
                                               boolean current, boolean postAlg, HttpSession session){

        State state = (State) session.getAttribute("state");
        Districting districting= state.getEnacted();
        List<Box>boxes=new ArrayList<>();

        double []upperExtreme={5,5,5,5};
        double []upperQuartile={15,15,15,25};
        double []median={25,25,25,25};
        double []lowerQuartile={35,35,35,35};
        double []lowerExtreme={45,45,35,45};
        Box box = new Box(upperExtreme,upperQuartile,median,lowerQuartile,lowerExtreme);

        return box;
    }

    /**
     * Sets the algorithm constraints
     * @param minPopulationEquality deviation from ideal population
     * @param minCompactness the geometric compactness computed by polsby popper
     * @return the thread id
     */
    public long setLimits(double minPopulationEquality, double minCompactness){
        Algorithm alg = new Algorithm(minPopulationEquality,minCompactness);
        Thread t = new Thread(alg);
        Object[] pair={t,alg};
        jobs.put(t.getId(),pair);
        return t.getId();
    }

    public Map<String,Object> startAlgorithm(long threadId, int districtingNum, HttpSession session){
        State state = (State)session.getAttribute("state");
        Districting districting=state.getDistrictings().get(districtingNum);


        String data2 = readFile("data/nv-cb-geo.geojson");
        FeatureCollection f2 = (FeatureCollection)GeoJSONFactory.create(data2);

        GeoJSONReader reader = new GeoJSONReader();
        Feature[]blocks = f2.getFeatures();
        List<List<CensusBlock>>distToBlocks=new ArrayList<>();
        for(int i=0;i<districting.getDistricts().size();i++){
            distToBlocks.add(new ArrayList<>());
        }

        for(Feature f:blocks){
            Integer distNum=(Integer)f.getProperties().get("districtId")-1;
            Long blockId=Long.parseLong((String)f.getProperties().get("blockId"));
            boolean border =(Boolean)f.getProperties().get("boundary");
            Geometry g =reader.read(f.getGeometry());
            CensusBlock cb = new CensusBlock(blockId,null,g,border);
            distToBlocks.get(distNum).add(cb);

        }

        for(int i=0;i<districting.getDistricts().size();i++){
            districting.getDistricts().get(i).setBlocks(distToBlocks.get(i));
        }



        Object[]pair = jobs.get(threadId);
        Algorithm alg=(Algorithm)pair[1];
        alg.setInProgressPlan(districting);

        Map<String ,Object> data= new Hashtable<>();
        data.put("Measures",districting.getMeasures());
        data.put("iterations",alg.getIterations());
        return data;

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
