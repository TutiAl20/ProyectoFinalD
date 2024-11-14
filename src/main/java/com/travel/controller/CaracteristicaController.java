package com.travel.controller;


import com.travel.dto.salida.CaracteristicaSalidaDto;
import com.travel.entity.Caracteristica;
import com.travel.service.CaracteristicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("travel/caracteristicas")
public class CaracteristicaController {

    private final CaracteristicaService caracteristicaService;

    @Autowired
    public CaracteristicaController(CaracteristicaService caracteristicaService) {
        this.caracteristicaService = caracteristicaService;
    }

    @GetMapping
    public List<CaracteristicaSalidaDto> listarTodas() {
        return caracteristicaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaracteristicaSalidaDto> obtenerPorId(@PathVariable Long id) {
        return new ResponseEntity<>(caracteristicaService.obtenerCaracteristicaDtoPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CaracteristicaSalidaDto> crear(@RequestBody Caracteristica caracteristica) {
        return new ResponseEntity<>(caracteristicaService.crear(caracteristica), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaracteristicaSalidaDto> actualizar(@PathVariable Long id, @RequestBody Caracteristica caracteristica) {
        return new ResponseEntity<>(caracteristicaService.actualizar(id, caracteristica), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        caracteristicaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}