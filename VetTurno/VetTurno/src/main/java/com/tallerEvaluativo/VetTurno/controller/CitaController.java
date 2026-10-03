package com.tallerEvaluativo.VetTurno.controller;

import com.tallerEvaluativo.VetTurno.dto.CitaDTO;
import com.tallerEvaluativo.VetTurno.dto.CitaRequest;
import com.tallerEvaluativo.VetTurno.service.CitaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@SecurityRequirement(name = "bearerAuth")
public class CitaController {

    private final CitaService service;

    public CitaController(CitaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CitaDTO> crear(
            @Valid @RequestBody CitaRequest request) {

        return ResponseEntity
                .status(201)
                .body(service.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<CitaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<List<CitaDTO>> listarPorVeterinario(
            @PathVariable Long veterinarioId) {

        return ResponseEntity.ok(
                service.listarPorVeterinario(veterinarioId)
        );
    }
}