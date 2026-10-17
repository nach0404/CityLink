
package modelo;

public abstract class Vehiculo {

    private String patente;
    private double capacidadKg;
    private double velocidadKmH;
    private double costoPorKm;

    public Vehiculo(String patente, double capacidadKg,
                    double velocidadKmH, double costoPorKm) {

        setPatente(patente);
        setCapacidadKg(capacidadKg);
        setVelocidadKmH(velocidadKmH);
        setCostoPorKm(costoPorKm);
    }

    // Getters
    public String getPatente() {
        return patente;
    }

    public double getCapacidadKg() {
        return capacidadKg;
    }

    public double getVelocidadKmH() {
        return velocidadKmH;
    }

    public double getCostoPorKm() {
        return costoPorKm;
    }

    // Setters con validación
    public void setPatente(String patente) {
        if (patente == null || patente.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "La patente no puede estar vacía."
            );
        }
        this.patente = patente.trim();
    }

    public void setCapacidadKg(double capacidadKg) {
        if (!Double.isFinite(capacidadKg) || capacidadKg <= 0) {
            throw new IllegalArgumentException(
                "La capacidad debe ser mayor que cero."
            );
        }
        this.capacidadKg = capacidadKg;
    }

    public void setVelocidadKmH(double velocidadKmH) {
        if (!Double.isFinite(velocidadKmH) || velocidadKmH <= 0) {
            throw new IllegalArgumentException(
                "La velocidad debe ser mayor que cero."
            );
        }
        this.velocidadKmH = velocidadKmH;
    }

    public void setCostoPorKm(double costoPorKm) {
        if (!Double.isFinite(costoPorKm) || costoPorKm < 0) {
            throw new IllegalArgumentException(
                "El costo por km no puede ser negativo."
            );
        }
        this.costoPorKm = costoPorKm;
    }

    // Cada subclase implementará su cálculo de tiempo.
    public abstract double calcularTiempoEntrega(double km);

    // Comprueba si el peso del pedido está dentro de la capacidad.
    public boolean puedeTransportar(Pedido pedido) {
        return pedido != null
                && pedido.getPesoKg() <= capacidadKg;
    }

    // Método auxiliar para calcular el tiempo con minutos extra.
    protected double calcularTiempoConExtra(double km,
                                            double minutosExtra) {
        if (!Double.isFinite(km) || km <= 0) {
            throw new IllegalArgumentException(
                "La distancia debe ser mayor que cero."
            );
        }

        return (km / velocidadKmH) * 60 + minutosExtra;
    }

    @Override
    public String toString() {
        return "Patente: " + patente
                + ", capacidad: " + capacidadKg + " kg"
                + ", velocidad: " + velocidadKmH + " km/h"
                + ", costo por km: $" + costoPorKm;
    }
}
