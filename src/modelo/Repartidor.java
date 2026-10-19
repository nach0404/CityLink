
package modelo;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.PedidoInvalidoException;

/**
 * Representa a un repartidor de CityLink.
 * Administra su vehículo y los pedidos que tiene asignados.
 * También permite consultar el estado de sus pedidos y calcular
 * la comisión correspondiente a las entregas realizadas.
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
     * Construye un repartidor con su legajo, nombre y vehículo.
     * Inicializa el arreglo donde se almacenarán sus pedidos.
     *
     * @param legajo número identificatorio del repartidor; debe ser mayor que cero
     * @param nombre nombre del repartidor; no puede estar vacío
     * @param vehiculo vehículo asignado al repartidor; no puede ser nulo
     * @throws IllegalArgumentException si el legajo, el nombre o el vehículo no son válidos
     */
    public Repartidor(int legajo, String nombre, Vehiculo vehiculo) {
        setLegajo(legajo);
        setNombre(nombre);
        setVehiculo(vehiculo);

        pedidos = new Pedido[MAX_PEDIDOS];
        cantidadPedidos = 0;
    }

    // Getters

    /**
     * Obtiene el legajo del repartidor.
     *
     * @return número identificatorio del repartidor
     */
    public int getLegajo() {
        return legajo;
    }

    /**
     * Obtiene el nombre del repartidor.
     *
     * @return nombre del repartidor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el vehículo asignado al repartidor.
     *
     * @return vehículo asignado
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * Obtiene la cantidad de pedidos asignados al repartidor.
     *
     * @return cantidad actual de pedidos asignados
     */
    public int getCantidadPedidos() {
        return cantidadPedidos;
    }

    /**
     * Devuelve una copia del arreglo de pedidos asignados.
     *
     * @return arreglo con los pedidos asignados hasta el momento
     */
    public Pedido[] getPedidos() {
        Pedido[] copia = new Pedido[cantidadPedidos];

        for (int i = 0; i < cantidadPedidos; i++) {
            copia[i] = pedidos[i];
        }

        return copia;
    }

    // Setters con validación

    /**
     * Establece el legajo del repartidor.
     *
     * @param legajo nuevo número identificatorio
     * @throws IllegalArgumentException si el legajo es menor o igual que cero
     */
    public void setLegajo(int legajo) {
        if (legajo <= 0) {
            throw new IllegalArgumentException(
                "El legajo debe ser mayor que cero."
            );
        }

        this.legajo = legajo;
    }

    /**
     * Establece el nombre del repartidor.
     *
     * @param nombre nuevo nombre del repartidor
     * @throws IllegalArgumentException si el nombre es nulo o está vacío
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                "El nombre no puede estar vacío."
            );
        }

        this.nombre = nombre.trim();
    }

    /**
     * Asigna un vehículo al repartidor.
     *
     * @param vehiculo vehículo que se desea asignar
     * @throws IllegalArgumentException si el vehículo es nulo
     */
    public void setVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException(
                "El repartidor debe tener un vehículo asignado."
            );
        }

        this.vehiculo = vehiculo;
    }

    /**
     * Asigna un pedido utilizando el vehículo actual del repartidor.
     *
     * @param pedido pedido que se desea asignar
     * @throws CapacidadExcedidaException si el vehículo no puede transportar el pedido
     * @throws AlmacenamientoLlenoException si se alcanzó el máximo de pedidos
     * @throws PedidoInvalidoException si el pedido no cumple alguna validación del sistema
     * @throws IllegalArgumentException si el pedido es nulo o ya está asignado
     */
    public void asignarPedido(Pedido pedido)
            throws CapacidadExcedidaException,
                   AlmacenamientoLlenoException,
                   PedidoInvalidoException {

        asignarPedido(pedido, this.vehiculo);
    }

    
    /**
     * Asigna un pedido utilizando el vehículo indicado.
     * Verifica que los datos sean válidos, que exista espacio disponible
     * y que el vehículo pueda transportar el pedido.
     *
     * @param pedido pedido que se desea asignar
     * @param vehiculoAsignado vehículo que se utilizará para transportar el pedido
     * @throws CapacidadExcedidaException si el vehículo no puede transportar el pedido
     * @throws AlmacenamientoLlenoException si se alcanzó el máximo de pedidos
     * @throws PedidoInvalidoException si el pedido no cumple alguna validación del sistema
     * @throws IllegalArgumentException si el pedido o el vehículo son nulos,
     * o si el pedido ya está asignado
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
     * Registra la entrega de un pedido previamente asignado.
     * El pedido debe encontrarse en un estado que permita marcarlo
     * como entregado.
     *
     * @param pedido pedido cuya entrega se desea registrar
     * @throws IllegalArgumentException si el pedido es nulo o no está asignado
     * @throws EstadoInvalidoException si el cambio al estado ENTREGADO no está permitido
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
     * Obtiene un resumen de los estados de los pedidos del repartidor.
     * Cuenta los pedidos pendientes, en camino y entregados.
     *
     * @return texto con el nombre del repartidor y la cantidad de pedidos
     * en cada estado
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
     * Calcula la comisión del repartidor como el 10 % del importe
     * total de los pedidos que se encuentran en estado ENTREGADO.
     *
     * @return importe de la comisión correspondiente a las entregas realizadas
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

    /**
     * Devuelve una representación textual del repartidor,
     * incluyendo su legajo, nombre, patente del vehículo y cantidad de pedidos.
     *
     * @return descripción del repartidor
     */
    @Override
    public String toString() {
        return "Legajo: " + legajo
                + ", nombre: " + nombre
                + ", vehículo: " + vehiculo.getPatente()
                + ", pedidos asignados: " + cantidadPedidos;
    }
}
