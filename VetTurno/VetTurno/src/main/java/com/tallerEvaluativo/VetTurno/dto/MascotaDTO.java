package com.tallerEvaluativo.VetTurno.dto;


public class MascotaDTO {

    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private Long propietarioId;
    private String propietarioNombre;

    public MascotaDTO() {
    }

    public MascotaDTO(
            Long id,
            String nombre,
            String especie,
            String raza,
            Long propietarioId,
            String propietarioNombre) {

        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.propietarioId = propietarioId;
        this.propietarioNombre = propietarioNombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaza() {
        return raza;
    }

    public Long getPropietarioId() {
        return propietarioId;
    }

    public String getPropietarioNombre() {
        return propietarioNombre;
    }
}