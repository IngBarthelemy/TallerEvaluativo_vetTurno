package com.tallerEvaluativo.VetTurno.service;

import com.tallerEvaluativo.VetTurno.dto.VeterinarioDTO;
import com.tallerEvaluativo.VetTurno.model.Veterinario;
import com.tallerEvaluativo.VetTurno.repository.VeterinarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarioService {

    private final VeterinarioRepository repository;

    public VeterinarioService(VeterinarioRepository repository) {
        this.repository = repository;
    }

    public VeterinarioDTO crear(VeterinarioDTO request) {

        Veterinario veterinario = new Veterinario(
                request.getNombre(),
                request.getEspecialidad()
        );

        return convertir(repository.save(veterinario));
    }

    public List<VeterinarioDTO> listar() {

        return repository.findAll()
                .stream()
                .map(this::convertir)
                .toList();
    }

    private VeterinarioDTO convertir(Veterinario veterinario) {

        return new VeterinarioDTO(
                veterinario.getId(),
                veterinario.getNombre(),
                veterinario.getEspecialidad()
        );
    }
}