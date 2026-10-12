package excepciones;
/**
 * Se lanza cuando el pedido no puede transportarse en un vehiculo
 */

public class CapacidadExcedidaException extends Exception {
    public CapacidadExcedidaException(String mensaje) {
        super(mensaje);
    }
}
