package com.norafit.norafit.console;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.norafit.norafit.entities.Exercise;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.factory.ExerciseFactory;
import com.norafit.norafit.factory.HIITCardioExerciseFactory;
import com.norafit.norafit.factory.SimpleCardioExerciseFactory;
import com.norafit.norafit.factory.StrengthExerciseFactory;
import com.norafit.norafit.services.AuthService;
import com.norafit.norafit.services.ExerciseService;
import com.norafit.norafit.services.RoutineService;
import com.norafit.norafit.facade.RoutineManagementFacade;

@Component
public class consoleApp {
  
   private final AuthService authService;
   private final RoutineService routineService;
   private final ExerciseService exerciseService;

   private User usuarioActual;


   public consoleApp(AuthService authService, RoutineService routineService, ExerciseService exerciseService) {
       this.authService = authService;
       this.routineService = routineService;
       this.exerciseService = exerciseService;
       this.usuarioActual = null;
   }


   public void start(){
       Scanner sc = new Scanner(System.in); // para leer lo que el usuario escriba por consola
       boolean running = true; //controla si el programa se sigue ejecutando
       while(running){ //mientras running sea verdadero muestra menú y procesa opciones
           try{
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
        private boolean menuNoLogueado(Scanner sc) { //menu en caso de que la persona no se haya logueado todavía
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
               case "0" -> { return false; }
               default -> System.out.println("Opción inválida.");
           }


       return true;
   }
   

// -----------1) REGISTRARSE (DE MENU BÁSICO) -----------------
   private void handleRegister(Scanner sc) {
       System.out.println("\n--- SIGN UP ---");


       System.out.print("Username: ");
       String username = sc.nextLine();


       System.out.print("Email: ");
       String email = sc.nextLine();


       System.out.print("Password: ");
       String password = sc.nextLine();


       User creado = authService.register(username, email, password);
       System.out.println(" Usuario registrado: " + creado.getUsername());


       // Opcional: iniciar sesión automáticamente
       // usuarioActual = creado;
   }

// -----------2) LOGIN (DE MENU BÁSICO) -----------------
   private void handleLogin(Scanner sc) {
       System.out.println("\n--- LOGIN ---");


       System.out.print("Email: ");
       String email = sc.nextLine();


       System.out.print("Password: ");
       String password = sc.nextLine();


       usuarioActual = authService.login(email, password);
       System.out.println("Login OK. Bienvenido/a, " + usuarioActual.getUsername());
   }


   // -----------3) CAMBIAR CONTRASEÑA (DE MENU BÁSICO) -----------------
   private void handleChangePassword(Scanner sc) {
       System.out.println("\n--- CAMBIAR CONTRASEÑA ---");


       System.out.print("Email: ");
       String email = sc.nextLine();


       System.out.print("Nueva contraseña: ");
       String newPassword = sc.nextLine();


       User actualizado = authService.changePassword(email, newPassword);
       System.out.println("Contraseña actualizada para: " + actualizado.getEmail());
   }

//----------------- MENÚ PRINCIPAL (DESPUÉS DE LOGUEARSE) -----------------
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
               System.out.println(" Sesión cerrada.");
           }
           case "0" -> { return false; }
           default -> System.out.println("Opción inválida.");
       }


       return true;
   }
// -----------------1) SUBMENÚ DE RUTINAS (DESPUÉS DE LOGUEARSE) -----------------
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
            case "0" -> { /* No hace nada, vuelve solo */ }
            default -> System.out.println("Opción inválida.");
        }
    }
    private Routine rutinaSeleccionada; // Variable global en la clase para guardar el contexto

    //------------------1) CREAR RUTINA -----------------
    private void handleCreateRoutine(Scanner sc) {
        System.out.println("\n--- NUEVA RUTINA ---");
        System.out.print("Nombre de la rutina (ej. Día de Pecho): ");
        String nombre = sc.nextLine();

        try {
            // Llamamos al método que creamos en el RoutineService
            Routine nueva = routineService.createRoutine(nombre, usuarioActual);
            System.out.println("✅ Rutina '" + nueva.getRoutineName() + "' creada con éxito.");
        } catch (Exception e) {
            System.out.println("X Error al crear rutina: " + e.getMessage());
        }
    }

    //-------------------2) VER RUTINAS -----------------
    private void handleListRoutines() {
    System.out.println("\n--- TUS RUTINAS ---");
    
    var rutinas = routineService.getRoutinesByUser(usuarioActual); 
    
    if (rutinas == null || rutinas.isEmpty()) {
        System.out.println("Aún no tienes rutinas creadas.");
    } else {
        // Añadimos r.getId() al principio para que sea fácil de identificar
        rutinas.forEach(r -> System.out.println(
            "[" + r.getId() + "] " + r.getRoutineName() + " (Creada el: " + r.getCreatedAt() + ")"
        ));
      }
    }

    //-------------------3) ELIMINAR RUTINA -----------------

    private void handleDeleteRoutine(Scanner sc) {
        handleListRoutines(); // Primero se las mostramos para que vea los IDs
        System.out.print("\nIngresa el ID de la rutina que deseas eliminar: ");
        
        try {
            Long id = Long.parseLong(sc.nextLine());
            routineService.removeRoutine(id, usuarioActual);
            System.out.println("✅ Rutina eliminada correctamente.");
        } catch (NumberFormatException e) {
            System.out.println("X El ID debe ser un número válido.");
        } catch (Exception e) {
            System.out.println("X Error: " + e.getMessage());
        }
    }

    //-------------------4) RENOMBRAR RUTINA -----------------

    private void handleRenameRoutine(Scanner sc) {
        handleListRoutines(); // Mostramos IDs
        System.out.print("\nIngresa el ID de la rutina a renombrar: ");
        
        try {
            Long id = Long.parseLong(sc.nextLine());
            System.out.print("Nuevo nombre: ");
            String nuevoNombre = sc.nextLine();

            routineService.renameRoutine(id, nuevoNombre, usuarioActual);
            System.out.println("✅ Rutina actualizada con éxito.");
        } catch (Exception e) {
            System.out.println("X Error: " + e.getMessage());
        }
    }

    //-------------------5) SELECCIONAR RUTINA PARA GESTIONAR EJERCICIOS -----------------

    private void handleSelectRoutine(Scanner sc) {
        handleListRoutines(); // Muestra las rutinas
        System.out.print("\nIngrese el ID de la rutina que desea gestionar: ");
        
        String input = sc.nextLine().trim(); // Leemos como String para evitar errores
        if (input.isEmpty()) return;

        try {
            Long id = Long.parseLong(input);
            // 1. Buscamos la rutina
            Routine encontrada = routineService.getRoutineById(id);
            
            if (encontrada != null) {
                // 2. LA ASIGNAMOS A LA VARIABLE GLOBAL
                this.rutinaSeleccionada = encontrada;
                // 3. ENTRAMOS AL SUBMENÚ
                menuDetalleRutina(sc);
            } else {
                System.out.println("❌ No se encontró la rutina con ese ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Error: Por favor ingresa un número válido.");
        } catch (Exception e) {
            // Imprime el error real para saber qué pasa
            System.out.println("❌ Error inesperado: " + e.getMessage());
            e.printStackTrace(); // Esto te dirá la línea exacta del error en la consola
        }
    }


    //------------------- SUBMENÚ PARA GESTIONAR EJERCICIOS DE LA RUTINA SELECCIONADA -----------------
    private void menuDetalleRutina(Scanner sc) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIONANDO RUTINA: " + rutinaSeleccionada.getRoutineName() + " ---");
            System.out.println("1) Añadir nuevo ejercicio");
            System.out.println("2) Ver ejercicios");
            System.out.println("3) Eliminar ejercicio");
            System.out.println("4) Renombrar ejercicio");
            System.out.println("0) Volver");
            System.out.print("Seleccione una opción: ");

            String opcion = sc.nextLine();
            switch (opcion) {
                case "1" -> handleAddExercise(sc);
                case "2" -> handleShowExercises();
                case "3" -> handleRemoveExercise(sc);
                case "4" -> handleRenameExercise(sc);
                case "0" -> volver = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    //------------------- MOSTRAR EJERCICIOS DE LA RUTINA SELECCIONADA -----------------
    private void handleShowExercises() {
        try {
            // Refrescamos la rutina para que traiga los ejercicios recién guardados
            this.rutinaSeleccionada = routineService.getRoutineById(rutinaSeleccionada.getId());
            
            System.out.println("\n--- EJERCICIOS EN: " + rutinaSeleccionada.getRoutineName() + " ---");
            List<Exercise> lista = rutinaSeleccionada.getExercises();

            if (lista == null || lista.isEmpty()) {
                System.out.println("No hay ejercicios registrados en esta rutina aún.");
            } else {
                for (Exercise ex : lista) {
                    System.out.println("- [" + ex.getId() + "] " + ex.getExerciseName());
                    // ... tus otros prints (descripción, peso, etc.)
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error al mostrar ejercicios: " + e.getMessage());
        }
    }

    //------------------- AÑADIR NUEVO EJERCICIO -----------------
      
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

    switch (tipo) {
        case "1" -> {
            System.out.print("¿Usa peso adicional? (S/N): ");
            boolean peso = sc.nextLine().equalsIgnoreCase("S");
            factory = new StrengthExerciseFactory(peso);
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
        exerciseService.addExercise(rutinaSeleccionada.getId(), factory, nombre, desc);
        this.rutinaSeleccionada = routineService.getRoutineById(rutinaSeleccionada.getId());
        System.out.println("✅ Ejercicio añadido con éxito.");
    } catch (Exception e) {
        System.out.println("❌ Error: " + e.getMessage());
        }
    }
    //------------------- ELIMINAR EJERCICIO DE LA RUTINA SELECCIONADA ----------------- 
     private void handleRemoveExercise(Scanner sc) {
    handleShowExercises(); // Mostramos la lista para que vea los IDs
    System.out.print("Ingrese el ID del ejercicio que desea eliminar: ");
    
    try {
        Long exId = Long.parseLong(sc.nextLine());
        
        // Llamamos al servicio
        exerciseService.deleteExercise(exId, rutinaSeleccionada.getId());
        
        // Refrescamos la rutina local para que la lista se actualice en la consola
        this.rutinaSeleccionada = routineService.getRoutineById(rutinaSeleccionada.getId());
        
        System.out.println("✅ Ejercicio eliminado con éxito.");
    } catch (NumberFormatException e) {
        System.out.println("❌ ID inválido.");
    } catch (Exception e) {
        System.out.println("❌ Error: " + e.getMessage());
    }
}
//------------------- RENOMBRAR EJERCICIO DE LA RUTINA SELECCIONADA -----------------
private void handleRenameExercise(Scanner sc) {
    handleShowExercises(); // Mostramos la lista para que el usuario vea los IDs actuales
    System.out.print("\nIngrese el ID del ejercicio que desea renombrar: ");
    
    try {
        Long exId = Long.parseLong(sc.nextLine().trim());
        
        System.out.print("Ingrese el nuevo nombre para el ejercicio: ");
        String nuevoNombre = sc.nextLine().trim();

        // Llamamos al service (que ya tiene la validación que agregaste)
        exerciseService.renameExercise(exId, nuevoNombre);
        
        // Refrescamos la rutina para ver los cambios reflejados
        this.rutinaSeleccionada = routineService.getRoutineById(rutinaSeleccionada.getId());
        
        System.out.println("✅ Ejercicio actualizado correctamente.");
    } catch (NumberFormatException e) {
        System.out.println("❌ Error: El ID debe ser un número.");
    } catch (Exception e) {
        System.out.println("❌ Error al renombrar: " + e.getMessage());
    }
 }
}





