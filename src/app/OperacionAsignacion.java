package app;

import modelo.Pedido;
import modelo.Repartidor;

/**
 * Representa una operación de asignación de un pedido a un repartidor.
 * Guarda ambos objetos para poder deshacer la asignación.
 */
public class OperacionAsignacion implements Operacion {

    private Pedido pedido;
    private Repartidor repartidor;

    /**
     * Construye una operación de asignación.
     *
     * @param pedido pedido que fue asignado
     * @param repartidor repartidor al que se asignó el pedido
     */
    public OperacionAsignacion(Pedido pedido, Repartidor repartidor) {
        this.pedido = pedido;
        this.repartidor = repartidor;
    }

    /**
     * Deshace la asignación, quita el pedido del repartidor,
     * elimina su vehículo y lo devuelve a la cola de pendientes.
     *
     * @param gestor sistema CityLink que administra las colas
     * @return mensaje que describe la operación deshecha
     */
    @Override
    public String deshacer(CityLink gestor) {
        repartidor.quitarPedido(pedido);
        pedido.quitarVehiculo();
        gestor.encolarPendiente(pedido);

        return "Se deshizo la asignación del pedido "
                + pedido.getCodigo()
                + " al repartidor " + repartidor.getNombre()
                + ". El pedido vuelve a pendientes.";
    }
}