package com.travel.controller;

import com.travel.dto.entrada.CaracteristicaDto;
import com.travel.dto.salida.CaracteristicaSalidaDto;
import com.travel.service.CaracteristicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/travel/public/caracteristicas")
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<CaracteristicaSalidaDto> crear(@ModelAttribute CaracteristicaDto caracteristicaDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        CaracteristicaSalidaDto nuevaCaracteristica = caracteristicaService.crear(currentUserName, caracteristicaDto);
        return new ResponseEntity<>(nuevaCaracteristica, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CaracteristicaSalidaDto> actualizar(
            @PathVariable Long id,
            @ModelAttribute CaracteristicaDto caracteristicaDto) {
        CaracteristicaSalidaDto caracteristicaActualizada = caracteristicaService.actualizar(id, caracteristicaDto);
        return new ResponseEntity<>(caracteristicaActualizada, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        caracteristicaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}