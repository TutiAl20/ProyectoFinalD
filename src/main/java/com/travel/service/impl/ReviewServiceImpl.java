package com.travel.service.impl;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.travel.dto.entrada.ReviewSalidaDto;
import com.travel.entity.Producto;
import com.travel.entity.Review;
import com.travel.entity.UserEntity;
import com.travel.repository.ProductoRepository;
import com.travel.repository.ReservaRepository;
import com.travel.repository.ReviewRepository;
import com.travel.repository.UserRepository;
import com.travel.service.ReviewDto;
import com.travel.service.ReviewService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductoRepository productoRepository;
    private final ReservaRepository reservaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewServiceImpl(
        ReviewRepository reviewRepository,
        UserRepository userRepository,
        ProductoRepository productoRepository,
        ReservaRepository reservaRepository,
        ModelMapper modelMapper
    ) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.productoRepository = productoRepository;
        this.reservaRepository = reservaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReviewSalidaDto agregarResena(String username, Long productoId, ReviewDto reviewDto) {
        UserEntity usuario = userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Producto producto = productoRepository.findById(productoId)
            .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

            throw new UnsupportedOperationException("Unimplemented method 'agregarResena'");

        // Verificar que el usuario haya completado una reserva de este producto.
        boolean haReservado = reservaRepository.findByUsuario(usuario).stream()
            .anyMatch(reserva -> reserva.getFechaTour().getProducto().getId().equals(productoId));

        if (!haReservado) {
            throw new IllegalArgumentException("No puedes reseñar un producto que no has reservado.");
        }

        Review review = new Review();
        review.setPuntuacion(reviewDto.getPuntuacion());
        review.setComentario(reviewDto.getComentario());
        review.setFechaPublicacion(LocalDateTime.now());
        review.setProducto(producto);
        review.setUsuario(usuario);

        Review guardada = reviewRepository.save(review);

        return mapearReviewASalidaDto(guardada);
    }

    @Override
    public List<ReviewSalidaDto> listarResenasPorProducto(Long productoId) {
        List<Review> reviews = reviewRepository.findByProductoId(productoId);
        return reviews.stream()
            .map(this::mapearReviewASalidaDto)
            .collect(Collectors.toList());
    }

    @Override
    public double calcularPuntuacionMedia(Long productoId) {
        List<Review> reviews = reviewRepository.findByProductoId(productoId);
        if (reviews.isEmpty()) {
            return 0.0;
        }
        double sum = reviews.stream().mapToInt(Review::getPuntuacion).sum();
        return sum / reviews.size();
    }

    @Override
    public long contarResenasPorProducto(Long productoId) {
        return reviewRepository.findByProductoId(productoId).size();
    }

    private ReviewSalidaDto mapearReviewASalidaDto(Review review) {
        ReviewSalidaDto dto = new ReviewSalidaDto();
        dto.setPuntuacion(review.getPuntuacion());
        dto.setComentario(review.getComentario());
        dto.setFechaPublicacion(review.getFechaPublicacion());
        dto.setNombreUsuario(review.getUsuario().getNombre());
        dto.setApellidoUsuario(review.getUsuario().getApellido());
        return dto;
    }


}
