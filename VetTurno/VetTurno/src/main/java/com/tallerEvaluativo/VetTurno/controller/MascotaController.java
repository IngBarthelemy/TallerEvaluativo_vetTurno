package com.tallerEvaluativo.VetTurno.controller;

import com.tallerEvaluativo.VetTurno.dto.MascotaDTO;
import com.tallerEvaluativo.VetTurno.dto.MascotaRequest;
import com.tallerEvaluativo.VetTurno.service.MascotaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService service;

    public MascotaController(MascotaService service) {
        this.service = service;
    }

    @Operation(
            summary = "Crear mascota",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping
    public ResponseEntity<MascotaDTO> crear(
            @Valid @RequestBody MascotaRequest request) {

        return ResponseEntity
                .status(201)
                .body(service.crear(request));
    }

    @Operation(
            summary = "Listar mascotas",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping
    public ResponseEntity<List<MascotaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}