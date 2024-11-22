package com.travel.service;

import com.travel.dto.salida.CaracteristicaSalidaDto;
import com.travel.entity.Caracteristica;

import java.util.List;

public interface CaracteristicaService {
    List<CaracteristicaSalidaDto> listarTodas();
    CaracteristicaSalidaDto obtenerCaracteristicaDtoPorId(Long id);
    Caracteristica obtenerPorId(Long id);
    CaracteristicaSalidaDto  crear(Caracteristica caracteristica);
    CaracteristicaSalidaDto actualizar(Long id, Caracteristica caracteristicaActualizada);
    void eliminar(Long id);

}

