package com.mavericks.server.api;

import com.mavericks.server.dto.Point;
import com.mavericks.server.dto.StateDTO;
import com.mavericks.server.entity.Districting;
import com.mavericks.server.entity.State;
import com.mavericks.server.service.DistrictingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public StateDTO getStateSummary(String stateName, HttpSession session){

        ///make query here
        //dummy object below
        State state = new State();
        return state.makeDTO();
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

    /**
     * Sets the algorithm constraints
     * @param minPopulationEquality deviation from ideal population
     * @param minCompactness the geometric compactness computed by polsby popper
     * @return the thread id
     */
    public long setLimits(double minPopulationEquality, double minCompactness){
//        Algorithm alg = new Algorithm(minPopulationEquality,minCompactness,100000);
//        Thread t = new Thread(alg);
//        Object[] pair={t,alg};
//        jobs.put(t.getId(),pair);
//        return t.getId();
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
