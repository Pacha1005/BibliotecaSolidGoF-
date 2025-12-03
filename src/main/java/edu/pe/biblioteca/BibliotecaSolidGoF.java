package edu.pe.biblioteca;

import edu.pe.biblioteca.domain.MaterialBibliotecario;
import edu.pe.biblioteca.domain.Usuario;
import edu.pe.biblioteca.domain.Prestamo;
import edu.pe.biblioteca.service.BibliotecaService;
import edu.pe.biblioteca.service.ReporteTexto;
import edu.pe.biblioteca.service.ReporteCSV;
import edu.pe.biblioteca.adapter.AdaptadorCSV;
import edu.pe.biblioteca.adapter.AdaptadorJSON;
import edu.pe.biblioteca.strategy.MultaPorDia;
import edu.pe.biblioteca.strategy.MultaPorTramo;
import edu.pe.biblioteca.strategy.NotificadorEmail;
import edu.pe.biblioteca.strategy.LoggerEventos;
import java.time.LocalDate;

public class BibliotecaSolidGoF {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE BIBLIOTECA - SOLID + GoF ===\n");
        
        BibliotecaService biblioteca = new BibliotecaService(new MultaPorDia(2.0));
        
        MaterialBibliotecario libro = biblioteca.registrarMaterial("LIBRO",
                "Clean Code", "Robert C. Martin", LocalDate.of(2008, 8, 1),
                "Programación");
        
        MaterialBibliotecario revista = biblioteca.registrarMaterial("REVISTA",
                "Wired", "Editor", LocalDate.of(2024, 11, 1),
                "12", "Condé Nast");
        
        System.out.println("\n--- Importando materiales (Adapter Pattern) ---");
        biblioteca.importarMateriales(new AdaptadorJSON(), "datos.json");
        
        Usuario usuario1 = biblioteca.registrarUsuario("Juan Pérez", "juan@ejemplo.com", "ESTUDIANTE");
        Usuario usuario2 = biblioteca.registrarUsuario("María López", "maria@ejemplo.com", "DOCENTE");
        
        Prestamo prestamo1 = biblioteca.crearPrestamo(usuario1, libro, -5);
        Prestamo prestamo2 = biblioteca.crearPrestamo(usuario2, revista, 10);
        
        System.out.println("\n--- Configurando observadores ---");
        NotificadorEmail notificador = new NotificadorEmail();
        LoggerEventos logger = new LoggerEventos();
        
        biblioteca.agregarObservadorPrestamo(prestamo1, notificador);
        biblioteca.agregarObservadorPrestamo(prestamo1, logger);
        
        biblioteca.verificarPrestamosVencidos();
        
        biblioteca.setPoliticaMulta(new MultaPorTramo());
        
        System.out.println("\n--- Devolución de material ---");
        biblioteca.devolverMaterial(prestamo1);
        
        // TEMPLATE METHOD PATTERN: Generar reportes con diferentes formatos
        System.out.println("\n--- TEMPLATE METHOD: Generando Reportes ---");
        System.out.println("\n📋 REPORTE EN TEXTO:");
        System.out.println(biblioteca.generarReporte(new ReporteTexto()));
        
        System.out.println("\n📊 REPORTE EN CSV:");
        System.out.println(biblioteca.generarReporte(new ReporteCSV()));
        
    }
}
