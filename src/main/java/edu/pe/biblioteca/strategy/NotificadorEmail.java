package edu.pe.biblioteca.strategy;

import edu.pe.biblioteca.domain.Prestamo;

public class NotificadorEmail implements ObservadorPrestamo {
    
    @Override
    public void prestamoVencido(Prestamo prestamo) {
        String mensaje = "ALERTA: Tu préstamo de '" + prestamo.getMaterial().getTitulo() 
                + "' está vencido desde hace " + prestamo.díasAtrasado() + " días.";
        enviarEmail(prestamo.getUsuario().getCorreo(), mensaje);
    }
    
    @Override
    public void materialDevuelto(Prestamo prestamo) {
        double multa = prestamo.calcularMulta();
        String mensaje = "Gracias por devolver '" + prestamo.getMaterial().getTitulo() + "'";
        if (multa > 0) {
            mensaje += "\nMulta generada: S/. " + multa;
        }
        enviarEmail(prestamo.getUsuario().getCorreo(), mensaje);
    }
    
    private void enviarEmail(String correo, String mensaje) {
        System.out.println("📧 [EMAIL a " + correo + "] " + mensaje);
    }
}
