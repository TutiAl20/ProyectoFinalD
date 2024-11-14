package com.travel.service;

import com.travel.dto.entrada.CategoriaDto;
import com.travel.dto.salida.CategoriaSalidaDto;
import com.travel.entity.Categoria;

import java.util.List;

public interface CategoriaService {
    List<CategoriaSalidaDto> listarTodas();
    CategoriaSalidaDto obtenerCategoriaDtoPorId(Long id);
    Categoria obtenerPorId(Long id);
    CategoriaSalidaDto  crear(CategoriaDto categoriaDto);
    CategoriaSalidaDto  actualizar(Long id, CategoriaDto categoriaDto);
    void eliminar(Long id);
}