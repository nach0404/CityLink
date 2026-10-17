package modelo;

import excepciones.PedidoInvalidoException;

/**
 * Clase PedidoFragil que extiende de Pedido
 * Redefine calcularCosto() aplicando un recargo fijo de $800
 */
public class PedidoFragil extends Pedido {

    private static final double RECARGO_FIJO = 800.0;

    /**
     * Crea un pedido fragil vacio, asignandole fecha y estado automaticamente(fecha actual y PENDIENTE)
     */
    public PedidoFragil() {
        super();
    }

    /**
     * Crea un pedido fragil con los datos indicados, asignandole fecha y estado automaticamente(fecha actual y PENDIENTE) 
     * @param codigo codigo de pedido
     * @param cliente cliente que realizo el pedido
     * @param destino lugar de destino del pedido
     * @param pesoKg peso del pedido en kilogramos
     * @param distanciaKm distancia en kilometros hasta el destino
     * @throws PedidoInvalidoException si los datos indicados no son validos
     */
    public PedidoFragil(int codigo, Cliente cliente, String destino, double pesoKg, double distanciaKm) throws PedidoInvalidoException {
        super(codigo, cliente, destino, pesoKg, distanciaKm);
    }

    /**
     * Calcula el costo de envio agregandole un recargo fijo de $800, segun el tipo de vehiculo
     * @param v tipo de vehiculo
     * @return costo de envio + recargo fijo
     */
    @Override
    public double calcularCosto(Vehiculo v) {
        return (COSTO_BASE + getDistanciaKm() * v.getCostoPorKm()) + RECARGO_FIJO;
    }
}
