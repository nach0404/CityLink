
package algoritmos;

import java.util.ArrayList;
import java.util.List;
import modelo.Pedido;

/**
 * Contiene métodos para intercalar dos listas de pedidos ordenadas.
 */
public class Intercalacion {

    /**
     * Intercala dos listas de pedidos ordenadas por código,
     * generando una nueva lista también ordenada.
     *
     * Utiliza dos índices, uno para cada lista, y compara
     * los códigos de los pedidos para agregar primero el menor.
     *
     * @param turnoManana lista de pedidos del turno mañana,
     *                    ordenada por código ascendente
     * @param turnoTarde lista de pedidos del turno tarde,
     *                   ordenada por código ascendente
     * @return nueva lista con los pedidos de ambos turnos intercalados
     * @throws IllegalArgumentException si alguna lista es nula
     */
    public static List<Pedido> intercalar(
            List<Pedido> turnoManana,
            List<Pedido> turnoTarde) {

        if (turnoManana == null || turnoTarde == null) {
            throw new IllegalArgumentException(
                "Las listas de los turnos no pueden ser nulas."
            );
        }

        List<Pedido> resultado = new ArrayList<>();

        int i = 0;
        int j = 0;

        // Comparar los pedidos mientras ambas listas tengan elementos.
        while (i < turnoManana.size() && j < turnoTarde.size()) {

            Pedido pedidoManana = turnoManana.get(i);
            Pedido pedidoTarde = turnoTarde.get(j);

            if (pedidoManana.getCodigo() <= pedidoTarde.getCodigo()) {
                resultado.add(pedidoManana);
                i++;
            } else {
                resultado.add(pedidoTarde);
                j++;
            }
        }

        // Agregar los elementos restantes del turno mañana.
        while (i < turnoManana.size()) {
            resultado.add(turnoManana.get(i));
            i++;
        }

        // Agregar los elementos restantes del turno tarde.
        while (j < turnoTarde.size()) {
            resultado.add(turnoTarde.get(j));
            j++;
        }

        return resultado;
    }
}
