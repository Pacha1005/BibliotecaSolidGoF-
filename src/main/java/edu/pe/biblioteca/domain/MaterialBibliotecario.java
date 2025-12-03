package edu.pe.biblioteca.domain;

import java.time.LocalDate;
import java.util.UUID;

public abstract class MaterialBibliotecario {

    protected UUID id;
    protected String titulo;
    protected String autor;
    protected LocalDate fechaPublicacion;
    protected boolean disponible = true;

    protected MaterialBibliotecario(String titulo, String autor, LocalDate fechaPublicacion) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void prestar() {
        this.disponible = false;
    }

    public void devolver() {
        this.disponible = true;
    }

    public abstract boolean esPrestable();

    public abstract String getTipo();
}

