package com.travel.Auth;


import com.travel.Security.Jwt.JwtService;
import com.travel.dto.entrada.ActualizarUsuarioRolDto;
import com.travel.dto.entrada.EmailDto;
import com.travel.dto.salida.UserSalidaDto;
import com.travel.dto.salida.UsuarioSalidaDto;
import com.travel.entity.Role;
import com.travel.entity.UserEntity;
import com.travel.exception.NotFoundException;
import com.travel.repository.RoleRepository;
import com.travel.repository.UserRepository;
import com.travel.service.EmailService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    EmailService emailService;
    @Value("${spring.mail.subject}")
    private String emailSubject;
    @Value("${spring.mail.text}")
    private String emailText;
    @Value("${spring.mail.from}")
    private String emailFrom;    

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

        EmailDto email = new EmailDto(emailFrom, user.getUsername(), emailSubject, emailText);
        emailService.sendEmail(email);

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

    public List<UserSalidaDto> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserSalidaDto.class))  // Convertimos cada entidad a DTO
                .collect(Collectors.toList());
    }

    public UserSalidaDto updateUserRole(ActualizarUsuarioRolDto userDto) {
        UserEntity user = getUserById(userDto.getUserId());

        Set<Role> newRoles = new HashSet<>();
        Role role = roleRepository.findByName(userDto.getNuevoRol());
        if (role == null) {
            throw new EntityNotFoundException("Rol no encontrado: ");
        }
        newRoles.add(role);

        user.setRoles(newRoles);
        userRepository.save(user);
        return modelMapper.map(user, UserSalidaDto.class);
    }

    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + userId));
    }

    public UsuarioSalidaDto obtenerUsuarioPorId(Long id) {
        UserEntity user = getUserById(id);
        return new UsuarioSalidaDto(user.getNombre(), user.getApellido(), user.getFecha());
    }     
    public boolean deleteUser(String email) {
        Optional<UserEntity> user = userRepository.findByUsername(email);
        boolean success = false;
        if(user.isPresent()) {
            userRepository.delete(user.get());
            success = true;
        } 
        return success;
    }

}