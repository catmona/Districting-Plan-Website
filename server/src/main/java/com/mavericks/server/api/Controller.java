package com.mavericks.server.api;

import com.mavericks.server.dto.AlgorithmDTO;
import com.mavericks.server.dto.DistrictingDTO;
import com.mavericks.server.dto.PlanDTO;
import com.mavericks.server.dto.StateDTO;
import com.mavericks.server.entity.Box;
import com.mavericks.server.enumeration.Basis;
import com.mavericks.server.enumeration.PopulationMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8081", allowCredentials = "true")
@RequestMapping(path = "api2/")
public class Controller {
    private final Handler handler;


    @Autowired
    public Controller(Handler handler) {
        this.handler = handler;
    }

    @GetMapping(value = "getStateSummary")
    public StateDTO handleStateSummary(@RequestParam("state")String state, HttpSession session){

        session.setAttribute("PopType",PopulationMeasure.TOTAL);
        return handler.getStateSummary(state,session);
    }

    @PostMapping(value = "setPopulationType")
    public ResponseEntity handlePopulationType(@RequestParam("populationType")String populationType, HttpSession session){
        session.setAttribute("PopType",mapPopToEnum(populationType));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "districtings")
    public List<DistrictingDTO> handleDistrictings(HttpSession session){
        return handler.getDistrictings(session);
    }

    @GetMapping(value = "districtingSummary")
    public PlanDTO handleDistrictingSummary(@RequestParam("districtingId")String districtingId,
                                            HttpSession session){
        return handler.getDistrictingSummary(districtingId,session);
    }

    @GetMapping(value = "districtingSummaries")
    public List<PlanDTO> handleDistrictingSummaries(HttpSession session){
        return handler.getDistrictingSummaries(session);
    }

    @GetMapping(value = "boxwhiskers")
    public Box handleBoxWhisker(@RequestParam("districtingId")long districtingId,
                                @RequestParam("basis") String basis,
                                @RequestParam("enacted")boolean enacted,
                                @RequestParam("current")boolean current,
                                @RequestParam("postAlg")boolean postAlg, HttpSession session){

        // TODO implement this, i think it was meant to return a list of district BoxWhiskers for a certain basis
        handler.getBoxWhisker(districtingId,mapBasisToEnum(basis),enacted,current,postAlg,session);
        return null;
    }

    @PostMapping("mapfilter")
    public ResponseEntity handleSetFilter(@RequestParam("stateFlag")boolean stateFlag,
                                          @RequestParam("districtFlag") boolean districtFlag,
                                          @RequestParam("precinctFlag") boolean precinctFlag,
                                          @RequestParam("censusBlockFlag")boolean censusBlockFlag,
                                          @RequestParam("countyFlag") boolean countyFlag,HttpSession session){
        return ResponseEntity.ok().build();
    }

    @PostMapping("algorithmlimits")
    public ResponseEntity handleLimits(@RequestParam("minPopulationEquality") double minPopulationEquality,
                             @RequestParam("minCompactness") double minCompactness, HttpSession session){
        handler.setLimits(minPopulationEquality,minCompactness,session);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "algorithm")
    public AlgorithmDTO handleStartAlgorithm(@RequestParam("districingNum")int districtingNum, HttpSession session){
        return handler.startAlgorithm(districtingNum,session);
    }

    @GetMapping(value="algorithmProgress")
    public AlgorithmDTO handleGetProgress(HttpSession session){
        return handler.getProgress(session);
    }

    @GetMapping(value = "algorithmResults")
    public AlgorithmDTO handleGetResults(HttpSession session){
        return handler.getResults(session);
    }

    @GetMapping(value = "stopAlgorithm")
    public AlgorithmDTO handleStopAlgorithm(HttpSession session){
        return handler.stopAlgorithm(session);
    }

    private PopulationMeasure mapPopToEnum(String s){
        switch (s.toUpperCase()){
            case "TOTAL":
                return PopulationMeasure.TOTAL;
            case "CVAP":
                return PopulationMeasure.CVAP;
            default:
                return PopulationMeasure.VAP;
        }
    }

    private Basis mapBasisToEnum(String s){
        switch (s.toUpperCase()){
            case "WHITE":
                return Basis.WHITE;
            case "AFRICAN_AMERICAN":
                return Basis.AFRICAN_AMERICAN;
            case "ASIAN":
                return Basis.ASIAN;
            case "HISPANIC":
                return Basis.HISPANIC;
            case "DEMOCRAT":
                return Basis.DEMOCRAT;
            default:
                return Basis.REPUBLICAN;
        }
    }








}
