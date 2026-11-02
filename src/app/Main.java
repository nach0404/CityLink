


package app;

import java.util.Scanner;
import modelo.*;
import excepciones.*;

public class Main {

    private static CityLink sistema = new CityLink();

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        try {

            // CARGA INICIAL
            precargarDatos();
            System.out.println("Datos de prueba cargados correctamente.");

            boolean salir = false;

            // MENU PRINCIPAL
            while (!salir) {

                mostrarMenu();

                System.out.print("Seleccione una opcion: ");
                String entrada = teclado.nextLine();

                int opcion;

                try {
                    opcion = Integer.parseInt(entrada);

                } catch (NumberFormatException e) {
                    System.out.println("Debe ingresar un numero.");
                    continue;
                }

                switch (opcion) {

                    case 1:
                        registrarCliente(teclado);
                        break;

                    case 2:
                        System.out.println("Registrar repartidor");
                        break;

                    case 3:
                        System.out.println("Registrar pedido");
                        break;

                    case 4:
                        System.out.println("Asignar pedido");
                        break;

                    case 5:
                        System.out.println("Cambiar estado de pedido");
                        break;

                    case 6:
                        System.out.println("Buscar cliente");
                        break;

                    case 7:
                        System.out.println(sistema.listarPedidos());
                        break;

                    case 8:
                        System.out.println("Consultar estado");
                        break;

                    case 9:
                        System.out.println("Importe total: $"
                                + sistema.calcularImporteTotalPedidos());

                        System.out.println("Comisiones:");
                        System.out.println(sistema.listarComisiones());
                        break;

                    case 10:
                        salir = true;
                        System.out.println("Gracias por utilizar CityLink.");
                        break;

                    default:
                        System.out.println("Opcion incorrecta. Intente nuevamente.");
                }
            }

        } catch (Exception e) {

            System.out.println("Error en el sistema: " + e.getMessage());

        } finally {

            teclado.close();
            System.out.println("Sistema finalizado.");
        }
    }

    // PRECARGA DE DATOS
    private static void precargarDatos() throws Exception {

        // CLIENTES
        Cliente c1 = new Cliente(1, "Ignacio Lucero", "2664000001", "Centro");
        Cliente c2 = new Cliente(2, "Maria Rojo", "2664000002", "Norte");
        Cliente c3 = new Cliente(3, "Gabriela Toledo", "2664000003", "Sur");
        Cliente c4 = new Cliente(4, "Analia Baigorria", "2664000004", "Este");
        Cliente c5 = new Cliente(5, "Antonella Oyola", "2664000005", "Oeste");

        sistema.registrarCliente(c1);
        sistema.registrarCliente(c2);
        sistema.registrarCliente(c3);
        sistema.registrarCliente(c4);
        sistema.registrarCliente(c5);

        // VEHICULOS
        Vehiculo bicicleta = new Bicicleta("ABC123");
        Vehiculo moto = new Moto("DEF456");
        Vehiculo camioneta = new Camioneta("GHI789");

        // REPARTIDORES
        Repartidor r1 = new Repartidor(101, "Franco", bicicleta);
        Repartidor r2 = new Repartidor(102, "Gian", moto);
        Repartidor r3 = new Repartidor(103, "Sofia", camioneta);

        sistema.registrarRepartidor(r1);
        sistema.registrarRepartidor(r2);
        sistema.registrarRepartidor(r3);

        // PEDIDOS
        Pedido p1 = new PedidoNormal(1001, c1, "Av. Central 100", 3, 10);
        Pedido p2 = new PedidoNormal(1002, c2, "Calle Norte 200", 5, 15);
        Pedido p3 = new PedidoNormal(1003, c3, "Calle Sur 300", 7, 8);

        Pedido p4 = new PedidoUrgente(1004, c4, "Av. Este 400", 10, 12);
        Pedido p5 = new PedidoUrgente(1005, c5, "Calle Oeste 500", 15, 20);
        Pedido p6 = new PedidoUrgente(1006, c1, "Av. Central 600", 8, 18);

        Pedido p7 = new PedidoFragil(1007, c2, "Calle Norte 700", 12, 10);
        Pedido p8 = new PedidoFragil(1008, c3, "Calle Sur 800", 20, 25);

        sistema.registrarPedido(p1);
        sistema.registrarPedido(p2);
        sistema.registrarPedido(p3);
        sistema.registrarPedido(p4);
        sistema.registrarPedido(p5);
        sistema.registrarPedido(p6);
        sistema.registrarPedido(p7);
        sistema.registrarPedido(p8);

        // ASIGNACIONES
        sistema.asignarPedido(1001, 101);
        sistema.asignarPedido(1002, 101);

        sistema.asignarPedido(1003, 102);
        sistema.asignarPedido(1004, 102);
        sistema.asignarPedido(1007, 102);

        sistema.asignarPedido(1005, 103);
        sistema.asignarPedido(1006, 103);
        sistema.asignarPedido(1008, 103);
    }

    // MOSTRAR MENU
    private static void mostrarMenu() {

        System.out.println("\n========== CITYLINK ==========");
        System.out.println("1. Registrar cliente");
        System.out.println("2. Registrar repartidor");
        System.out.println("3. Registrar pedido");
        System.out.println("4. Asignar pedido a repartidor");
        System.out.println("5. Cambiar estado de pedido");
        System.out.println("6. Buscar cliente");
        System.out.println("7. Listar pedidos");
        System.out.println("8. Consultar estado de pedido o repartidor");
        System.out.println("9. Consultar importe total y comisiones");
        System.out.println("10. Salir");
        System.out.println("==============================");
    }

    // REGISTRAR CLIENTE
    private static void registrarCliente(Scanner teclado) {

        try {

            System.out.println("\n--- REGISTRAR CLIENTE ---");

            System.out.print("Ingrese el codigo: ");
            int codigo = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese el nombre: ");
            String nombre = teclado.nextLine();

            System.out.print("Ingrese el telefono: ");
            String telefono = teclado.nextLine();

            System.out.print("Ingrese la zona: ");
            String zona = teclado.nextLine();

            Cliente cliente = new Cliente(codigo, nombre, telefono, zona);

            sistema.registrarCliente(cliente);

            System.out.println("Cliente registrado correctamente.");

        } catch (NumberFormatException e) {

            System.out.println("Error: el codigo debe ser un numero.");

        } catch (AlmacenamientoLlenoException | IllegalArgumentException e) {

            System.out.println("No se pudo registrar el cliente: " + e.getMessage());
        }
    }
}
