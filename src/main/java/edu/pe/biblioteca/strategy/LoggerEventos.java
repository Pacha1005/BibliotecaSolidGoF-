package edu.pe.biblioteca.strategy;

import edu.pe.biblioteca.domain.Prestamo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerEventos implements ObservadorPrestamo {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Override
    public void prestamoVencido(Prestamo prestamo) {
        String log = String.format("[%s] VENCIMIENTO - Usuario: %s, Material: %s, Días atrasado: %d",
                LocalDateTime.now().format(formatter),
                prestamo.getUsuario().getNombre(),
                prestamo.getMaterial().getTitulo(),
                prestamo.díasAtrasado());
        System.out.println("📋 " + log);
    }
    
    @Override
    public void materialDevuelto(Prestamo prestamo) {
        String log = String.format("[%s] DEVOLUCIÓN - Usuario: %s, Material: %s, Multa: S/. %.2f",
                LocalDateTime.now().format(formatter),
                prestamo.getUsuario().getNombre(),
                prestamo.getMaterial().getTitulo(),
                prestamo.calcularMulta());
        System.out.println("📋 " + log);
    }
}
