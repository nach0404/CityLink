
package modelo;

/**
 * Interfaz que define el cálculo de un importe
 * económico dentro del sistema CityLink.
 *
 * Es implementada por Pedido y Repartidor.
 */
public interface Facturable {

    /**
     * Calcula el importe correspondiente al objeto.
     *
     * @return el importe calculado.
     */
    double calcularImporte();
}
