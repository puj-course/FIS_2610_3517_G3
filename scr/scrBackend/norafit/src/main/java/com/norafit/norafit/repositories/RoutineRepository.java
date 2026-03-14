package com.norafit.norafit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.norafit.norafit.entities.Routine;

public interface RoutineRepository extends JpaRepository<Routine, Integer> {

  //Necesasrio para guardar en la base de datos

}
