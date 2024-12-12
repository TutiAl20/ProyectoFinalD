package com.travel.dto.entrada;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaracteristicaDto {
    private String name;
    private MultipartFile icon;

}
