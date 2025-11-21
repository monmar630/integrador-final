package dao;

import config.DatabaseConnection;
import entities.Envio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnvioDao {

    // ============================
    // CREAR (INSERT)
    // ============================
    public void crear(Envio envio) throws SQLException {

        String sql = "INSERT INTO envio (tracking, empresa, tipo, costo, fechaDespacho, fechaEstimada, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, envio.getTracking());
            stmt.setString(2, envio.getEmpresa());
            stmt.setString(3, envio.getTipo());
            stmt.setDouble(4, envio.getCosto());
            stmt.setDate(5, Date.valueOf(envio.getFechaDespacho()));
            stmt.setDate(6, Date.valueOf(envio.getFechaEstimada()));
            stmt.setString(7, envio.getEstado());

            stmt.executeUpdate();

            //OBTENER EL ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                envio.setId(rs.getLong(1));  // asigna el ID al objeto
            }
        }
    }

    // ============================
    // LEER POR ID
    // ============================
    public Envio leer(Long id) throws SQLException {

        String sql = "SELECT * FROM envio WHERE id = ? AND eliminado = 0";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return mapearEnvio(rs);
        }

        return null;
    }

    // ============================
    // LEER TODOS
    // ============================
    public List<Envio> leerTodos() throws SQLException {
        List<Envio> lista = new ArrayList<>();

        String sql = "SELECT * FROM envio WHERE eliminado = 0";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) lista.add(mapearEnvio(rs));
        }

        return lista;
    }

    // ============================
    // ACTUALIZAR
    // ============================
    public void actualizar(Envio envio) throws SQLException {

        String sql = "UPDATE envio SET tracking=?, empresa=?, tipo=?, costo=?, fechaDespacho=?, fechaEstimada=?, estado=? " +
                     "WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, envio.getTracking());
            stmt.setString(2, envio.getEmpresa());
            stmt.setString(3, envio.getTipo());
            stmt.setDouble(4, envio.getCosto());
            stmt.setDate(5, Date.valueOf(envio.getFechaDespacho()));
            stmt.setDate(6, Date.valueOf(envio.getFechaEstimada()));
            stmt.setString(7, envio.getEstado());
            stmt.setLong(8, envio.getId());

            stmt.executeUpdate();
        }
    }

    // ============================
    // ELIMINADO LÓGICO
    // ============================
    public void eliminar(Long id) throws SQLException {

        String sql = "UPDATE envio SET eliminado = 1 WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    // ============================
    // BUSCAR POR TRACKING
    // ============================
    public Envio buscarPorTracking(String tracking) throws SQLException {

        String sql = "SELECT * FROM envio WHERE tracking=? AND eliminado = 0";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tracking);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return mapearEnvio(rs);
        }

        return null;
    }

    // ============================
    // MAPEO
    // ============================
    private Envio mapearEnvio(ResultSet rs) throws SQLException {

        return new Envio(
                rs.getLong("id"),
                rs.getBoolean("eliminado"),
                rs.getString("tracking"),
                rs.getString("empresa"),
                rs.getString("tipo"),
                rs.getDouble("costo"),
                rs.getDate("fechaDespacho").toLocalDate(),
                rs.getDate("fechaEstimada").toLocalDate(),
                rs.getString("estado")
        );
    }
}

