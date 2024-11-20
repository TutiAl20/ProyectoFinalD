package com.travel.dto.salida;

import com.travel.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSalidaDto {
    private Long id;
    private String username;
    private String nombre;
    private String apellido;
    private Set<Role> roles;
}
