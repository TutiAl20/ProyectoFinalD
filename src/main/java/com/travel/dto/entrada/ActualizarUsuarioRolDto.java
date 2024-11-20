package com.travel.dto.entrada;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarUsuarioRolDto {
    private Long userId;
    private String nuevoRol;
}
