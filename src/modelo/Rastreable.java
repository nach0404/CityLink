
package modelo;

/**
 * Interfaz que permite consultar el estado actual
 * de un objeto del sistema CityLink.
 *
 * Es implementada por Pedido y Repartidor.
 */
public interface Rastreable {

    /**
     * Obtiene el estado actual del objeto.
     *
     * @return el estado actual como texto.
     */
    String obtenerEstadoActual();
}
