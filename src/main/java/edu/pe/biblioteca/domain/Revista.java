package edu.pe.biblioteca.domain;

import java.time.LocalDate;

public class Revista extends MaterialBibliotecario {
    private int numero;
    private String editorial;

    public Revista(String titulo, String autor, LocalDate fechaPublicacion, int numero, String editorial) {
        super(titulo, autor, fechaPublicacion);
        this.numero = numero;
        this.editorial = editorial;
    }

    public int getNumero() {
        return numero;
    }

    public String getEditorial() {
        return editorial;
    }

    @Override
    public boolean esPrestable() {
        return true;
    }

    @Override
    public String getTipo() {
        return "REVISTA";
    }
}
