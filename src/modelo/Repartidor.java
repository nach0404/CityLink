
package modelo;

import java.util.ArrayList;

import excepciones.AlmacenamientoLlenoException;
import excepciones.CapacidadExcedidaException;
import excepciones.PedidoInvalidoException;

/**
 * Representa a un repartidor de CityLink.
 * Administra su vehículo, los pedidos asignados y el historial
 * de entregas realizadas.
 */
public class Repartidor implements Rastreable, Facturable {

    private int legajo;
    private String nombre;
    private Vehiculo vehiculo;
    private ArrayList<Pedido> pedidos;
    private ArrayList<Pedido> historial;

    private static final int MAX_PEDIDOS = 100;
    private static final double PORCENTAJE_COMISION = 0.10;

    /**
     * Construye un repartidor con su legajo, nombre y vehículo.
     * Inicializa las listas de pedidos e historial.
     *
     * @param legajo número identificatorio del repartidor
     * @param nombre nombre del repartidor
     * @param vehiculo vehículo asignado
     * @throws IllegalArgumentException si algún dato no es válido
     */
    public Repartidor(int legajo, String nombre, Vehiculo vehiculo) {
        setLegajo(legajo);
        setNombre(nombre);
        setVehiculo(vehiculo);

        pedidos = new ArrayList<>();
        historial = new ArrayList<>();
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

    /**
     * Obtiene la cantidad de pedidos asignados.
     *
     * @return cantidad de pedidos
     */
    public int getCantidadPedidos() {
        return pedidos.size();
    }

    /**
     * Devuelve una copia de los pedidos asignados.
     *
     * @return lista de pedidos asignados
     */
    public ArrayList<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }

    /**
     * Devuelve una copia del historial de entregas.
     *
     * @return lista de pedidos entregados
     */
    public ArrayList<Pedido> getHistorial() {
        return new ArrayList<>(historial);
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

        for (int i = 0; i < nombre.length(); i++) {
            char caracter = nombre.charAt(i);

            if (!Character.isLetter(caracter) && caracter != ' ') {
                throw new IllegalArgumentException(
                    "El nombre solo puede contener letras y espacios."
                );
            }
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
     * Asigna un pedido utilizando el vehículo actual.
     *
     * @param pedido pedido que se desea asignar
     * @throws CapacidadExcedidaException si el vehículo no puede transportarlo
     * @throws AlmacenamientoLlenoException si se alcanzó el límite de pedidos
     * @throws PedidoInvalidoException si el pedido no es válido
     */
    public void asignarPedido(Pedido pedido)
            throws CapacidadExcedidaException,
                   AlmacenamientoLlenoException,
                   PedidoInvalidoException {

        asignarPedido(pedido, this.vehiculo);
    }

    /**
     * Asigna un pedido utilizando el vehículo indicado.
     *
     * @param pedido pedido que se desea asignar
     * @param vehiculoAsignado vehículo que transportará el pedido
     * @throws CapacidadExcedidaException si el vehículo no puede transportarlo
     * @throws AlmacenamientoLlenoException si se alcanzó el límite de pedidos
     * @throws PedidoInvalidoException si el pedido no es válido
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

        if (pedidos.size() >= MAX_PEDIDOS) {
            throw new AlmacenamientoLlenoException(
                "El repartidor alcanzó el máximo de pedidos."
            );
        }

        // Evitar asignaciones duplicadas.
        for (Pedido p : pedidos) {
            if (p.getCodigo() == pedido.getCodigo()) {
                throw new IllegalArgumentException(
                    "El pedido ya está asignado a este repartidor."
                );
            }
        }

        // Verificar que el vehículo pueda transportar el pedido.
        if (!vehiculoAsignado.puedeTransportar(pedido)) {
            throw new CapacidadExcedidaException(
                "El vehículo no puede transportar este pedido."
            );
        }

        pedido.setVehiculo(vehiculoAsignado);
        pedidos.add(pedido);
    }

    /**
     * Registra la entrega de un pedido asignado.
     * Verifica que esté en camino, cambia su estado a ENTREGADO
     * y lo agrega al historial.
     *
     * @param pedido pedido cuya entrega se registra
     * @throws IllegalArgumentException si el pedido es nulo,
     * no está asignado, no está en camino o ya fue entregado
     */
    public void registrarEntrega(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException(
                "El pedido no puede ser nulo."
            );
        }

        boolean asignado = false;

        for (Pedido p : pedidos) {
            if (p.getCodigo() == pedido.getCodigo()) {
                asignado = true;
                break;
            }
        }

        if (!asignado) {
            throw new IllegalArgumentException(
                "El pedido no está asignado a este repartidor."
            );
        }

        // Evitar registrar dos veces el mismo pedido.
        for (Pedido p : historial) {
            if (p.getCodigo() == pedido.getCodigo()) {
                throw new IllegalArgumentException(
                    "La entrega de este pedido ya fue registrada."
                );
            }
        }

        // Solo se pueden entregar pedidos que estén en camino.
        if (pedido.getEstado() != EstadoPedido.EN_CAMINO) {
            throw new IllegalArgumentException(
                "El pedido debe estar en camino para registrar su entrega."
            );
        }

        pedido.cambiarEstado(EstadoPedido.ENTREGADO);

        historial.add(pedido);
    }

    /**
     * Obtiene un resumen de los estados de los pedidos.
     *
     * @return cantidad de pedidos pendientes, en camino y entregados
     */
    @Override
    public String obtenerEstadoActual() {

        int pendientes = 0;
        int enCamino = 0;
        int entregados = 0;

        for (Pedido pedido : pedidos) {

            EstadoPedido estado = pedido.getEstado();

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
     * Calcula la comisión del repartidor como el 10 %
     * del importe total de las entregas registradas.
     *
     * @return importe de la comisión
     */
    @Override
    public double calcularImporte() {

        double totalEntregado = 0;

        for (Pedido pedido : historial) {
            totalEntregado += pedido.calcularImporte();
        }

        return totalEntregado * PORCENTAJE_COMISION;
    }

    /**
     * Devuelve una representación textual del repartidor.
     *
     * @return descripción del repartidor
     */
    @Override
    public String toString() {

        return "Legajo: " + legajo
                + ", nombre: " + nombre
                + ", vehículo: " + vehiculo.getPatente()
                + ", pedidos asignados: " + pedidos.size()
                + ", entregas realizadas: " + historial.size();
    }

    /**
     * Quita un pedido de los asignados al repartidor.
     * Se utiliza para deshacer una asignación.
     *
     * @param pedido pedido que se desea quitar
     * @throws IllegalArgumentException si el pedido es nulo
     * o no está asignado a este repartidor
     */
    public void quitarPedido(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException(
                "El pedido no puede ser nulo."
            );
        }

        for (int i = 0; i < pedidos.size(); i++) {

            if (pedidos.get(i).getCodigo() == pedido.getCodigo()) {
                pedidos.remove(i);
                return;
            }
        }

        throw new IllegalArgumentException(
            "El pedido no está asignado a este repartidor."
        );
    }

        /**
     * Quita una entrega del historial.
     * Se utiliza cuando se deshace el cambio de estado
     * de un pedido entregado.
     *
     * @param pedido pedido cuya entrega se desea quitar
     */
    public void quitarEntrega(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException(
                "El pedido no puede ser nulo."
            );
        }

        for (int i = 0; i < historial.size(); i++) {

            if (historial.get(i).getCodigo() == pedido.getCodigo()) {
                historial.remove(i);
                return;
            }
        }
    }
}
