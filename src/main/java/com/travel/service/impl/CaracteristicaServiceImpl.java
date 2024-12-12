package com.travel.service.impl;


import com.travel.dto.entrada.CaracteristicaDto;
import com.travel.dto.salida.CaracteristicaSalidaDto;
import com.travel.entity.Caracteristica;
import com.travel.entity.UserEntity;
import com.travel.exception.NotFoundException;
import com.travel.repository.CaracteristicaRepository;
import com.travel.repository.UserRepository;
import com.travel.service.CaracteristicaService;
import com.travel.service.S3Service;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CaracteristicaServiceImpl implements CaracteristicaService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CaracteristicaRepository caracteristicaRepository;

    @Autowired
    private S3Service s3Service;


    public CaracteristicaServiceImpl(UserRepository userRepository, ModelMapper modelMapper, CaracteristicaRepository caracteristicaRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.caracteristicaRepository = caracteristicaRepository;
    }

    @Override
    public List<CaracteristicaSalidaDto> listarTodas() {
        return caracteristicaRepository.findAll().stream()
                .map(caracteristica -> modelMapper.map(caracteristica, CaracteristicaSalidaDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CaracteristicaSalidaDto obtenerCaracteristicaDtoPorId(Long id) {
        Caracteristica caracteristica = obtenerPorId(id);
        return modelMapper.map(caracteristica, CaracteristicaSalidaDto.class);
    }

    @Override
    public Caracteristica obtenerPorId(Long id) {
        return caracteristicaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Característica no encontrada"));
    }

    @Override
    public CaracteristicaSalidaDto crear(String currentUserName, CaracteristicaDto caracteristicaDto) {
        UserEntity usuario = userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Caracteristica caracteristica = new Caracteristica();
        caracteristica.setName(caracteristicaDto.getName());

        if (caracteristicaDto.getIcon() != null && !caracteristicaDto.getIcon().isEmpty()) {
            String iconUrl = s3Service.subirImagen(caracteristicaDto.getIcon());
            caracteristica.setIcon(iconUrl);
        }

        caracteristica.setUsuario(usuario);
        Caracteristica nuevaCaracteristica = caracteristicaRepository.save(caracteristica);
        return modelMapper.map(nuevaCaracteristica, CaracteristicaSalidaDto.class);
    }

    @Override
    public CaracteristicaSalidaDto actualizar(Long id, CaracteristicaDto caracteristicaDto) {
        Caracteristica caracteristicaExistente = obtenerPorId(id);

        if (caracteristicaDto.getIcon() != null && !caracteristicaDto.getIcon().isEmpty()) {
            s3Service.eliminarImagen(caracteristicaExistente.getIcon());
            String nuevaIconUrl = s3Service.subirImagen(caracteristicaDto.getIcon());
            caracteristicaExistente.setIcon(nuevaIconUrl);
        }

        caracteristicaExistente.setName(caracteristicaDto.getName());
        Caracteristica caracteristicaActualizada = caracteristicaRepository.save(caracteristicaExistente);
        return modelMapper.map(caracteristicaActualizada, CaracteristicaSalidaDto.class);
    }

    @Override
    public void eliminar(Long id) {
        Caracteristica caracteristica = obtenerPorId(id);

        if (caracteristica.getIcon() != null) {
            s3Service.eliminarImagen(caracteristica.getIcon());
        }

        caracteristicaRepository.delete(caracteristica);
    }
}
