
package modelo;

/**
 * Representa una camioneta utilizada para realizar entregas en CityLink.
 * Tiene una capacidad máxima de 500 kg, una velocidad de 30 km/h
 * y un costo de funcionamiento de $300 por kilómetro.
 */
public class Camioneta extends Vehiculo {

    /**
     * Construye una camioneta con los valores establecidos para este vehículo.
     *
     * @param patente patente identificatoria de la camioneta
     */
    public Camioneta(String patente) {
        super(patente, 500, 30, 300);
    }

    /**
     * Calcula el tiempo estimado de entrega en camioneta.
     * Al tiempo de recorrido se le agregan 10 minutos por carga y descarga.
     *
     * @param km distancia del recorrido en kilómetros
     * @return tiempo estimado de entrega en minutos, incluidos los
     * minutos adicionales de carga y descarga
     * @throws IllegalArgumentException si la distancia no es válida
     */
    @Override
    public double calcularTiempoEntrega(double km) {
        return calcularTiempoConExtra(km, 10);
    }
}
