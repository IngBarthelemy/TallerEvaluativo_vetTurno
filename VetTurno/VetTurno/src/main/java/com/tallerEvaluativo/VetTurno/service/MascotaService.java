package com.tallerEvaluativo.VetTurno.service;

import com.tallerEvaluativo.VetTurno.dto.MascotaDTO;
import com.tallerEvaluativo.VetTurno.dto.MascotaRequest;
import com.tallerEvaluativo.VetTurno.exception.BusinessException;
import com.tallerEvaluativo.VetTurno.model.Mascota;
import com.tallerEvaluativo.VetTurno.model.Propietario;
import com.tallerEvaluativo.VetTurno.repository.MascotaRepository;
import com.tallerEvaluativo.VetTurno.repository.PropietarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final PropietarioRepository propietarioRepository;

    public MascotaService(
            MascotaRepository mascotaRepository,
            PropietarioRepository propietarioRepository) {

        this.mascotaRepository = mascotaRepository;
        this.propietarioRepository = propietarioRepository;
    }

    public MascotaDTO crear(MascotaRequest request) {

        Propietario propietario = propietarioRepository
                .findById(request.getPropietarioId())
                .orElseThrow(() ->
                        new BusinessException(
                                "El propietario indicado no existe"
                        )
                );

        Mascota mascota = new Mascota(
                request.getNombre(),
                request.getEspecie(),
                request.getRaza(),
                propietario
        );

        return convertir(mascotaRepository.save(mascota));
    }

    public List<MascotaDTO> listar() {

        return mascotaRepository.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    private MascotaDTO convertir(Mascota mascota) {

        return new MascotaDTO(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                mascota.getPropietario().getId(),
                mascota.getPropietario().getNombre()
        );
    }
}