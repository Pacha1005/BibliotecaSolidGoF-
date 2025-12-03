package edu.pe.biblioteca.adapter;

import edu.pe.biblioteca.domain.MaterialBibliotecario;
import edu.pe.biblioteca.factory.MaterialFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorJSON implements ImportadorDatos {
    
    @Override
    public List<MaterialBibliotecario> importar(String json) {
        List<MaterialBibliotecario> materiales = new ArrayList<>();
        
        // Simulación: en producción usarías una librería JSON
        // Por ahora, parseamos un formato simple
        
        System.out.println("📄 Importando desde JSON...");
        
        // Ejemplo: simular algunos datos
        MaterialBibliotecario libro = MaterialFactory.crearMaterial("LIBRO",
                "Effective Java", "Joshua Bloch", LocalDate.of(2018, 1, 1),
                "Programación");
        
        MaterialBibliotecario revista = MaterialFactory.crearMaterial("REVISTA",
                "Nature", "Editor", LocalDate.of(2024, 10, 1),
                "450", "Springer");
        
        materiales.add(libro);
        materiales.add(revista);
        
        System.out.println("✓ Importados " + materiales.size() + " materiales desde JSON");
        
        return materiales;
    }
}
