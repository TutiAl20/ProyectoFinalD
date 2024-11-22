package com.travel.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserDetailsService usuarioDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for simplicity (enable it if needed)
            .csrf(csrf -> csrf.disable())            
            // Define authorization rules
            .authorizeHttpRequests(auth -> auth
                // Allow public access to /travel/public/**
                .requestMatchers("/travel/public/**").permitAll()
                // Secure /travel/admin/** with ADMIN role
                .requestMatchers(HttpMethod.POST, "/travel/admin/usuarios/registrar").hasRole("ADMIN")
                .requestMatchers("/travel/admin/**").hasRole("ADMIN")
                // Require authentication for any other request
                .anyRequest().authenticated()
            )

            // Use HTTP Basic Authentication
            .httpBasic();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(usuarioDetailsService);
        return authBuilder.build();
    }
}