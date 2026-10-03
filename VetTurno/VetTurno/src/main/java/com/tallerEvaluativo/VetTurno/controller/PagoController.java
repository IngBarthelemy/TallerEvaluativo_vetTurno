package com.tallerEvaluativo.VetTurno.controller;

import com.stripe.exception.StripeException;
import com.tallerEvaluativo.VetTurno.dto.PagoRequest;
import com.tallerEvaluativo.VetTurno.dto.PagoResponse;
import com.tallerEvaluativo.VetTurno.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @Operation(
            summary = "Crear checkout de pago",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/checkout")
    public ResponseEntity<PagoResponse> crearCheckout(
            @Valid @RequestBody PagoRequest request) {

        try {

            PagoResponse response =
                    pagoService.crearCheckout(
                            request.getCitaId()
                    );

            return ResponseEntity
                    .status(201)
                    .body(response);

        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }

    @Operation(
            summary = "Pago realizado correctamente"
    )
    @GetMapping("/success")
    public ResponseEntity<String> success() {

        return ResponseEntity.ok(
                "Pago realizado correctamente."
        );
    }

    @Operation(
            summary = "Pago cancelado"
    )
    @GetMapping("/cancel")
    public ResponseEntity<String> cancel() {

        return ResponseEntity.ok(
                "El pago fue cancelado."
        );
    }
}