package com.mavericks.server.repository;

import com.mavericks.server.entity.Population;
import com.mavericks.server.enumeration.Demographic;
import com.mavericks.server.enumeration.PopulationMeasure;
import com.mavericks.server.enumeration.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PopulationRepository extends JpaRepository<Population,Long> {
    @Query(value = "SELECT * FROM Populations WHERE regionType = :regionType " +
            "AND regionId = :regionId " +
            "AND populationMeasureType = :populationMeasureType;", nativeQuery = true)
    List<Population> findAllRegionPopulationsByMeasure(
            @Param("regionType")Region regionType,
            @Param("regionId") long regionId,
            @Param("populationMeasureType") PopulationMeasure popMeasureType);

    @Query(value = "SELECT * FROM Populations WHERE regionType = :regionType " +
            "AND regionId = :regionId " +
            "AND populationMeasureType = :populationMeasureType " +
            "AND demographicType = :demographicType;", nativeQuery = true)
    List<Population> findAllRegionPopulationsByMeasureAndDemographic(
            @Param("regionType")Region regionType,
            @Param("regionId") long regionId,
            @Param("populationMeasureType") PopulationMeasure popMeasureType,
            @Param("demographicType") Demographic demographicType);
}
