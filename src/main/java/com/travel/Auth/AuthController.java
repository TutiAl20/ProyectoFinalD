package com.travel.Auth;


import com.travel.dto.entrada.ActualizarUsuarioRolDto;
import com.travel.dto.salida.UserSalidaDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("authController1")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        AuthResponse response = authService.login(request);
        if (response.getErrorMessage() != null) {
            return ResponseEntity.badRequest().body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request)
    {
        AuthResponse response = authService.register(request);
        if (response.getErrorMessage() != null) {
            return ResponseEntity.badRequest().body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserSalidaDto>> getAllUsers() {
        List<UserSalidaDto> users = authService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/actualizar-rol")
    public ResponseEntity<UserSalidaDto> updateUserRole(@RequestBody ActualizarUsuarioRolDto userDto) {
        try {
            UserSalidaDto updatedUser = authService.updateUserRole(userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }








}