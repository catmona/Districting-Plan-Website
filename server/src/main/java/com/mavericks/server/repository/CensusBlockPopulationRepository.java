package com.mavericks.server.repository;

import com.mavericks.server.entity.CBPopKey;
import com.mavericks.server.entity.CensusBlockPopulation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CensusBlockPopulationRepository extends JpaRepository<CensusBlockPopulation, CBPopKey> {
    List<CensusBlockPopulation> findByCensusBlockId(long censusBlockId);
}
