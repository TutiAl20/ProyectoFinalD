package com.travel.service;

import com.travel.entity.Favorito;
import com.travel.entity.Usuario;
import com.travel.entity.Producto;

import java.util.List;

public interface FavoritoService {
    Favorito guardarFavorito(Usuario usuario, Producto producto);

    List<Favorito> obtenerFavoritosPorUsuario(Usuario usuario);
}