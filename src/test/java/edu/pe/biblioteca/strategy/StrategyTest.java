package edu.pe.biblioteca.strategy;



import edu.pe.biblioteca.domain.Libro;
import edu.pe.biblioteca.domain.Prestamo;
import edu.pe.biblioteca.domain.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Pruebas de Strategy Pattern")
class StrategyTest {
    private Usuario usuario;
    private Libro libro;
    @Mock
    private ObservadorPrestamo observador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario("Juan Pérez", "juan@test.com", "ESTUDIANTE");
        libro = new Libro("Clean Code", "Robert Martin", LocalDate.of(2008, 8, 1), "Programación");
    }

    @Test
    @DisplayName("MultaPorDia debe calcular correctamente")
    void testMultaPorDiaCalculo() {
        PoliticaMulta politica = new MultaPorDia(5.0);
        Prestamo prestamo = new Prestamo(usuario, libro, LocalDate.now().minusDays(3));
        
        double multa = politica.calcularMulta(prestamo);
        
        assertEquals(15.0, multa); // 3 días * 5.0
    }

    @Test
    @DisplayName("MultaPorTramo debe asignar tramo correcto")
    void testMultaPorTramo() {
        PoliticaMulta politica = new MultaPorTramo();
        
        // Test tramo 1 (1-3 días)
        Prestamo prestamo1 = new Prestamo(usuario, 
                new Libro("Test1", "Author", LocalDate.now(), "Test"), 
                LocalDate.now().minusDays(2));
        assertEquals(5.0, politica.calcularMulta(prestamo1));
        
        // Test tramo 2 (4-7 días)
        Prestamo prestamo2 = new Prestamo(usuario, 
                new Libro("Test2", "Author", LocalDate.now(), "Test"), 
                LocalDate.now().minusDays(5));
        assertEquals(10.0, politica.calcularMulta(prestamo2));
        
        // Test tramo 3 (8+ días)
        Prestamo prestamo3 = new Prestamo(usuario, 
                new Libro("Test3", "Author", LocalDate.now(), "Test"), 
                LocalDate.now().minusDays(10));
        assertEquals(20.0, politica.calcularMulta(prestamo3));
    }

    @Test
    @DisplayName("Observer debe ser notificado de vencimiento")
    void testObserverVencimiento() {
        Prestamo prestamo = new Prestamo(usuario, libro, LocalDate.now().minusDays(5));
        prestamo.agregarObservador(observador);
        
        prestamo.verificarVencimiento();
        
        verify(observador, times(1)).prestamoVencido(prestamo);
    }

    @Test
    @DisplayName("Observer debe ser notificado de devolución")
    void testObserverDevolucion() {
        Prestamo prestamo = new Prestamo(usuario, libro, LocalDate.now().plusDays(14));
        prestamo.agregarObservador(observador);
        
        prestamo.devolverMaterial();
        
        verify(observador, times(1)).materialDevuelto(prestamo);
    }

    @Test
    @DisplayName("Observer debe ser removido correctamente")
    void testRemoverObservador() {
        Prestamo prestamo = new Prestamo(usuario, libro, LocalDate.now().minusDays(5));
        prestamo.agregarObservador(observador);
        prestamo.removerObservador(observador);
        
        prestamo.verificarVencimiento();
        
        verify(observador, never()).prestamoVencido(prestamo);
    }
}
