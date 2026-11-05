

package app;

import java.util.Scanner;
import modelo.*;
import excepciones.*;


/**
 * Clase principal de CityLink.
 * Inicia la aplicación, carga los datos de prueba
 * y gestiona el menú principal del sistema.
 */
public class Main {

    private static CityLink sistema = new CityLink();

    /**
     * Inicia la aplicación y ejecuta el menú principal.
     *
     * @param args argumentos recibidos desde la línea de comandos
     */
    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        try {

            // CARGA INICIAL
            sistema.cargarDatosPrueba();
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
                        registrarRepartidor(teclado);
                        break;

                    case 3:
                        registrarPedido(teclado);
                        break;

                    case 4:
                        asignarPedido(teclado);
                        break;

                    case 5:
                        cambiarEstadoPedido(teclado);
                        break;

                    case 6:
                        buscarCliente(teclado);
                        break;

                    case 7:
                        System.out.println(sistema.listarPedidos());
                        break;

                    case 8:
                        consultarEstado(teclado);
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

    // REGISTRAR REPARTIDOR
    private static void registrarRepartidor(Scanner teclado) {

        try {

            System.out.println("\n--- REGISTRAR REPARTIDOR ---");

            System.out.print("Ingrese el legajo: ");
            int legajo = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese el nombre: ");
            String nombre = teclado.nextLine();

            System.out.println("\nSeleccione el vehiculo:");
            System.out.println("1. Bicicleta");
            System.out.println("2. Moto");
            System.out.println("3. Camioneta");

            System.out.print("Opcion: ");
            int tipo = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese la patente: ");
            String patente = teclado.nextLine();

            Vehiculo vehiculo;

            switch (tipo) {

                case 1:
                    vehiculo = new Bicicleta(patente);
                    break;

                case 2:
                    vehiculo = new Moto(patente);
                    break;

                case 3:
                    vehiculo = new Camioneta(patente);
                    break;

                default:
                    System.out.println("Tipo de vehiculo incorrecto.");
                    return;
            }

            Repartidor repartidor = new Repartidor(legajo, nombre, vehiculo);

            sistema.registrarRepartidor(repartidor);

            System.out.println("Repartidor registrado correctamente.");

        } catch (NumberFormatException e) {

            System.out.println("Error: debe ingresar un numero valido.");

        } catch (AlmacenamientoLlenoException | IllegalArgumentException e) {

            System.out.println("No se pudo registrar el repartidor: " + e.getMessage());
        }
    }

    // REGISTRAR PEDIDO
    private static void registrarPedido(Scanner teclado) {

        try {

            System.out.println("\n--- REGISTRAR PEDIDO ---");

            System.out.println("Seleccione el tipo de pedido:");
            System.out.println("1. Normal");
            System.out.println("2. Urgente");
            System.out.println("3. Fragil");

            System.out.print("Opcion: ");
            int tipo = Integer.parseInt(teclado.nextLine());

            if (tipo < 1 || tipo > 3) {
                System.out.println("Tipo de pedido incorrecto.");
                return;
            }

            System.out.print("Ingrese el codigo del pedido: ");
            int codigo = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese el codigo del cliente: ");
            int codigoCliente = Integer.parseInt(teclado.nextLine());

            // Buscamos al cliente registrado
            Cliente cliente = sistema.buscarCliente(codigoCliente);

            System.out.print("Ingrese el destino: ");
            String destino = teclado.nextLine();

            System.out.print("Ingrese el peso en kg: ");
            double peso = Double.parseDouble(teclado.nextLine());

            System.out.print("Ingrese la distancia en km: ");
            double distancia = Double.parseDouble(teclado.nextLine());

            Pedido pedido;

            // Creamos el pedido según el tipo elegido
            switch (tipo) {

                case 1:
                    pedido = new PedidoNormal(
                            codigo, cliente, destino, peso, distancia);
                    break;

                case 2:
                    pedido = new PedidoUrgente(
                            codigo, cliente, destino, peso, distancia);
                    break;

                default:
                    pedido = new PedidoFragil(
                            codigo, cliente, destino, peso, distancia);
                    break;
            }

            sistema.registrarPedido(pedido);

            System.out.println("Pedido registrado correctamente.");

        } catch (NumberFormatException e) {

            System.out.println("Error: debe ingresar valores numericos validos.");

        } catch (ClienteNoEncontradoException e) {

            System.out.println("No se encontro el cliente: " + e.getMessage());

        } catch (PedidoInvalidoException | AlmacenamientoLlenoException e) {

            System.out.println("No se pudo registrar el pedido: " + e.getMessage());

        } catch (IllegalArgumentException e) {

            System.out.println("Datos incorrectos: " + e.getMessage());
        }
    }

    // ASIGNAR PEDIDO A REPARTIDOR
    private static void asignarPedido(Scanner teclado) {

        try {

            System.out.println("\n--- ASIGNAR PEDIDO ---");

            System.out.print("Ingrese el codigo del pedido: ");
            int codigoPedido = Integer.parseInt(teclado.nextLine());

            System.out.print("Ingrese el legajo del repartidor: ");
            int legajo = Integer.parseInt(teclado.nextLine());

            sistema.asignarPedido(codigoPedido, legajo);

            System.out.println("Pedido asignado correctamente.");

        } catch (NumberFormatException e) {

            System.out.println("Error: debe ingresar valores numericos.");

        } catch (PedidoInvalidoException e) {

            System.out.println("No se pudo encontrar el pedido: " + e.getMessage());

        } catch (CapacidadExcedidaException e) {

            System.out.println("El repartidor no puede transportar este pedido: "
                    + e.getMessage());

        } catch (AlmacenamientoLlenoException e) {

            System.out.println("No se pudo asignar el pedido: " + e.getMessage());

        } catch (IllegalArgumentException e) {

            System.out.println("No se encontro el repartidor: " + e.getMessage());
        }
    }

    // CAMBIAR ESTADO DE PEDIDO
    private static void cambiarEstadoPedido(Scanner teclado) {

        try {

            System.out.println("\n--- CAMBIAR ESTADO DE PEDIDO ---");

            System.out.print("Ingrese el codigo del pedido: ");
            int codigoPedido = Integer.parseInt(teclado.nextLine());

            System.out.println("\nSeleccione el nuevo estado:");
            System.out.println("1. EN_CAMINO");
            System.out.println("2. ENTREGADO");
            System.out.println("3. CANCELADO");

            System.out.print("Opcion: ");
            int opcion = Integer.parseInt(teclado.nextLine());

            EstadoPedido nuevoEstado;

            switch (opcion) {

                case 1:
                    nuevoEstado = EstadoPedido.EN_CAMINO;
                    break;

                case 2:
                    nuevoEstado = EstadoPedido.ENTREGADO;
                    break;

                case 3:
                    nuevoEstado = EstadoPedido.CANCELADO;
                    break;

                default:
                    System.out.println("Opcion de estado incorrecta.");
                    return;
            }

            sistema.cambiarEstado(codigoPedido, nuevoEstado);

            System.out.println("Estado del pedido actualizado correctamente.");

        } catch (NumberFormatException e) {

            System.out.println("Error: debe ingresar un numero valido.");

        } catch (PedidoInvalidoException e) {

            System.out.println("No se encontro el pedido: " + e.getMessage());

        } catch (EstadoInvalidoException e) {

            System.out.println("No se puede realizar el cambio de estado: "
                    + e.getMessage());
        }
    }

    // BUSCAR CLIENTE
    private static void buscarCliente(Scanner teclado) {

        try {

            System.out.println("\n--- BUSCAR CLIENTE ---");
            System.out.println("1. Buscar por codigo");
            System.out.println("2. Buscar por nombre");

            System.out.print("Seleccione una opcion: ");
            int opcion = Integer.parseInt(teclado.nextLine());

            Cliente cliente;

            switch (opcion) {

                case 1:
                    System.out.print("Ingrese el codigo del cliente: ");
                    int codigo = Integer.parseInt(teclado.nextLine());

                    cliente = sistema.buscarCliente(codigo);
                    break;

                case 2:
                    System.out.print("Ingrese el nombre del cliente: ");
                    String nombre = teclado.nextLine();

                    cliente = sistema.buscarCliente(nombre);
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
                    return;
            }

            System.out.println("\nCliente encontrado:");
            System.out.println("Codigo: " + cliente.getCodigo());
            System.out.println("Nombre: " + cliente.getNombre());
            System.out.println("Telefono: " + cliente.getTelefono());
            System.out.println("Zona: " + cliente.getZona());

        } catch (NumberFormatException e) {

            System.out.println("Error: el codigo debe ser numerico.");

        } catch (ClienteNoEncontradoException e) {

            System.out.println("Cliente no encontrado: " + e.getMessage());
        }
    }

    // CONSULTAR ESTADO
    private static void consultarEstado(Scanner teclado) {

        try {

            System.out.println("\n--- CONSULTAR ESTADO ---");
            System.out.println("1. Consultar estado de un pedido");
            System.out.println("2. Consultar estado de un repartidor");

            System.out.print("Seleccione una opcion: ");
            int opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {

                case 1:
                    System.out.print("Ingrese el codigo del pedido: ");
                    int codigoPedido = Integer.parseInt(teclado.nextLine());

                    System.out.println(sistema.consultarEstadoPedido(codigoPedido));
                    break;

                case 2:
                    System.out.print("Ingrese el legajo del repartidor: ");
                    int legajo = Integer.parseInt(teclado.nextLine());

                    System.out.println(sistema.consultarEstadoRepartidor(legajo));
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
                    break;
            }

        } catch (NumberFormatException e) {

            System.out.println("Error: debe ingresar un numero valido.");

        } catch (PedidoInvalidoException e) {

            System.out.println("No se encontro el pedido: " + e.getMessage());

        } catch (IllegalArgumentException e) {

            System.out.println("No se encontro el repartidor: " + e.getMessage());
        }
    }
}
