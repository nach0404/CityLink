package modelo;
/**
 *  Valores de estado de un pedido fijos para evitar estados erroneos
 */
public enum EstadoPedido {
    PENDIENTE, EN_CAMINO, ENTREGADO, CANCELADO;
}
