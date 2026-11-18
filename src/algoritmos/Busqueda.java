
package algoritmos;

import java.util.List;
import modelo.Pedido;
import modelo.Cliente;

/**
 * Contiene métodos de búsqueda para pedidos y clientes de CityLink.
 * Implementa búsqueda lineal y búsqueda binaria manualmente.
 */
public class Busqueda {

    /**
     * Busca un pedido por su código utilizando búsqueda lineal.
     *
     * Recorre la lista desde el principio hasta encontrar
     * un pedido con el código indicado.
     *
     * @param pedidos lista de pedidos donde buscar
     * @param codigo código del pedido buscado
     * @return el pedido encontrado o null si no existe
     * @throws IllegalArgumentException si la lista es nula
     */
    public static Pedido buscarPedidoLineal(List<Pedido> pedidos, int codigo) {

        if (pedidos == null) {
            throw new IllegalArgumentException("La lista de pedidos no puede ser nula.");
        }

        for (Pedido pedido : pedidos) {
            if (pedido.getCodigo() == codigo) {
                return pedido;
            }
        }

        return null;
    }

    /**
     * Busca un cliente por su nombre utilizando búsqueda lineal.
     *
     * La comparación no distingue entre mayúsculas y minúsculas.
     *
     * @param clientes lista de clientes donde buscar
     * @param nombre nombre del cliente buscado
     * @return el cliente encontrado o null si no existe
     * @throws IllegalArgumentException si la lista es nula
     */
    public static Cliente buscarClienteLineal(List<Cliente> clientes, String nombre) {

        if (clientes == null) {
            throw new IllegalArgumentException("La lista de clientes no puede ser nula.");
        }

        if (nombre == null) {
            return null;
        }

        for (Cliente cliente : clientes) {
            if (cliente.getNombre().equalsIgnoreCase(nombre)) {
                return cliente;
            }
        }

        return null;
    }

    /**
     * Busca un pedido por su código utilizando búsqueda binaria.
     *
     * La lista debe estar ordenada previamente por código
     * de menor a mayor.
     *
     * @param pedidos lista de pedidos ordenada por código
     * @param codigo código del pedido buscado
     * @return el pedido encontrado o null si no existe
     * @throws IllegalArgumentException si la lista es nula
     */
    public static Pedido buscarPedidoBinario(List<Pedido> pedidos, int codigo) {

        if (pedidos == null) {
            throw new IllegalArgumentException("La lista de pedidos no puede ser nula.");
        }

        int inicio = 0;
        int fin = pedidos.size() - 1;

        while (inicio <= fin) {

            int medio = inicio + (fin - inicio) / 2;

            int codigoMedio = pedidos.get(medio).getCodigo();

            if (codigoMedio == codigo) {
                return pedidos.get(medio);
            }

            if (codigo < codigoMedio) {
                fin = medio - 1;
            } else {
                inicio = medio + 1;
            }
        }

        return null;
    }
}
