package app.repo;

import app.database.DatabaseConnection;
import app.domain.Sala;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLSalaRepository {
    
    public Sala findById(String id) {
        String sql = "SELECT id, nombre, capacidad FROM salas WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Sala(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getInt("capacidad")
                );
            }
            
            return null;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar sala: " + e.getMessage(), e);
        }
    }
    
    public List<Sala> findAll() {
        String sql = "SELECT id, nombre, capacidad FROM salas ORDER BY nombre";
        List<Sala> salas = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                salas.add(new Sala(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getInt("capacidad")
                ));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener salas: " + e.getMessage(), e);
        }
        
        return salas;
    }
    
    public void save(Sala sala) {
        String sql = "INSERT INTO salas (id, nombre, capacidad) VALUES (?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE nombre = ?, capacidad = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, sala.getId());
            stmt.setString(2, sala.getNombre());
            stmt.setInt(3, sala.getCapacidad());
            stmt.setString(4, sala.getNombre());
            stmt.setInt(5, sala.getCapacidad());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar sala: " + e.getMessage(), e);
        }
    }
}
