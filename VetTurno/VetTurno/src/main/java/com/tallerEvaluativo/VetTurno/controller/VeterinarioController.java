package com.tallerEvaluativo.VetTurno.controller;

import com.tallerEvaluativo.VetTurno.dto.VeterinarioDTO;
import com.tallerEvaluativo.VetTurno.service.VeterinarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioController {

    private final VeterinarioService service;

    public VeterinarioController(VeterinarioService service) {
        this.service = service;
    }

    @Operation(
            summary = "Crear veterinario",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping
    public ResponseEntity<VeterinarioDTO> crear(
            @Valid @RequestBody VeterinarioDTO request) {

        return ResponseEntity
                .status(201)
                .body(service.crear(request));
    }

    @Operation(
            summary = "Listar veterinarios",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping
    public ResponseEntity<List<VeterinarioDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}