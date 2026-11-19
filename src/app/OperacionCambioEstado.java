package app;

import modelo.EstadoPedido;
import modelo.Pedido;

/**
* Operacion de cambio de estado de un pedido. Guarda el estado anterior para poder restaurarlo al deshacer.
*/
public class OperacionCambioEstado implements Operacion {
    private Pedido pedido;
    private EstadoPedido anterior;

    /**
     * Crea la operacion de cambio de estado de un pedido
     * @param pedido pedido a deshacer el estado
     * @param anterior estado anterior del pedido
     */
    public OperacionCambioEstado(Pedido pedido, EstadoPedido anterior) {
        this.pedido = pedido;
        this.anterior = anterior;
    }

    /**
     * Deshace el estado de un pedido al estado anterior
     * @return texto con el codigo del pedido y estado anterior
     */
     @Override
public String deshacer(CityLink gestor) {

    if (pedido.getEstado() == EstadoPedido.ENTREGADO
            && anterior == EstadoPedido.EN_CAMINO) {

        gestor.quitarEntregaRepartidor(pedido);
    }

    pedido.restaurarEstado(anterior);

    return "Se deshizo el cambio de estado del pedido "
            + pedido.getCodigo()
            + ", vuelve a "
            + anterior;
}
        
}