package edu.pe.biblioteca.domain;



import edu.pe.biblioteca.domain.Libro;
import edu.pe.biblioteca.domain.Libro;
import edu.pe.biblioteca.domain.Prestamo;
import edu.pe.biblioteca.domain.Prestamo;
import edu.pe.biblioteca.domain.Usuario;
import edu.pe.biblioteca.domain.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas de la clase Usuario")
class UsuarioTest {
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("Juan Pérez", "juan@test.com", "ESTUDIANTE");
    }

    @Test
    @DisplayName("Debe crear usuario correctamente")
    void testCrearUsuario() {
        assertNotNull(usuario.getId());
        assertEquals("Juan Pérez", usuario.getNombre());
        assertEquals("juan@test.com", usuario.getCorreo());
        assertEquals("ESTUDIANTE", usuario.getTipo());
        assertTrue(usuario.isActivo());
    }

    @Test
    @DisplayName("Debe agregar préstamo al usuario")
    void testAgregarPrestamo() {
        Libro libro = new Libro("Clean Code", "Robert Martin", LocalDate.now(), "Programación");
        Prestamo prestamo = new Prestamo(usuario, libro, LocalDate.now().plusDays(14));
        
        assertEquals(1, usuario.getPrestamos().size());
        assertTrue(usuario.getPrestamos().contains(prestamo));
    }

    @Test
    @DisplayName("Debe desactivar usuario")
    void testDesactivarUsuario() {
        usuario.desactivar();
        
        assertFalse(usuario.isActivo());
    }
}
