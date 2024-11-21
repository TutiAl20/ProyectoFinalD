package com.travel.service;


import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.travel.entity.Usuario;
import com.travel.exception.UserAlreadyExistException;

public interface UsuarioDetailsService {
    public Usuario loadUserByName(String email) throws UsernameNotFoundException;

    public List<Usuario> loadAllUsers();
    
    public Usuario registrarUsuario(Usuario usuario) throws UserAlreadyExistException;

    public Usuario actualizarUsuario(Usuario usuario) throws UsernameNotFoundException;

    public String getCurrentUserEmail();

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

}