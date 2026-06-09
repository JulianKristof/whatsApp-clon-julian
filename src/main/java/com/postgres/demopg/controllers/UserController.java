package com.postgres.demopg.controllers;

import com.postgres.demopg.models.User;
import com.postgres.demopg.payload.request.UpdateProfileRequest;
import com.postgres.demopg.payload.response.UserResponse;
import com.postgres.demopg.repository.UserRepository;
import com.postgres.demopg.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    private User getCurrentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetailsImpl)) {
            throw new RuntimeException("No autenticado");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication) {
        User user = getCurrentUser(authentication);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateCurrentUser(
            @RequestBody UpdateProfileRequest request,
            Authentication authentication
    ) {
        User user = getCurrentUser(authentication);

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            user.setName(request.getName().trim());
        }

        if (request.getAvatar() != null && !request.getAvatar().trim().isEmpty()) {
            user.setAvatar(request.getAvatar().trim().substring(0, 1).toUpperCase());
        }

        if (request.getProfileImageBase64() != null) {
            user.setProfileImageBase64(request.getProfileImageBase64());
            user.setProfileImageMimeType(request.getProfileImageMimeType());
        }

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(new UserResponse(savedUser));
    }

    @GetMapping
    public ResponseEntity<?> getUsers(Authentication authentication) {
        User currentUser = getCurrentUser(authentication);

        List<UserResponse> users = userRepository.findAll()
                .stream()
                .filter(user -> !user.getId().equals(currentUser.getId()))
                .map(UserResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }
}