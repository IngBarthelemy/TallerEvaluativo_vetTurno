package com.tallerEvaluativo.VetTurno.dto;

import jakarta.validation.constraints.NotBlank;

public class VeterinarioDTO {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    public VeterinarioDTO() {
    }

    public VeterinarioDTO(
            Long id,
            String nombre,
            String especialidad) {

        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}