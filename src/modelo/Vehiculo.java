
package modelo;

/**
 * Clase abstracta que representa un vehículo utilizado para realizar
 * entregas en el sistema CityLink.
 * Contiene los datos comunes de los vehículos y define los métodos
 * para calcular el tiempo de entrega y verificar la capacidad de carga.
 *
 * Las subclases deben implementar el cálculo del tiempo de entrega.
 */
public abstract class Vehiculo {

    private String patente;
    private double capacidadKg;
    private double velocidadKmH;
    private double costoPorKm;

    /**
     * Construye un vehículo con su patente, capacidad, velocidad y costo
     * por kilómetro.
     *
     * @param patente patente identificatoria del vehículo
     * @param capacidadKg capacidad máxima de carga en kilogramos
     * @param velocidadKmH velocidad promedio en kilómetros por hora
     * @param costoPorKm costo de funcionamiento por kilómetro
     * @throws IllegalArgumentException si la patente está vacía, la capacidad
     * o la velocidad no son válidas, o el costo es negativo o no finito
     */
    public Vehiculo(String patente, double capacidadKg,
                    double velocidadKmH, double costoPorKm) {

        setPatente(patente);
        setCapacidadKg(capacidadKg);
        setVelocidadKmH(velocidadKmH);
        setCostoPorKm(costoPorKm);
    }

    // Getters

    /**
     * Obtiene la patente del vehículo.
     *
     * @return patente identificatoria del vehículo
     */
    public String getPatente() {
        return patente;
    }

    /**
     * Obtiene la capacidad máxima de carga del vehículo.
     *
     * @return capacidad en kilogramos
     */
    public double getCapacidadKg() {
        return capacidadKg;
    }

    /**
     * Obtiene la velocidad promedio del vehículo.
     *
     * @return velocidad en kilómetros por hora
     */
    public double getVelocidadKmH() {
        return velocidadKmH;
    }

    /**
     * Obtiene el costo de funcionamiento por kilómetro.
     *
     * @return costo por kilómetro
     */
    public double getCostoPorKm() {
        return costoPorKm;
    }

    // Setters con validación

    /**
     * Establece la patente del vehículo.
     *
     * @param patente nueva patente del vehículo
     * @throws IllegalArgumentException si la patente es nula o está vacía
     */
    public void setPatente(String patente) {
        if (patente == null || patente.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "La patente no puede estar vacía."
            );
        }
        this.patente = patente.trim();
    }
    
    /**
     * Establece la capacidad máxima de carga del vehículo.
     *
     * @param capacidadKg capacidad en kilogramos
     * @throws IllegalArgumentException si la capacidad no es finita
     * o es menor o igual a cero
     */
    public void setCapacidadKg(double capacidadKg) {
        if (!Double.isFinite(capacidadKg) || capacidadKg <= 0) {
            throw new IllegalArgumentException(
                "La capacidad debe ser mayor que cero."
            );
        }
        this.capacidadKg = capacidadKg;
    }

    /**
     * Establece la capacidad máxima de carga del vehículo.
     *
     * @param capacidadKg capacidad en kilogramos
     * @throws IllegalArgumentException si la capacidad no es finita
     * o es menor o igual a cero
     */
    public void setVelocidadKmH(double velocidadKmH) {
        if (!Double.isFinite(velocidadKmH) || velocidadKmH <= 0) {
            throw new IllegalArgumentException(
                "La velocidad debe ser mayor que cero."
            );
        }
        this.velocidadKmH = velocidadKmH;
    }

    /**
     * Establece el costo de funcionamiento por kilómetro.
     *
     * @param costoPorKm costo por kilómetro
     * @throws IllegalArgumentException si el costo no es finito o es negativo
     */
    public void setCostoPorKm(double costoPorKm) {
        if (!Double.isFinite(costoPorKm) || costoPorKm < 0) {
            throw new IllegalArgumentException(
                "El costo por km no puede ser negativo."
            );
        }
        this.costoPorKm = costoPorKm;
    }

    /**
     * Calcula el tiempo estimado necesario para recorrer una distancia.
     * Cada subclase implementa el cálculo según las características
     * particulares del vehículo.
     *
     * @param km distancia que se debe recorrer, en kilómetros
     * @return tiempo estimado de entrega, en minutos
     * @throws IllegalArgumentException si la distancia no es válida
     */
    public abstract double calcularTiempoEntrega(double km);


    /**
     * Comprueba si el vehículo puede transportar un pedido según su peso.
     *
     * @param pedido pedido que se desea transportar
     * @return true si el pedido no es nulo y su peso no supera la capacidad;
     * false en caso contrario
     */
    public boolean puedeTransportar(Pedido pedido) {
        return pedido != null
                && pedido.getPesoKg() <= capacidadKg;
    }


    /**
     * Calcula el tiempo de recorrido y suma los minutos adicionales
     * correspondientes al tipo de vehículo.
     *
     * @param km distancia que se debe recorrer, en kilómetros
     * @param minutosExtra minutos adicionales al tiempo de recorrido
     * @return tiempo total estimado, en minutos
     * @throws IllegalArgumentException si la distancia no es finita
     * o es menor o igual a cero
     */
    protected double calcularTiempoConExtra(double km,
                                            double minutosExtra) {
        if (!Double.isFinite(km) || km <= 0) {
            throw new IllegalArgumentException(
                "La distancia debe ser mayor que cero."
            );
        }

        return (km / velocidadKmH) * 60 + minutosExtra;
    }

    /**
     * Devuelve una representación textual de los datos del vehículo.
     *
     * @return texto con la patente, capacidad, velocidad y costo por kilómetro
     */
    @Override
    public String toString() {
        return "Patente: " + patente
                + ", capacidad: " + capacidadKg + " kg"
                + ", velocidad: " + velocidadKmH + " km/h"
                + ", costo por km: $" + costoPorKm;
    }
}
