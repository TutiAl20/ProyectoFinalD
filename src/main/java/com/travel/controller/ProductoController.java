package com.travel.controller;

import java.time.LocalDate;
import java.util.List;

import com.travel.dto.entrada.ProductoDto;
import com.travel.dto.salida.ProductoSalidaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.travel.entity.Producto;
import com.travel.exception.TravelRepositoryException;
import com.travel.service.ProductoService;

@RequestMapping("/travel/public/productos")
@RestController
public class ProductoController {
    @Autowired
    ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoSalidaDto>> listarTodosLosProductos() {
        List<ProductoSalidaDto> productos = productoService.listarTodosLosProductos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductoSalidaDto> crearProducto(@ModelAttribute ProductoDto productoDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        ProductoSalidaDto nuevoProducto = productoService.crearProducto(currentUserName,productoDTO);
        return new ResponseEntity<>(nuevoProducto,  HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoSalidaDto> listarProductoPorId(@PathVariable("id") Long productoId) {
        ProductoSalidaDto productoSalidaDto = productoService.listarProductoPorId(productoId);
        return new ResponseEntity<>(productoSalidaDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<ProductoSalidaDto> obtenerProductosPorCategoria(@PathVariable Long categoriaId) {
        return productoService.obtenerProductosPorCategoria(categoriaId);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ProductoSalidaDto>> obtenerProductosDisponibles(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFinal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFinal,
            @RequestParam(value = "query", required = false) String query) {

        List<ProductoSalidaDto> productos = productoService.obtenerProductosDisponiblesPorRangoDeFechas(fechaInicio, fechaFinal, query);
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

}