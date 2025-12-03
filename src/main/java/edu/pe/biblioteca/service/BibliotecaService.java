package edu.pe.biblioteca.service;

import edu.pe.biblioteca.domain.MaterialBibliotecario;
import edu.pe.biblioteca.domain.Usuario;
import edu.pe.biblioteca.domain.Prestamo;
import edu.pe.biblioteca.strategy.ObservadorPrestamo;
import edu.pe.biblioteca.adapter.ImportadorDatos;
import edu.pe.biblioteca.factory.MaterialFactory;
import edu.pe.biblioteca.strategy.PoliticaMulta;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * FACADE PATTERN: Encapsula la complejidad de:
 * - Creación de materiales (Factory)
 * - Gestión de préstamos
 * - Cálculo de multas (Strategy)
 * 
 * Ventaja: UI/CLI solo llama a BibliotecaService, no toca detalles internos
 * (OCP: puedo cambiar implementación sin afectar clientes)
 * (SRP: BibliotecaService coordina, pero cada clase hace su trabajo)
 */
public class BibliotecaService {
    private List<MaterialBibliotecario> materiales = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Prestamo> prestamos = new ArrayList<>();
    private PoliticaMulta politicaMulta;

    public BibliotecaService(PoliticaMulta politicaMulta) {
        this.politicaMulta = politicaMulta;
    }

    // ========== GESTIÓN DE MATERIALES ==========
    
    public MaterialBibliotecario registrarMaterial(String tipo, String titulo, 
            String autor, LocalDate fecha, String... parametros) {
        MaterialBibliotecario material = MaterialFactory.crearMaterial(tipo, titulo, autor, fecha, parametros);
        materiales.add(material);
        System.out.println("✓ Material registrado: " + titulo + " (" + tipo + ")");
        return material;
    }

    public List<MaterialBibliotecario> listarMaterialesDisponibles() {
        return materiales.stream()
                .filter(MaterialBibliotecario::isDisponible)
                .toList();
    }

    // ========== GESTIÓN DE USUARIOS ==========
    
    public Usuario registrarUsuario(String nombre, String correo, String tipo) {
        Usuario usuario = new Usuario(nombre, correo, tipo);
        usuarios.add(usuario);
        System.out.println("✓ Usuario registrado: " + nombre + " (" + tipo + ")");
        return usuario;
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    // ========== GESTIÓN DE PRÉSTAMOS ==========
    
    public Prestamo crearPrestamo(Usuario usuario, MaterialBibliotecario material, int díasMaximos) {
        if (!material.isDisponible()) {
            throw new IllegalStateException("Material no disponible: " + material.getTitulo());
        }
        
        Prestamo prestamo = new Prestamo(usuario, material, LocalDate.now().plusDays(díasMaximos));
        prestamo.setPoliticaMulta(politicaMulta);
        prestamos.add(prestamo);
        
        System.out.println("✓ Préstamo creado: " + usuario.getNombre() + " → " + material.getTitulo());
        return prestamo;
    }

    public void devolverMaterial(Prestamo prestamo) {
        if (!prestamo.isActivo()) {
            throw new IllegalStateException("Préstamo ya fue devuelto");
        }
        
        double multa = prestamo.calcularMulta();
        prestamo.devolverMaterial();
        
        System.out.println("✓ Material devuelto: " + prestamo.getMaterial().getTitulo());
        if (multa > 0) {
            System.out.println("  ⚠ Multa generada: S/. " + multa);
        }
    }

    public List<Prestamo> listarPrestamosActivos() {
        return prestamos.stream()
                .filter(Prestamo::isActivo)
                .toList();
    }

    public List<Prestamo> listarPrestamosVencidos() {
        return prestamos.stream()
                .filter(Prestamo::estáVencido)
                .toList();
    }

    // ========== REPORTES ==========
    
    public void generarReportePrestamos() {
        System.out.println("\n=== REPORTE DE PRÉSTAMOS ===");
        System.out.println("Total de préstamos activos: " + listarPrestamosActivos().size());
        System.out.println("Préstamos vencidos: " + listarPrestamosVencidos().size());
        System.out.println("Materiales disponibles: " + listarMaterialesDisponibles().size());
    }

    public void setPoliticaMulta(PoliticaMulta nuevaPolitica) {
        this.politicaMulta = nuevaPolitica;
        System.out.println("✓ Política de multa actualizada");
    }
    
    // En la clase, añade esto:



    public void agregarObservadorPrestamo(Prestamo prestamo, ObservadorPrestamo observador) {
        prestamo.agregarObservador(observador);
    }

    public void verificarPrestamosVencidos() {
        System.out.println("\n--- Verificando préstamos vencidos ---");
        for (Prestamo prestamo : prestamos) {
            prestamo.verificarVencimiento();
        }
    }
      public void importarMateriales(ImportadorDatos importador, String ruta) {
        List<MaterialBibliotecario> importados = importador.importar(ruta);
        for (MaterialBibliotecario material : importados) {
            materiales.add(material);
        }
    }
        public String generarReporte(GeneradorReporte generador) {
        return generador.generar(prestamos);
    }


}
