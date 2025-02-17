package com.example.chatbot.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.chatbot.Model.Role;
import com.example.chatbot.Model.User;
import com.example.chatbot.Repository.UserRepository;
import com.example.chatbot.Config.AuthResponse;
import com.example.chatbot.Config.LoginRequest;
import com.example.chatbot.Config.RegisterRequest;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Constructor manual para inyección de dependencias
    @Autowired
    public AuthService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest request) {
        // Autenticación sin contraseña
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verifica si el teléfono proporcionado coincide con el del usuario
        if (!user.getTelefono().equals(request.getTelefono())) {
            throw new RuntimeException("Teléfono incorrecto");
        }

        // Genera un token para el usuario autenticado
        String token = jwtService.getToken(user);

        // Crear AuthResponse sin builder
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        return response;
    }

    public AuthResponse register(RegisterRequest request) {
        // Crea un nuevo usuario y lo guarda
        User user = new User();
        user.setUsername(request.getUsername());
        user.setTelefono(request.getTelefono());
        user.setRole(Role.USER);

        userRepository.save(user);

        // Genera un token para el nuevo usuario registrado
        AuthResponse response = new AuthResponse();
        response.setToken(jwtService.getToken(user));
        return response;
    }
}
