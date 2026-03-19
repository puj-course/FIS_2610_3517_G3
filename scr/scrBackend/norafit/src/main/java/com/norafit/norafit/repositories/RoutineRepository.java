package com.norafit.norafit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.norafit.norafit.entities.Routine;

public interface RoutineRepository extends JpaRepository<Routine, Integer> {
    List<Routine> findByUserId(Integer userId);
}
