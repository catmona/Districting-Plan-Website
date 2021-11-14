package com.mavericks.server.repository;

import com.mavericks.server.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State,String> {
}
