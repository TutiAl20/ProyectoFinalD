package com.travel.service.impl;

import com.travel.entity.Usuario;
import com.travel.exception.UserAlreadyExistException;
import com.travel.repository.UsuarioRepository;
import com.travel.service.EmailService;
import com.travel.service.UsuarioDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service   // Indica que esta clase es un servicio de Spring
public class UsuarioDetailsServiceImpl implements UsuarioDetailsService, UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    @Autowired
    private EmailService emailService;

    // Constructor para inyectar el repositorio de Usuario
    public UsuarioDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método que carga los detalles del usuario por el nombre de usuario (en este caso, el correo electrónico)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByEmail(email); // Aquí debes definir el método en tu repositorio
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        // Retorna un objeto UserDetails con los datos del usuario (como el email y la contraseña)
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getContrasena(),
            user.getRoles().stream()
                .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList())
        );
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) throws UserAlreadyExistException {
        Usuario usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioEncontrado != null) {
            throw new UserAlreadyExistException("Este usuario ya existe");
        }
        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        // Enviar correo de confirmación
        String subject = "Registro exitoso en Travel";
        String text = String.format(
            "Hola %s,\n\nGracias por registrarte en nuestra plataforma.\n\nTu usuario: %s\nTu correo: %s\n\nInicia sesión aquí: http://localhost:8080/login\n\n¡Esperamos que disfrutes la experiencia!",
            usuario.getNombre(),
            usuario.getNombre(),
            usuario.getEmail()
        );
        // servicio de email
        emailService.sendEmail(usuario.getEmail(), subject, text);

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
            usuarioRepository.updateUsuario(usuario.getNombre(), usuario.getApellido(), usuario.getContrasena(), usuario.getEmail());
            return usuario;
        }
    }

    @Override
    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public Usuario loadUserByName(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email); // Aquí debes definir el método en tu repositorio
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
            // Retorna un objeto UserDetails con los datos del usuario (como el email y la contraseña)
        return usuario;
    }
}
