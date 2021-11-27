package com.mavericks.server.api;

//import com.mavericks.server.Algorithm;
import com.mavericks.server.Algorithm;
import com.mavericks.server.dto.AlgorithmDTO;
import com.mavericks.server.dto.DistrictingDTO;
import com.mavericks.server.dto.PlanDTO;
import com.mavericks.server.dto.StateDTO;
import com.mavericks.server.entity.*;
import com.mavericks.server.enumeration.Basis;
import com.mavericks.server.enumeration.Demographic;
import com.mavericks.server.enumeration.PopulationMeasure;
import com.mavericks.server.repository.*;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

@Component
public class Handler {

    @Autowired
    public StateRepository stateRepo;
    @Autowired
    public DistrictingRepository districtingRepo;
    @Autowired
    public DistrictRepository distRepo;
    @Autowired
    public PopulationRepository popRepo;
    @Autowired
    public DistrictElectionRepository distElectionRepo;

    private final Map<String,Algorithm> jobs;

    @Autowired
    private ObjectProvider<Algorithm> provider;

    //private final CensusBlockPopulationRepository;

    @Autowired
    public Handler(Map<String, Algorithm> jobs) {
        this.jobs = jobs;
    }

    public Election test(){
        District d = distRepo.findAll().get(0);
        Election e = d.getElection();
        return e;
    }

    /**
     * Get the summary information for a state's enacted districting.
     * @param stateName State abbreviation
     * @param session
     * @return
     */
    public StateDTO getStateSummary(String stateName, HttpSession session){
        if (stateName == null || stateName == "") {
            return null;
        }
        State state = stateRepo.getById(stateName);
        PopulationMeasure popType = (PopulationMeasure) session.getAttribute("PopType");
        session.setAttribute("state", state);
        return state.makeDTO(popType);
    }


    public List<DistrictingDTO> getDistrictings(HttpSession session){
        State state = (State) session.getAttribute("state");
        List<DistrictingDTO> plansPreview = new ArrayList<>();
        Districting enacted = state.getEnacted();
        // TODO stubbed. we don't have the SeaWulf districtings yet
        for(int i=0;i<30;i++){
            plansPreview.add(enacted.makeDistrictDTO());
        }

        return plansPreview;
    }

    public PlanDTO getDistrictingSummary(String districtingId, HttpSession session){
        State state = (State) session.getAttribute("state");
        Districting districting = null;
        for (Districting d : state.getDistrictings()) {
            if (d.getId().equals(districtingId)) {
                districting = d;
                break;
            }
        }

        if (districting == null) {
            // districting plan not found
        }

        PopulationMeasure popType = (PopulationMeasure) session.getAttribute("PopType");
        return districting.makePlanDTO(popType);
    }

    public List<PlanDTO> getDistrictingSummaries(HttpSession session){
        // TODO get all districting summaries of the currently selected state
        State state = (State) session.getAttribute("state");
        List<Districting> districtings = state.getDistrictings();
        List<PlanDTO> planDTOs = new ArrayList<PlanDTO>();
        // TODO will use Set later
//        for (int i = 0; i < districtings.size(); i++) {
//            planDTOs.add(i, districtings.get(i).makePlanDTO());
//        }

        return planDTOs;
    }

    /**
     * Get box and whisker data for all of a districting's districts, by basis.
     * @param districtingId
     * @param basis
     * @param enacted true if using the data for the enacted districting plan
     * @param current alright someone help me I don't actually know what these are
     * @param postAlg same here
     * @param session
     * @return
     */
    public Set<Box> getBoxWhisker(long districtingId, Basis basis, boolean enacted,
                                  boolean current, boolean postAlg, HttpSession session){

        State state = (State) session.getAttribute("state");
        Districting districting = state.getEnacted();

        // TODO iterate through Districting.districts and
        //  use the BoxWhiskerRepository to get the box and whisker data of a certain district.

        double []upperExtreme={5,5,5,5};
        double []upperQuartile={15,15,15,25};
        double []median={25,25,25,25};
        double []lowerQuartile={35,35,35,35};
        double []lowerExtreme={45,45,35,45};

        return null;
    }

    public void setLimits(double minPopulationEquality, double minCompactness,HttpSession session){

        Algorithm alg = provider.getObject();
        alg.setMinPopulationEquality(minPopulationEquality);
        alg.setMinCompactness(minCompactness);
        jobs.put(session.getId(),alg);
    }

    public AlgorithmDTO startAlgorithm(int districtingNum, HttpSession session){
        State state = (State)session.getAttribute("state");
        Algorithm alg = jobs.get(session.getId());
        Districting plan =state.getEnacted();
        alg.setInProgressPlan(plan);
        alg.setPopulationMeasure((PopulationMeasure) session.getAttribute("PopType"));
        AlgorithmDTO dto = new AlgorithmDTO(plan.getMeasures(),0,true,null,null);
        alg.run();
        return dto;
    }

    public AlgorithmDTO getProgress(HttpSession session){
        return jobs.get(session.getId()).getProgress();
    }


    public AlgorithmDTO getResults(HttpSession session){
        return jobs.get(session.getId()).getResults();
    }

    public AlgorithmDTO stopAlgorithm(HttpSession session){
        Algorithm alg = jobs.get(session.getId());
        alg.setRunning(false);
        return alg.getProgress();
    }



}
