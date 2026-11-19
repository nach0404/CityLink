
package app;

import modelo.*;
import excepciones.*;

import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Deque;

/**
 * Gestor principal de CityLink.
 * Administra clientes, pedidos, repartidores y las operaciones del sistema.
 * Utiliza TreeMap para clientes, ArrayList para pedidos y repartidores,
 * colas para pedidos pendientes y una pila para deshacer operaciones.
 */
public class CityLink {

    private TreeMap<Integer, Cliente> clientes;
    private List<Pedido> pedidos;
    private List<Repartidor> repartidores;
    private Queue<Pedido> urgentes;
    private Queue<Pedido> normales;
    private Deque<Operacion> historial;

    /**
     * Crea un gestor vacío e inicializa las estructuras dinámicas.
     */
    public CityLink() {
        clientes = new TreeMap<>();
        pedidos = new ArrayList<>();
        repartidores = new ArrayList<>();
        urgentes = new ArrayDeque<>();
        normales = new ArrayDeque<>();
        historial = new ArrayDeque<>();
    }

    /**
     * Registra un nuevo cliente.
     *
     * @param c cliente a registrar
     * @throws IllegalArgumentException si el código ya existe
     */
    public void registrarCliente(Cliente c) {

        if (c == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }

        if (clientes.containsKey(c.getCodigo())) {
            throw new IllegalArgumentException("Codigo de cliente repetido!");
        }

        clientes.put(c.getCodigo(), c);
    }

    /**
     * Busca un cliente por su código.
     *
     * @param codigo código del cliente
     * @return cliente encontrado
     * @throws ClienteNoEncontradoException si no existe
     */
    public Cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {

        Cliente c = clientes.get(codigo);

        if (c == null) {
            throw new ClienteNoEncontradoException(
                "No se encontro al cliente con el codigo: " + codigo
            );
        }

        return c;
    }

    /**
     * Busca un cliente por su nombre.
     *
     * @param nombre nombre del cliente
     * @return cliente encontrado
     * @throws ClienteNoEncontradoException si no existe
     */
    public Cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {

        for (Cliente c : clientes.values()) {
            if (c.getNombre().equalsIgnoreCase(nombre)) {
                return c;
            }
        }

        throw new ClienteNoEncontradoException(
            "No se encontro al cliente " + nombre
        );
    }

    /**
     * Elimina un cliente por su código.
     *
     * @param codigo código del cliente
     * @throws ClienteNoEncontradoException si no existe
     */
    public void eliminarCliente(int codigo) throws ClienteNoEncontradoException {

        if (clientes.remove(codigo) == null) {
            throw new ClienteNoEncontradoException(
                "No se encontro al cliente con el codigo: " + codigo
            );
        }
    }

    /**
     * Lista todos los clientes registrados.
     *
     * @return información de los clientes
     */
    public String listarClientes() {

        if (clientes.isEmpty()) {
            return "No hay clientes registrados";
        }

        String acumulador = "";

        for (Cliente c : clientes.values()) {
            acumulador += c.toString() + "\n";
        }

        return acumulador;
    }

    /**
     * Registra un nuevo repartidor.
     *
     * @param r repartidor a registrar
     * @throws IllegalArgumentException si el legajo ya existe
     */
    public void registrarRepartidor(Repartidor r) {

        if (r == null) {
            throw new IllegalArgumentException("El repartidor no puede ser nulo.");
        }

        for (Repartidor repartidor : repartidores) {
            if (r.getLegajo() == repartidor.getLegajo()) {
                throw new IllegalArgumentException("Legajo de repartidor repetido!");
            }
        }

        repartidores.add(r);
    }

    /**
     * Registra un pedido y lo incorpora a la cola correspondiente.
     *
     * @param p pedido a registrar
     * @throws PedidoInvalidoException si el código está repetido
     * @throws ClienteNoEncontradoException si el cliente no está registrado
     */
    public void registrarPedido(Pedido p)
            throws PedidoInvalidoException, ClienteNoEncontradoException {

        if (p == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }

        for (Pedido pedido : pedidos) {
            if (pedido.getCodigo() == p.getCodigo()) {
                throw new PedidoInvalidoException("Codigo de pedido repetido!");
            }
        }

        buscarCliente(p.getCliente().getCodigo());

        if (p.esUrgente()) {
            urgentes.offer(p);
        } else {
            normales.offer(p);
        }

        pedidos.add(p);

        historial.push(new OperacionAlta(p));
    }

    /**
     * Busca un pedido por su código.
     *
     * @param codigo código del pedido
     * @return pedido encontrado
     * @throws PedidoInvalidoException si no existe
     */
    public Pedido buscarPedido(int codigo) throws PedidoInvalidoException {

        for (Pedido p : pedidos) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }

        throw new PedidoInvalidoException(
            "No se encontro un pedido con el codigo: " + codigo
        );
    }

    /**
     * Busca un repartidor por su legajo.
     *
     * @param legajo legajo del repartidor
     * @return repartidor encontrado
     * @throws IllegalArgumentException si no existe
     */
    public Repartidor buscarRepartidor(int legajo) {

        for (Repartidor repartidor : repartidores) {
            if (legajo == repartidor.getLegajo()) {
                return repartidor;
            }
        }

        throw new IllegalArgumentException(
            "No se encontro un repartidor con el legajo: " + legajo
        );
    }

    /**
     * Quita del historial del repartidor la entrega de un pedido.
     *
     * @param pedido pedido cuya entrega se desea quitar
     */
    public void quitarEntregaRepartidor(Pedido pedido) {

        for (Repartidor repartidor : repartidores) {

            for (Pedido asignado : repartidor.getPedidos()) {

                if (asignado.getCodigo() == pedido.getCodigo()) {
                    repartidor.quitarEntrega(pedido);
                    return;
                }
            }
        }
    }

    /**
     * Asigna un pedido a un repartidor y lo retira de su cola de pendientes.
     *
     * @param codigoPedido código del pedido
     * @param legajo legajo del repartidor
     * @throws CapacidadExcedidaException si el vehículo no puede transportar el pedido
     * @throws AlmacenamientoLlenoException si se excede la capacidad del repartidor
     * @throws PedidoInvalidoException si el pedido no existe o no es válido
     */
    public void asignarPedido(int codigoPedido, int legajo)
            throws CapacidadExcedidaException,
                   AlmacenamientoLlenoException,
                   PedidoInvalidoException {

        Pedido pedido = buscarPedido(codigoPedido);
        Repartidor repartidor = buscarRepartidor(legajo);

        repartidor.asignarPedido(pedido);

        if (pedido.esUrgente()) {
            urgentes.remove(pedido);
        } else {
            normales.remove(pedido);
        }

        historial.push(new OperacionAsignacion(pedido, repartidor));
    }

    /**
     * Cambia el estado de un pedido.
     * Cuando pasa a ENTREGADO, registra la entrega en el repartidor responsable.
     *
     * @param codigoPedido código del pedido
     * @param nuevo nuevo estado
     * @throws PedidoInvalidoException si el pedido no existe
     */
    public void cambiarEstado(int codigoPedido, EstadoPedido nuevo)
            throws PedidoInvalidoException {

        Pedido pedido = buscarPedido(codigoPedido);
        EstadoPedido anterior = pedido.getEstado();

        if (nuevo == EstadoPedido.ENTREGADO) {

            Repartidor responsable = null;

            for (Repartidor repartidor : repartidores) {

                for (Pedido asignado : repartidor.getPedidos()) {

                    if (asignado.getCodigo() == codigoPedido) {
                        responsable = repartidor;
                        break;
                    }
                }

                if (responsable != null) {
                    break;
                }
            }

            if (responsable == null) {
                throw new IllegalArgumentException(
                    "El pedido no esta asignado a ningun repartidor."
                );
            }

            responsable.registrarEntrega(pedido);

        } else {
            pedido.cambiarEstado(nuevo);
        }

        historial.push(new OperacionCambioEstado(pedido, anterior));
    }

    /**
     * Lista todos los pedidos registrados.
     *
     * @return información de los pedidos, incluyendo costo y tiempo cuando tienen vehículo
     */
    public String listarPedidos() {

        String acumulador = "";

        for (Pedido p : pedidos) {

            Vehiculo v = p.getVehiculo();

            if (v == null) {
                acumulador += "| Codigo: " + p.getCodigo()
                    + " - Tipo: " + p.getClass().getSimpleName()
                    + " - Sin vehiculo asignado\n";

            } else {
                double costo = p.calcularCosto(v);
                double tiempo = v.calcularTiempoEntrega(p.getDistanciaKm());

                acumulador += "| Codigo: " + p.getCodigo()
                    + " - Tipo: " + p.getClass().getSimpleName()
                    + " - Costo: " + String.format("%.2f", costo)
                    + " - Tiempo: " + tiempo + " min\n";
            }
        }

        if (acumulador.isEmpty()) {
            return "No hay pedidos registrados.";
        }

        return acumulador;
    }

    /**
     * Consulta el estado de un pedido.
     *
     * @param codigoPedido código del pedido
     * @return nombre del estado actual
     * @throws PedidoInvalidoException si el pedido no existe
     */
    public String consultarEstadoPedido(int codigoPedido)
            throws PedidoInvalidoException {

        Pedido p = buscarPedido(codigoPedido);
        return p.obtenerEstadoActual();
    }

    /**
     * Consulta el resumen de estados de los pedidos de un repartidor.
     *
     * @param legajo legajo del repartidor
     * @return resumen de los estados
     */
    public String consultarEstadoRepartidor(int legajo) {

        Repartidor r = buscarRepartidor(legajo);
        return r.obtenerEstadoActual();
    }

    /**
     * Calcula el importe total de los pedidos que tienen vehículo asignado.
     *
     * @return importe total
     */
    public double calcularImporteTotalPedidos() {

        double totalPedidos = 0;

        for (Pedido p : pedidos) {
            if (p.getVehiculo() != null) {
                totalPedidos += p.calcularImporte();
            }
        }

        return totalPedidos;
    }

    /**
     * Lista las comisiones de todos los repartidores.
     *
     * @return nombres y comisiones
     */
    public String listarComisiones() {

        String acumulador = "";

        for (Repartidor repartidor : repartidores) {
            acumulador += "| Nombre: " + repartidor.getNombre()
                + " - Comisiones: "
                + String.format("%.2f", repartidor.calcularImporte())
                + "\n";
        }

        return acumulador;
    }

    /**
     * Carga los datos de prueba del sistema.
     * Este método debe ejecutarse una sola vez al iniciar la aplicación.
     *
     * @throws AlmacenamientoLlenoException si se excede la capacidad
     * @throws CapacidadExcedidaException si un vehículo no puede transportar un pedido
     * @throws ClienteNoEncontradoException si el cliente no está registrado
     * @throws PedidoInvalidoException si algún pedido no es válido
     */
    public void cargarDatosPrueba()
            throws AlmacenamientoLlenoException,
                   CapacidadExcedidaException,
                   ClienteNoEncontradoException,
                   PedidoInvalidoException {

        // Clientes
        Cliente c1 = new Cliente(1, "Ignacio Lucero", "2657400101", "Centro");
        Cliente c2 = new Cliente(2, "Maria Rojo", "2657400303", "Zona Sur");
        Cliente c3 = new Cliente(3, "Gabriela Toledo", "2657400404", "Barrio Oeste");
        Cliente c4 = new Cliente(4, "Analia Baigorria", "2657400202", "Barrio Norte");
        Cliente c5 = new Cliente(5, "Antonella Oyola", "2657400505", "Zona Este");

        registrarCliente(c1);
        registrarCliente(c2);
        registrarCliente(c3);
        registrarCliente(c4);
        registrarCliente(c5);

        // Vehículos
        Vehiculo bicicleta = new Bicicleta("BICI-001");
        Vehiculo moto = new Moto("AB123CD");
        Vehiculo camioneta = new Camioneta("AC456EF");

        // Repartidores
        Repartidor r1 = new Repartidor(101, "Franco", bicicleta);
        Repartidor r2 = new Repartidor(102, "Gian", moto);
        Repartidor r3 = new Repartidor(103, "Sofia", camioneta);

        registrarRepartidor(r1);
        registrarRepartidor(r2);
        registrarRepartidor(r3);

        // Pedidos
        Pedido p1 = new PedidoNormal(1001, c1, "Av. Central 100", 3, 10);
        Pedido p2 = new PedidoNormal(1002, c2, "Calle Norte 200", 5, 15);
        Pedido p3 = new PedidoNormal(1003, c3, "Calle Sur 300", 7, 8);
        Pedido p4 = new PedidoUrgente(1004, c4, "Av. Este 400", 10, 12);
        Pedido p5 = new PedidoUrgente(1005, c5, "Calle Oeste 500", 15, 20);
        Pedido p6 = new PedidoUrgente(1006, c1, "Av. Central 600", 8, 18);
        Pedido p7 = new PedidoFragil(1007, c2, "Calle Norte 700", 12, 10);
        Pedido p8 = new PedidoFragil(1008, c3, "Calle Sur 800", 20, 25);

        registrarPedido(p1);
        registrarPedido(p2);
        registrarPedido(p3);
        registrarPedido(p4);
        registrarPedido(p5);
        registrarPedido(p6);
        registrarPedido(p7);
        registrarPedido(p8);

        // Asignaciones
        asignarPedido(1001, 101);
        asignarPedido(1002, 101);
        asignarPedido(1003, 102);
        asignarPedido(1004, 102);
        asignarPedido(1005, 103);
        asignarPedido(1006, 103);

        // Estados de prueba
        cambiarEstado(1001, EstadoPedido.EN_CAMINO);
        cambiarEstado(1001, EstadoPedido.ENTREGADO);

        cambiarEstado(1002, EstadoPedido.EN_CAMINO);
        cambiarEstado(1002, EstadoPedido.ENTREGADO);

        cambiarEstado(1003, EstadoPedido.EN_CAMINO);

        // Las operaciones de carga inicial no deben poder deshacerse.
        historial.clear();
    }

    /**
     * Atiende al siguiente pedido pendiente.
     * Prioriza la cola de urgentes y luego la de normales.
     *
     * @return siguiente pedido
     * @throws SinPedidosPendientesException si no hay pedidos pendientes
     */
    public Pedido atenderSiguiente() throws SinPedidosPendientesException {

        Pedido siguiente = urgentes.poll();

        if (siguiente == null) {
            siguiente = normales.poll();
        }

        if (siguiente == null) {
            throw new SinPedidosPendientesException(
                "No hay pedidos pendientes..."
            );
        }

        return siguiente;
    }

    /**
     * Elimina un pedido de la lista y de su cola correspondiente.
     *
     * @param p pedido a quitar
     */
    public void quitarPedido(Pedido p) {

        if (p == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }

        pedidos.remove(p);

        if (p.esUrgente()) {
            urgentes.remove(p);
        } else {
            normales.remove(p);
        }
    }

    /**
     * Devuelve un pedido a la cola de pendientes que le corresponde.
     *
     * @param p pedido que vuelve a estar pendiente
     */
    public void encolarPendiente(Pedido p) {

        if (p == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo.");
        }

        if (p.esUrgente()) {
            urgentes.offer(p);
        } else {
            normales.offer(p);
        }
    }

    /**
     * Deshace la última operación registrada.
     *
     * @return descripción de la operación deshecha
     * @throws NadaParaDeshacerException si no hay operaciones recientes
     */
    public String deshacerUltima() throws NadaParaDeshacerException {

        if (historial.isEmpty()) {
            throw new NadaParaDeshacerException(
                "No hay operaciones recientes"
            );
        }

        Operacion ultima = historial.pop();

        return ultima.deshacer(this);
    }
}
