package com.tallerEvaluativo.VetTurno.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "propietarios")
public class Propietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @NotBlank
    @Column(nullable = false)
    private String telefono;

    @Email
    private String email;

    @OneToMany(
            mappedBy = "propietario",
            cascade = CascadeType.ALL
    )
    private List<Mascota> mascotas = new ArrayList<>();

    // Constructor vacío requerido por JPA
    public Propietario() {
    }

    // Constructor para crear un propietario
    public Propietario(
            String nombre,
            String email,
            String telefono) {

        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    // Constructor completo
    public Propietario(
            Long id,
            String nombre,
            String telefono,
            String email,
            List<Mascota> mascotas) {

        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.mascotas = mascotas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
}

