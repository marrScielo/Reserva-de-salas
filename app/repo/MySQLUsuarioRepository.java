package app.repo;

import app.database.DatabaseConnection;
import app.domain.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLUsuarioRepository {
    
    public Usuario findById(String id) {
        String sql = "SELECT id, nombre, rol FROM usuarios WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Usuario(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("rol")
                );
            }
            
            return null;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario: " + e.getMessage(), e);
        }
    }
    
    public List<Usuario> findAll() {
        String sql = "SELECT id, nombre, rol FROM usuarios ORDER BY nombre";
        List<Usuario> usuarios = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                usuarios.add(new Usuario(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("rol")
                ));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuarios: " + e.getMessage(), e);
        }
        
        return usuarios;
    }
    
    public void save(Usuario usuario) {
        String sql = "INSERT INTO usuarios (id, nombre, rol) VALUES (?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE nombre = ?, rol = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getId());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getRol());
            stmt.setString(4, usuario.getNombre());
            stmt.setString(5, usuario.getRol());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar usuario: " + e.getMessage(), e);
        }
    }
}
