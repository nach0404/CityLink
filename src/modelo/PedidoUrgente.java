package modelo;

import excepciones.PedidoInvalidoException;

/**
 * Clase PedidoUrgente que extiende de la clase abstracta Pedido
 * redefine calcularCosto() aplicando un recargo del 50% sobre el total
 */
public class PedidoUrgente extends Pedido {

    private static final double RECARGO = 0.50;

    /**
     * Crea un pedido urgente vacio, asignandole fecha y estado automaticamente(fecha actual y PENDIENTE)
     */
    public PedidoUrgente() {
        super();
    }
    
    /**
     * Crea un pedido urgente con los datos indicados, fecha y estado se asignan automaticamente(fecha actual y PENDIENTE)
     * @param codigo codigo de pedido
     * @param cliente cliente que realizo el pedido
     * @param destino lugar de destino del pedido
     * @param pesoKg peso del pedido en kilogramos
     * @param distanciaKm distancia en kilometros hasta el destino
     * @throws PedidoInvalidoException si los datos indicados no son validos
     */
    public PedidoUrgente(int codigo, Cliente cliente, String destino, double pesoKg, double distanciaKm) throws PedidoInvalidoException {
        super(codigo, cliente, destino, pesoKg, distanciaKm);
    }

    /**
     * Calcula el costo de envio sumandole un recargo del 50% sobre el total, segun el tipo de vehiculo
     * @param v tipo de vehiculo
     * @return costo de envio + recargo
     */
    @Override
    public double calcularCosto(Vehiculo v) {
        double subtotal = COSTO_BASE + getDistanciaKm() * v.getCostoPorKm();
        return subtotal * (1 + RECARGO);
    }
}
