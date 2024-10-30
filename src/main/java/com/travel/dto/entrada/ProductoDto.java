package com.travel.dto.entrada;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public class ProductoDto {
    private String nombre;
    private String descripcion;
    private String region;
    private int cantidad;
    private double precio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha; // Puedes usar LocalDate si lo prefieres
    private int categoria;
    private List<MultipartFile> imagenes;

    public ProductoDto(){}
    public ProductoDto(String nombre, String descripcion, String region, int cantidad, double precio, Date fecha, int categoria, List<MultipartFile> imagenes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.region = region;
        this.cantidad = cantidad;
        this.precio = precio;
        this.fecha = fecha;
        this.categoria = categoria;
        this.imagenes = imagenes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public List<MultipartFile> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<MultipartFile> imagenes) {
        this.imagenes = imagenes;
    }
}
