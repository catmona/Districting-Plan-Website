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
import com.mavericks.server.repository.DistrictElectionRepository;
import com.mavericks.server.repository.DistrictingRepository;
import com.mavericks.server.repository.PopulationRepository;
import com.mavericks.server.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

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
    public DistrictingRepository distRepo;
    @Autowired
    public PopulationRepository popRepo;
    @Autowired
    public DistrictElectionRepository distElectionRepo;

    private final Map<String,Algorithm> jobs;

    //private final CensusBlockPopulationRepository;

    @Autowired
    public Handler(Map<String, Algorithm> jobs) {
        this.jobs = jobs;
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
        Optional<Districting> districting = state.getDistrictings()
                .stream().filter(d -> d.getId() == districtingId).findFirst();

        if (!districting.isPresent()) {
            // districting plan not found
        }

        return districting.get().makePlanDTO();

//        State state = (State) session.getAttribute("state");
//        Districting districting = state.getDistrictings().get((int)districtingId);
//        PlanDTO planDTO= districting.makePlanDTO();
//        return planDTO;
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
        Algorithm alg = new Algorithm(minPopulationEquality,minCompactness);
        jobs.put(session.getId(),alg);
    }

    public AlgorithmDTO startAlgorithm(int districtingNum, HttpSession session){
        State state = (State)session.getAttribute("state");
        Algorithm alg = jobs.get(session.getId());
        Districting plan =state.getDistrictings().get(districtingNum);
        alg.setInProgressPlan(plan);
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
