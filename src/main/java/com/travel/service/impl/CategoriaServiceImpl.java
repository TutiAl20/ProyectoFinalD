package com.travel.service.impl;


import com.travel.dto.entrada.CategoriaDto;
import com.travel.dto.salida.CategoriaSalidaDto;
import com.travel.entity.Categoria;
import com.travel.exception.NotFoundException;
import com.travel.repository.CategoriaRepository;
import com.travel.service.CategoriaService;
import com.travel.service.S3Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final ModelMapper modelMapper;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    private S3Service s3Service;

    
    public CategoriaServiceImpl(ModelMapper modelMapper, CategoriaRepository categoriaRepository) {
        this.modelMapper = modelMapper;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<CategoriaSalidaDto> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaSalidaDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaSalidaDto obtenerCategoriaDtoPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
        return modelMapper.map(categoria, CategoriaSalidaDto.class);
    }

    @Override
    public Categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
    }

    @Override
    public CategoriaSalidaDto  crear(CategoriaDto categoriaDto) {
        Categoria categoria = new Categoria();

        categoria.setName(categoriaDto.getName());
        categoria.setDescripcion(categoriaDto.getDescripcion());

        if (categoriaDto.getImage() != null && !categoriaDto.getImage().isEmpty()) {
            String imagenUrl = s3Service.subirImagen(categoriaDto.getImage());
            categoria.setImage(imagenUrl);

        }
        categoria.setName(categoriaDto.getName());
        categoria.setDescripcion(categoriaDto.getDescripcion());


        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return modelMapper.map(categoriaGuardada, CategoriaSalidaDto.class);
    }

    @Override
    public CategoriaSalidaDto  actualizar(Long id, CategoriaDto categoriaDto) {
        Categoria categoriaExistente =obtenerPorId(id);

        // Si hay una nueva imagen, eliminar la anterior y subir la nueva

        if (categoriaDto.getImage() != null && !categoriaDto.getImage().isEmpty()) {
            s3Service.eliminarImagen(categoriaExistente.getImage()); // Eliminar la imagen anterior
            String nuevaImagenUrl = s3Service.subirImagen(categoriaDto.getImage()); // Subir la nueva
            categoriaExistente.setImage(nuevaImagenUrl);
        }

        categoriaExistente.setName(categoriaDto.getName());

        categoriaExistente.setDescripcion(categoriaDto.getDescripcion());

        Categoria categoriaActualizada = categoriaRepository.save(categoriaExistente);
        return modelMapper.map(categoriaActualizada, CategoriaSalidaDto.class);
    }

    @Override
    public void eliminar(Long id) {
        Categoria categoria = obtenerPorId(id);

        // Verificar si la categoría tiene productos asociados
        if (categoria.getProductos() != null && !categoria.getProductos().isEmpty()) {
            // Si tiene productos asociados, lanzar una excepción o retornar algún valor
            throw new IllegalStateException("No se puede eliminar la categoría porque tiene productos asociados.");
        }

        // Eliminar la imagen asociada del bucket
        s3Service.eliminarImagen(categoria.getImage());


        // Eliminar la categoría de la base de datos
        categoriaRepository.delete(categoria);
    }

}