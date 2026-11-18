package excepciones;

/**
 * Se lanza cuando se intenta deshacer y no hay operaciones en la pila
 */
public class NadaParaDeshacerException extends Exception {
    public NadaParaDeshacerException(String mensaje) {
        super(mensaje);
    }
}
