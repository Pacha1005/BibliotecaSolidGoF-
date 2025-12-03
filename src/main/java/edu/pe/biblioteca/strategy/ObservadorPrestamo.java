package edu.pe.biblioteca.strategy;

import edu.pe.biblioteca.domain.Prestamo;

/**
 * OBSERVER PATTERN: Define el contrato para observadores de eventos de préstamo
 * (SRP: cada observador tiene una responsabilidad específica)
 * (OCP: puedo agregar nuevos tipos de notificaciones sin modificar Prestamo)
 */
public interface ObservadorPrestamo {
    void prestamoVencido(Prestamo prestamo);
    void materialDevuelto(Prestamo prestamo);
}
