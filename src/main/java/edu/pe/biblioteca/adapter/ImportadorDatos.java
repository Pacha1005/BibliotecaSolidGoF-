package edu.pe.biblioteca.adapter;

import edu.pe.biblioteca.domain.MaterialBibliotecario;
import java.util.List;

/**
 * ADAPTER PATTERN: Interfaz común para diferentes fuentes de datos
 * (OCP: puedo agregar nuevos adaptadores sin modificar existentes)
 * (DIP: dependo de la abstracción, no de implementaciones concretas)
 */
public interface ImportadorDatos {
    List<MaterialBibliotecario> importar(String ruta);
}
