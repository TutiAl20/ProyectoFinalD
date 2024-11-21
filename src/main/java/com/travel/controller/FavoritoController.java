package com.travel.controller;

import com.travel.entity.Favorito;
import com.travel.entity.Producto;
import com.travel.entity.Usuario;
import com.travel.service.FavoritoService;
import com.travel.service.UsuarioDetailsService;
import com.travel.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/travel/public/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    @Autowired
    private ProductoRepository productoRepository;

    // Endpoint para agregar un producto como favorito
    @PostMapping("/{productoId}")
    public Favorito agregarFavorito(@PathVariable Long productoId) {
        String email = usuarioDetailsService.getCurrentUserEmail(); // Obtener el usuario autenticado
        Usuario usuario = usuarioDetailsService.loadUserByName(email);

        // Verificar si el producto existe
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        // Guardar el producto como favorito
        return favoritoService.guardarFavorito(usuario, producto);
    }

    // Endpoint para obtener los favoritos de un usuario
    @GetMapping
    public List<Favorito> obtenerFavoritos() {
        String email = usuarioDetailsService.getCurrentUserEmail(); // Obtener el usuario autenticado
        Usuario usuario = usuarioDetailsService.loadUserByName(email);

        // Listar los productos favoritos del usuario
        return favoritoService.obtenerFavoritosPorUsuario(usuario);
    }
}