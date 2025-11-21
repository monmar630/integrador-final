package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // VARIABLES CONFIGURABLES (las podés modificar cuando quieras)
    private static final String URL = "jdbc:mysql://localhost:3306/mdb_tpi?useSSL=false&serverTimezone=UTC";
    private static String USER = "root";      // Cambiable
    private static String PASSWORD = "MySQL_Root123!";      // Cambiable

    /**
     * Permite cambiar usuario y contraseña en tiempo de ejecución
     */
    public static void setCredentials(String user, String password) {
        USER = user;
        PASSWORD = password;
    }

    public static Connection getConnection() throws SQLException {
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new SQLException("Error: No se encontró el driver de MySQL.", e);
    }
        
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public static void main(String[] args) {
    try {
        Connection conn = DatabaseConnection.getConnection();
        System.out.println("Conexión exitosa!");
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
}
