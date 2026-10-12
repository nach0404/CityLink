package excepciones;

/**
 * Se lanza cuando los datos de un pedido no son validos
 */
public class PedidoInvalidoException extends Exception {
    public PedidoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
