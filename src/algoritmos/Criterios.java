package algoritmos;

import java.util.Comparator;
import modelo.Pedido;

/**
 * Criterios para comparar pedidos. Cada criterio es un Comparator que se
 * le pasa a los algoritmos de ordenamiento para elegir por que campo ordenar.
 */
public class Criterios {

    /** Ordena de menor a mayor distancia en kilometros. */
    public static final Comparator<Pedido> POR_DISTANCIA =
            (a, b) -> Double.compare(a.getDistanciaKm(), b.getDistanciaKm());

    /** Ordena del pedido mas antiguo al mas reciente. */
    public static final Comparator<Pedido> POR_FECHA =
            (a, b) -> a.getFechaHora().compareTo(b.getFechaHora());

    /**
     * Ordena de menor a mayor costo. El costo depende del vehiculo, asi que
     * los pedidos que todavia no tienen vehiculo asignado van al final.
     */
    public static final Comparator<Pedido> POR_COSTO = (a, b) -> {
        if (a.getVehiculo() == null && b.getVehiculo() == null) return 0;
        if (a.getVehiculo() == null) return 1;
        if (b.getVehiculo() == null) return -1;
        return Double.compare(a.calcularImporte(), b.calcularImporte());
    };
}