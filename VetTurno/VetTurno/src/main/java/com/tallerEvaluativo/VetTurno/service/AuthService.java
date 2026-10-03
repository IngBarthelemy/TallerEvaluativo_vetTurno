package com.tallerEvaluativo.VetTurno.service;

import com.tallerEvaluativo.VetTurno.dto.AuthResponse;
import com.tallerEvaluativo.VetTurno.dto.LoginRequest;
import com.tallerEvaluativo.VetTurno.dto.RegistroRequest;
import com.tallerEvaluativo.VetTurno.exception.BusinessException;
import com.tallerEvaluativo.VetTurno.model.Rol;
import com.tallerEvaluativo.VetTurno.model.Usuario;
import com.tallerEvaluativo.VetTurno.repository.UsuarioRepository;
import com.tallerEvaluativo.VetTurno.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse registrar(RegistroRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(
                    "El email ya está registrado"
            );
        }

        Usuario usuario = new Usuario();

        usuario.setEmail(request.getEmail());

        usuario.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Nunca aceptamos el rol enviado por el cliente.
        usuario.setRol(Rol.USER);

        usuarioRepository.save(usuario);

        String token = jwtService.generarToken(usuario);

        return new AuthResponse(
                token,
                "Bearer",
                usuario.getEmail(),
                usuario.getRol().name()
        );
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BusinessException("Usuario no encontrado")
                );

        String token = jwtService.generarToken(usuario);

        return new AuthResponse(
                token,
                "Bearer",
                usuario.getEmail(),
                usuario.getRol().name()
        );
    }

}
