package com.mavericks.server.service;

import com.mavericks.server.entity.CensusBlock;
import com.mavericks.server.entity.State;
import com.mavericks.server.repository.DistrictElectionRepository;
import com.mavericks.server.repository.DistrictingRepository;
import com.mavericks.server.repository.PopulationRepository;
import com.mavericks.server.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryService  {
    // made these public just for testing

    @Autowired
    public StateRepository stateRepo;
    @Autowired
    public DistrictingRepository distRepo;
    @Autowired
    public PopulationRepository popRepo;
    @Autowired
    public DistrictElectionRepository distElectionRepo;

}
