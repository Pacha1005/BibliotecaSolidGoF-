package edu.pe.biblioteca.domain;

import java.time.LocalDate;

public class Libro extends MaterialBibliotecario {

    private String categoria;

    public Libro(String titulo, String autor, LocalDate fechaPublicacion, String categoria) {
        super(titulo, autor, fechaPublicacion);
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public boolean esPrestable() {
        return true;
    }

    @Override
    public String getTipo() {
        return "LIBRO";
    }
}

