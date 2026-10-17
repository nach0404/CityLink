
package modelo;

public class Camioneta extends Vehiculo {

    public Camioneta(String patente) {
        super(patente, 500, 30, 300);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return calcularTiempoConExtra(km, 10);
    }
}
