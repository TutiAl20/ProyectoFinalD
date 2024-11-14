package com.travel.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Caracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String icono;

    @ManyToMany(mappedBy = "caracteristicas")
    private List<Producto> productos;

    public Caracteristica(){}
    public Caracteristica(Long id, String nombre, String icono, List<Producto> productos) {
        this.id = id;
        this.nombre = nombre;
        this.icono = icono;
        this.productos = productos;
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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}