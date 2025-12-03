package edu.pe.biblioteca.service;

import edu.pe.biblioteca.domain.Prestamo;
import java.util.List;

/**
 * TEMPLATE METHOD PATTERN: Define el esqueleto de un algoritmo
 * dejando que subclases implementen pasos específicos
 * (OCP: puedo agregar nuevos tipos de reportes sin modificar esta clase)
 * (SRP: cada subclase se enfoca en su formato específico)
 */
public abstract class GeneradorReporte {
    
    /**
     * Template Method: Define el flujo general
     */
    public final String generar(List<Prestamo> prestamos) {
        StringBuilder sb = new StringBuilder();
        
        // Paso 1: Encabezado
        sb.append(generarEncabezado()).append("\n");
        
        // Paso 2: Cuerpo (cada subclase lo implementa diferente)
        sb.append(generarCuerpo(prestamos)).append("\n");
        
        // Paso 3: Pie
        sb.append(generarPie(prestamos)).append("\n");
        
        return sb.toString();
    }
    
    protected abstract String generarEncabezado();
    
    protected abstract String generarCuerpo(List<Prestamo> prestamos);
    
    protected String generarPie(List<Prestamo> prestamos) {
        return "--- Fin del Reporte ---";
    }
}
