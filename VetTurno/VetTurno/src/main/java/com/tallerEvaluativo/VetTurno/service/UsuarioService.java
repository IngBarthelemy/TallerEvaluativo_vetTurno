package com.tallerEvaluativo.VetTurno.service;


import com.tallerEvaluativo.VetTurno.model.Usuario;
import com.tallerEvaluativo.VetTurno.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //
    public List<Usuario> obtenerDatos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }


    public Usuario guardar(Usuario usuario) {

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya esta registrado");

        }
        return usuarioRepository.save(usuario);

    }
    public void eliminar(Long id){
        usuarioRepository.deleteById(id);
    }
}
