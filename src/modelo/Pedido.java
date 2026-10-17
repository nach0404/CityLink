package modelo;

import java.time.LocalDateTime;
import excepciones.*;

/**
 * Clase abstracta Pedido que representa un pedido de envio
 * las subclases definen como se calcula el costo en calcularCosto().
 */
public abstract class Pedido implements Rastreable, Facturable {
    private int codigo;
    private Cliente cliente;
    private String destino;
    private double pesoKg, distanciaKm;
    private LocalDateTime fechaHora;
    private EstadoPedido estado;
    protected static final double COSTO_BASE = 1000.0;
    private Vehiculo vehiculo;

    /**
     * Crea un pedido vacio asignandole la fecha y estado automaticamente
     */
    public Pedido() {
        this.fechaHora = LocalDateTime.now();
        this.estado = EstadoPedido.PENDIENTE;
    }

    /**
     * Crea un pedido con los datos indicados. Fecha y estado se asignan automaticamente
     * @param codigo codigo de pedido
     * @param cliente cliente que realiza el pedido
     * @param destino lugar de destino del pedido
     * @param pesoKg peso del pedido en kilogramos
     * @param distanciaKm distancia en kilometros hasta el destino
     * @throws PedidoInvalidoException si algunos de los datos no es valido
     */
    public Pedido(int codigo, Cliente cliente, String destino, double pesoKg, double distanciaKm) throws PedidoInvalidoException {
        setCodigo(codigo);
        setCliente(cliente);
        setDestino(destino);
        setPesoKg(pesoKg);
        setDistanciaKm(distanciaKm);
        this.fechaHora = LocalDateTime.now();
        this.estado = EstadoPedido.PENDIENTE;
    }


    // --- GETTERS --- //


    /**
     * Devuelve el codigo del pedido
     * @return codigo del pedido
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Devuelve al cliente
     * @return cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Devuelve el lugar de destino del pedido
     * @return destino del pedido
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Devuelve el peso total en kilos del pedido
     * @return peso del pedido
     */
    public double getPesoKg() {
        return pesoKg;
    }

    /**
     * Devuelve la distancia total en kilometros hasta el destino
     * @return distancia del pedido
     */
    public double getDistanciaKm() {
        return distanciaKm;
    }

    /**
     * Devuelve la hora y fecha de emision del pedido
     * @return fecha y hora del pedido
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Devuelve el estado del pedido
     * @return estado del pedido
     */
    public EstadoPedido getEstado() {
        return estado;
    }

    /**
     * Devuelve el tipo de vehiculo asignado al pedido
     * @return tipo de vehiculo
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    

    // --- SETTERS --- //

    /**
     * Establece el codigo de un pedido
     * @param codigo codigo del pedido
     * @throws PedidoInvalidoException si el codigo ingresado no es mayor a 0
     */
    public void setCodigo(int codigo) throws PedidoInvalidoException {
        if(codigo <= 0) {
            throw new PedidoInvalidoException("Error: el codigo del pedido no es valido");
        }

        this.codigo = codigo;
    }

    /**
     * Establece al cliente propietario del pedido
     * @param cliente cliente propietario del pedido
     * @throws PedidoInvalidoException si el cliente ingresado es nulo
     */
    public void setCliente(Cliente cliente) throws PedidoInvalidoException {
        if(cliente == null) {
            throw new PedidoInvalidoException("Error: el cliente ingresado no existe o no se encontro");
        }

        this.cliente = cliente;
    }

    /**
     * Establece el lugar de destino del pedido
     * @param destino destino del pedido
     * @throws PedidoInvalidoException si el destino ingresado es nulo o esta vacio
     */
    public void setDestino(String destino)  throws PedidoInvalidoException {
        if (destino == null || destino.isBlank()) {
            throw new PedidoInvalidoException("Error: el destino ingresado no es valido");
        }

        this.destino = destino;
    }

    /**
     * Establece el peso total en kilogramos del pedido
     * @param pesoKg peso del pedido
     * @throws PedidoInvalidoException si el peso ingresado es menor o igual a 0 o es NaN
     */
    public void setPesoKg(double pesoKg) throws PedidoInvalidoException {
        if (pesoKg <= 0 || Double.isNaN(pesoKg)) {
            throw new PedidoInvalidoException("Error: el peso ingresado no es valido");
        }

        this.pesoKg = pesoKg;
    }

    /**
     * Establece la distancia total en kilometros hasta el destino del pedido
     * @param distanciaKm distancia que recorre el pedido
     * @throws PedidoInvalidoException si la distancia ingresada es menor o igual a 0 o es NaN
     */
    public void setDistanciaKm(double distanciaKm) throws PedidoInvalidoException {
        if (distanciaKm <= 0 || Double.isNaN(distanciaKm)) {
            throw new PedidoInvalidoException("Error: la distancia ingresada no es valida");
        }

        this.distanciaKm = distanciaKm;
    }

    /**
     * Establece el vehiculo al pedido
     * @param v vehiculo del pedido
     * @throws PedidoInvalidoException si el vehiculo asignado es nulo
     */
    public void setVehiculo(Vehiculo v) throws PedidoInvalidoException {
        if(v == null) {
            throw new PedidoInvalidoException("Error: no se asigno un vehiculo al pedido");
        }

        this.vehiculo = v;
    }


    // --- METODOS --- //

    /**
     * Metodo abstracto que calcula el costo segun el vehiculo
     * @param v tipo de vehiculo
     * @return costo
     */
    public abstract double calcularCosto(Vehiculo v);

    /**
     * Establece un nuevo estado
     * @param nuevo nuevo estado
     * @throws EstadoInvalidoException si el cambio de estado no es valido
     */
    public void cambiarEstado(EstadoPedido nuevo) throws EstadoInvalidoException {
        boolean estadoValido = (estado == EstadoPedido.PENDIENTE && nuevo == EstadoPedido.EN_CAMINO) 
                                || (estado == EstadoPedido.EN_CAMINO && nuevo == EstadoPedido.ENTREGADO)
                                || (estado == EstadoPedido.PENDIENTE && nuevo == EstadoPedido.CANCELADO);
        
        if(!estadoValido) {
            throw new EstadoInvalidoException("No se puede pasar de " + estado + " a " + nuevo);
        }

        this.estado = nuevo;
    }

    /**
     * Devuelve el estado actual como string
     * @return estado actual del pedido
     */
    @Override 
    public String obtenerEstadoActual() {
        return estado.name();
    }

    /**
     * Calcula el importe utilizando el metodo abstracto calcularCosto()
     * @return importe total
     */
    @Override 
    public double calcularImporte() {
        if(vehiculo == null) {
            throw new IllegalStateException("El pedido no tiene vehiculo asignado");
        }

        return calcularCosto(vehiculo);
    }
}
