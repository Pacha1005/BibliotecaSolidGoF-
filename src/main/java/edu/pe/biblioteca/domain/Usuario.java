package edu.pe.biblioteca.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Usuario {
    private UUID id;
    private String nombre;
    private String correo;
    private String tipo; // ESTUDIANTE, DOCENTE, EXTERNO
    private boolean activo = true;
    private List<Prestamo> prestamos = new ArrayList<>();

    public Usuario(String nombre, String correo, String tipo) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.correo = correo;
        this.tipo = tipo;
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isActivo() {
        return activo;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        this.prestamos.add(prestamo);
    }

    public void desactivar() {
        this.activo = false;
    }
}
