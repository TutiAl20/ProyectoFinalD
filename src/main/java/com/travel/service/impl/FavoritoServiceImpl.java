package com.travel.service.impl;

import com.travel.entity.Favorito;
import com.travel.entity.Usuario;
import com.travel.entity.Producto;
import com.travel.repository.FavoritoRepository;
import org.springframework.stereotype.Service;
import com.travel.service.FavoritoService;

import java.util.List;

@Service
public class FavoritoServiceImpl implements FavoritoService {

    private final FavoritoRepository favoritoRepository;

    public FavoritoServiceImpl(FavoritoRepository favoritoRepository) {
        this.favoritoRepository = favoritoRepository;
    }

    @Override
    public Favorito guardarFavorito(Usuario usuario, Producto producto) {
        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setProducto(producto);
        return favoritoRepository.save(favorito);
    }

    @Override
    public List<Favorito> obtenerFavoritosPorUsuario(Usuario usuario) {
        return favoritoRepository.findByUsuario(usuario);
    }
}