package com.travel.dto.entrada;


import org.springframework.web.multipart.MultipartFile;

public class CategoriaDto {
    private String titulo;
    private String descripcion;
    private MultipartFile imagenRepresentativa;

    public CategoriaDto(){}
    public CategoriaDto(String titulo, String descripcion, MultipartFile imagenRepresentativa) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagenRepresentativa = imagenRepresentativa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MultipartFile getImagenRepresentativa() {
        return imagenRepresentativa;
    }

    public void setImagenRepresentativa(MultipartFile imagenRepresentativa) {
        this.imagenRepresentativa = imagenRepresentativa;
    }
}