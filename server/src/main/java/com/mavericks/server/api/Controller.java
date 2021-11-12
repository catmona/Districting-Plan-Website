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
    public Map<String,Object> handleStateSummary(@RequestParam("stateId")long stateId, HttpSession session){

        session.setAttribute("state",Math.round(Math.random()*18));
        return handler.getStateSummary(stateId);
    }

    @PostMapping(value = "setPopulationType")
    public ResponseEntity handlePopulationType(@RequestParam("populationType")String populationType, HttpSession session){
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "districtings")
    public Map<String,Object> handleDistrictings(@RequestParam("stateId") long stateId,HttpSession session){
        System.out.println(session.getAttribute("state"));
        Map<String,Object> m= new Hashtable<>();
        m.put("state",session.getAttribute("state"));
        return m;
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
        return 0;
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










}
