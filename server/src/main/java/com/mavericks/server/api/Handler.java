package com.mavericks.server.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.Map;

@Component
public class Handler {

    private final Map<Long,Object[]> jobs;

    @Autowired
    public Handler(Map<Long, Object[]> jobs) {
        this.jobs = jobs;
    }

    public Map<String,Object> getStateSummary(long stateId){

        Map<String,Object> m= new Hashtable<>();
        m.put("hi","mom");
        return m;
    }


    public Map<String,Object> getDistrictings(long stateId){
        return new Hashtable<>();
    }

    public Map<String,Object> getDistrictingSummary(long districtingId){
        return new Hashtable<>();
    }

    public Map<String,Object> getBoxWhisker(long stateId,long districtingId, long demographicId,boolean enacted,
                                               boolean current){
        return new Hashtable<>();
    }


    public long setLimits(double minPopulationEquality, double minCompactness){
        return 0;
    }

    public Map<String,Object> startAlgorithm(long threadId){
        return new Hashtable<>();
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
