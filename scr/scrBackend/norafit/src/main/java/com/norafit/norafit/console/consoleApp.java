package com.norafit.norafit.console;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.entities.StrengthExercise;
import com.norafit.norafit.entities.StrengthSeries;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.facade.RoutineManagementFacade;
import com.norafit.norafit.factory.ExerciseFactory;
import com.norafit.norafit.factory.HIITCardioExerciseFactory;
import com.norafit.norafit.factory.SimpleCardioExerciseFactory;
import com.norafit.norafit.factory.StrengthExerciseFactory;
import com.norafit.norafit.services.AuthService;
import com.norafit.norafit.services.ExerciseService;
import com.norafit.norafit.services.HIITExecutionService;
import com.norafit.norafit.services.StrengthSeriesService;

@Component
public class consoleApp {

    private final AuthService authService;
    private final RoutineManagementFacade routineFacade;
    private final ExerciseService exerciseService;
    private final StrengthSeriesService strengthSeriesService;
    private final HIITExecutionService hiitExecutionService;

    private User usuarioActual;
    private Routine rutinaSeleccionada;

    public consoleApp(
            AuthService authService,
            RoutineManagementFacade routineFacade,
            ExerciseService exerciseService,
            StrengthSeriesService strengthSeriesService,
            HIITExecutionService hiitExecutionService) {
        this.authService = authService;
        this.routineFacade = routineFacade;
        this.exerciseService = exerciseService;
        this.strengthSeriesService = strengthSeriesService;
        this.hiitExecutionService = hiitExecutionService;
        this.usuarioActual = null;
        this.rutinaSeleccionada = null;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            try {
                if (usuarioActual == null) {
                    running = menuNoLogueado(sc);
                } else {
                    running = menuLogueado(sc);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("X " + e.getMessage());
            } catch (Exception e) {
                System.out.println("X Ocurrió un error inesperado: " + e.getMessage());
            }
        }

        sc.close();
        System.out.println("Programa finalizado.");
    }

    //----------------- MENÚ BÁSICO INICIAL -----------------
    private boolean menuNoLogueado(Scanner sc) {
        System.out.println("\n=== NORAFIT (CONSOLA) ===");
        System.out.println("1) Sign up");
        System.out.println("2) Login");
        System.out.println("3) Cambiar contraseña");
        System.out.println("0) Salir");
        System.out.print("Opción: ");

        String op = sc.nextLine().trim();

        switch (op) {
            case "1" -> handleRegister(sc);
            case "2" -> handleLogin(sc);
            case "3" -> handleChangePassword(sc);
            case "0" -> {
                return false;
            }
            default -> System.out.println("Opción inválida.");
        }

        return true;
    }

    //----------------- 1) REGISTRARSE -----------------
    private void handleRegister(Scanner sc) {
        System.out.println("\n--- SIGN UP ---");

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User creado = authService.register(username, email, password);
        System.out.println("Usuario registrado: " + creado.getUsername());
    }

    //----------------- 2) LOGIN -----------------
    private void handleLogin(Scanner sc) {
        System.out.println("\n--- LOGIN ---");

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        usuarioActual = authService.login(email, password);
        System.out.println("Login OK. Bienvenido/a, " + usuarioActual.getUsername());
    }

    //----------------- 3) CAMBIAR CONTRASEÑA -----------------
    private void handleChangePassword(Scanner sc) {
        System.out.println("\n--- CAMBIAR CONTRASEÑA ---");

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Nueva contraseña: ");
        String newPassword = sc.nextLine();

        User actualizado = authService.changePassword(email, newPassword);
        System.out.println("Contraseña actualizada para: " + actualizado.getEmail());
    }

    //----------------- MENÚ PRINCIPAL -----------------
    private boolean menuLogueado(Scanner sc) {
        System.out.println("\n=== MENÚ (Usuario: " + usuarioActual.getUsername() + ") ===");
        System.out.println("1) Gestionar Rutinas");
        System.out.println("2) Cerrar sesión");
        System.out.println("0) Salir");
        System.out.print("Opción: ");

        String op = sc.nextLine().trim();

        switch (op) {
            case "1" -> menuRutinas(sc);
            case "2" -> {
                usuarioActual = null;
                rutinaSeleccionada = null;
                System.out.println("Sesión cerrada.");
            }
            case "0" -> {
                return false;
            }
            default -> System.out.println("Opción inválida.");
        }

        return true;
    }

    //----------------- SUBMENÚ RUTINAS -----------------
    private void menuRutinas(Scanner sc) {
        System.out.println("\n--- GESTIÓN DE RUTINAS ---");
        System.out.println("1) Crear nueva rutina");
        System.out.println("2) Ver mis rutinas");
        System.out.println("3) Eliminar una rutina");
        System.out.println("4) Renombrar rutina");
        System.out.println("5) Seleccionar rutina para gestionar ejercicios");
        System.out.println("0) Volver al menú principal");
        System.out.print("Opción: ");

        String op = sc.nextLine().trim();

        switch (op) {
            case "1" -> handleCreateRoutine(sc);
            case "2" -> handleListRoutines();
            case "3" -> handleDeleteRoutine(sc);
            case "4" -> handleRenameRoutine(sc);
            case "5" -> handleSelectRoutine(sc);
            case "0" -> {
            }
            default -> System.out.println("Opción inválida.");
        }
    }

    //----------------- 1) CREAR RUTINA -----------------
    private void handleCreateRoutine(Scanner sc) {
        System.out.println("\n--- NUEVA RUTINA ---");
        System.out.print("Nombre de la rutina (ej. Día de Pecho): ");
        String nombre = sc.nextLine();

        try {
            Routine nueva = routineFacade.createRoutine(nombre, usuarioActual);
            System.out.println("Rutina '" + nueva.getRoutineName() + "' creada con éxito.");
        } catch (Exception e) {
            System.out.println("X Error al crear rutina: " + e.getMessage());
        }
    }

    //----------------- 2) VER RUTINAS -----------------
    private void handleListRoutines() {
        System.out.println("\n--- TUS RUTINAS ---");

        var rutinas = routineFacade.listUserRoutines(usuarioActual);

        if (rutinas == null || rutinas.isEmpty()) {
            System.out.println("Aún no tienes rutinas creadas.");
        } else {
            rutinas.forEach(r -> System.out.println(
                    "[" + r.getId() + "] " + r.getRoutineName() + " (Creada el: " + r.getCreatedAt() + ")"
            ));
        }
    }

    //----------------- 3) ELIMINAR RUTINA -----------------
    private void handleDeleteRoutine(Scanner sc) {
        handleListRoutines();
        System.out.print("\nIngresa el ID de la rutina que deseas eliminar: ");

        try {
            Long id = Long.parseLong(sc.nextLine());
            routineFacade.removeRoutine(id, usuarioActual);
            System.out.println("Rutina eliminada correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("X El ID debe ser un número válido.");
        } catch (Exception e) {
            System.out.println("X Error: " + e.getMessage());
        }
    }

    //----------------- 4) RENOMBRAR RUTINA -----------------
    private void handleRenameRoutine(Scanner sc) {
        handleListRoutines();
        System.out.print("\nIngresa el ID de la rutina a renombrar: ");

        try {
            Long id = Long.parseLong(sc.nextLine());
            System.out.print("Nuevo nombre: ");
            String nuevoNombre = sc.nextLine();

            routineFacade.renameRoutine(id, nuevoNombre, usuarioActual);
            System.out.println("Rutina actualizada con éxito.");
        } catch (NumberFormatException e) {
            System.out.println("X El ID debe ser un número válido.");
        } catch (Exception e) {
            System.out.println("X Error: " + e.getMessage());
        }
    }

    //----------------- 5) SELECCIONAR RUTINA -----------------
    private void handleSelectRoutine(Scanner sc) {
        handleListRoutines();
        System.out.print("\nIngrese el ID de la rutina que desea gestionar: ");

        String input = sc.nextLine().trim();
        if (input.isEmpty()) {
            return;
        }

        try {
            Long id = Long.parseLong(input);
            Routine encontrada = routineFacade.getRoutineById(id);

            if (encontrada != null) {
                this.rutinaSeleccionada = encontrada;
                menuDetalleRutina(sc);
            } else {
                System.out.println("No se encontró la rutina con ese ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor ingresa un número válido.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //----------------- SUBMENÚ EJERCICIOS -----------------
    private void menuDetalleRutina(Scanner sc) {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n--- GESTIONANDO RUTINA: " + rutinaSeleccionada.getRoutineName() + " ---");
            System.out.println("1) Añadir nuevo ejercicio");
            System.out.println("2) Ver ejercicios");
            System.out.println("3) Eliminar ejercicio");
            System.out.println("4) Renombrar ejercicio");
            System.out.println("5) Modificar repeticiones de StrengthSeries");
            System.out.println("6) Ejecutar rutina HIIT");
            System.out.println("0) Volver");
            System.out.print("Seleccione una opción: ");

            String opcion = sc.nextLine();
            switch (opcion) {
                case "1" -> handleAddExercise(sc);
                case "2" -> handleShowExercises();
                case "3" -> handleRemoveExercise(sc);
                case "4" -> handleRenameExercise(sc);
                case "5" -> handleUpdateStrengthSeriesRepetitions(sc);
                case "6" -> handleExecuteHIITRoutine(sc);
                case "0" -> volver = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    //----------------- MOSTRAR EJERCICIOS -----------------
    private void handleShowExercises() {
        try {
            this.rutinaSeleccionada = routineFacade.getRoutineById(rutinaSeleccionada.getId());

            System.out.println("\n--- EJERCICIOS EN: " + rutinaSeleccionada.getRoutineName() + " ---");
            List<Exercise> lista = rutinaSeleccionada.getExercises();

            if (lista == null || lista.isEmpty()) {
                System.out.println("No hay ejercicios registrados en esta rutina aún.");
            } else {
                for (Exercise ex : lista) {
                    System.out.println("- [" + ex.getId() + "] " + ex.getExerciseName());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar ejercicios: " + e.getMessage());
        }
    }

    //----------------- AÑADIR EJERCICIO -----------------
    private void handleAddExercise(Scanner sc) {
        System.out.println("\n--- NUEVO EJERCICIO ---");
        System.out.println("1) Fuerza");
        System.out.println("2) Cardio Simple");
        System.out.println("3) HIIT");
        System.out.print("Tipo: ");
        String tipo = sc.nextLine().trim();

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Descripción: ");
        String desc = sc.nextLine();

        ExerciseFactory factory;
        boolean esFuerza = false;

        switch (tipo) {
            case "1" -> {
                System.out.print("¿Usa peso adicional? (S/N): ");
                boolean peso = sc.nextLine().equalsIgnoreCase("S");
                factory = new StrengthExerciseFactory(peso);
                esFuerza = true;
            }
            case "2" -> {
                System.out.print("Duración (min): ");
                int dur = Integer.parseInt(sc.nextLine());
                System.out.print("Intensidad: ");
                String intens = sc.nextLine();
                factory = new SimpleCardioExerciseFactory(dur, intens);
            }
            case "3" -> {
                System.out.print("Rondas: ");
                int rounds = Integer.parseInt(sc.nextLine());
                System.out.print("Tiempo de trabajo (seg): ");
                int work = Integer.parseInt(sc.nextLine());
                System.out.print("Tiempo de descanso (seg): ");
                int rest = Integer.parseInt(sc.nextLine());
                factory = new HIITCardioExerciseFactory(rounds, work, rest);
            }
            default -> {
                System.out.println("Tipo no válido.");
                return;
            }
        }

        try {
            Exercise ejercicioCreado = exerciseService.addExercise(
                    rutinaSeleccionada.getId(),
                    factory,
                    nombre,
                    desc
            );

            if (esFuerza && ejercicioCreado instanceof StrengthExercise strengthExercise) {
                System.out.print("¿Cuántas series desea registrar?: ");
                int cantidadSeries = Integer.parseInt(sc.nextLine().trim());

                if (cantidadSeries <= 0) {
                    throw new IllegalArgumentException("Debe registrar al menos una serie.");
                }

                for (int i = 1; i <= cantidadSeries; i++) {
                    System.out.println("\n--- Serie " + i + " ---");

                    System.out.print("Repeticiones: ");
                    int repetitions = Integer.parseInt(sc.nextLine().trim());

                    float weight = 0;
                    if (strengthExercise.isHasWeight()) {
                        System.out.print("Peso: ");
                        weight = Float.parseFloat(sc.nextLine().trim());
                    }

                    System.out.print("Descanso en segundos: ");
                    int restTimeSeconds = Integer.parseInt(sc.nextLine().trim());

                    strengthSeriesService.createSeries(
                            strengthExercise,
                            i,
                            repetitions,
                            weight,
                            restTimeSeconds
                    );
                }
            }

            this.rutinaSeleccionada = routineFacade.getRoutineById(rutinaSeleccionada.getId());
            System.out.println("Ejercicio añadido con éxito.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Debes ingresar valores numéricos válidos.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //----------------- ELIMINAR EJERCICIO -----------------
    private void handleRemoveExercise(Scanner sc) {
        handleShowExercises();
        System.out.print("Ingrese el ID del ejercicio que desea eliminar: ");

        try {
            Long exId = Long.parseLong(sc.nextLine());

            exerciseService.deleteExercise(exId, rutinaSeleccionada.getId());
            this.rutinaSeleccionada = routineFacade.getRoutineById(rutinaSeleccionada.getId());

            System.out.println("Ejercicio eliminado con éxito.");
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //----------------- RENOMBRAR EJERCICIO -----------------
    private void handleRenameExercise(Scanner sc) {
        handleShowExercises();
        System.out.print("\nIngrese el ID del ejercicio que desea renombrar: ");

        try {
            Long exId = Long.parseLong(sc.nextLine().trim());

            System.out.print("Ingrese el nuevo nombre para el ejercicio: ");
            String nuevoNombre = sc.nextLine().trim();

            exerciseService.renameExercise(exId, nuevoNombre);
            this.rutinaSeleccionada = routineFacade.getRoutineById(rutinaSeleccionada.getId());

            System.out.println("Ejercicio actualizado correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID debe ser un número.");
        } catch (Exception e) {
            System.out.println("Error al renombrar: " + e.getMessage());
        }
    }

    //----------------- MODIFICAR REPETICIONES DE SERIES -----------------
    private void handleUpdateStrengthSeriesRepetitions(Scanner sc) {
        try {
            this.rutinaSeleccionada = routineFacade.getRoutineById(rutinaSeleccionada.getId());

            System.out.println("\n--- SERIES DE FUERZA EN: " + rutinaSeleccionada.getRoutineName() + " ---");
            boolean foundSeries = false;

            for (Exercise ex : rutinaSeleccionada.getExercises()) {
                if (ex instanceof StrengthExercise strengthExercise) {
                    System.out.println("Ejercicio: [" + strengthExercise.getId() + "] " + strengthExercise.getExerciseName());

                    List<StrengthSeries> seriesList = strengthSeriesService
                            .getSeriesByStrengthExerciseId(strengthExercise.getId());

                    if (seriesList == null || seriesList.isEmpty()) {
                        System.out.println("   Sin series registradas.");
                    } else {
                        foundSeries = true;
                        for (StrengthSeries series : seriesList) {
                            System.out.println("   Serie ID: " + series.getId()
                                    + " | Número: " + series.getSeriesNumber()
                                    + " | Repeticiones: " + series.getRepetitions()
                                    + " | Peso: " + series.getWeight()
                                    + " | Descanso: " + series.getRestTimeSeconds() + "s");
                        }
                    }
                }
            }

            if (!foundSeries) {
                System.out.println("No hay StrengthSeries disponibles para modificar.");
                return;
            }

            System.out.print("\nIngrese el ID de la StrengthSeries a modificar: ");
            Long seriesId = Long.parseLong(sc.nextLine().trim());

            System.out.print("Ingrese el nuevo numero de repeticiones: ");
            int nuevasRepeticiones = Integer.parseInt(sc.nextLine().trim());

            StrengthSeries actualizada = strengthSeriesService.updateRepetitions(seriesId, nuevasRepeticiones);
            System.out.println("Repeticiones actualizadas correctamente. Nuevo valor: " + actualizada.getRepetitions());

        } catch (NumberFormatException e) {
            System.out.println("Error: Debes ingresar valores numericos validos.");
        } catch (Exception e) {
            System.out.println("Error al actualizar repeticiones: " + e.getMessage());
        }
    }

        //----------------- EJECUTAR RUTINA HIIT -----------------
    private void handleExecuteHIITRoutine(Scanner sc) {
        try {
            this.rutinaSeleccionada = routineFacade.getRoutineById(rutinaSeleccionada.getId());

            if (!hiitExecutionService.routineHasHIIT(rutinaSeleccionada)) {
                System.out.println("La rutina seleccionada no contiene ejercicios HIIT.");
                return;
            }

            System.out.println("\n--- EJECUCIÓN DE RUTINA HIIT ---");
            System.out.println("1) Tiempo real");
            System.out.println("2) Simulación rápida");
            System.out.print("Seleccione el modo de ejecución: ");

            String option = sc.nextLine().trim();
            boolean realTime;

            switch (option) {
                case "1" -> realTime = true;
                case "2" -> realTime = false;
                default -> {
                    System.out.println("Modo no válido.");
                    return;
                }
            }

            List<String> events = hiitExecutionService.executeRoutine(rutinaSeleccionada, realTime);

            System.out.println();
            for (String event : events) {
                System.out.println(event);
            }

        } catch (Exception e) {
            System.out.println("Error al ejecutar la rutina HIIT: " + e.getMessage());
        }
    }
}
