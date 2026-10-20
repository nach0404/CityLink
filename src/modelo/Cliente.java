package modelo;

public class Cliente {

    private int codigo;
    private String nombre, telefono, zona;

    public Cliente() {}

    public Cliente(int codigo, String nombre, String telefono, String zona) {
        setCodigo(codigo);
        setNombre(nombre);
        setTelefono(telefono);
        setZona(zona);
    }

    // --- GETTERS DE LOS ATRIBUTOS ---

    /**
     * Devuelve el codigo del cliente.
     * @return codigo del cliente
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Devuelve el nombre del cliente.
     * @return nombre del cliente
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el numero de telefono del cliente.
     * @return telefono del cliente
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Devuelve la zona de la ciudad del cliente.
     * @return zona del cliente
     */
    public String getZona() {
        return zona;
    }

    // --- SETTERS DE LOS ATRIBUTOS CON VALIDACIONES ---

    /**
     * Establece el código del cliente.
     * @param codigo código del cliente
     * @throws IllegalArgumentException si el código no es mayor a 0
     */
    public void setCodigo(int codigo) {
        if(codigo <= 0) {
            throw new IllegalArgumentException("Error: el codigo de cliente no es valido");
        }

        this.codigo = codigo;
    }

    /**
     * Establece el nombre del cliente
     * @param nombre nombre del cliente
     * @throws IllegalArgumentException si el nombre es nulo o esta vacio
     */
    public void setNombre(String nombre) {
        if(nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Error: el nombre ingresado no es valido");
        }

        this.nombre = nombre;
    }

    /**
     * Establece el telefono del cliente
     * @param telefono telefono del cliente
     * @throws IllegalArgumentException si el telefono es nulo o esta vacio
     */
    public void setTelefono(String telefono) {
        if(telefono == null || telefono.isBlank()) {
            throw new IllegalArgumentException("Error: el telefono ingresado no es valido");
        }

        this.telefono = telefono;
    }

    /**
     * Establece la zona de la ciudad del cliente
     * @param zona zona del cliente
     * @throws IllegalArgumentException si la zona es nula o esta vacia
     */
    public void setZona(String zona) {
        if(zona == null || zona.isBlank()) {
            throw new IllegalArgumentException("Error: la zona ingresada no es valida");
        }

        this.zona = zona;
    }

    /** 
     * Devuelve la informacion del cliente
     * @return informacion del cliente
     */
    @Override
    public String toString() {
        return "[ CODIGO: " + codigo + " | NOMBRE: " + nombre + " | TELEFONO: " + telefono + " | ZONA: " + zona + " ]";
    }
    
}