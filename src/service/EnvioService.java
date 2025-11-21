package service;

import dao.EnvioDao;
import entities.Envio;

import java.sql.SQLException;
import java.util.List;

public class EnvioService {

    private final EnvioDao envioDao = new EnvioDao();

    // Crear Envio
    public void crearEnvio(Envio envio) throws SQLException {
        validarEnvio(envio);
        envioDao.crear(envio);
    }

    // Leer por ID
    public Envio obtenerEnvio(Long id) throws SQLException {
        return envioDao.leer(id);
    }

    // Leer todos
    public List<Envio> obtenerTodos() throws SQLException {
        return envioDao.leerTodos();
    }

    // Actualizar
    public void actualizarEnvio(Envio envio) throws SQLException {
        validarEnvio(envio);
        envioDao.actualizar(envio);
    }

    // Eliminar lógico
    public void eliminarEnvio(Long id) throws SQLException {
        envioDao.eliminar(id);
    }

    // Buscar por tracking
    public Envio buscarPorTracking(String tracking) throws SQLException {
        return envioDao.buscarPorTracking(tracking);
    }

    // VALIDACIONES BÁSICAS
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
