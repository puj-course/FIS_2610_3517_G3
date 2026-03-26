package com.norafit.norafit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.norafit.norafit.entities.Routine;

public interface RoutineRepository extends JpaRepository<Routine, Integer> {

    List<Routine> findByUserId(Integer userId);

    @Modifying
    @Query("UPDATE Routine r SET r.routineName = :newName WHERE r.routineId = :id")
    void renameRoutine(@Param("id") Integer id, @Param("newName") String newName);
}
