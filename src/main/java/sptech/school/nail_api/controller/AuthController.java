package sptech.school.nail_api.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.nail_api.dto.auth.LoginRequest;
import sptech.school.nail_api.dto.auth.RegisterRequest;
import sptech.school.nail_api.dto.user.UserResponse;
import sptech.school.nail_api.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        UserResponse response = authService.login(request);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Integer> register(@Valid @RequestBody RegisterRequest request) {
        Integer id = authService.register(request);
        return ResponseEntity.status(201).body(id);
    }

}