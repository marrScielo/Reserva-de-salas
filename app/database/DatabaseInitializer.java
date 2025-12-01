package app.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    
    public static void initialize() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Verificar si la base de datos existe y tiene las tablas
            stmt.execute("CREATE DATABASE IF NOT EXISTS reserva_salas");
            stmt.execute("USE reserva_salas");
            
            // Crear tabla usuarios
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id VARCHAR(50) PRIMARY KEY," +
                "nombre VARCHAR(100) NOT NULL," +
                "rol VARCHAR(50) NOT NULL" +
                ")"
            );
            
            // Crear tabla salas
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS salas (" +
                "id VARCHAR(50) PRIMARY KEY," +
                "nombre VARCHAR(100) NOT NULL," +
                "capacidad INT NOT NULL" +
                ")"
            );
            
            // Crear tabla reservas
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS reservas (" +
                "id VARCHAR(50) PRIMARY KEY," +
                "sala_id VARCHAR(50) NOT NULL," +
                "usuario_id VARCHAR(50) NOT NULL," +
                "inicio DATETIME NOT NULL," +
                "fin DATETIME NOT NULL," +
                "estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE'," +
                "INDEX idx_sala_fecha (sala_id, inicio, fin)" +
                ")"
            );
            
            // Insertar datos de ejemplo si no existen
            stmt.execute(
                "INSERT IGNORE INTO usuarios (id, nombre, rol) VALUES " +
                "('U1', 'Ana', 'Empleado')," +
                "('U2', 'Carlos', 'Gerente')," +
                "('U3', 'María', 'Empleado')"
            );
            
            stmt.execute(
                "INSERT IGNORE INTO salas (id, nombre, capacidad) VALUES " +
                "('S1', 'Sala A', 6)," +
                "('S2', 'Sala B', 10)," +
                "('S3', 'Sala C', 4)"
            );
            
            System.out.println("✓ Base de datos inicializada correctamente");
            
        } catch (SQLException e) {
            System.err.println("✗ Error al inicializar la base de datos: " + e.getMessage());
            System.err.println("Asegúrate de que MySQL esté ejecutándose y las credenciales sean correctas");
        }
    }
    
    public static boolean testConnection() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
