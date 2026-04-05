package com.norafit.norafit.console;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.services.AuthService;
import org.springframework.stereotype.Component;
import com.norafit.norafit.services.RoutineServices;
import com.norafit.norafit.entities.Routine;
import com.norafit.norafit.entities.Exercises;
import java.util.List;

import java.util.Scanner;

@Component
public class consoleApp {
  
   private final AuthService authService;
   private User usuarioActual;
   private final RoutineServices routineServices;


   public consoleApp(AuthService authService, RoutineServices routineServices) {
    this.authService = authService;
    this.routineServices = routineServices;
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


   private void handleLogin(Scanner sc) {
       System.out.println("\n--- LOGIN ---");


       System.out.print("Email: ");
       String email = sc.nextLine();


       System.out.print("Password: ");
       String password = sc.nextLine();


       usuarioActual = authService.login(email, password);
       System.out.println("Login OK. Bienvenido/a, " + usuarioActual.getUsername());
   }


   private boolean menuLogueado(Scanner sc) {
       System.out.println("\n=== MENÚ (Usuario: " + usuarioActual.getUsername() + ") ===");
       System.out.println("1) Ver rutinas");
       System.out.println("2) Cerrar sesión");
       System.out.println("0) Salir");
       System.out.print("Opción: ");


       String op = sc.nextLine().trim();


       switch (op) {
           case "1" -> handleRutinas(sc);
           case "2" -> {
               usuarioActual = null;
               System.out.println("✅ Sesión cerrada.");
           }
           case "0" -> { return false; }
           default -> System.out.println("Opción inválida.");
       }


       return true;
   }

-------1) SUBMENÚ DE RUTINAS (DESPUÉS DE LOGUEARSE) ----------------- 

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

    private Routine rutinaSeleccionada; 

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
  
   //cambiar contraseña
   private void handleChangePassword(Scanner sc) {
       System.out.println("\n--- CAMBIAR CONTRASEÑA ---");


       System.out.print("Email: ");
       String email = sc.nextLine();


       System.out.print("Nueva contraseña: ");
       String newPassword = sc.nextLine();


       User actualizado = authService.changePassword(email, newPassword);
       System.out.println("Contraseña actualizada para: " + actualizado.getEmail());
   }

private void handleRutinas(Scanner sc) {
    System.out.println("\n--- RUTINAS ---");

    System.out.print("Ingrese el ID de la rutina: ");
    String input = sc.nextLine().trim();

    try {
        Integer routineId = Integer.parseInt(input);

        Routine routine = routineServices.getRoutineWithExercises(routineId);

        System.out.println("\nRutina: " + routine.getRoutineName());
        System.out.println("Ejercicios:");

        List<Exercises> exercises = routine.getExercises();

        if (exercises == null || exercises.isEmpty()) {
            System.out.println("  (Sin ejercicios)");
            return;
        }

        for (Exercises ex : exercises) {
            System.out.println(" - " + ex.getExerciseName());
        }

    } catch (NumberFormatException e) {
        System.out.println("ID inválido.");
    }
}
}
