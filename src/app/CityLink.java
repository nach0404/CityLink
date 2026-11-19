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
 * Gestor CityLink
 * contiene las estructuras dinamicas. Los clientes en arbol, pedidos y repartidores en ArrayList,
 * y operaciones del sistema.
 */
public class CityLink {

    private TreeMap<Integer, Cliente> clientes;

    private List<Pedido> pedidos;

    private List<Repartidor> repartidores;

    private Queue<Pedido> urgentes;

    private Queue<Pedido> normales; 

    private Deque<Operacion> historial;

    /**
     * Crea un gestor vacio inicializando las estructuras dinamicas
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
     * Registra un nuevo cliente
     * @param c cliente
     * @throws IllegalArgumentException si el codigo del cliente a registrar ya existe.
     */
    public void registrarCliente(Cliente c) {

        if (clientes.containsKey(c.getCodigo())) throw new IllegalArgumentException("Codigo de cliente repetido!");

        clientes.put(c.getCodigo(), c);
    }

    /**
     * Busca un cliente por su codigo
     * @param codigo codigo del cliente
     * @return devuelve al cliente
     * @throws ClienteNoEncontradoException si no se encontro ningun cliente con ese codigo
     */
    public Cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {

        Cliente c = clientes.get(codigo);
        if(c == null) throw new ClienteNoEncontradoException("No se encontro al cliente con el codigo: " + codigo);

        return c;
    }

    /**
     * Busca un cliente por su nombre
     * @param nombre nombre del cliente
     * @return devuelve al cliente
     * @throws ClienteNoEncontradoException si no se encontro ningun cliente con ese nombre
     */
    public Cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
        for (Cliente c : clientes.values()) {
            if(c.getNombre().equalsIgnoreCase(nombre)) return c;
        }
        throw new ClienteNoEncontradoException("No se encontro al cliente " + nombre);
    }

    /**
     * Elimina un cliente por su codigo
     * @param codigo codigo de cliente
     * @throws ClienteNoEncontradoException si el codigo del cliente no existe
     */
    public void eliminarCliente(int codigo) throws ClienteNoEncontradoException {

        if(clientes.remove(codigo) == null) throw new ClienteNoEncontradoException("No se encontro al cliente con el codigo: " + codigo);
    }

    /**
     * Lista todos los clientes registrados
     * @return texto con informacion de todos los clientes
     */
    public String listarClientes() {

        if(clientes.isEmpty()) return "No hay clientes registrados";

        String acumulador = "";

        for (Cliente c : clientes.values()) {
            acumulador += c.toString() + "\n";
        }

        return acumulador;
    }

    /**
     * Registra un nuevo repartidor
     * @param r repartidor
     * @throws IllegalArgumentException si el legajo del repartidor ya existe
     */
    public void registrarRepartidor(Repartidor r) {

        for (Repartidor repartidor : repartidores) {
            if(r.getLegajo() == repartidor.getLegajo()) throw new IllegalArgumentException("Legajo de repartidor repetido!");
        }

        repartidores.add(r);
    }

    /**
     * Registra un pedido en pedidos urgentes o normales
     * @param p pedido
     * @throws PedidoInvalidoException si el codigo del pedido esta repetido
     * @throws ClienteNoEncontradoException si no se encontro al cliente del pedido
     */
    public void registrarPedido(Pedido p) throws PedidoInvalidoException, ClienteNoEncontradoException {

        for (Pedido pedido : pedidos) {
            if (pedido.getCodigo() == p.getCodigo()) throw new PedidoInvalidoException("Codigo de pedido repetido!");
        }

        buscarCliente(p.getCliente().getCodigo());

        if(p.esUrgente()) {
            urgentes.offer(p);
        } else {
            normales.offer(p);
        }

        pedidos.add(p);

        historial.push(new OperacionAlta(p));
    }

    /**
     * Busca un pedido por su codigo
     * @param codigo codigo del pedido
     * @return devuelve el pedido
     * @throws PedidoInvalidoException si no se encuentra un pedido con ese codigo
     */
    public Pedido buscarPedido(int codigo) throws PedidoInvalidoException {
        for(Pedido p : pedidos) {
            if(p.getCodigo() == codigo) {
                return p; 
            }
        }

        throw new PedidoInvalidoException("No se encontro un pedido con el codigo: " + codigo);
    }

    /**
     * Busca un repartidor por su legajo
     * @param legajo legajo del repartidor
     * @return devuelve al repartidor
     * @throws IllegalArgumentException si no se encuentra un repartidor con ese legajo
     */
    public Repartidor buscarRepartidor(int legajo) {
        for (Repartidor repartidor : repartidores) {
            if (legajo == repartidor.getLegajo()) return repartidor;
        }

        throw new IllegalArgumentException("No se encontro un repartidor con el legajo: " + legajo);
    }

    /**
     * Asigna un pedido a un repartidor
     * @param codigoPedido codigo del pedido
     * @param legajo legajo del repartidor
     * @throws CapacidadExcedidaException si el vehiculo no puede transportar al pedido 
     * @throws AlmacenamientoLlenoException si la cantidad de pedidos excede a la capacidad del repartidor
     * @throws PedidoInvalidoException si el pedido no cumple alguna validacion
     */
    public void asignarPedido(int codigoPedido, int legajo) throws CapacidadExcedidaException, AlmacenamientoLlenoException, PedidoInvalidoException {
        Pedido pedido = buscarPedido(codigoPedido);
        Repartidor repartidor = buscarRepartidor(legajo);

        repartidor.asignarPedido(pedido);

        if (pedido.esUrgente()) {
            urgentes.remove(pedido);
        } else {
            normales.remove(pedido);
        }
    }

    /**
     * Cambia el estado de un pedido
     * @param codigoPedido codigo del pedido
     * @param nuevo estado nuevo del pedido
     * @throws PedidoInvalidoException si el pedido no cumple alguna validacion
     * @throws EstadoInvalidoException si el estado nuevo no cumple con lo valido
     */
    public void cambiarEstado(int codigoPedido, EstadoPedido nuevo) throws PedidoInvalidoException {
        Pedido pedido = buscarPedido(codigoPedido);
        EstadoPedido anterior = pedido.getEstado();
        pedido.cambiarEstado(nuevo);

        historial.push(new OperacionCambioEstado(pedido, anterior));

    }

    /**
     * Lista todos los pedidos cargados, uno por linea
     * @return texto con el codigo, tipo, costo y tiempo estimado de cada pedido.
     * si un pedido no tiene vehiculo asignado, solo muestra codigo y tipo.
     */
    public String listarPedidos() {
        String acumulador = "";

        for(Pedido p : pedidos) {

            Vehiculo v = p.getVehiculo();

            if(v == null) {
                acumulador += "| Codigo: " + p.getCodigo() + 
                             " - Tipo: " + p.getClass().getSimpleName() + 
                             " - Sin vehiculo asignado" + "\n";
            } else {
                double costo = p.calcularCosto(v);
                double tiempo = v.calcularTiempoEntrega(p.getDistanciaKm());
                
                acumulador += "| Codigo: " + p.getCodigo() + 
                             " - Tipo: " + p.getClass().getSimpleName() + 
                             " - Costo: " + String.format("%.2f", costo) + " - Tiempo: " + tiempo + " min" + "\n";

            }
        }

        return acumulador;
    }

    /**
     * Consulta el estado de un pedido
     * @param codigoPedido codigo del pedido
     * @return texto del nombre de estado
     * @throws PedidoInvalidoException si no se encuentra un pedido con ese codigo
     */
    public String consultarEstadoPedido(int codigoPedido) throws PedidoInvalidoException {
        Pedido p = buscarPedido(codigoPedido);
        return p.obtenerEstadoActual();
    }

    /**
     * Consulta el resumen de estados de los pedidos de un repartidor
     * @param legajo legajo del repartidor
     * @return texto con el resumen de los estados de pedidos
     * @throws IllegalArgumentException si no se encuentra un repartidor con ese legajo
     */
    public String consultarEstadoRepartidor(int legajo) {
        Repartidor r = buscarRepartidor(legajo);
        return r.obtenerEstadoActual();
    }

    /**
     * Devuelve importe total de todos los pedidos
     * @return double del importe de todos los pedidos
     * solo se suman los pedidos que ya tienen vehiculo
     */
    public double calcularImporteTotalPedidos() {
        double totalPedidos = 0;
        for(Pedido p : pedidos) {
            if(p.getVehiculo() != null) {
                totalPedidos += p.calcularImporte();
            }
        }

        return totalPedidos;
    }

    /**
     * Devuelve la comision de los pedidos entregados de cada repartidor, uno por linea
     * @return texto con nombre y comisiones de entregados
     */
    public String listarComisiones() {
        String acumulador = "";

        for(Repartidor repartidor : repartidores) {
            acumulador += "| Nombre: " + repartidor.getNombre() + " - Comisiones: " + String.format("%.2f", repartidor.calcularImporte()) + "\n";
        }

        return acumulador;
    }

    /**
     * Datos de prueba, carga clientes, repartidores y pedidos. Se debe llamar una sola vez.
     * @throws AlmacenamientoLlenoException si la cantidad excede a la capacidad
     * @throws CapacidadExcedidaException si el vehiculo no puede transportar el pedido
     * @throws ClienteNoEncontradoException si el cliente de algun pedido no esta registrado
     * @throws PedidoInvalidoException si algun dato de un pedido no es valido
     */
    public void cargarDatosPrueba() throws AlmacenamientoLlenoException, CapacidadExcedidaException, ClienteNoEncontradoException, PedidoInvalidoException {


        Cliente c1 = new Cliente(1, "Ignacio Lucero", "2657400101", "Centro");
        registrarCliente(c1);

        Cliente c2 = new Cliente(2, "Maria Rojo", "2657400303", "Zona Sur");
        registrarCliente(c2);

        Cliente c3 = new Cliente(3, "Gabriela Toledo", "2657400404", "Barrio Oeste");
        registrarCliente(c3);

        Cliente c4 = new Cliente(4, "Analia Baigorria", "2657400202", "Barrio Norte");
        registrarCliente(c4);

        Cliente c5 = new Cliente(5, "Antonella Oyola", "2657400505", "Zona Este");
        registrarCliente(c5);

        Vehiculo bicicleta = new Bicicleta("BICI-001");
        Vehiculo moto = new Moto("AB123CD");
        Vehiculo camioneta = new Camioneta("AC456EF");

        Repartidor r1 = new Repartidor(101, "Franco", bicicleta);
        registrarRepartidor(r1);

        Repartidor r2 = new Repartidor(102, "Gian", moto);
        registrarRepartidor(r2);

        Repartidor r3 = new Repartidor(103, "Sofia", camioneta);
        registrarRepartidor(r3);

        // PEDIDOS
        Pedido p1 = new PedidoNormal(1001, c1, "Av. Central 100", 3, 10);
        Pedido p2 = new PedidoNormal(1002, c2, "Calle Norte 200", 5, 15);
        Pedido p3 = new PedidoNormal(1003, c3, "Calle Sur 300", 7, 8);

        Pedido p4 = new PedidoUrgente(1004, c4, "Av. Este 400", 10, 12);
        Pedido p5 = new PedidoUrgente(1005, c5, "Calle Oeste 500", 15, 20);
        Pedido p6 = new PedidoUrgente(1006, c1, "Av. Central 600", 8, 18);

        Pedido p7 = new PedidoFragil(1007, c2, "Calle Norte 700", 12, 10);
        Pedido p8 = new PedidoFragil(1008, c3, "Calle Sur 800", 20, 25);

        // REGISTRAR PEDIDOS
        registrarPedido(p1);
        registrarPedido(p2);
        registrarPedido(p3);
        registrarPedido(p4);
        registrarPedido(p5);
        registrarPedido(p6);
        registrarPedido(p7);
        registrarPedido(p8);

        // ASIGNACIONES
        asignarPedido(1001, 101);
        asignarPedido(1002, 101);

        asignarPedido(1003, 102);
        asignarPedido(1004, 102);

        asignarPedido(1005, 103);
        asignarPedido(1006, 103);

        cambiarEstado(1001, EstadoPedido.EN_CAMINO);
        cambiarEstado(1001, EstadoPedido.ENTREGADO);

        cambiarEstado(1002, EstadoPedido.EN_CAMINO);
        cambiarEstado(1002, EstadoPedido.ENTREGADO);

        cambiarEstado(1003, EstadoPedido.EN_CAMINO);

        historial.clear();
        
    }

    /**
     * Atiende al siguiente pedido pendiente: primero los urgentes y luego los normales, ambos por orden de llegada
     * @return pedido urgente o normal
     * @throws SinPedidosPendientesException si no hay pedidos pendientes
     */
    public Pedido atenderSiguiente() throws SinPedidosPendientesException {
        Pedido siguiente = urgentes.poll();

        if(siguiente == null) {
            siguiente = normales.poll();
        }

        if(siguiente == null) {
            throw new SinPedidosPendientesException("No hay pedidos pendientes...");
        }

        return siguiente;
    }

    /**
     * Elimina un pedido de la lista pedidos, y de su cola segun el tipo(urgentes o normales)
     * @param p pedido a quitar
    */
    public void quitarPedido(Pedido p) {

        pedidos.remove(p);

        if(p.esUrgente()) {
            urgentes.remove(p);
        } else {
            normales.remove(p);
        }
    }

    /**
     * Deshace la ultima operacion registrada en el historial
     * @throws NadaParaDeshacerException si no hay operaciones recientes
     * @return texto del ultimo pedido que se elimino
    */
    public String deshacerUltima() throws NadaParaDeshacerException {

        if(historial.isEmpty()) throw new NadaParaDeshacerException("No hay operaciones recientes");

        Operacion ultima = historial.pop();

        return ultima.deshacer(this);
    }
}

