package com.mavericks.server.repository;

import com.mavericks.server.entity.CensusBlock;
import com.mavericks.server.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District,String> {
    @Query(value = "SELECT dn.neighborId FROM DistrictNeighbors dn " +
            "WHERE dn.districtId = :districtId;", nativeQuery = true)
    List<CensusBlock> findBlockNeighbors(@Param("districtId") String districtId);
}
