package com.mavericks.server.service;

import com.mavericks.server.entity.CensusBlockPopulation;
import com.mavericks.server.entity.Demographic;
import com.mavericks.server.entity.PopulationMeasure;
import com.mavericks.server.repository.CensusBlockPopulationRepository;
import com.mavericks.server.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StateService {
    @Autowired
    private StateRepository stateRepo;
    @Autowired
    private CensusBlockPopulationRepository cbRepo;

}
