package com.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.travel.entity.Usuario;
import com.travel.exception.UserAlreadyExistException;
import com.travel.service.UsuarioDetailsService;

@RequestMapping("/travel/usuarios")
@RestController   // Indica que esta clase es un controlador REST
public class AAuthController {

    @Autowired
    UsuarioDetailsService usuarioDetailsService;

    
    // Ruta para obtener el perfil del usuario autenticado
    @GetMapping("/profile")
    public String getProfile() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Perfil de usuario: " + email; // Devuelve el perfil del usuario autenticado
    }

    @GetMapping("/login")
    public String loginPage() {
        return "Login Page"; // Puedes retornar una página de login personalizada si lo deseas
    }

    @PostMapping("/")
     public Usuario registrarUsuario(@RequestBody Usuario usuario) { 
      Usuario nuevoUsuario = null;
    try {
        nuevoUsuario = usuarioDetailsService.registrarUsuario(usuario);
    } catch (UserAlreadyExistException e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
    }
        return nuevoUsuario; 
    }

    
}