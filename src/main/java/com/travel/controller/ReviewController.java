package com.travel.controller;

import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.dto.entrada.ReviewSalidaDto;
import com.travel.service.ReviewDto;
import com.travel.service.ReviewService;

@RestController
@RequestMapping("/travel/public/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{productoId}")
    public ResponseEntity<ReviewSalidaDto> crearResena(@PathVariable Long productoId, @RequestBody ReviewDto reviewDto, Authentication authentication) {
        String username = authentication.name();
        ReviewSalidaDto respuesta = reviewService.agregarResena(username, productoId, reviewDto);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @GetMapping("/{productoId}")
    public ResponseEntity<List<ReviewSalidaDto>> listarResenas(@PathVariable Long productoId) {
        List<ReviewSalidaDto> resenas = reviewService.listarResenasPorProducto(productoId);
        return ResponseEntity.ok(resenas);
    }
}



