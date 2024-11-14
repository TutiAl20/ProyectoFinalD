package com.travel.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity  // Anotación que indica que esta clase es una entidad JPA (se corresponde con una tabla en la base de datos)
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueEmail", columnNames = { "email" }) })
public class Usuario {

    @Id   // Indica que este campo es la clave primaria de la entidad
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // Configura la generación automática de IDs
    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacío")   // Valida que el nombre no esté vacío

    private String nombre;

    @NotEmpty(message = "El apellido no puede estar vacío")  // Valida que el apellido no esté vacío
    private String apellido;

    @Email(message = "El correo electrónico debe ser válido")  // Valida que el email tenga un formato válido
    @NotEmpty(message = "El correo electrónico no puede estar vacío")   // Valida que el email no esté vacío
    @Column(unique=true)
    private String email;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")    // Valida que la contraseña tenga al menos 6 caracteres
    private String contrasena;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
            )
    private Set<Role> roles = new HashSet<>();

    public Usuario() {

    }

    public Usuario(@NotEmpty(message = "El nombre no puede estar vacío") String nombre,
            @NotEmpty(message = "El apellido no puede estar vacío") String apellido,
            @Email(message = "El correo electrónico debe ser válido") @NotEmpty(message = "El correo electrónico no puede estar vacío") String email,
            @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String contrasena,
            Set<Role> roles) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasena = contrasena;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }   

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}