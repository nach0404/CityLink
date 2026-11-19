
package app;

import modelo.Pedido;

/**
 * Representa la operación de alta de un pedido.
 * Guarda el pedido para poder eliminarlo al deshacer.
 */
public class OperacionAlta implements Operacion {

    private Pedido pedido;

    /**
     * Construye una operación de alta.
     *
     * @param pedido pedido registrado
     */
    public OperacionAlta(Pedido pedido) {
        this.pedido = pedido;
    }

    /**
     * Elimina del sistema el pedido dado de alta.
     *
     * @param gestor sistema CityLink que administra los pedidos
     * @return texto que describe el pedido eliminado
     */
    @Override
    public String deshacer(CityLink gestor) {
        gestor.quitarPedido(pedido);
        return "Se deshizo el alta del pedido " + pedido.getCodigo();
    }
}
