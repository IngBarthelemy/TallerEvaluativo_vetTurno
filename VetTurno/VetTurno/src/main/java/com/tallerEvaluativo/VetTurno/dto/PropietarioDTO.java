package com.tallerEvaluativo.VetTurno.dto;

public class PropietarioDTO {

    private Long id;
    private String nombre;
    private String email;
    private String telefono;

    public PropietarioDTO() {
    }

    public PropietarioDTO(
            Long id,
            String nombre,
            String email,
            String telefono) {

        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }
}
