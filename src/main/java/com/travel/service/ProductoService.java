package com.travel.service;

import java.time.LocalDate;
import java.util.List;

import com.travel.dto.entrada.ProductoDto;
import com.travel.dto.salida.ProductoSalidaDto;

public interface ProductoService {
    List<ProductoSalidaDto> listarTodosLosProductos();
    ProductoSalidaDto crearProducto(String currentUserName,ProductoDto productoDTO);
    ProductoSalidaDto listarProductoPorId(Long id);
    void eliminar(Long id);
    List<ProductoSalidaDto> obtenerProductosPorCategoria(Long categoriaId);
    List<ProductoSalidaDto> obtenerProductosDisponiblesPorRangoDeFechas(LocalDate fechaInicio, LocalDate fechaFinal, String query);
}

