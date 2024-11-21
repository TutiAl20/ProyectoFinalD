package com.travel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.travel.entity.Usuario;
import com.travel.exception.UserAlreadyExistException;
import com.travel.service.UsuarioDetailsService;

@RequestMapping("/travel/admin/usuarios")
@RestController   // Indica que esta clase es un controlador REST
public class AuthController {

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

    @GetMapping("/listar")
    public List<Usuario> listarUsuarios() {
        return usuarioDetailsService.loadAllUsers();
    }

    @PostMapping("/registrar")
    public Usuario registrarUsuario(@RequestBody Usuario usuario) { 
        Usuario nuevoUsuario = null;
        try {
            nuevoUsuario = usuarioDetailsService.registrarUsuario(usuario);
        } catch (UserAlreadyExistException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
        return nuevoUsuario; 
    }    

    @PutMapping("/actualizar")
    public Usuario actualizarUsuario(@RequestBody Usuario usuario) { 
        Usuario nuevoUsuario = null;
        try {
            nuevoUsuario = usuarioDetailsService.actualizarUsuario(usuario);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
        return nuevoUsuario; 
    }    

}