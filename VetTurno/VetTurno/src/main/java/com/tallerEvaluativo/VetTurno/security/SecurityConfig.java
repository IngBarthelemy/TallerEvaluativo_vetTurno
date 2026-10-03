package com.tallerEvaluativo.VetTurno.security;

import com.tallerEvaluativo.VetTurno.exception.ApiError;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDateTime;
import java.util.Map;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UsuarioDetailsService userDetailsService;

    public SecurityConfig(
            JwtAuthFilter jwtAuthFilter,
            UsuarioDetailsService userDetailsService) {

        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider)
            throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authenticationProvider(authenticationProvider)

                .authorizeHttpRequests(auth -> auth

                        // ==========================================
                        // REGISTRO Y LOGIN
                        // ==========================================
                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()

                        // ==========================================
                        // SWAGGER / OPENAPI
                        // ==========================================
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // ==========================================
                        // WEBHOOK DE STRIPE
                        // ==========================================
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/stripe/webhook"
                        ).permitAll()

                        // ==========================================
                        // RETORNO DE STRIPE
                        // No necesitan JWT porque Stripe
                        // redirige al navegador a estas URLs.
                        // ==========================================
                        .requestMatchers(
                                "/api/pagos/success",
                                "/api/pagos/cancel"
                        ).permitAll()

                        // ==========================================
                        // CHECKOUT DE STRIPE
                        // El usuario debe estar autenticado.
                        // ==========================================
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/pagos/checkout"
                        ).authenticated()

                        // ==========================================
                        // CREAR VETERINARIO
                        // SOLAMENTE ADMIN
                        // ==========================================
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/veterinarios"
                        ).hasRole("ADMIN")

                        // ==========================================
                        // TODO LO DEMÁS
                        // REQUIERE AUTENTICACIÓN
                        // ==========================================
                        .anyRequest().authenticated()
                )

                .exceptionHandling(exception -> exception

                        // ==========================================
                        // 401 - NO AUTENTICADO
                        // ==========================================
                        .authenticationEntryPoint(
                                (request, response, ex) -> {

                                    response.setStatus(401);
                                    response.setContentType(
                                            MediaType.APPLICATION_JSON_VALUE
                                    );

                                    ApiError error =
                                            new ApiError(
                                                    401,
                                                    "Autenticación requerida",
                                                    Map.of(),
                                                    LocalDateTime.now()
                                            );

                                    response.getWriter().write(
                                            crearJsonError(error)
                                    );
                                }
                        )

                        // ==========================================
                        // 403 - SIN PERMISOS
                        // ==========================================
                        .accessDeniedHandler(
                                (request, response, ex) -> {

                                    response.setStatus(403);
                                    response.setContentType(
                                            MediaType.APPLICATION_JSON_VALUE
                                    );

                                    ApiError error =
                                            new ApiError(
                                                    403,
                                                    "No tienes permisos para realizar esta operación",
                                                    Map.of(),
                                                    LocalDateTime.now()
                                            );

                                    response.getWriter().write(
                                            crearJsonError(error)
                                    );
                                }
                        )
                )

                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    private String crearJsonError(ApiError error) {

        String errores = "{}";

        return "{"
                + "\"status\":" + error.getStatus() + ","
                + "\"mensaje\":\""
                + escapar(error.getMensaje())
                + "\","
                + "\"errores\":"
                + errores
                + ","
                + "\"timestamp\":\""
                + error.getTimestamp()
                + "\""
                + "}";
    }

    private String escapar(String texto) {

        if (texto == null) {
            return "";
        }

        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}