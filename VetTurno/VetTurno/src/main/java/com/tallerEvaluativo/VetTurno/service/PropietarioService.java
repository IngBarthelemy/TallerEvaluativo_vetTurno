package com.tallerEvaluativo.VetTurno.service;

import com.tallerEvaluativo.VetTurno.dto.PropietarioDTO;
import com.tallerEvaluativo.VetTurno.dto.PropietarioRequest;
import com.tallerEvaluativo.VetTurno.exception.BusinessException;
import com.tallerEvaluativo.VetTurno.model.Propietario;
import com.tallerEvaluativo.VetTurno.repository.PropietarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropietarioService {

    private final PropietarioRepository repository;

    public PropietarioService(PropietarioRepository repository) {
        this.repository = repository;
    }

    public PropietarioDTO crear(PropietarioRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new BusinessException(
                    "Ya existe un propietario con ese email"
            );
        }

        Propietario propietario = new Propietario(
                request.getNombre(),
                request.getEmail(),
                request.getTelefono()
        );

        return convertir(repository.save(propietario));
    }

    public List<PropietarioDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    private PropietarioDTO convertir(Propietario propietario) {

        return new PropietarioDTO(
                propietario.getId(),
                propietario.getNombre(),
                propietario.getEmail(),
                propietario.getTelefono()
        );
    }
}