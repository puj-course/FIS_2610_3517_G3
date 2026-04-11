package com.norafit.norafit.facade;

import java.util.List;

import org.springframework.stereotype.Component;

import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.services.RoutineService;

@Component
public class RoutineManagementFacade {

    private final RoutineService routineService;

    public RoutineManagementFacade(RoutineService routineService) {
        this.routineService = routineService;
    }

    public Routine createRoutine(String name, User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario es obligatorio.");
        }
        return routineService.createRoutine(name, user);
    }

    public List<Routine> listUserRoutines(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario es obligatorio.");
        }
        return routineService.getRoutinesByUser(user);
    }

    public Routine renameRoutine(Long routineId, String newName, User user) {
        if (routineId == null) {
            throw new IllegalArgumentException("El ID de la rutina es obligatorio.");
        }
        if (user == null) {
            throw new IllegalArgumentException("El usuario es obligatorio.");
        }
        return routineService.renameRoutine(routineId, newName, user);
    }

    public void removeRoutine(Long routineId, User user) {
        if (routineId == null) {
            throw new IllegalArgumentException("El ID de la rutina es obligatorio.");
        }
        if (user == null) {
            throw new IllegalArgumentException("El usuario es obligatorio.");
        }
        routineService.removeRoutine(routineId, user);
    }

    public Routine getRoutineById(Long routineId) {
        if (routineId == null) {
            throw new IllegalArgumentException("El ID de la rutina es obligatorio.");
        }
        return routineService.getRoutineById(routineId);
    }
}
