package com.tallerEvaluativo.VetTurno.repository;

import com.tallerEvaluativo.VetTurno.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    boolean existsByVeterinarioIdAndFechaHora(
            Long veterinarioId,
            LocalDateTime FechaHora
    );
    List<Cita> findByVeterinarioId(Long veterinarioId);
}
