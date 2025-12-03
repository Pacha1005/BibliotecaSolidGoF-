package edu.pe.biblioteca.factory;

import edu.pe.biblioteca.domain.Libro;
import edu.pe.biblioteca.domain.Revista;
import edu.pe.biblioteca.domain.MaterialBibliotecario;
import java.time.LocalDate;

public abstract class MaterialFactory {
    
    /**
     * Factory Method - crea un material según el tipo
     * Encapsula la lógica de creación (SRP + OCP)
     */
    public static MaterialBibliotecario crearMaterial(String tipo, 
            String titulo, String autor, LocalDate fecha, String... parametrosAdicionales) {
        
        switch (tipo.toUpperCase()) {
            case "LIBRO":
                String categoria = parametrosAdicionales.length > 0 ? parametrosAdicionales[0] : "General";
                return new Libro(titulo, autor, fecha, categoria);
                
            case "REVISTA":
                int numero = parametrosAdicionales.length > 0 ? Integer.parseInt(parametrosAdicionales[0]) : 1;
                String editorial = parametrosAdicionales.length > 1 ? parametrosAdicionales[1] : "Desconocida";
                return new Revista(titulo, autor, fecha, numero, editorial);
                
            default:
                throw new IllegalArgumentException("Tipo de material no soportado: " + tipo);
        }
    }
}
