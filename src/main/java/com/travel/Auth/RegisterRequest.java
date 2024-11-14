package com.travel.Auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class RegisterRequest {
    @NotBlank(message = "Email es obligatorio")
    @Email(message = "Email invalido")
    String username;
    @NotBlank(message = "Contraseña es obligatorio")
    String password;
    @NotBlank(message = "Usuario es obligatorio")
    String nombre;
    @NotBlank(message = "Usuario es obligatorio")
    String apellido;
}
