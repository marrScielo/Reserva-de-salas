package app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Configuración de conexión - Ajusta estos valores según tu instalación de MySQL
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "reserva_salas";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + 
                                      "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    
    private static Connection connection;
    
    private DatabaseConnection() {}
    
    public static Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("✓ Conexión a MySQL establecida");
                } catch (ClassNotFoundException e) {
                    throw new SQLException("Driver MySQL no encontrado. Verifica que mysql-connector-j esté en lib/", e);
                }
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("✗ Error de conexión a MySQL: " + e.getMessage());
            throw e;
        }
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("✓ Conexión a MySQL cerrada");
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}
