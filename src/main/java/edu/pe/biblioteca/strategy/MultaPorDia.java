package edu.pe.biblioteca.strategy;

import edu.pe.biblioteca.domain.Prestamo;

public class MultaPorDia implements PoliticaMulta {
    private double costoXdia;

    public MultaPorDia(double costoXdia) {
        this.costoXdia = costoXdia;
    }

    @Override
    public double calcularMulta(Prestamo prestamo) {
        if (!prestamo.estáVencido()) {
            return 0.0;
        }
        long diasAtrasado = prestamo.díasAtrasado();
        return diasAtrasado * costoXdia;
    }
}
