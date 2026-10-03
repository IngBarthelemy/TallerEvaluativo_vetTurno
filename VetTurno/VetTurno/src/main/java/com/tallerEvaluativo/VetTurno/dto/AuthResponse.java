package com.tallerEvaluativo.VetTurno.dto;

public class AuthResponse {

    private String token;
    private String tipo;
    private String email;
    private String rol;

    public AuthResponse() {
    }

    public AuthResponse(
            String token,
            String tipo,
            String email,
            String rol) {

        this.token = token;
        this.tipo = tipo;
        this.email = email;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }
}
