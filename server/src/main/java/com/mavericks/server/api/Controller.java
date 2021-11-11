package com.mavericks.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;
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
    public Map<String,Object> handleStateSummary(long stateId, HttpSession session){

        return new Hashtable<>();
    }

    @PostMapping(value = "setPopulationType")
    public ResponseEntity handlePopulationType(String populationType, HttpSession session){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "districtings")
    public Map<String,Object> handleDistrictings(long stateId){
        return new Hashtable<>();
    }

    @GetMapping(value = "districtingSummary")
    public Map<String,Object> handleDistrictingSummary(long districtingId){
        return new Hashtable<>();
    }

    @GetMapping(value = "boxwhiskers")
    public Map<String,Object> handleBoxWhisker(long stateId,long districtingId, long demographicId,boolean enacted,
                                               boolean current){
        return new Hashtable<>();
    }

    @PostMapping("mapfilter")
    public ResponseEntity handleSetFilter(boolean stateFlag, boolean districtFlag, boolean precinctFlag,
                                          boolean censusBlockFlag, boolean countyFlag){
        return ResponseEntity.ok().build();
    }

    @PostMapping("algorithmlimits")
    public long handleLimits(double minPopulationEquality, double minCompactness){
        return 0;
    }

    @GetMapping(value = "algorithm")
    public Map<String,Object> handleStartAlgorithm(long threadId){
        return new Hashtable<>();
    }

    @GetMapping(value="algorithmProgress")
    public Map<String,Object> handleGetProgress(long threadId){
        return new Hashtable<>();
    }

    @GetMapping(value = "algorithmResults")
    public Map<String,Object> handleGetResults(long threadId){
        return new Hashtable<>();
    }

    @GetMapping(value = "stopAlgorithm")
    public Map<String,Object> handleStopAlgorithm(long threadId){
        return new Hashtable<>();
    }










}
