package com.travel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.travel.entity.Producto;
import com.travel.exception.TravelRepositoryException;
import com.travel.service.ProductoService;

@RequestMapping("/travel/public/productos")
@RestController
public class ProductoController {
    @Autowired
    ProductoService productoService;

    @GetMapping("/")    
    List<Producto> listarProductos() {    
        return productoService.listarProductos();
    }

    @GetMapping("/{nombre}")
    Producto listarProductoPorNombre(@PathVariable String nombre) {    
        return productoService.listarProductosPorNombre(nombre);
    }

    @PostMapping("/")
    Producto newProducto(@RequestBody Producto producto)  {
        Producto newProducto = null;
        try {
            newProducto = productoService.agregarProducto(producto);
        } catch (TravelRepositoryException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
        return newProducto;
    }

    @DeleteMapping("/{id}")
    void borrarProducto(@PathVariable long id) {
        try {
            productoService.deleteProducto(id);
        } catch (TravelRepositoryException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("/imagenes/{id}")
    void borrarImagen(@PathVariable long id) {
        try {
            productoService.borrarImagen(id);
        } catch (TravelRepositoryException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
