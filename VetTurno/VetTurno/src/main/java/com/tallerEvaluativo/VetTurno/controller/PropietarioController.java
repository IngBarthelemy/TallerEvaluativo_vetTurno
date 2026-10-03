package com.tallerEvaluativo.VetTurno.controller;

import com.tallerEvaluativo.VetTurno.dto.PropietarioDTO;
import com.tallerEvaluativo.VetTurno.dto.PropietarioRequest;
import com.tallerEvaluativo.VetTurno.service.PropietarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propietarios")
public class PropietarioController {

    private final PropietarioService service;

    public PropietarioController(PropietarioService service) {
        this.service = service;
    }

    @Operation(
            summary = "Crear propietario",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping
    public ResponseEntity<PropietarioDTO> crear(
            @Valid @RequestBody PropietarioRequest request) {

        return ResponseEntity
                .status(201)
                .body(service.crear(request));
    }

    @Operation(
            summary = "Listar propietarios",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping
    public ResponseEntity<List<PropietarioDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}