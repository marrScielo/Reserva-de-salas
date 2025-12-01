package app.repo;

import app.database.DatabaseConnection;
import app.domain.Reserva;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MySQLReservaRepository implements ReservaRepository {
    
    @Override
    public void save(Reserva r) {
        String sql = "INSERT INTO reservas (id, sala_id, usuario_id, inicio, fin, estado) VALUES (?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE estado = ?, inicio = ?, fin = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, r.getId());
            stmt.setString(2, r.getSalaId());
            stmt.setString(3, r.getUsuarioId());
            stmt.setTimestamp(4, Timestamp.valueOf(r.getInicio()));
            stmt.setTimestamp(5, Timestamp.valueOf(r.getFin()));
            stmt.setString(6, r.getEstado().name());
            stmt.setString(7, r.getEstado().name());
            stmt.setTimestamp(8, Timestamp.valueOf(r.getInicio()));
            stmt.setTimestamp(9, Timestamp.valueOf(r.getFin()));
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error SQL al guardar reserva: " + e.getMessage());
            throw new RuntimeException("Error al guardar reserva en la base de datos", e);
        }
    }
    
    @Override
    public List<Reserva> findBySala(String salaId) {
        String sql = "SELECT id, sala_id, usuario_id, inicio, fin, estado FROM reservas WHERE sala_id = ? ORDER BY inicio DESC";
        List<Reserva> reservas = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, salaId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String id = rs.getString("id");
                String sala = rs.getString("sala_id");
                String usuario = rs.getString("usuario_id");
                LocalDateTime inicio = rs.getTimestamp("inicio").toLocalDateTime();
                LocalDateTime fin = rs.getTimestamp("fin").toLocalDateTime();
                String estadoStr = rs.getString("estado");
                
                Reserva reserva = new Reserva(id, sala, usuario, inicio, fin);
                
                // Restaurar estado
                if ("APROBADA".equals(estadoStr)) {
                    reserva.aprobar();
                } else if ("RECHAZADA".equals(estadoStr)) {
                    reserva.rechazar();
                }
                
                reservas.add(reserva);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar reservas: " + e.getMessage(), e);
        }
        
        return reservas;
    }
    
    public List<Reserva> findAll() {
        String sql = "SELECT id, sala_id, usuario_id, inicio, fin, estado FROM reservas ORDER BY inicio DESC";
        List<Reserva> reservas = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                String id = rs.getString("id");
                String sala = rs.getString("sala_id");
                String usuario = rs.getString("usuario_id");
                LocalDateTime inicio = rs.getTimestamp("inicio").toLocalDateTime();
                LocalDateTime fin = rs.getTimestamp("fin").toLocalDateTime();
                String estadoStr = rs.getString("estado");
                
                Reserva reserva = new Reserva(id, sala, usuario, inicio, fin);
                
                if ("APROBADA".equals(estadoStr)) {
                    reserva.aprobar();
                } else if ("RECHAZADA".equals(estadoStr)) {
                    reserva.rechazar();
                }
                
                reservas.add(reserva);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todas las reservas: " + e.getMessage(), e);
        }
        
        return reservas;
    }
    
    public List<Reserva> findByUsuario(String usuarioId) {
        String sql = "SELECT id, sala_id, usuario_id, inicio, fin, estado FROM reservas WHERE usuario_id = ? ORDER BY inicio DESC";
        List<Reserva> reservas = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String id = rs.getString("id");
                String sala = rs.getString("sala_id");
                String usuario = rs.getString("usuario_id");
                LocalDateTime inicio = rs.getTimestamp("inicio").toLocalDateTime();
                LocalDateTime fin = rs.getTimestamp("fin").toLocalDateTime();
                String estadoStr = rs.getString("estado");
                
                Reserva reserva = new Reserva(id, sala, usuario, inicio, fin);
                
                if ("APROBADA".equals(estadoStr)) {
                    reserva.aprobar();
                } else if ("RECHAZADA".equals(estadoStr)) {
                    reserva.rechazar();
                }
                
                reservas.add(reserva);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar reservas del usuario: " + e.getMessage(), e);
        }
        
        return reservas;
    }
    
    public void deleteById(String id) {
        String sql = "DELETE FROM reservas WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar reserva: " + e.getMessage(), e);
        }
    }
    
    public void updateEstado(String id, String estado) {
        String sql = "UPDATE reservas SET estado = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, estado);
            stmt.setString(2, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estado de reserva: " + e.getMessage(), e);
        }
    }
}
