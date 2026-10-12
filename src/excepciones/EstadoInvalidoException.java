package excepciones;

/**
 * Se lanza cuando se intenta una transicion de estado no permitida
 */

public class EstadoInvalidoException extends RuntimeException {
    public EstadoInvalidoException(String mensaje) {
        super(mensaje);
    }
}