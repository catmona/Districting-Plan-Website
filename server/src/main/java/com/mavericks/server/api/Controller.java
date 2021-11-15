package com.mavericks.server.api;

import com.mavericks.server.dto.DistricitingDTO;
import com.mavericks.server.dto.StateDTO;
import com.mavericks.server.entity.PopulationMeasure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping(path = "api2/")
public class Controller {

    private final Handler handler;

    @Autowired
    public Controller(Handler handler) {
        this.handler = handler;
    }

    @GetMapping(value = "getStateSummary")
    public StateDTO handleStateSummary(@RequestParam("state")String state, HttpSession session){

        return handler.getStateSummary(state,session);
    }

    @PostMapping(value = "setPopulationType")
    public ResponseEntity handlePopulationType(@RequestParam("populationType")String populationType, HttpSession session){
        session.setAttribute("PopType",mapStringToEnum(populationType));
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "districtings")
    public List<DistricitingDTO> handleDistrictings(@RequestParam("state") String state, HttpSession session){
        return handler.getDistrictings(state,session);
    }

    @GetMapping(value = "districtingSummary")
    public Map<String,Object> handleDistrictingSummary(@RequestParam("districtingId")long districtingId,
                                                       HttpSession session){
        return new Hashtable<>();
    }

    @GetMapping(value = "boxwhiskers")
    public Map<String,Object> handleBoxWhisker(@RequestParam("stateId")long stateId,
                                               @RequestParam("districtingId")long districtingId,
                                               @RequestParam("demographicId") long demographicId,
                                               @RequestParam("enacted")boolean enacted,
                                               @RequestParam("current")boolean current,HttpSession session){
        return new Hashtable<>();
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
    public long handleLimits(@RequestParam("minPopulationEquality") double minPopulationEquality,
                             @RequestParam("minCompactness") double minCompactness){
        return handler.setLimits(minPopulationEquality,minCompactness);
    }

    @GetMapping(value = "algorithm")
    public Map<String,Object> handleStartAlgorithm(@RequestParam("threadId")long threadId){
        return new Hashtable<>();
    }

    @GetMapping(value="algorithmProgress")
    public Map<String,Object> handleGetProgress(@RequestParam("threadId") long threadId){
        return new Hashtable<>();
    }

    @GetMapping(value = "algorithmResults")
    public Map<String,Object> handleGetResults(@RequestParam("threadId") long threadId){
        return new Hashtable<>();
    }

    @GetMapping(value = "stopAlgorithm")
    public Map<String,Object> handleStopAlgorithm(@RequestParam("threadId") long threadId){
        return new Hashtable<>();
    }

    private PopulationMeasure mapStringToEnum(String s){
        switch (s){
            case "TOTAL":
                return PopulationMeasure.TOTAL;
            case "CVAP":
                return PopulationMeasure.CVAP;
            default:
                return PopulationMeasure.VAP;
        }
    }








}
