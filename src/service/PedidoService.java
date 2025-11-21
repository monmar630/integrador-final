package service;

import config.DatabaseConnection;
import dao.EnvioDao;
import dao.PedidoDao;
import entities.Envio;
import entities.Pedido;

import java.sql.SQLException;
import java.util.List;

public class PedidoService {

    private final PedidoDao pedidoDao = new PedidoDao();
    private final EnvioDao envioDao = new EnvioDao();

    // ===========================================
    // CREAR Pedido + Envio (VERSIÓN FUNCIONAL)
    // ===========================================
    public void crearPedidoConEnvio(Pedido pedido, Envio envio) throws SQLException {

        validarPedido(pedido);
        validarEnvio(envio);

        // 1) Primero guardar el envío
        envioDao.crear(envio);

        // 2) Relacionarlo al pedido (ahora tiene ID)
        pedido.setEnvio(envio);

        // 3) Guardar el pedido
        pedidoDao.crear(pedido);
    }

    // ===========================================
    // CRUD SIMPLES
    // ===========================================
    public Pedido obtenerPedido(Long id) throws SQLException {
        return pedidoDao.leer(id);
    }

    public List<Pedido> obtenerTodos() throws SQLException {
        return pedidoDao.leerTodos();
    }

    public void actualizarPedido(Pedido pedido) throws SQLException {
        validarPedido(pedido);
        pedidoDao.actualizar(pedido);
    }

    public void eliminarPedido(Long id) throws SQLException {
        pedidoDao.eliminar(id);
    }

    // ===========================================
    // VALIDACIONES BÁSICAS
    // ===========================================
    private void validarPedido(Pedido pedido) {
        if (pedido.getNumero() == null || pedido.getNumero().isBlank())
            throw new IllegalArgumentException("El número de pedido no puede estar vacío.");

        if (pedido.getTotal() <= 0)
            throw new IllegalArgumentException("El total debe ser mayor a 0.");

        if (pedido.getFecha() == null)
            throw new IllegalArgumentException("La fecha no puede ser nula.");
    }

    private void validarEnvio(Envio envio) {
        if (envio.getTracking() == null || envio.getTracking().isBlank())
            throw new IllegalArgumentException("El tracking no puede estar vacío.");

        if (envio.getCosto() <= 0)
            throw new IllegalArgumentException("El costo debe ser mayor que 0.");

        if (envio.getFechaDespacho() == null || envio.getFechaEstimada() == null)
            throw new IllegalArgumentException("Las fechas no pueden ser nulas.");

        if (envio.getFechaEstimada().isBefore(envio.getFechaDespacho()))
            throw new IllegalArgumentException("La fecha estimada no puede ser anterior a la fecha de despacho.");
    }
}

