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
        String enactedId = state.getEnacted().getId();
        districtings.stream().filter(d -> !d.getId().equals(enactedId)).map(d -> d.makeDistrictDTO()).forEach(dto -> plansPreview.add(dto));
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

    public BoxWhiskerPlotDTO getBoxWhisker(String districtingId, Basis basis, boolean selected, boolean enacted,
                                           HttpSession session){
        State state = (State) session.getAttribute("state");
        BoxWhiskerPlotDTO dto = new BoxWhiskerPlotDTO();
        dto.setBoxWhisker(state.getBoxWhiskerByBasis(basis));
        List<PopulationCopy> districtPoints = new ArrayList<>();
        // user wants selected points and selected is not enacted
        if (!districtingId.equals(state.getEnacted().getId()) && selected) {
            Districting selectedPlan = state.getDistricting(districtingId);
            districtPoints = new ArrayList<>();
            for (District d: selectedPlan.getDistricts()) {
                districtPoints.add(d.getPopulation().get(0));
            }
            dto.setSelectedPoints(districtPoints);
        }
        if (enacted) {
            districtPoints = new ArrayList<>();
            for (District d: state.getEnacted().getDistricts()) {
                districtPoints.add(d.getPopulation().get(0));
            }
            dto.setEnactedPoints(districtPoints);
        }
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
        alg.setInProgressPlan(plan);
        alg.setPopulationMeasure((PopulationMeasure) session.getAttribute("PopType"));
        AlgorithmDTO dto = new AlgorithmDTO(plan.getMeasures().getPopulationEquality(),0,true,null,null, -1);
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

    public void removeAlg(HttpSession session){
        jobs.remove(session.getId());
    }

}
