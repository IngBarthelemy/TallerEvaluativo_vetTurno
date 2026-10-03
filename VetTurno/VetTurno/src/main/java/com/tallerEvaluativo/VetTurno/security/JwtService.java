package com.tallerEvaluativo.VetTurno.security;

import com.tallerEvaluativo.VetTurno.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key;
    private final long expiration;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration) {

        this.key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        this.expiration = expiration;
    }

    public String generarToken(Usuario usuario) {

        Date ahora = new Date();

        Date expiracion = new Date(
                ahora.getTime() + expiration
        );

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("rol", usuario.getRol().name())
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(key)
                .compact();
    }

    public String extraerEmail(String token) {

        return obtenerClaims(token).getSubject();
    }

    public boolean esValido(String token, String email) {

        try {
            Claims claims = obtenerClaims(token);

            return claims.getSubject().equals(email)
                    && claims.getExpiration().after(new Date());

        } catch (Exception e) {
            return false;
        }
    }

    private Claims obtenerClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
