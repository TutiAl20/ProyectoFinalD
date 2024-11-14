package com.travel.service.impl;

import com.travel.entity.Producto;
import com.travel.entity.Usuario;
import com.travel.exception.UserAlreadyExistException;
import com.travel.repository.UsuarioRepository;
import com.travel.service.UsuarioDetailsService;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service   // Indica que esta clase es un servicio de Spring
public class UsuarioDetailsServiceImpl implements UsuarioDetailsService, UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    // Constructor para inyectar el repositorio de Usuario
    public UsuarioDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método que carga los detalles del usuario por el nombre de usuario (en este caso, el correo electrónico)
    @Override
    public Usuario loadUserByName(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email); // Aquí debes definir el método en tu repositorio
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
         // Retorna un objeto UserDetails con los datos del usuario (como el email y la contraseña)
        return usuario;
    }
    @Override
    public Usuario registrarUsuario(Usuario usuario) throws UserAlreadyExistException{
        Usuario usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());
        Usuario nuevoUsuario = null;
        if (usuarioEncontrado != null && usuarioEncontrado.getEmail().equals(usuario.getEmail())){
            throw new UserAlreadyExistException("Este usuario ya existe");
        }
        else {
          nuevoUsuario = usuarioRepository.save(usuario);
        }
        return nuevoUsuario;
    }

    @Override
    public List<Usuario> loadAllUsers() {
        List<Usuario> usuarios = StreamSupport
            .stream(usuarioRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());

        return usuarios; // Devuelve la lista de usuarios
    }

    @Override
    @Transactional
    public Usuario actualizarUsuario(Usuario usuario) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.getUserByUsername(usuario.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else {            
            usuarioRepository.updateUsuario(usuario.getNombre(),usuario.getApellido(),usuario.getContrasena(),usuario.getEmail());
            return usuario;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(
            user.getNombre(),
            user.getContrasena(),
            user.getRoles().stream()
                .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getName()))
                .toList() 
        );
    }
}
