package com.tallerEvaluativo.VetTurno.security;

import com.tallerEvaluativo.VetTurno.model.Usuario;
import com.tallerEvaluativo.VetTurno.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioDetailsService
        implements UserDetailsService {

    private final UsuarioRepository repository;

    public UsuarioDetailsService(
            UsuarioRepository repository) {

        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuario no encontrado"
                        )
                );

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(
                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_" +
                                                usuario.getRol().name()
                                )
                        )
                )
                .build();
    }
}
