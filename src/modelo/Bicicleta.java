
package modelo;

public class Bicicleta extends Vehiculo {

    public Bicicleta(String patente) {
        super(patente, 10, 15, 50);
    }

    @Override
    public double calcularTiempoEntrega(double km) {
        return calcularTiempoConExtra(km, 0);
    }

    @Override
    public boolean puedeTransportar(Pedido pedido) {
        if (pedido == null) {
            return false;
        }

        // Una bicicleta no puede llevar pedidos frágiles.
        if (pedido instanceof PedidoFragil) {
            return false;
        }

        return super.puedeTransportar(pedido);
    }
}
