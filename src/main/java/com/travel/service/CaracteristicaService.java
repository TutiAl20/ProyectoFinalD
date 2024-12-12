package com.travel.service;

import com.travel.dto.entrada.CaracteristicaDto;
import com.travel.dto.salida.CaracteristicaSalidaDto;
import com.travel.entity.Caracteristica;

import java.util.List;

public interface CaracteristicaService {
    // Método actualizado
    CaracteristicaSalidaDto crear(String currentUserName, CaracteristicaDto caracteristicaDto);

    // Otros métodos
    List<CaracteristicaSalidaDto> listarTodas();
    CaracteristicaSalidaDto obtenerCaracteristicaDtoPorId(Long id);
    Caracteristica obtenerPorId(Long id);
    CaracteristicaSalidaDto actualizar(Long id, CaracteristicaDto caracteristicaDto);
    void eliminar(Long id);
}

