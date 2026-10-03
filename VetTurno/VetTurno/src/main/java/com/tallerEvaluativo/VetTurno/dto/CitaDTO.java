package com.tallerEvaluativo.VetTurno.dto;

import java.time.LocalDateTime;

public class CitaDTO {

    private Long id;
    private LocalDateTime fechaHora;
    private String motivo;

    private String mascota;
    private String propietario;
    private String veterinario;

    public CitaDTO() {
    }

    public CitaDTO(
            Long id,
            LocalDateTime fechaHora,
            String motivo,
            String mascota,
            String propietario,
            String veterinario) {

        this.id = id;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.mascota = mascota;
        this.propietario = propietario;
        this.veterinario = veterinario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getMascota() {
        return mascota;
    }

    public void setMascota(String mascota) {
        this.mascota = mascota;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }
}
