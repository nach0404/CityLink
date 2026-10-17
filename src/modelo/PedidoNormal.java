package modelo;

import excepciones.PedidoInvalidoException;

/**
 * Clase PedidoNormal que extiende de Pedido
 * Redefine calcularCosto() sin recargos.
 */
public class PedidoNormal extends Pedido {

    /**
     * Crea un pedido normal vacio, fecha y estado se asignan automaticamente(fecha actual y PENDIENTE)
     */
    public PedidoNormal() {
        super();
    }

    /**
     * Crea un pedido normal con los datos indicados, fecha y estado se asignan automaticamente(fecha actual y PENDIENTE)
     * @param codigo codigo del pedido
     * @param cliente cliente que realiza el pedido
     * @param destino lugar de destino del pedido
     * @param pesoKg peso del pedido en kilogramos
     * @param distanciaKm distancia hasta el destino en kilometros
     * @throws PedidoInvalidoException si algunos de los datos no es valido
     */
    public PedidoNormal(int codigo, Cliente cliente, String destino, double pesoKg, double distanciaKm) throws PedidoInvalidoException {
        super(codigo, cliente, destino, pesoKg, distanciaKm);
    }

    /**
     * Calcula el costo de envio sin recargos, segun el tipo de vehiculo
     * @param v tipo de vehiculo
     * @return costo de envio sin recargo
     */
    @Override
    public double calcularCosto(Vehiculo v) {
        return COSTO_BASE + (getDistanciaKm() * v.getCostoPorKm());
    }
}