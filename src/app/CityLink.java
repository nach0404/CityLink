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

    /**
     * Devuelve importe total de todos los pedidos
     * @return double del importe de todos los pedidos
     * solo se suman los pedidos que ya tienen vehiculo
     */
    public double calcularImporteTotalPedidos() {
        double totalPedidos = 0;
        for(int i = 0; i < cantPedidos; i++) {
            if(pedidos[i].getVehiculo() != null) {
                totalPedidos += pedidos[i].calcularImporte();
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

        for(int i = 0; i < cantRepartidores; i++) {
            acumulador += "| Nombre: " + repartidores[i].getNombre() + " - Comisiones: " + repartidores[i].calcularImporte() + "\n";
        }

        return acumulador;
    }

    /**
     * Datos de prueba, carga clientes, repartidores y pedidos. Se de llamar una sola vez.
     * @throws AlmacenamientoLlenoException si la cantidad excede a la capacidad
     * @throws CapacidadExcedidaException si el vehiculo no puede transportar el pedido
     * @throws ClienteNoEncontradoException si el cliente de algun pedido no esta registrado
     * @throws PedidoInvalidoException si algun dato de un pedido no es valido
     */
    public void cargarDatosPrueba() throws AlmacenamientoLlenoException, CapacidadExcedidaException, ClienteNoEncontradoException, PedidoInvalidoException {

        Cliente c1 = new Cliente(1, "Martin Gomez", "2657-400101", "Centro");
        registrarCliente(c1);

        Cliente c2 = new Cliente(2, "Camila Rodríguez", "2657-400303", "Zona Sur");
        registrarCliente(c2);

        Cliente c3 = new Cliente(3, "Joaquín Pérez", "2657-400404", "Barrio Oeste");
        registrarCliente(c3);

        Cliente c4 = new Cliente(4, "Lucía Fernández", "2657-400202", "Barrio Norte");
        registrarCliente(c4);


        Cliente c5 = new Cliente(5, "Valentin López", "2657-400505", "Zona Este");
        registrarCliente(c5);

        Vehiculo bicicleta = new Bicicleta("BICI-001");
        Vehiculo moto = new Moto("AB123CD");
        Vehiculo camioneta = new Camioneta("AC456EF");

        Repartidor r1 = new Repartidor(101, "Diego Suarez", bicicleta);
        registrarRepartidor(r1);

        Repartidor r2 = new Repartidor(102, "Sofia Herrera", moto);
        registrarRepartidor(r2);

        Repartidor r3 = new Repartidor(103, "Tomas Acosta", camioneta);
        registrarRepartidor(r3);

        Pedido p1 = new PedidoNormal(1, c1, "Centro", 2, 3);
        registrarPedido(p1);

        Pedido p2 = new PedidoUrgente(2, c2, "Zona Sur", 5, 6);
        registrarPedido(p2);

        Pedido p3 = new PedidoFragil(3, c3, "Barrio Oeste", 3, 4);
        registrarPedido(p3);

        Pedido p4 = new PedidoNormal(4, c4, "Barrio Norte", 8, 2.5);
        registrarPedido(p4);

        Pedido p5 = new PedidoUrgente(5, c5, "Zona Este", 1.5, 7);
        registrarPedido(p5);

        Pedido p6 = new PedidoFragil(6, c1, "Centro", 4, 3.5);
        registrarPedido(p6);

        Pedido p7 = new PedidoNormal(7, c2, "Zona Sur", 25, 5);
        registrarPedido(p7);

        Pedido p8 = new PedidoNormal(8, c3, "Barrio Oeste", 120, 8);
        registrarPedido(p8);

        asignarPedido(1, 101);
        asignarPedido(4, 101);

        asignarPedido(2, 102);
        asignarPedido(7, 102);

        asignarPedido(3, 103);
        asignarPedido(6, 103);
        asignarPedido(8, 103);

        cambiarEstado(1, EstadoPedido.EN_CAMINO);
        cambiarEstado(1, EstadoPedido.ENTREGADO);

        cambiarEstado(2, EstadoPedido.EN_CAMINO);
        cambiarEstado(2, EstadoPedido.ENTREGADO);

        cambiarEstado(3, EstadoPedido.EN_CAMINO);
        
    }
}
