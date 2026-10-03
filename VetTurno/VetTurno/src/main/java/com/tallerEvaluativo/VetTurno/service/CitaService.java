package com.tallerEvaluativo.VetTurno.service;

import com.tallerEvaluativo.VetTurno.dto.CitaDTO;
import com.tallerEvaluativo.VetTurno.dto.CitaRequest;
import com.tallerEvaluativo.VetTurno.exception.BusinessException;
import com.tallerEvaluativo.VetTurno.model.Cita;
import com.tallerEvaluativo.VetTurno.model.Mascota;
import com.tallerEvaluativo.VetTurno.model.Veterinario;
import com.tallerEvaluativo.VetTurno.repository.CitaRepository;
import com.tallerEvaluativo.VetTurno.repository.MascotaRepository;
import com.tallerEvaluativo.VetTurno.repository.VeterinarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;

    public CitaService(
            CitaRepository citaRepository,
            MascotaRepository mascotaRepository,
            VeterinarioRepository veterinarioRepository) {

        this.citaRepository = citaRepository;
        this.mascotaRepository = mascotaRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    public CitaDTO crear(CitaRequest request) {

        if (!request.getFechaHora().isAfter(LocalDateTime.now())) {
            throw new BusinessException(
                    "La fecha de la cita debe ser futura"
            );
        }

        Mascota mascota = mascotaRepository
                .findById(request.getMascotaId())
                .orElseThrow(() ->
                        new BusinessException(
                                "La mascota indicada no existe"
                        )
                );

        Veterinario veterinario = veterinarioRepository
                .findById(request.getVeterinarioId())
                .orElseThrow(() ->
                        new BusinessException(
                                "El veterinario indicado no existe"
                        )
                );

        boolean ocupado =
                citaRepository.existsByVeterinarioIdAndFechaHora(
                        veterinario.getId(),
                        request.getFechaHora()
                );

        if (ocupado) {
            throw new BusinessException(
                    "El veterinario ya tiene una cita en ese horario"
            );
        }

        Cita cita = new Cita(
                request.getFechaHora(),
                request.getMotivo(),
                mascota,
                veterinario
        );

        return convertir(citaRepository.save(cita));
    }

    public List<CitaDTO> listar() {

        return citaRepository.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    public List<CitaDTO> listarPorVeterinario(Long veterinarioId) {

        if (!veterinarioRepository.existsById(veterinarioId)) {
            throw new BusinessException(
                    "El veterinario indicado no existe"
            );
        }

        return citaRepository.findByVeterinarioId(veterinarioId)
                .stream()
                .map(this::convertir)
                .toList();
    }

    private CitaDTO convertir(Cita cita) {

        return new CitaDTO(
                cita.getId(),
                cita.getFechaHora(),
                cita.getMotivo(),
                cita.getMascota().getNombre(),
                cita.getMascota()
                        .getPropietario()
                        .getNombre(),
                cita.getVeterinario().getNombre()
        );
    }
}