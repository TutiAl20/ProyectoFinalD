package com.travel.dto.salida;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSalidaDto {
    private int puntuacion;
    private String comentario;
    private LocalDateTime fechaPublicacion;
    private String nombreUsuario;
    private String apellidoUsuario;
}