package com.travel.service;

import java.util.List;

import com.travel.dto.entrada.ReviewDto;
import com.travel.dto.salida.ReviewSalidaDto;

public interface ReviewService {
    ReviewSalidaDto agregarResena(ReviewDto reviewDto);
    List<ReviewSalidaDto> listarResenasPorProducto(Long productoId);
    List<ReviewSalidaDto> listarResenas();
    double calcularPuntuacionMedia(Long productoId);
    long contarResenasPorProducto(Long productoId);
}

