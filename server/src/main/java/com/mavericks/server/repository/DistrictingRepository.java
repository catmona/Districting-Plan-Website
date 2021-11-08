package com.mavericks.server.repository;

import com.mavericks.server.entity.Districting;
import com.mavericks.server.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictingRepository extends JpaRepository<Districting,Integer> {
}
