package edu.pe.biblioteca.service;

import edu.pe.biblioteca.domain.Prestamo;
import java.util.List;

public class ReporteTexto extends GeneradorReporte {
    
    @Override
    protected String generarEncabezado() {
        return "╔════════════════════════════════════════╗\n" +
               "║    REPORTE DE PRÉSTAMOS (TEXTO)       ║\n" +
               "╚════════════════════════════════════════╝";
    }
    
    @Override
    protected String generarCuerpo(List<Prestamo> prestamos) {
        StringBuilder sb = new StringBuilder();
        
        for (Prestamo p : prestamos) {
            sb.append("─────────────────────────────────\n");
            sb.append("Usuario: ").append(p.getUsuario().getNombre()).append("\n");
            sb.append("Material: ").append(p.getMaterial().getTitulo()).append("\n");
            sb.append("Fecha Préstamo: ").append(p.getFechaPrestamo()).append("\n");
            sb.append("Vencimiento: ").append(p.getFechaDevolucionEsperada()).append("\n");
            sb.append("Estado: ").append(p.isActivo() ? "ACTIVO" : "DEVUELTO").append("\n");
            if (p.estáVencido()) {
                sb.append("⚠ VENCIDO - Días atrasado: ").append(p.díasAtrasado()).append("\n");
            }
        }
        
        return sb.toString();
    }
    
    @Override
    protected String generarPie(List<Prestamo> prestamos) {
        long activos = prestamos.stream().filter(Prestamo::isActivo).count();
        long vencidos = prestamos.stream().filter(Prestamo::estáVencido).count();
        return "Total de préstamos: " + prestamos.size() + "\n" +
               "Activos: " + activos + " | Vencidos: " + vencidos;
    }
}
