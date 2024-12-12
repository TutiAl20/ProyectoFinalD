package com.travel.service;

import java.util.List;

import com.travel.dto.entrada.ReviewSalidaDto;

public interface ReviewService {
    ReviewSalidaDto agregarResena(String username, Long productoId, ReviewDto reviewDto);
    List<ReviewSalidaDto> listarResenasPorProducto(Long productoId);
    double calcularPuntuacionMedia(Long productoId);
    long contarResenasPorProducto(Long productoId);
}

