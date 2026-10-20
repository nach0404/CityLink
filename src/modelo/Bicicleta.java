
package modelo;

/**
 * Representa una bicicleta utilizada para realizar entregas en CityLink.
 * Tiene una capacidad máxima de 10 kg, una velocidad de 15 km/h
 * y un costo de funcionamiento de $50 por kilómetro.
 * No permite transportar pedidos frágiles.
 */
public class Bicicleta extends Vehiculo {

    /**
     * Construye una bicicleta con los valores establecidos para este vehículo.
     *
     * @param patente patente identificatoria de la bicicleta
     */
    public Bicicleta(String patente) {
        super(patente, 10, 15, 50);
    }

    /**
     * Calcula el tiempo estimado de entrega en bicicleta, sin agregar
     * minutos adicionales al tiempo de recorrido.
     *
     * @param km distancia del recorrido en kilómetros
     * @return tiempo estimado de entrega en minutos
     * @throws IllegalArgumentException si la distancia no es válida
     */
    @Override
    public double calcularTiempoEntrega(double km) {
        return calcularTiempoConExtra(km, 0);
    }

    /**
     * Comprueba si la bicicleta puede transportar un pedido.
     * No permite pedidos frágiles y respeta la capacidad máxima de 10 kg.
     *
     * @param pedido pedido que se desea transportar
     * @return true si el pedido no es nulo, no es frágil y no supera
     * la capacidad del vehículo; false en caso contrario
     */
    @Override
    public boolean puedeTransportar(Pedido pedido) {
        if (pedido == null) {
            return false;
        }

        // Una bicicleta no puede llevar pedidos frágiles.
        if (pedido instanceof PedidoFragil) {
            return false;
        }

        return super.puedeTransportar(pedido);
    }
}
