package edu.pe.biblioteca.strategy;

import edu.pe.biblioteca.domain.Prestamo;

public class MultaPorTramo implements PoliticaMulta {
    /**
     * Tramos progresivos:
     * 1-3 días: S/. 5
     * 4-7 días: S/. 10
     * 8+ días: S/. 20
     */
    private static final double MULTA_TRAMO1 = 5.0;
    private static final double MULTA_TRAMO2 = 10.0;
    private static final double MULTA_TRAMO3 = 20.0;

    @Override
    public double calcularMulta(Prestamo prestamo) {
        if (!prestamo.estáVencido()) {
            return 0.0;
        }
        
        long diasAtrasado = prestamo.díasAtrasado();
        
        if (diasAtrasado <= 3) {
            return MULTA_TRAMO1;
        } else if (diasAtrasado <= 7) {
            return MULTA_TRAMO2;
        } else {
            return MULTA_TRAMO3;
        }
    }
}

