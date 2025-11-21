package main;

import entities.Envio;
import entities.Pedido;
import service.EnvioService;
import service.PedidoService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AppMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final EnvioService envioService = new EnvioService();
    private final PedidoService pedidoService = new PedidoService();

    public void iniciar() {

        int opcion = -1;

        while (opcion != 0) {

            System.out.println("\n====== MENU PRINCIPAL ======");
            System.out.println("1. Crear Envío");
            System.out.println("2. Crear Pedido + Envío");
            System.out.println("3. Listar Envíos");
            System.out.println("4. Listar Pedidos");
            System.out.println("5. Buscar Envío por Tracking");
            System.out.println("6. Buscar Pedido por ID");
            System.out.println("7. Actualizar Envío");
            System.out.println("8. Actualizar Pedido");
            System.out.println("9. Eliminar Envío");
            System.out.println("10. Eliminar Pedido");
            System.out.println("0. Salir");
            System.out.print("Elegir opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> crearEnvioSeguro();
                case 2 -> crearPedidoConEnvioSeguro();
                case 3 -> listarEnviosSeguro();
                case 4 -> listarPedidosSeguro();
                case 5 -> buscarEnvioPorTrackingSeguro();
                case 6 -> buscarPedidoPorIdSeguro();
                case 7 -> actualizarEnvioSeguro();
                case 8 -> actualizarPedidoSeguro();
                case 9 -> eliminarEnvioSeguro();
                case 10 -> eliminarPedidoSeguro();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // ============================================================
    // MÉTODOS "SEGUROS" QUE NO ROMPEN EL MENÚ
    // ============================================================

    private void crearEnvioSeguro() {
        try { crearEnvio(); }
        catch (Exception e) { System.out.println("ERROR: " + e.getMessage()); }
    }

    private void crearPedidoConEnvioSeguro() {
        try { crearPedidoConEnvio(); }
        catch (Exception e) { System.out.println("ERROR: " + e.getMessage()); }
    }

    private void listarEnviosSeguro() {
        try { listarEnvios(); }
        catch (Exception e) { System.out.println("ERROR: " + e.getMessage()); }
    }

    private void listarPedidosSeguro() {
        try { listarPedidos(); }
        catch (Exception e) { System.out.println("ERROR: " + e.getMessage()); }
    }

    private void buscarEnvioPorTrackingSeguro() {
        try { buscarEnvioPorTracking(); }
        catch (Exception e) { System.out.println("ERROR: " + e.getMessage()); }
    }

    private void buscarPedidoPorIdSeguro() {
        try { buscarPedidoPorId(); }
        catch (Exception e) { System.out.println("ERROR: " + e.getMessage()); }
    }

    private void actualizarEnvioSeguro() {
        try { actualizarEnvio(); }
        catch (Exception e) { System.out.println("ERROR: " + e.getMessage()); }
    }

    private void actualizarPedidoSeguro() {
        try { actualizarPedido(); }
        catch (Exception e) { System.out.println("ERROR: " + e.getMessage()); }
    }

    private void eliminarEnvioSeguro() {
        try { eliminarEnvio(); }
        catch (Exception e) { System.out.println("ERROR: " + e.getMessage()); }
    }

    private void eliminarPedidoSeguro() {
        try { eliminarPedido(); }
        catch (Exception e) { System.out.println("ERROR: " + e.getMessage()); }
    }


    // ============================================================
    // OPCIONES ORIGINALES
    // ============================================================

    private void crearEnvio() throws SQLException {
        System.out.println("\n--- Crear Envío ---");

        Envio envio = new Envio();

        System.out.print("Tracking: ");
        envio.setTracking(scanner.nextLine());

        System.out.print("Empresa: ");
        envio.setEmpresa(scanner.nextLine().toUpperCase());

        System.out.print("Tipo: ");
        envio.setTipo(scanner.nextLine().toUpperCase());

        System.out.print("Costo: ");
        envio.setCosto(Double.parseDouble(scanner.nextLine()));

        System.out.print("Fecha despacho (YYYY-MM-DD): ");
        envio.setFechaDespacho(LocalDate.parse(scanner.nextLine()));

        System.out.print("Fecha estimada (YYYY-MM-DD): ");
        envio.setFechaEstimada(LocalDate.parse(scanner.nextLine()));

        System.out.print("Estado: ");
        envio.setEstado(scanner.nextLine().toUpperCase());

        envioService.crearEnvio(envio);

        System.out.println("Envío creado con ID: " + envio.getId());
    }

    private void crearPedidoConEnvio() throws SQLException {
        System.out.println("\n--- Crear Pedido + Envío ---");

        Envio envio = new Envio();

        System.out.print("Tracking: ");
        envio.setTracking(scanner.nextLine());

        System.out.print("Empresa: ");
        envio.setEmpresa(scanner.nextLine().toUpperCase());

        System.out.print("Tipo: ");
        envio.setTipo(scanner.nextLine().toUpperCase());

        System.out.print("Costo: ");
        envio.setCosto(Double.parseDouble(scanner.nextLine()));

        System.out.print("Fecha despacho (YYYY-MM-DD): ");
        envio.setFechaDespacho(LocalDate.parse(scanner.nextLine()));

        System.out.print("Fecha estimada (YYYY-MM-DD): ");
        envio.setFechaEstimada(LocalDate.parse(scanner.nextLine()));

        System.out.print("Estado del envío: ");
        envio.setEstado(scanner.nextLine().toUpperCase());

        Pedido pedido = new Pedido();

        System.out.print("Número de pedido: ");
        pedido.setNumero(scanner.nextLine());

        System.out.print("Fecha (YYYY-MM-DD): ");
        pedido.setFecha(LocalDate.parse(scanner.nextLine()));

        System.out.print("Cliente: ");
        pedido.setClienteNombre(scanner.nextLine());

        System.out.print("Total: ");
        pedido.setTotal(Double.parseDouble(scanner.nextLine()));

        System.out.print("Estado del pedido: ");
        pedido.setEstado(scanner.nextLine().toUpperCase());

        pedidoService.crearPedidoConEnvio(pedido, envio);

        System.out.println("Pedido creado con ID: " + pedido.getId());
        System.out.println("Envío asociado con ID: " + envio.getId());
    }

    private void listarEnvios() throws SQLException {
        System.out.println("\n--- Envíos ---");
        List<Envio> lista = envioService.obtenerTodos();
        lista.forEach(System.out::println);
    }

    private void listarPedidos() throws SQLException {
        System.out.println("\n--- Pedidos ---");
        List<Pedido> lista = pedidoService.obtenerTodos();
        lista.forEach(System.out::println);
    }

    private void buscarEnvioPorTracking() throws SQLException {
        System.out.print("Tracking: ");
        System.out.println(envioService.buscarPorTracking(scanner.nextLine()));
    }

    private void buscarPedidoPorId() throws SQLException {
        System.out.print("ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.println(pedidoService.obtenerPedido(id));
    }

    private void actualizarEnvio() throws SQLException {
        System.out.print("ID del envío: ");
        Long id = Long.parseLong(scanner.nextLine());

        Envio envio = envioService.obtenerEnvio(id);
        if (envio == null) {
            System.out.println("No existe ese envío.");
            return;
        }

        System.out.print("Nuevo estado: ");
        envio.setEstado(scanner.nextLine().toUpperCase());

        envioService.actualizarEnvio(envio);
        System.out.println("Actualizado!");
    }

    private void actualizarPedido() throws SQLException {
        System.out.print("ID del pedido: ");
        Long id = Long.parseLong(scanner.nextLine());

        Pedido pedido = pedidoService.obtenerPedido(id);
        if (pedido == null) {
            System.out.println("No existe ese pedido.");
            return;
        }

        System.out.print("Nuevo estado: ");
        pedido.setEstado(scanner.nextLine().toUpperCase());

        pedidoService.actualizarPedido(pedido);
        System.out.println("Actualizado!");
    }

    private void eliminarEnvio() throws SQLException {
        System.out.print("ID: ");
        envioService.eliminarEnvio(Long.parseLong(scanner.nextLine()));
        System.out.println("Eliminado!");
    }

    private void eliminarPedido() throws SQLException {
        System.out.print("ID: ");
        pedidoService.eliminarPedido(Long.parseLong(scanner.nextLine()));
        System.out.println("Eliminado!");
    }
}

