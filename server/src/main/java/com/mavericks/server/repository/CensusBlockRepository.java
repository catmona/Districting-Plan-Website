package com.mavericks.server.repository;

import com.mavericks.server.entity.CensusBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CensusBlockRepository extends JpaRepository<CensusBlock,String> {
    @Query(value = "SELECT cbs.id, cbs.precinctId, cbs.geometry, cbs.isBorderBlock FROM CensusBlocks cbs " +
            "JOIN Precincts ps ON ps.id = cbs.precinctId " +
            "JOIN Districts ds ON ds.id = ps.districtId " +
            "WHERE ds.id = :districtId;", nativeQuery = true)
    List<CensusBlock> findAllByDistrict(@Param("districtId") long districtId);

    @Query(value = "SELECT cbn.neighborId FROM CensusBlockNeighbors cbn " +
            "WHERE cbn.censusBlockId = :censusBlockId;", nativeQuery = true)
    List<String> findBlockNeighborsId(@Param("censusBlockId") String censusBlockId);

    @Query(value = "SELECT cb.* FROM CensusBlocks cb " +
            "JOIN CensusBlockNeighbors cbn ON cb.id = cbn.neighborId " +
            "WHERE cbn.censusBlockId = :censusBlockId;", nativeQuery = true)
    List<CensusBlock> findBlockNeighbors(@Param("censusBlockId") String censusBlockId);
}
