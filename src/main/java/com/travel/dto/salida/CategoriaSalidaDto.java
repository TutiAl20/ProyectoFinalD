package com.travel.dto.salida;


public class CategoriaSalidaDto {
    private Long id;
    private String titulo;
    private String descripcion;
    private String imagenRepresentativa;  // URL de la imagen representativa

    public CategoriaSalidaDto(){}
    public CategoriaSalidaDto(Long id, String titulo, String descripcion, String imagenRepresentativa) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagenRepresentativa = imagenRepresentativa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImagenRepresentativa() {
        return imagenRepresentativa;
    }

    public void setImagenRepresentativa(String imagenRepresentativa) {
        this.imagenRepresentativa = imagenRepresentativa;
    }
}
