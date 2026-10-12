package excepciones;
/**
 * Se lanza cuando se busca un cliente que no existe
 */
public class ClienteNoEncontrado extends Exception {

    public ClienteNoEncontrado(String mensaje) {
        super(mensaje);
    }
}