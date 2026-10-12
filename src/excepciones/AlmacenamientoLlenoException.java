package excepciones;

/**
 * Se lanza cuando se intenta agregar un elemento a un arreglo que ya esta lleno
 */

public class AlmacenamientoLlenoException extends Exception {
    public AlmacenamientoLlenoException(String mensaje) {
        super(mensaje);
    }
}