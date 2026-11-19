package algoritmos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import modelo.Pedido;

/**
 * Algoritmos de ordenamiento escritos a mano (sin Collections.sort).
 * Ninguno modifica la lista original: devuelven una lista nueva ordenada.
 * El criterio de orden se elige con un Comparator (ver Criterios).
 */
public class Ordenamiento {

    /**
     * Ordenamiento por insercion. Va armando una parte ordenada a la izquierda:
     * toma cada pedido y lo inserta en su lugar corriendo los mayores un lugar.
     * Complejidad: O(n^2) en el peor caso, O(n) si ya esta casi ordenada.
     *
     * @param lista pedidos a ordenar
     * @param criterio criterio de comparacion
     * @return lista nueva con los pedidos ordenados
     */
    public static List<Pedido> insercion(List<Pedido> lista, Comparator<Pedido> criterio) {
        List<Pedido> resultado = new ArrayList<>(lista);

        for (int i = 1; i < resultado.size(); i++) {
            Pedido clave = resultado.get(i);
            int j = i - 1;

            while (j >= 0 && criterio.compare(resultado.get(j), clave) > 0) {
                resultado.set(j + 1, resultado.get(j));
                j--;
            }

            resultado.set(j + 1, clave);
        }

        return resultado;
    }

    /**
     * Ordenamiento por mezcla (mergesort). Divide la lista a la mitad,
     * ordena cada mitad de forma recursiva y despues mezcla las dos mitades.
     * Complejidad: O(n log n) siempre.
     *
     * @param lista pedidos a ordenar
     * @param criterio criterio de comparacion
     * @return lista nueva con los pedidos ordenados
     */
    public static List<Pedido> mergeSort(List<Pedido> lista, Comparator<Pedido> criterio) {
        if (lista.size() <= 1) {
            return new ArrayList<>(lista);
        }

        int mitad = lista.size() / 2;
        List<Pedido> izquierda = mergeSort(lista.subList(0, mitad), criterio);
        List<Pedido> derecha = mergeSort(lista.subList(mitad, lista.size()), criterio);

        return mezclar(izquierda, derecha, criterio);
    }

    /**
     * Mezcla dos listas ya ordenadas en una sola, con un unico recorrido.
     * Si hay empate, gana la de la izquierda (el orden se mantiene estable).
     */
    private static List<Pedido> mezclar(List<Pedido> izq, List<Pedido> der, Comparator<Pedido> criterio) {
        List<Pedido> resultado = new ArrayList<>();
        int i = 0;
        int d = 0;

        while (i < izq.size() && d < der.size()) {
            if (criterio.compare(izq.get(i), der.get(d)) <= 0) {
                resultado.add(izq.get(i));
                i++;
            } else {
                resultado.add(der.get(d));
                d++;
            }
        }

        while (i < izq.size()) {
            resultado.add(izq.get(i));
            i++;
        }

        while (d < der.size()) {
            resultado.add(der.get(d));
            d++;
        }

        return resultado;
    }
}