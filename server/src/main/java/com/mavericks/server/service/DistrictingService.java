package com.mavericks.server.service;

import com.mavericks.server.entity.Districting;
import com.mavericks.server.repository.DistrictingRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DistrictingService {

    @Autowired
    private DistrictingRepository distRepo;

    public void testCreateDistricting(String test) {
        // this is just an example method that adds a Districting to the db.
        Districting dist = new Districting();
        dist.setTest(test);
        distRepo.save(dist); // save() adds the entity into the db.
    }

    public List<Districting> testSeeAllDistrictings() {
        // this is just an example method that returns all entries in the SQL Districting table.
        return distRepo.findAll();
    }
}
