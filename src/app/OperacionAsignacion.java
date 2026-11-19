package app;

import modelo.Pedido;
import modelo.Repartidor;

/**
 * Operacion de asignacion de un pedido a un repartidor. Guarda ambos para poder
 * revertirla: el pedido deja al repartidor, pierde el vehiculo y vuelve a la
 * cola de pendientes.
 */
public class OperacionAsignacion implements Operacion {

    private Pedido pedido;
    private Repartidor repartidor;

    /**
     * Crea la operacion de asignacion
     * @param pedido pedido que se asigno
     * @param repartidor repartidor al que se asigno
     */
    public OperacionAsignacion(Pedido pedido, Repartidor repartidor) {
        this.pedido = pedido;
        this.repartidor = repartidor;
    }

    /**
     * Quita el pedido del repartidor, le saca el vehiculo y lo devuelve a la cola de pendientes
     * @return texto que describe lo que se deshizo
     */
    @Override
    public String deshacer(CityLink gestor) {
        repartidor.quitarPedido(pedido);
        pedido.quitarVehiculo();
        gestor.encolarPendiente(pedido);

        return "Se deshizo la asignacion del pedido " + pedido.getCodigo()
                + " al repartidor " + repartidor.getNombre() + ", vuelve a pendientes";
    }
}