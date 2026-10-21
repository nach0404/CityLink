
package modelo;

/**
 * Representa una moto utilizada para realizar entregas en CityLink.
 * Tiene una capacidad máxima de 30 kg, una velocidad de 40 km/h
 * y un costo de funcionamiento de $120 por kilómetro.
 */
public class Moto extends Vehiculo {

    /**
     * Construye una moto con los valores establecidos para este vehículo.
     *
     * @param patente patente identificatoria de la moto
     */
    public Moto(String patente) {
        super(patente, 30, 40, 120);
    }

    /**
     * Calcula el tiempo estimado de entrega en moto.
     * Al tiempo de recorrido se le agregan 5 minutos por estacionamiento.
     *
     * @param km distancia del recorrido en kilómetros
     * @return tiempo estimado de entrega en minutos, incluidos los
     * minutos adicionales por estacionamiento
     * @throws IllegalArgumentException si la distancia no es válida
     */
    @Override
    public double calcularTiempoEntrega(double km) {
        return calcularTiempoConExtra(km, 5);
    }
}
