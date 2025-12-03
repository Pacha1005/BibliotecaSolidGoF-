package edu.pe.biblioteca.service;

import edu.pe.biblioteca.domain.Prestamo;
import java.util.List;

public class ReporteCSV extends GeneradorReporte {
    
    @Override
    protected String generarEncabezado() {
        return "Usuario,Material,Fecha Préstamo,Vencimiento,Estado,Días Atrasado,Multa";
    }
    
    @Override
    protected String generarCuerpo(List<Prestamo> prestamos) {
        StringBuilder sb = new StringBuilder();
        
        for (Prestamo p : prestamos) {
            sb.append(p.getUsuario().getNombre()).append(",");
            sb.append(p.getMaterial().getTitulo()).append(",");
            sb.append(p.getFechaPrestamo()).append(",");
            sb.append(p.getFechaDevolucionEsperada()).append(",");
            sb.append(p.isActivo() ? "ACTIVO" : "DEVUELTO").append(",");
            sb.append(p.díasAtrasado()).append(",");
            sb.append(String.format("%.2f", p.calcularMulta())).append("\n");
        }
        
        return sb.toString();
    }
    
    @Override
    protected String generarPie(List<Prestamo> prestamos) {
        double multaTotal = prestamos.stream()
                .mapToDouble(Prestamo::calcularMulta)
                .sum();
        return "Total multas generadas,S/. " + String.format("%.2f", multaTotal);
    }
}
