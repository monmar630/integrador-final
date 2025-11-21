package dao;

import config.DatabaseConnection;
import entities.Pedido;
import entities.Envio;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao {

    private final EnvioDao envioDao = new EnvioDao();

    // ============================
    // CREAR (INSERT)
    // ============================
    public void crear(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedido (numero, fecha, clienteNombre, total, estado, envio) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pedido.getNumero());
            stmt.setDate(2, Date.valueOf(pedido.getFecha()));
            stmt.setString(3, pedido.getClienteNombre());
            stmt.setDouble(4, pedido.getTotal());
            stmt.setString(5, pedido.getEstado());
            stmt.setLong(6, pedido.getEnvio().getId()); // FK

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) pedido.setId(rs.getLong(1));
        }
    }

    // ============================
    // LEER POR ID (CORREGIDO CON JOIN)
    // ============================
    public Pedido leer(Long id) throws SQLException {
        String sql = "SELECT p.*, " +
                     "e.id AS envio_id, e.eliminado AS envio_eliminado, e.tracking, e.empresa, " +
                     "e.tipo, e.costo, e.fechaDespacho, e.fechaEstimada, e.estado AS envio_estado " +
                     "FROM pedido p " +
                     "LEFT JOIN envio e ON p.envio = e.id " +
                     "WHERE p.id = ? AND p.eliminado = 0";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearPedidoConEnvio(rs);
            }
        }
        return null;
    }

    // ============================
    // LEER TODOS (CORREGIDO CON JOIN)
    // ============================
    public List<Pedido> leerTodos() throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT p.*, " +
                     "e.id AS envio_id, e.eliminado AS envio_eliminado, e.tracking, e.empresa, " +
                     "e.tipo, e.costo, e.fechaDespacho, e.fechaEstimada, e.estado AS envio_estado " +
                     "FROM pedido p " +
                     "LEFT JOIN envio e ON p.envio = e.id " +
                     "WHERE p.eliminado = 0";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearPedidoConEnvio(rs));
            }
        }
        return lista;
    }

    // ============================
    // ACTUALIZAR
    // ============================
    public void actualizar(Pedido pedido) throws SQLException {
        String sql = "UPDATE pedido SET numero=?, fecha=?, clienteNombre=?, total=?, estado=?, envio=? " +
                     "WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pedido.getNumero());
            stmt.setDate(2, Date.valueOf(pedido.getFecha()));
            stmt.setString(3, pedido.getClienteNombre());
            stmt.setDouble(4, pedido.getTotal());
            stmt.setString(5, pedido.getEstado());
            stmt.setLong(6, pedido.getEnvio().getId());
            stmt.setLong(7, pedido.getId());

            stmt.executeUpdate();
        }
    }

    // ============================
    // ELIMINADO LÓGICO
    // ============================
    public void eliminar(Long id) throws SQLException {
        String sql = "UPDATE pedido SET eliminado = 1 WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    // ============================
    // MAPEO SIMPLE (sin JOIN)
    // ============================
    private Pedido mapearPedido(ResultSet rs) throws SQLException {
        Long envioId = rs.getLong("envio");
        Envio envio = envioDao.leer(envioId); // carga la relación 1→1

        return new Pedido(
                rs.getLong("id"),
                rs.getBoolean("eliminado"),
                rs.getString("numero"),
                rs.getDate("fecha").toLocalDate(),
                rs.getString("clienteNombre"),
                rs.getDouble("total"),
                rs.getString("estado"),
                envio
        );
    }

    // ============================
    // MAPEO CON JOIN (NUEVO - CORREGIDO)
    // ============================
    private Pedido mapearPedidoConEnvio(ResultSet rs) throws SQLException {
        // Mapear Envío desde el JOIN
        Envio envio = null;
        Long envioId = rs.getLong("envio_id");
        
        if (envioId != null && envioId > 0) {
            envio = new Envio(
                envioId,
                rs.getBoolean("envio_eliminado"),
                rs.getString("tracking"),
                rs.getString("empresa"),
                rs.getString("tipo"),
                rs.getDouble("costo"),
                rs.getDate("fechaDespacho").toLocalDate(),
                rs.getDate("fechaEstimada").toLocalDate(),
                rs.getString("envio_estado")
            );
        }

        // Mapear Pedido
        return new Pedido(
                rs.getLong("id"),
                rs.getBoolean("eliminado"),
                rs.getString("numero"),
                rs.getDate("fecha").toLocalDate(),
                rs.getString("clienteNombre"),
                rs.getDouble("total"),
                rs.getString("estado"),
                envio
        );
    }
}