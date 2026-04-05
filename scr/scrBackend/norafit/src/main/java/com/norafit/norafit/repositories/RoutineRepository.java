package com.norafit.norafit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.norafit.norafit.entities.Routine;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {
    // Busca por el ID del objeto User dentro de Routine
    List<Routine> findByUser_Id(Long userId);
}
