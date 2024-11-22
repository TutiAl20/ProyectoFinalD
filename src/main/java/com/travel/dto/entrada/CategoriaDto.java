package com.travel.dto.entrada;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDto {
    private String name;
    private String descripcion;
    private MultipartFile image;

}