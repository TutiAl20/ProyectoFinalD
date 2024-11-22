package com.travel.dto.salida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaSalidaDto {
    private Long id;
    private String name;
    private String descripcion;
    private String image;  // URL de la imagen representativa

}
