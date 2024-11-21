package com.travel.controller;

import com.travel.entity.Usuario;
import com.travel.service.EmailService;
import com.travel.service.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/travel/public/mail")
public class MailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    // Endpoint para reenviar correo de confirmación
    @GetMapping("/resend/{email}")
    public String resendConfirmationEmail(@PathVariable String email) {
        // Buscar al usuario por email
        Usuario usuario = usuarioDetailsService.loadUserByName(email);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }

        // Componer el correo de confirmación
        String subject = "Reenvío: Registro exitoso en Travel";
        String text = String.format(
            "Hola %s,\n\nTu registro en Travel fue exitoso.\nTu correo: %s\n\nInicia sesión aquí: http://localhost:8080/login\n\n¡Esperamos que disfrutes la experiencia!",
            usuario.getNombre(),
            usuario.getEmail()
        );

        // Enviar el correo
        emailService.sendEmail(usuario.getEmail(), subject, text);

        return "Correo de confirmación reenviado con éxito.";
    }
}