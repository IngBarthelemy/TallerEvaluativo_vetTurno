package com.tallerEvaluativo.VetTurno.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "citas",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_veterinario_fecha_hora",
                        columnNames = {"veterinario_id", "fecha_hora"}
                )
        }
)

public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Future
    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @NotBlank
    @Column(nullable = false)
    private String motivo;

    @ManyToOne(fetch= FetchType.LAZY, optional = false)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "veterinario_id", nullable = false)
    private Veterinario veterinario;

    @Enumerated(EnumType.STRING)
    private EstadoPago estadoPago = EstadoPago.PENDIENTE;

    //Constructor vacio
    public Cita() {

    }

    //Constructor con todos los atributos


    public Cita(Long id, LocalDateTime fechaHora, String motivo, Veterinario veterinario, Mascota mascota) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.veterinario = veterinario;
        this.mascota = mascota;
    }

    public Cita(LocalDateTime fechaHora, String motivo, Mascota mascota, Veterinario veterinario){
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.mascota = mascota;
        this.veterinario = veterinario;
    }

    //Getter y Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Future LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(@Future LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public @NotBlank String getMotivo() {
        return motivo;
    }

    public void setMotivo(@NotBlank String motivo) {
        this.motivo = motivo;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }
}
