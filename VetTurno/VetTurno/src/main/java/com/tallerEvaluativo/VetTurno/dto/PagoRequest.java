package com.tallerEvaluativo.VetTurno.dto;

import jakarta.validation.constraints.NotNull;

public class PagoRequest {

    @NotNull
    private Long citaId;

    public PagoRequest() {
    }

    public PagoRequest(Long citaId) {
        this.citaId = citaId;
    }

    public Long getCitaId() {
        return citaId;
    }

    public void setCitaId(Long citaId) {
        this.citaId = citaId;
    }
}
