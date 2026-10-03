package com.tallerEvaluativo.VetTurno.controller;

import com.tallerEvaluativo.VetTurno.dto.AuthResponse;
import com.tallerEvaluativo.VetTurno.dto.LoginRequest;
import com.tallerEvaluativo.VetTurno.dto.RegistroRequest;
import com.tallerEvaluativo.VetTurno.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registrar(
            @Valid @RequestBody RegistroRequest request) {

        return ResponseEntity.ok(
                service.registrar(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                service.login(request)
        );
    }
}