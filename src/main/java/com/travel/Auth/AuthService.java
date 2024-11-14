package com.travel.Auth;


import com.travel.Security.Jwt.JwtService;
import com.travel.entity.Role;
import com.travel.entity.UserEntity;
import com.travel.repository.RoleRepository;
import com.travel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;


    public AuthResponse login(LoginRequest request) {
        try {
            Optional<UserEntity> optionalUser = userRepository.findByUsername(request.getUsername());
            if (optionalUser.isPresent()) {
                UserEntity user = optionalUser.get();

                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
                String token = jwtService.getToken(user);
                return AuthResponse.builder()
                            .token(token)
                            .build();

            } else {
                return AuthResponse.builder()
                        .errorMessage("Usuario no registrado")
                        .build();
            }
        } catch (AuthenticationException ex) {
            return AuthResponse.builder()
                    .errorMessage("Email o contraseña inválidos")
                    .build();
        }
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return AuthResponse.builder()
                    .errorMessage("El correo ya está registrado")
                    .build();
        }

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("USER");
        roles.add(userRole);

        UserEntity user = createUserEntity(request, roles);
        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }

    private UserEntity createUserEntity(RegisterRequest request, Set<Role> roles) {
        return UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .fecha(LocalDate.now())
                .roles(roles)
                .build();
    }
}
