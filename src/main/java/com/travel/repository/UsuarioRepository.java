package com.travel.repository;

import com.travel.entity.Usuario;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository   // Indica que esta es una interfaz de repositorio
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

     // Método para encontrar un usuario por su email
    Usuario findByEmail(String email); // Método para buscar por correo electrónico
    
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Usuario getUserByUsername(@Param("email") String email);

    @Modifying
    @Query("update Usuario u set u.nombre = ?1, u.apellido = ?2, u.contrasena = ?3 where u.email = ?4")
    void updateUsuario(String nombre, String apellido, String contrasena, String email);

    
}