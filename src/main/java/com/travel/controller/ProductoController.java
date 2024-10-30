package com.travel.controller;

import java.util.List;

import com.travel.dto.entrada.ProductoDto;
import com.travel.dto.salida.ProductoSalidaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.travel.entity.Producto;
import com.travel.exception.TravelRepositoryException;
import com.travel.service.ProductoService;


@RequestMapping("/travel/productos")
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        return newProducto;
    }


    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable long id )  {
        try {
             productoService.deleteProducto(id);
        } catch (TravelRepositoryException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    
    }


    @PostMapping("/crear")
    public ResponseEntity<ProductoSalidaDto> crearProducto(@ModelAttribute ProductoDto productoDTO) {
        ProductoSalidaDto nuevoProducto = productoService.crearProducto(productoDTO);
        return ResponseEntity.ok(nuevoProducto);
    }

    @GetMapping("/por-id/{id}")
    public ResponseEntity<ProductoSalidaDto> listarProductoPorId(@PathVariable("id") Long productoId) {
        ProductoSalidaDto productoSalidaDto = productoService.listarProductoPorId(productoId);
        return new ResponseEntity<>(productoSalidaDto, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ProductoSalidaDto>> listarTodosLosProductos() {
        List<ProductoSalidaDto> productos = productoService.listarTodosLosProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }
    
}
