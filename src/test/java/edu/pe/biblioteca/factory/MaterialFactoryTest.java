package edu.pe.biblioteca.factory;



import edu.pe.biblioteca.domain.MaterialBibliotecario;
import edu.pe.biblioteca.domain.Libro;
import edu.pe.biblioteca.domain.Revista;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas del Factory Method")
class MaterialFactoryTest {

    @Test
    @DisplayName("Debe crear un Libro usando Factory")
    void testCrearLibro() {
        MaterialBibliotecario material = MaterialFactory.crearMaterial("LIBRO",
                "Clean Code", "Robert Martin", LocalDate.of(2008, 8, 1),
                "Programación");
        
        assertNotNull(material);
        assertTrue(material instanceof Libro);
        assertEquals("Clean Code", material.getTitulo());
        assertEquals("LIBRO", material.getTipo());
    }

    @Test
    @DisplayName("Debe crear una Revista usando Factory")
    void testCrearRevista() {
        MaterialBibliotecario material = MaterialFactory.crearMaterial("REVISTA",
                "Wired", "Editor", LocalDate.of(2024, 11, 1),
                "12", "Condé Nast");
        
        assertNotNull(material);
        assertTrue(material instanceof Revista);
        assertEquals("Wired", material.getTitulo());
        assertEquals("REVISTA", material.getTipo());
    }

    @Test
    @DisplayName("Debe lanzar excepción para tipo desconocido")
    void testTipoDesconocido() {
        assertThrows(IllegalArgumentException.class, () -> {
            MaterialFactory.crearMaterial("LIBRO_ELECTRONICO",
                    "Title", "Author", LocalDate.now());
        });
    }

    @Test
    @DisplayName("Debe crear material con parámetros por defecto")
    void testCrearMaterialPorDefecto() {
        MaterialBibliotecario material = MaterialFactory.crearMaterial("LIBRO",
                "Test", "Author", LocalDate.now());
        
        assertTrue(material.esPrestable());
        assertTrue(material.isDisponible());
    }
}
