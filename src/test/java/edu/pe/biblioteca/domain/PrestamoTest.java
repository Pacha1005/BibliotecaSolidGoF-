package edu.pe.biblioteca.domain;



import edu.pe.biblioteca.strategy.MultaPorDia;
import edu.pe.biblioteca.strategy.MultaPorTramo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de la clase Prestamo")
class PrestamoTest {
    private Usuario usuario;
    private Libro libro;
    private Prestamo prestamo;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("Juan Pérez", "juan@test.com", "ESTUDIANTE");
        libro = new Libro("Clean Code", "Robert Martin", LocalDate.of(2008, 8, 1), "Programación");
        prestamo = new Prestamo(usuario, libro, LocalDate.now().plusDays(14));
    }

    @Test
    @DisplayName("Debe crear un préstamo correctamente")
    void testCrearPrestamo() {
        assertNotNull(prestamo.getId());
        assertTrue(prestamo.isActivo());
        assertFalse(libro.isDisponible());
        assertEquals(usuario, prestamo.getUsuario());
    }

    @Test
    @DisplayName("Debe detectar préstamo vencido")
    void testPrestamoVencido() {
        Libro libroTest = new Libro("Effective Java", "Joshua Bloch", LocalDate.of(2018, 1, 1), "Programación");
        Prestamo prestamoVencido = new Prestamo(usuario, libroTest, LocalDate.now().minusDays(5));
        
        assertTrue(prestamoVencido.estáVencido());
        assertEquals(5, prestamoVencido.díasAtrasado());
    }

    @Test
    @DisplayName("Debe calcular multa con estrategia por día")
    void testMultaPorDia() {
        Libro libroTest = new Libro("Test Book", "Author", LocalDate.now(), "Test");
        Prestamo prestamoVencido = new Prestamo(usuario, libroTest, LocalDate.now().minusDays(5));
        
        prestamoVencido.setPoliticaMulta(new MultaPorDia(2.0));
        double multa = prestamoVencido.calcularMulta();
        
        assertEquals(10.0, multa);
    }

    @Test
    @DisplayName("Debe calcular multa con estrategia por tramo")
    void testMultaPorTramo() {
        Libro libroTest = new Libro("Test Book", "Author", LocalDate.now(), "Test");
        Prestamo prestamoVencido = new Prestamo(usuario, libroTest, LocalDate.now().minusDays(5));
        
        prestamoVencido.setPoliticaMulta(new MultaPorTramo());
        double multa = prestamoVencido.calcularMulta();
        
        assertEquals(10.0, multa);
    }

    @Test
    @DisplayName("Debe devolver material correctamente")
    void testDevolverMaterial() {
        prestamo.devolverMaterial();
        
        assertFalse(prestamo.isActivo());
        assertTrue(libro.isDisponible());
        assertNotNull(prestamo.getFechaDevolucionReal());
    }

    @Test
    @DisplayName("Debe calcular multa 0 para préstamo sin vencer")
    void testSinMultaSinVencer() {
        prestamo.setPoliticaMulta(new MultaPorDia(2.0));
        
        assertEquals(0.0, prestamo.calcularMulta());
    }
}
