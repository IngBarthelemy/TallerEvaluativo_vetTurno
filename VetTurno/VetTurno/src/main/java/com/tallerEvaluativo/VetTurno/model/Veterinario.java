package com.tallerEvaluativo.VetTurno.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "veterinarios")

public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @NotBlank
    @Column(nullable = false)
    private String especialidad;

    // Constructor vacio
    public Veterinario(){

    }

    //Constructor con todos los atributos

    public Veterinario(Long id, String nombre, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    public Veterinario(String nombre, String especialidad) {

        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    //Getter y Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(@NotBlank String especialidad) {
        this.especialidad = especialidad;
    }
}
