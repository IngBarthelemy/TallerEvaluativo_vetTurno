package com.tallerEvaluativo.VetTurno.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.tallerEvaluativo.VetTurno.dto.PagoResponse;
import com.tallerEvaluativo.VetTurno.model.Cita;
import com.tallerEvaluativo.VetTurno.model.EstadoPago;
import com.tallerEvaluativo.VetTurno.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PagoService {

    private final CitaRepository citaRepository;

    private final String successUrl;
    private final String cancelUrl;

    public PagoService(
            CitaRepository citaRepository,
            @Value("${stripe.success-url}") String successUrl,
            @Value("${stripe.cancel-url}") String cancelUrl) {

        this.citaRepository = citaRepository;
        this.successUrl = successUrl;
        this.cancelUrl = cancelUrl;
    }

    public PagoResponse crearCheckout(Long citaId)
            throws StripeException {

        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cita no encontrada"
                        )
                );

        if (cita.getEstadoPago() == EstadoPago.PAGADO) {
            throw new RuntimeException(
                    "La cita ya está pagada"
            );
        }

        /*
         * Precio de ejemplo:
         * 50 USD = 5000 centavos.
         */
        long precio = 5000L;

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(
                                SessionCreateParams.Mode.PAYMENT
                        )
                        .setSuccessUrl(successUrl)
                        .setCancelUrl(cancelUrl)
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                                SessionCreateParams
                                                        .LineItem
                                                        .PriceData
                                                        .builder()
                                                        .setCurrency("usd")
                                                        .setUnitAmount(precio)
                                                        .setProductData(
                                                                SessionCreateParams
                                                                        .LineItem
                                                                        .PriceData
                                                                        .ProductData
                                                                        .builder()
                                                                        .setName(
                                                                                "Cita veterinaria - "
                                                                                        + cita.getId()
                                                                        )
                                                                        .setDescription(
                                                                                cita.getMotivo()
                                                                        )
                                                                        .build()
                                                        )
                                                        .build()
                                        )
                                        .build()
                        )
                        .putMetadata(
                                "citaId",
                                String.valueOf(cita.getId())
                        )
                        .build();

        Session session = Session.create(params);

        return new PagoResponse(
                session.getId(),
                session.getUrl()
        );
    }
}
