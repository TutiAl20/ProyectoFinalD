package com.travel.service;

import java.util.List;

import com.travel.dto.entrada.ProductoDto;
import com.travel.dto.salida.ProductoSalidaDto;
import com.travel.entity.Producto;
import com.travel.exception.TravelRepositoryException;

public interface ProductoService {

    List<Producto> listarProductos();

    // Método para listar todos los productos
    Producto listarProductosPorId(long id);

    Producto listarProductosPorNombre(String nombre);
    
    Producto agregarProducto(Producto producto) throws TravelRepositoryException;


    void deleteProducto(long id) throws TravelRepositoryException;


    ProductoSalidaDto listarProductoPorId(Long id);
    ProductoSalidaDto crearProducto(ProductoDto productoDTO);
    List<ProductoSalidaDto> listarTodosLosProductos();
}
