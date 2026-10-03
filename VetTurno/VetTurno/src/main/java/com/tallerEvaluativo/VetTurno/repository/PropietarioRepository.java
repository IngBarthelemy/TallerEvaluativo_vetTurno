package com.tallerEvaluativo.VetTurno.repository;

import com.tallerEvaluativo.VetTurno.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropietarioRepository extends JpaRepository<Propietario, Long> {

    boolean existsByEmail(String email);
}
