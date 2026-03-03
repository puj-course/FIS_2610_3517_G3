package com.norafit.norafit.console;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.services.AuthService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class consoleApp {
    
    private final AuthService authService;
    private User usuarioActual;

    public consoleApp(AuthService authService) {
        this.authService = authService;
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
            System.out.println("0) Salir");
            System.out.print("Opción: ");

            String op = sc.nextLine().trim(); 

            switch (op) {
                case "1" -> handleRegister(sc);
                case "2" -> handleLogin(sc);
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
        System.out.println("1) Rutinas (pendiente)");
        System.out.println("2) Cerrar sesión");
        System.out.println("0) Salir");
        System.out.print("Opción: ");

        String op = sc.nextLine().trim();

        switch (op) {
            case "1" -> System.out.println("Rutinas: cuando se tenga routineService.");
            case "2" -> {
                usuarioActual = null;
                System.out.println("✅ Sesión cerrada.");
            }
            case "0" -> { return false; }
            default -> System.out.println("Opción inválida.");
        }

        return true;
    }
    }

