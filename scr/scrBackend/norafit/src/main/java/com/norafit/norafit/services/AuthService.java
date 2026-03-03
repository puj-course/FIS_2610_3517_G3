package com.norafit.norafit.services;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import com.norafit.norafit.entities.User;
import com.norafit.norafit.repositories.UserRepository;
@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //registrar nuevo usuario 
    public User register(String username, String email, String password){

        // 1) Validaciones básicas
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("El username es obligatorio.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }
        if (!email.contains("@")) { // validación mínima por ahora
            throw new IllegalArgumentException("Email inválido.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }

        // 2) Validar que el email no exista
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }

        // 3) Crear el usuario 
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password); // luego se hashea
        user.setRole('U');
        user.setCreatedAt(LocalDate.now());
        // 4) Guardar en BD y devolver el guardado
        return userRepository.save(user);
    }

    //Entrar a la aplicación
    public User login(String email, String password) {

    if (email == null || email.isBlank()) {
        throw new IllegalArgumentException("El email es obligatorio.");
    }

    if (password == null || password.isBlank()) {
        throw new IllegalArgumentException("La contraseña es obligatoria.");
    }

    User user2 = userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

    if (!user2.getPassword().equals(password)) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }

     return user2;
}

}
