package com.travel.dto.salida;


import com.travel.dto.entrada.FechaDisponibleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoSalidaDto {
    private Long id; // ID del producto
    private String nombre; // Nombre del producto
    private String descripcion; // Descripción del producto
    private String ubicacion; // Región donde se ofrece el producto
    private double precio; // Precio del producto
    private List<String> imagenes; // URLs de las imágenes
    private CategoriaSalidaDto categoria; // Información de la categoría
    private List<CaracteristicaSalidaDto> caracteristicas; // Informacion de características
    private List<FechaDisponibleDto> fechasDisponibles;

    private double puntuacionMedia;
    private long totalResenas;
}