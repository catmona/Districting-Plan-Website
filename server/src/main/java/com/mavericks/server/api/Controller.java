package com.mavericks.server.api;

import com.mavericks.server.dto.*;
import com.mavericks.server.entity.Box;
import com.mavericks.server.entity.District;
import com.mavericks.server.entity.Election;
import com.mavericks.server.entity.PopulationCopy;
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

    @GetMapping(value = "boxwhiskers")
    public BoxWhiskerPlotDTO handleBoxWhisker(@RequestParam("districtingId")String districtingId,
                                              @RequestParam("basis") String basis,
                                              @RequestParam("selected")boolean selected,
                                              @RequestParam("enacted")boolean enacted,
                                              HttpSession session){
        return handler.getBoxWhisker(districtingId,mapBasisToEnum(basis),selected,enacted,session);
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
                                       HttpSession session){
        handler.setLimits(minPopulationEquality,session);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "algorithm")
    public AlgorithmDTO handleStartAlgorithm(@RequestParam("districingNum")String districtingNum, HttpSession session){
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

    @GetMapping(value = "removeAlgorithm")
    public ResponseEntity removeAlgorithm(HttpSession session){
        handler.removeAlg(session);
        return ResponseEntity.ok().build();
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
                return Basis.BLACK;
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
