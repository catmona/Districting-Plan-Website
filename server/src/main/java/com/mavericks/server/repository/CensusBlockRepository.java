package com.mavericks.server.repository;

import com.mavericks.server.entity.CensusBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CensusBlockRepository extends JpaRepository<CensusBlock,Long> {
    @Query(value = "SELECT cbs.id, cbs.precinctId, cbs.geometry, cbs.isBorderBlock FROM CensusBlocks cbs " +
            "JOIN Precincts ps ON ps.id = cbs.precinctId " +
            "JOIN Districts ds ON ds.id = ps.districtId " +
            "WHERE ds.id = :censusBlockId;", nativeQuery = true)
    Set<CensusBlock> findAllByCensusBlock(@Param("censusBlockId") long censusBlockId);
}
