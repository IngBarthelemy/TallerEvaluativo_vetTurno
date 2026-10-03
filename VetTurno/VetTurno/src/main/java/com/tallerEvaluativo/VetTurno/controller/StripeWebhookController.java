package com.tallerEvaluativo.VetTurno.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.tallerEvaluativo.VetTurno.model.Cita;
import com.tallerEvaluativo.VetTurno.model.EstadoPago;
import com.tallerEvaluativo.VetTurno.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
public class StripeWebhookController {

    private final CitaRepository citaRepository;

    private final String webhookSecret;

    public StripeWebhookController(
            CitaRepository citaRepository,
            @Value("${stripe.webhook-secret}") String webhookSecret) {

        this.citaRepository = citaRepository;
        this.webhookSecret = webhookSecret;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(
            @RequestBody String payload,
            @RequestHeader(value = "Stripe-Signature", required = false)
            String signature) {

        if (signature == null || signature.isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Falta la firma de Stripe");
        }

        final Event event;

        try {
            event = Webhook.constructEvent(
                    payload,
                    signature,
                    webhookSecret
            );

        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest()
                    .body("Firma inválida");
        }

        if ("checkout.session.completed".equals(event.getType())) {

            StripeObject stripeObject = event
                    .getDataObjectDeserializer()
                    .getObject()
                    .orElse(null);

            if (stripeObject instanceof Session session) {

                String citaId = session
                        .getMetadata()
                        .get("citaId");

                if (citaId != null) {
                    try {

                        Long id = Long.valueOf(citaId);

                        Cita cita = citaRepository
                                .findById(id)
                                .orElse(null);

                        if (cita != null) {

                            cita.setEstadoPago(
                                    EstadoPago.PAGADO
                            );

                            citaRepository.save(cita);
                        }

                    } catch (NumberFormatException e) {

                        return ResponseEntity.badRequest()
                                .body("citaId inválido");
                    }
                }
            }
        }

        return ResponseEntity.ok("OK");
    }
}