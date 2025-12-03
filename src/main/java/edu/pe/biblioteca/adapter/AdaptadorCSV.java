package edu.pe.biblioteca.adapter;

import edu.pe.biblioteca.domain.MaterialBibliotecario;
import edu.pe.biblioteca.factory.MaterialFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorCSV implements ImportadorDatos {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<MaterialBibliotecario> importar(String ruta) {
        List<MaterialBibliotecario> materiales = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {
            String linea;
            boolean esEncabezado = true;
            
            while ((linea = reader.readLine()) != null) {
                if (esEncabezado) {
                    esEncabezado = false;
                    continue; // Saltar encabezado
                }
                
                MaterialBibliotecario material = parsearLineaCSV(linea);
                if (material != null) {
                    materiales.add(material);
                }
            }
            
            System.out.println("✓ Importados " + materiales.size() + " materiales desde CSV");
        } catch (IOException e) {
            System.err.println("✗ Error al leer CSV: " + e.getMessage());
        }
        
        return materiales;
    }

    private MaterialBibliotecario parsearLineaCSV(String linea) {
        String[] campos = linea.split(",");
        
        if (campos.length < 4) {
            return null;
        }
        
        String tipo = campos[0].trim();
        String titulo = campos[1].trim();
        String autor = campos[2].trim();
        LocalDate fecha = LocalDate.parse(campos[3].trim(), dateFormatter);
        
        if ("LIBRO".equals(tipo.toUpperCase())) {
            String categoria = campos.length > 4 ? campos[4].trim() : "General";
            return MaterialFactory.crearMaterial("LIBRO", titulo, autor, fecha, categoria);
        } else if ("REVISTA".equals(tipo.toUpperCase())) {
            int numero = campos.length > 4 ? Integer.parseInt(campos[4].trim()) : 1;
            String editorial = campos.length > 5 ? campos[5].trim() : "Desconocida";
            return MaterialFactory.crearMaterial("REVISTA", titulo, autor, fecha, String.valueOf(numero), editorial);
        }
        
        return null;
    }
}
