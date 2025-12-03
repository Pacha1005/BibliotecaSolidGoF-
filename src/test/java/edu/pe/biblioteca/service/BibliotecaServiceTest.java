package edu.pe.biblioteca.service;



import edu.pe.biblioteca.domain.MaterialBibliotecario;
import edu.pe.biblioteca.domain.Usuario;
import edu.pe.biblioteca.domain.Prestamo;
import edu.pe.biblioteca.strategy.MultaPorDia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de BibliotecaService (Facade)")
class BibliotecaServiceTest {
    private BibliotecaService biblioteca;
    private MaterialBibliotecario libro;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        biblioteca = new BibliotecaService(new MultaPorDia(2.0));
        libro = biblioteca.registrarMaterial("LIBRO",
                "Clean Code", "Robert Martin", LocalDate.of(2008, 8, 1),
                "Programación");
        usuario = biblioteca.registrarUsuario("Juan Pérez", "juan@test.com", "ESTUDIANTE");
    }

    @Test
    @DisplayName("Debe registrar material correctamente")
    void testRegistrarMaterial() {
        assertNotNull(libro);
        assertEquals("Clean Code", libro.getTitulo());
        assertEquals(1, biblioteca.listarMaterialesDisponibles().size());
    }

    @Test
    @DisplayName("Debe registrar usuario correctamente")
    void testRegistrarUsuario() {
        assertNotNull(usuario);
        assertEquals("Juan Pérez", usuario.getNombre());
        assertEquals(1, biblioteca.listarUsuarios().size());
    }

    @Test
    @DisplayName("Debe crear préstamo y no dejar material disponible")
    void testCrearPrestamo() {
        Prestamo prestamo = biblioteca.crearPrestamo(usuario, libro, 14);
        
        assertNotNull(prestamo);
        assertFalse(libro.isDisponible());
        assertEquals(0, biblioteca.listarMaterialesDisponibles().size());
    }

    @Test
    @DisplayName("Debe lanzar excepción si material no está disponible")
    void testCrearPrestamoSinDisponibilidad() {
        MaterialBibliotecario libro2 = biblioteca.registrarMaterial("LIBRO",
                "Effective Java", "Joshua Bloch", LocalDate.of(2018, 1, 1),
                "Programación");
        
        Prestamo prestamo = biblioteca.crearPrestamo(usuario, libro2, 14);
        
        assertThrows(IllegalStateException.class, () -> {
            biblioteca.crearPrestamo(usuario, libro2, 14);
        });
    }

    @Test
    @DisplayName("Debe devolver material correctamente")
    void testDevolverMaterial() {
        Prestamo prestamo = biblioteca.crearPrestamo(usuario, libro, 14);
        biblioteca.devolverMaterial(prestamo);
        
        assertTrue(libro.isDisponible());
        assertEquals(1, biblioteca.listarMaterialesDisponibles().size());
    }

    @Test
    @DisplayName("Debe generar reporte en texto")
    void testGenerarReporteTexto() {
        Prestamo prestamo = biblioteca.crearPrestamo(usuario, libro, 14);
        String reporte = biblioteca.generarReporte(new ReporteTexto());
        
        assertNotNull(reporte);
        assertTrue(reporte.contains("Juan Pérez"));
        assertTrue(reporte.contains("Clean Code"));
    }

    @Test
    @DisplayName("Debe generar reporte en CSV")
    void testGenerarReporteCSV() {
        Prestamo prestamo = biblioteca.crearPrestamo(usuario, libro, 14);
        String reporte = biblioteca.generarReporte(new ReporteCSV());
        
        assertNotNull(reporte);
        assertTrue(reporte.contains("Usuario,Material"));
        assertTrue(reporte.contains("Juan Pérez,Clean Code"));
    }

    @Test
    @DisplayName("Debe listar préstamos activos")
    void testListarPrestamosActivos() {
        Prestamo prestamo = biblioteca.crearPrestamo(usuario, libro, 14);
        
        assertEquals(1, biblioteca.listarPrestamosActivos().size());
        assertTrue(biblioteca.listarPrestamosActivos().contains(prestamo));
    }
}
