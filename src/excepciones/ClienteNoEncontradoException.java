package excepciones;
/**
 * Se lanza cuando se busca un cliente que no existe
 */
public class ClienteNoEncontradoException extends Exception {

    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}