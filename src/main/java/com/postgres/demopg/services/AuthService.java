package com.postgres.demopg.services;

import com.postgres.demopg.models.User;
import com.postgres.demopg.payload.request.LoginRequest;
import com.postgres.demopg.payload.request.SignupRequest;
import com.postgres.demopg.payload.response.JwtResponse;
import com.postgres.demopg.payload.response.MessageResponse;
import com.postgres.demopg.repository.UserRepository;
import com.postgres.demopg.security.jwt.JwtUtils;
import com.postgres.demopg.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder encoder,
            JwtUtils jwtUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setOnline(true);
        user.setLastSeen("en línea");
        userRepository.save(user);

        return ResponseEntity.ok(
                new JwtResponse(
                        jwt,
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getName(),
                        user.getAvatar(),
                        user.isOnline(),
                        user.getLastSeen()
                )
        );
    }

    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        if (signUpRequest.getUsername() == null || signUpRequest.getUsername().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("El username es obligatorio"));
        }

        if (signUpRequest.getEmail() == null || signUpRequest.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("El email es obligatorio"));
        }

        if (signUpRequest.getPassword() == null || signUpRequest.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("La contraseña es obligatoria"));
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Ese username ya existe"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Ese email ya existe"));
        }

        String name = signUpRequest.getName();

        if (name == null || name.trim().isEmpty()) {
            name = signUpRequest.getUsername();
        }

        String avatar = signUpRequest.getAvatar();

        if (avatar == null || avatar.trim().isEmpty()) {
            avatar = name.substring(0, 1).toUpperCase();
        }

        User user = new User(
                signUpRequest.getUsername().trim(),
                signUpRequest.getEmail().trim(),
                encoder.encode(signUpRequest.getPassword()),
                name.trim(),
                avatar.trim()
        );

        user.setOnline(false);
        user.setLastSeen("Sin actividad reciente");

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Usuario registrado correctamente"));
    }

    public ResponseEntity<?> logout(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl)) {
            return ResponseEntity.status(401).body(new MessageResponse("No autenticado"));
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setOnline(false);
        user.setLastSeen(getCurrentLastSeen());
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Sesión cerrada correctamente"));
    }

    private String getCurrentLastSeen() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'últ. vez hoy a las' h:mm a");

        String formatted = now.format(formatter)
                .replace("AM", "a. m.")
                .replace("PM", "p. m.");

        return formatted;
    }
}