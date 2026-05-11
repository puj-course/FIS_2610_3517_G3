package com.norafit.norafit.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.norafit.norafit.dto.response.RoutineResponse;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.facade.RoutineManagementFacade;
import com.norafit.norafit.repositories.UserRepository;

@RestController
@RequestMapping("/routines")
public class RoutineController {

    private final RoutineManagementFacade routineFacade;
    private final UserRepository userRepository;

    public RoutineController(RoutineManagementFacade routineFacade, UserRepository userRepository) {
        this.routineFacade = routineFacade;
        this.userRepository = userRepository;
    }
    @Transactional
    public RoutineResponse toResponse(Routine routine) {
    List<String> names = routine.getExercises()
        .stream()
        .map(e -> e.getExerciseName())
        .toList();
    return new RoutineResponse(
        routine.getId(),
        routine.getRoutineName(),
        routine.getCreatedAt(),
        routine.getTotalTimeSeconds(),
        names
    );
    }

    // GET /routines?userId=1
    @GetMapping
public ResponseEntity<List<RoutineResponse>> listRoutines(@RequestParam Long userId) {
    User user = userRepository.findById(userId.intValue())
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    List<RoutineResponse> response = routineFacade.listUserRoutines(user)
        .stream().map(this::toResponse).toList();
    return ResponseEntity.ok(response);
}

@PostMapping
public ResponseEntity<RoutineResponse> createRoutine(@RequestParam Long userId, @RequestBody CreateRoutineRequest request) {
    User user = userRepository.findById(userId.intValue())
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    return ResponseEntity.ok(toResponse(routineFacade.createRoutine(request.name(), user)));
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteRoutine(@PathVariable Long id, @RequestParam Long userId) {
    User user = userRepository.findById(userId.intValue())
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    routineFacade.removeRoutine(id, user);
    return ResponseEntity.ok().build();
}

@PutMapping("/{id}/rename")
public ResponseEntity<RoutineResponse> renameRoutine(@PathVariable Long id, @RequestParam Long userId, @RequestBody RenameRoutineRequest request) {
    User user = userRepository.findById(userId.intValue())
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    return ResponseEntity.ok(toResponse(routineFacade.renameRoutine(id, request.newName(), user)));
}

@GetMapping("/{id}")
public ResponseEntity<RoutineResponse> getRoutineById(@PathVariable Long id) {
    return ResponseEntity.ok(toResponse(routineFacade.getRoutineById(id)));
}

    record CreateRoutineRequest(String name) {}
    record RenameRoutineRequest(String newName) {}
}
