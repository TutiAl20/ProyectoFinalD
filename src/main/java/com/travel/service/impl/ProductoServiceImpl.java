package com.travel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.amazonaws.services.kms.model.NotFoundException;
import com.travel.dto.entrada.ProductoDto;
import com.travel.dto.salida.ProductoSalidaDto;
import com.travel.entity.ProductoImagen;
import com.travel.service.S3Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.travel.entity.Producto;
import com.travel.exception.TravelRepositoryException;
import com.travel.repository.ProductoRepository;
import com.travel.service.ProductoService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

// Clase de servicio para manejar la lógica de productos
@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ModelMapper modelMapper;


    // Método para listar todos los productos
    public List<Producto> listarProductos() {
        List<Producto> productos = StreamSupport
            .stream(productoRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());

        return productos; // Devuelve la lista de productos
    }

    // Método para listar productos por id
    public Producto listarProductosPorId(long id) {
        return productoRepository.findById(id); // Devuelve un producto por id
    }

    // Método para listar productos por nombre
    public Producto listarProductosPorNombre(String nombre) {
        return productoRepository.findByNombre(nombre); // Devuelve un producto por nombre
    }

    public Producto agregarProducto(Producto producto) throws TravelRepositoryException {
        Producto productoExistente = productoRepository.findByNombre(producto.getNombre());
        if(productoExistente != null) {
           throw new TravelRepositoryException("Producto con nombre: " + producto.getNombre() + " ya existe!");
        } else {
            Producto productoGuardado = productoRepository.save(producto);
            return productoGuardado;
        }
    }

    public void deleteProducto(long id) throws TravelRepositoryException{
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        } else {
            throw new TravelRepositoryException(id + "Producto no encontrado");
        }
    }


    public ProductoSalidaDto  crearProducto(ProductoDto productoDTO) {
        // Subir las imágenes a AWS S3 y obtener las URLs
        List<ProductoImagen> productoImagenes = new ArrayList<>();
        List<String> imagenUrls = new ArrayList<>();

        // Convertir ProductoDto a Producto usando ModelMapper
        Producto producto = modelMapper.map(productoDTO, Producto.class);

        // Crear las imágenes y establecer la referencia al producto
        for (MultipartFile imagen : productoDTO.getImagenes()) {
            String imagenUrl = s3Service.subirImagen(imagen);
            ProductoImagen productoImagen = new ProductoImagen();
            productoImagen.setImagen(imagenUrl);
            productoImagen.setProducto(producto); // Asignar el producto a cada imagen
            productoImagenes.add(productoImagen);
            imagenUrls.add(imagenUrl);
        }

        // Asignar las imágenes al producto antes de guardar
        producto.setImagenes(productoImagenes);

        // Guardar el producto en la base de datos
        Producto productoGuardado = productoRepository.save(producto);

        // Convertir Producto guardado a ProductoSalidaDto usando ModelMapper
        ProductoSalidaDto productoSalidaDto = modelMapper.map(productoGuardado, ProductoSalidaDto.class);

        // Configurar las URLs de las imágenes en ProductoSalidaDto
        productoSalidaDto.setImagenes(imagenUrls);

        return productoSalidaDto;
    }

    public ProductoSalidaDto listarProductoPorId(Long id) {
        // Buscar el producto por id
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        // Mapear Producto a ProductoSalidaDto
        ProductoSalidaDto productoSalidaDto = modelMapper.map(producto, ProductoSalidaDto.class);

        // Extraer URLs de imágenes y asignarlas al DTO de salida
        List<String> imagenUrls = producto.getImagenes().stream()
                .map(ProductoImagen::getImagen)
                .collect(Collectors.toList());
        productoSalidaDto.setImagenes(imagenUrls);

        return productoSalidaDto;
    }

    public List<ProductoSalidaDto> listarTodosLosProductos() {
        // Convertir Iterable a List
        List<Producto> productos = StreamSupport.stream(productoRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        // Mapear cada Producto a ProductoSalidaDto
        return productos.stream()
                .map(producto -> {
                    ProductoSalidaDto dto = modelMapper.map(producto, ProductoSalidaDto.class);
                    List<String> imagenUrls = producto.getImagenes().stream()
                            .map(ProductoImagen::getImagen)
                            .collect(Collectors.toList());
                    dto.setImagenes(imagenUrls);
                    return dto;
                })
                .collect(Collectors.toList());
    }

     
    

}


