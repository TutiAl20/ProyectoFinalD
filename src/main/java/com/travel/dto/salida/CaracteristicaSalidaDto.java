package com.travel.dto.salida;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaracteristicaSalidaDto {
    private Long id;
    private String name;
    private String icon;
}
