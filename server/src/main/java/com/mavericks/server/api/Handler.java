package com.mavericks.server.api;

//import com.mavericks.server.Algorithm;
import com.mavericks.server.Algorithm;
import com.mavericks.server.dto.*;
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

    public List<PopulationCopy> test(){
        Districting d = districtingRepo.getById("NVSW2488");
        List<District> dists = d.getDistricts();
        List<PopulationCopy> copy = new ArrayList<>();
        for (District dist : dists) {
            copy.add(dist.getPopulation().get(0));
        }
        int a  = 1+1;
        return copy;
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
        List<Districting> districtings = state.getDistrictings();
        districtings.stream().map(d -> d.makeDistrictDTO()).forEach(dto -> plansPreview.add(dto));
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
            return null;
        }

        PopulationMeasure popType = (PopulationMeasure) session.getAttribute("PopType");
        return districting.makePlanDTO(popType);
    }

    public BoxWhiskerPlotDTO getBoxWhisker(long districtingId, Basis basis, boolean enacted, boolean postAlg,
                                  HttpSession session){
        // do not overlay currently selected SeaWulf plan if districtingId = -1
        State state = (State) session.getAttribute("state");
        BoxWhiskerPlotDTO dto = new BoxWhiskerPlotDTO();
        dto.setBoxWhisker(state.getBoxWhiskerByBasis(basis));
        return dto;
    }

    public void setLimits(double minPopulationEquality, HttpSession session){

        Algorithm alg = provider.getObject();
        alg.setMinPopulationEquality(minPopulationEquality);
        jobs.put(session.getId(),alg);
    }

    public AlgorithmDTO startAlgorithm(String districtingNum, HttpSession session){
        State state = (State)session.getAttribute("state");
        Algorithm alg = jobs.get(session.getId());
        Districting plan = state.getDistricting(districtingNum);
        plan.getMeasures().setPopulationEqualityScore(1-plan.getMeasures().getPopulationEqualityScore());
        alg.setPopulationMeasure((PopulationMeasure) session.getAttribute("PopType"));
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
        alg.setFlag(false);
        return alg.getProgress();
    }

}
