package edu.pe.biblioteca.domain;

import edu.pe.biblioteca.strategy.PoliticaMulta;
import edu.pe.biblioteca.strategy.ObservadorPrestamo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Prestamo {
    private UUID id;
    private Usuario usuario;
    private MaterialBibliotecario material;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private boolean activo = true;
    private PoliticaMulta politicaMulta;
    private List<ObservadorPrestamo> observadores = new ArrayList<>(); // ← OBSERVER

    public Prestamo(Usuario usuario, MaterialBibliotecario material, LocalDate fechaDevolucionEsperada) {
        this.id = UUID.randomUUID();
        this.usuario = usuario;
        this.material = material;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.material.prestar();
        usuario.agregarPrestamo(this);
    }

    public UUID getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public MaterialBibliotecario getMaterial() {
        return material;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public boolean isActivo() {
        return activo;
    }

    public boolean estáVencido() {
        return activo && LocalDate.now().isAfter(fechaDevolucionEsperada);
    }

    public void devolverMaterial() {
        this.fechaDevolucionReal = LocalDate.now();
        this.activo = false;
        this.material.devolver();
        notificarObservadores("devolucion"); // ← NOTIFICAR
    }

    public long díasAtrasado() {
        if (!estáVencido()) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(fechaDevolucionEsperada, LocalDate.now());
    }

    public void setPoliticaMulta(PoliticaMulta politica) {
        this.politicaMulta = politica;
    }

    public double calcularMulta() {
        if (politicaMulta == null) {
            return 0.0;
        }
        return politicaMulta.calcularMulta(this);
    }

    // ========== OBSERVER PATTERN ==========
    
    public void agregarObservador(ObservadorPrestamo observador) {
        this.observadores.add(observador);
    }

    public void removerObservador(ObservadorPrestamo observador) {
        this.observadores.remove(observador);
    }

    public void verificarVencimiento() {
        if (estáVencido()) {
            notificarObservadores("vencimiento");
        }
    }

    private void notificarObservadores(String tipo) {
        for (ObservadorPrestamo obs : observadores) {
            if ("vencimiento".equals(tipo)) {
                obs.prestamoVencido(this);
            } else if ("devolucion".equals(tipo)) {
                obs.materialDevuelto(this);
            }
        }
    }
}
