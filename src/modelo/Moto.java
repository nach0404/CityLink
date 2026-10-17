
package modelo;

public class Moto extends Vehiculo {

    public Moto(String patente) {
        super(patente, 30, 40, 120);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return calcularTiempoConExtra(km, 5);
    }
}
