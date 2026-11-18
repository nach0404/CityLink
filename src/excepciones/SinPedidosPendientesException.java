package excepciones;

/**
 * Se lanza cuando no hay pedidos pendientes de atender
 */
public class SinPedidosPendientesException extends Exception {
    public SinPedidosPendientesException(String mensaje) {
        super(mensaje);
    }
}
