package app;

import modelo.Pedido;

/**
* Operación de alta de un pedido. guarda el pedido para poder eliminarlo al deshacer
*/
public class OperacionAlta implements Operacion {

    private Pedido pedido;
    
    /**
    * Crea la operación de alta guardando el pedido registrado
    * @param pedido pedido registrado
    */
    public OperacionAlta(Pedido pedido) {
        this.pedido = pedido;
    }

    /**
    * Elimina del sistema el pedido dado de alta
    * @return texto del pedido eliminado
    */
    @Override
    public String deshacer(CityLink gestor) {
        gestor.quitarPedido(pedido);
        return "Se deshizo el alta del pedido " + pedido.getCodigo();
    }
}
