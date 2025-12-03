package edu.pe.biblioteca.strategy;

import edu.pe.biblioteca.domain.Prestamo;

/**
 * STRATEGY PATTERN: Define familia de algoritmos intercambiables
 * para calcular multas según política diferente
 * (SRP: cada estrategia tiene una sola responsabilidad)
 * (OCP: puedo agregar nuevas políticas sin modificar Prestamo)
 */
public interface PoliticaMulta {
    double calcularMulta(Prestamo prestamo);
}
