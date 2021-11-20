package com.mavericks.server.repository;

import com.mavericks.server.entity.BoxWhisker;
import com.mavericks.server.enumeration.Basis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface BoxWhiskerRepository extends JpaRepository<BoxWhisker,Long> {
    @Query(value = "SELECT * FROM BoxWhisker WHERE districtId = :districtId " +
            "AND basisType = :basisType ", nativeQuery = true)
    Set<BoxWhisker> findAllDistrictBoxWhiskersByBasis(
            @Param("districtId") long districtId,
            @Param("basisType") Basis basisType);
}
