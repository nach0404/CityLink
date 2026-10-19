
package modelo;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.PedidoInvalidoException;

/**
 * Representa a un repartidor de CityLink.
 * Administra su vehículo y los pedidos asignados.
 */
public class Repartidor implements Rastreable, Facturable {

    private int legajo;
    private String nombre;
    private Vehiculo vehiculo;
    private Pedido[] pedidos;
    private int cantidadPedidos;

    private static final int MAX_PEDIDOS = 100;
    private static final double PORCENTAJE_COMISION = 0.10;

    /**
     * Constructor completo.
     */
    public Repartidor(int legajo, String nombre, Vehiculo vehiculo) {
        setLegajo(legajo);
        setNombre(nombre);
        setVehiculo(vehiculo);

        pedidos = new Pedido[MAX_PEDIDOS];
        cantidadPedidos = 0;
    }

    // Getters

    public int getLegajo() {
        return legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public int getCantidadPedidos() {
        return cantidadPedidos;
    }

    /**
     * Devuelve una copia del arreglo de pedidos asignados.
     */
    public Pedido[] getPedidos() {
        Pedido[] copia = new Pedido[cantidadPedidos];

        for (int i = 0; i < cantidadPedidos; i++) {
            copia[i] = pedidos[i];
        }

        return copia;
    }

    // Setters con validación

    public void setLegajo(int legajo) {
        if (legajo <= 0) {
            throw new IllegalArgumentException(
                "El legajo debe ser mayor que cero."
            );
        }

        this.legajo = legajo;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                "El nombre no puede estar vacío."
            );
        }

        this.nombre = nombre.trim();
    }

    public void setVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException(
                "El repartidor debe tener un vehículo asignado."
            );
        }

        this.vehiculo = vehiculo;
    }

    /**
     * Sobrecarga: asigna un pedido usando el vehículo del repartidor.
     */
    public void asignarPedido(Pedido pedido)
            throws CapacidadExcedidaException,
                   AlmacenamientoLlenoException,
                   PedidoInvalidoException {

        asignarPedido(pedido, this.vehiculo);
    }

    /**
     * Sobrecarga: asigna un pedido utilizando el vehículo indicado.
     */
    public void asignarPedido(Pedido pedido, Vehiculo vehiculoAsignado)
            throws CapacidadExcedidaException,
                   AlmacenamientoLlenoException,
                   PedidoInvalidoException {

        if (pedido == null) {
            throw new IllegalArgumentException(
                "El pedido no puede ser nulo."
            );
        }

        if (vehiculoAsignado == null) {
            throw new IllegalArgumentException(
                "El vehículo no puede ser nulo."
            );
        }

        if (cantidadPedidos >= MAX_PEDIDOS) {
            throw new AlmacenamientoLlenoException(
                "El repartidor alcanzó el máximo de pedidos."
            );
        }

        // No permitir asignar dos veces el mismo pedido.
        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getCodigo() == pedido.getCodigo()) {
                throw new IllegalArgumentException(
                    "El pedido ya está asignado a este repartidor."
                );
            }
        }

        // Verificar capacidad y restricciones del vehículo.
        if (!vehiculoAsignado.puedeTransportar(pedido)) {
            throw new CapacidadExcedidaException(
                "El vehículo no puede transportar este pedido."
            );
        }

        // Guardar el vehículo utilizado para calcular el costo.
        pedido.setVehiculo(vehiculoAsignado);

        pedidos[cantidadPedidos] = pedido;
        cantidadPedidos++;
    }

    /**
     * Registra la entrega de un pedido asignado.
     * El pedido debe estar en camino.
     */
    public void registrarEntrega(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException(
                "El pedido no puede ser nulo."
            );
        }

        boolean asignado = false;

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getCodigo() == pedido.getCodigo()) {
                asignado = true;
                break;
            }
        }

        if (!asignado) {
            throw new IllegalArgumentException(
                "El pedido no está asignado a este repartidor."
            );
        }

        pedido.cambiarEstado(EstadoPedido.ENTREGADO);
    }

    /**
     * Devuelve el estado general del repartidor.
     */
    @Override
    public String obtenerEstadoActual() {
        int pendientes = 0;
        int enCamino = 0;
        int entregados = 0;

        for (int i = 0; i < cantidadPedidos; i++) {
            EstadoPedido estado = pedidos[i].getEstado();

            if (estado == EstadoPedido.PENDIENTE) {
                pendientes++;
            } else if (estado == EstadoPedido.EN_CAMINO) {
                enCamino++;
            } else if (estado == EstadoPedido.ENTREGADO) {
                entregados++;
            }
        }

        return "Repartidor " + nombre
                + " | Pendientes: " + pendientes
                + " | En camino: " + enCamino
                + " | Entregados: " + entregados;
    }

    /**
     * Calcula el 10 % del importe de los pedidos entregados.
     */
    @Override
    public double calcularImporte() {
        double totalEntregado = 0;

        for (int i = 0; i < cantidadPedidos; i++) {
            if (pedidos[i].getEstado() == EstadoPedido.ENTREGADO) {
                totalEntregado += pedidos[i].calcularImporte();
            }
        }

        return totalEntregado * PORCENTAJE_COMISION;
    }

    @Override
    public String toString() {
        return "Legajo: " + legajo
                + ", nombre: " + nombre
                + ", vehículo: " + vehiculo.getPatente()
                + ", pedidos asignados: " + cantidadPedidos;
    }
}
