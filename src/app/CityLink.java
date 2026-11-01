package app;
import modelo.*;


import excepciones.*;

/**
 * Gestor CityLink
 * contiene los arreglos de clientes, pedidos, repartidores
 * y operaciones del sistema.
 */
public class CityLink {

    private static final int MAX_CLIENTES = 50;
    private Cliente[] clientes;
    private int cantClientes;

    private static final int MAX_PEDIDOS = 100;
    private Pedido[] pedidos;
    private int cantPedidos;

    private static final int MAX_REPARTIDORES = 10;
    private Repartidor[] repartidores;
    private int cantRepartidores;

    /**
     * Crea un gestor vacio inicializando los arreglos con sus capacidades ya definidas, y contadores en 0.
     */
    public CityLink() {
        clientes = new Cliente[MAX_CLIENTES];
        pedidos = new Pedido[MAX_PEDIDOS];
        repartidores = new Repartidor[MAX_REPARTIDORES];
        cantClientes = 0;
        cantPedidos = 0;
        cantRepartidores = 0;
    }

    /**
     * Registra un nuevo cliente
     * @param c cliente
     * @throws AlmacenamientoLlenoException si la cantidad de clientes excede la capacidad establecida.
     * @throws IllegalArgumentException si el codigo del cliente a registrar ya existe.
     */
    public void registrarCliente(Cliente c) throws AlmacenamientoLlenoException {
        if (cantClientes >= MAX_CLIENTES) throw new AlmacenamientoLlenoException("Capacidad de clientes excedida!");

        for(int i = 0; i < cantClientes; i++) {
            if (clientes[i].getCodigo() == c.getCodigo()) throw new IllegalArgumentException("Codigo de cliente repetido!");
        }

        clientes[cantClientes] = c;
        cantClientes++;
    }

    /**
     * Busca un cliente por su codigo
     * @param codigo codigo del cliente
     * @return devuelve al cliente
     * @throws ClienteNoEncontradoException si no se encontro ningun cliente con ese codigo
     */
    public Cliente buscarCliente(int codigo) throws ClienteNoEncontradoException {
        for(int i = 0; i < cantClientes; i++) {
            if(clientes[i].getCodigo() == codigo) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException("No se encontro al cliente con el codigo: " + codigo);
    }

    /**
     * Busca un cliente por su nombre
     * @param nombre nombre del cliente
     * @return devuelve al cliente
     * @throws ClienteNoEncontradoException si no se encontro ningun cliente con ese nombre
     */
    public Cliente buscarCliente(String nombre) throws ClienteNoEncontradoException {
        for(int i = 0; i < cantClientes; i++) {
            if (clientes[i].getNombre().equalsIgnoreCase(nombre)) {
                return clientes[i];
            }
        }
        throw new ClienteNoEncontradoException("No se encontro al cliente " + nombre);
    }

    /**
     * Elimina un cliente por su codigo
     * @param codigo codigo de cliente
     * @throws ClienteNoEncontradoException si el codigo del cliente no existe
     */
    public void eliminarCliente(int codigo) throws ClienteNoEncontradoException {
        int posicion = -1;

        for(int i = 0; i < cantClientes; i++) {
            if(clientes[i].getCodigo() == codigo) {
                posicion = i;
            }
        }

        if (posicion == -1) throw new ClienteNoEncontradoException("No se encontro al cliente con el codigo: " + codigo);

        for(int i = posicion; i < cantClientes - 1; i++) {
            clientes[i] = clientes[i + 1];
        }

        clientes[cantClientes - 1] = null;
        cantClientes--;

    }

    /**
     * Registra un nuevo repartidor
     * @param r repartidor 
     * @throws AlmacenamientoLlenoException si la cantidad de repartidores excede a la capacidad
     * @throws IllegalArgumentException si el legajo del repartidor ya existe
     */
    public void registrarRepartidor(Repartidor r) throws AlmacenamientoLlenoException {
        if (cantRepartidores >= MAX_REPARTIDORES) throw new AlmacenamientoLlenoException("Capacidad de repartidores excedida!");

        for(int i = 0; i < cantRepartidores; i++) {
            if (repartidores[i].getLegajo() == r.getLegajo()) throw new IllegalArgumentException("Legajo de repartidor repetido!");
        }

        repartidores[cantRepartidores] = r;
        cantRepartidores++;
    }

    /**
     * Registra un pedido
     * @param p pedido
     * @throws AlmacenamientoLlenoException si la cantidad de pedidos excede a la capacidad
     * @throws PedidoInvalidoException si el codigo del pedido esta repetido
     * @throws ClienteNoEncontradoException si no se encontro al cliente del pedido
     */
    public void registrarPedido(Pedido p) throws AlmacenamientoLlenoException, PedidoInvalidoException, ClienteNoEncontradoException {
        if (cantPedidos >= MAX_PEDIDOS) throw new AlmacenamientoLlenoException("Capacidad de pedidos excedida");

        for (int i = 0; i < cantPedidos; i++) {
            if (pedidos[i].getCodigo() == p.getCodigo()) throw new PedidoInvalidoException("Codigo de pedido repetido!");
        }

        buscarCliente(p.getCliente().getCodigo());

        pedidos[cantPedidos] = p;
        cantPedidos++;
    }

    /**
     * Busca un pedido por su codigo
     * @param codigo codigo del pedido
     * @return devuelve el pedido
     * @throws PedidoInvalidoException si no se encuentra un pedido con ese codigo
     */
    public Pedido buscarPedido(int codigo) throws PedidoInvalidoException {
        for(int i = 0; i < cantPedidos; i++) {
            if(pedidos[i].getCodigo() == codigo) {
                return pedidos[i]; 
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
        for(int i = 0; i < cantRepartidores; i++) {
            if(repartidores[i].getLegajo() == legajo) {
                return repartidores[i];
            }
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
        pedido.cambiarEstado(nuevo);
    }

    /**
     * Lista todos los pedidos cargados, uno por linea
     * @return texto con el codigo, tipo, costo y tiempo estimado de cada pedido.
     * si un pedido no tiene vehiculo asignado, solo muestra codigo y tipo.
     */
    public String listarPedidos() {
        String acumulador = "";

        for(int i = 0; i < cantPedidos; i++) {

            Vehiculo v = pedidos[i].getVehiculo();

            if(v == null) {
                acumulador += "| Codigo: " + pedidos[i].getCodigo() + 
                             " - Tipo: " + pedidos[i].getClass().getSimpleName() + 
                             " - Sin vehiculo asignado" + "\n";
            } else {
                double costo = pedidos[i].calcularCosto(v);
                double tiempo = v.calcularTiempoEntrega(pedidos[i].getDistanciaKm());
                
                acumulador += "| Codigo: " + pedidos[i].getCodigo() + 
                             " - Tipo: " + pedidos[i].getClass().getSimpleName() + 
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



}
