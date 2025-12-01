package app;

import app.database.DatabaseConnection;
import app.database.DatabaseInitializer;
import app.domain.Reserva;
import app.domain.Sala;
import app.domain.Usuario;
import app.repo.MySQLReservaRepository;
import app.repo.MySQLSalaRepository;
import app.repo.MySQLUsuarioRepository;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public class TestMySQL {
    public static void main(String[] args) {
        System.out.println("=== Test de Conexión MySQL ===\n");
        
        // 1. Probar conexión
        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("✓ Conexión exitosa a MySQL");
            System.out.println("  Base de datos: reserva_salas\n");
        } catch (Exception e) {
            System.err.println("✗ Error de conexión: " + e.getMessage());
            System.err.println("\nVerifica:");
            System.err.println("1. MySQL está ejecutándose");
            System.err.println("2. Usuario y contraseña en DatabaseConnection.java");
            System.err.println("3. Base de datos 'reserva_salas' existe\n");
            return;
        }
        
        // 2. Inicializar base de datos
        System.out.println("Inicializando base de datos...");
        DatabaseInitializer.initialize();
        System.out.println();
        
        // 3. Probar repositorio de salas
        System.out.println("=== Test Repositorio de Salas ===");
        MySQLSalaRepository salaRepo = new MySQLSalaRepository();
        List<Sala> salas = salaRepo.findAll();
        System.out.println("Salas encontradas: " + salas.size());
        for (Sala s : salas) {
            System.out.println("  - " + s.getNombre() + " (Capacidad: " + s.getCapacidad() + ")");
        }
        System.out.println();
        
        // 4. Probar repositorio de usuarios
        System.out.println("=== Test Repositorio de Usuarios ===");
        MySQLUsuarioRepository usuarioRepo = new MySQLUsuarioRepository();
        List<Usuario> usuarios = usuarioRepo.findAll();
        System.out.println("Usuarios encontrados: " + usuarios.size());
        for (Usuario u : usuarios) {
            System.out.println("  - " + u.getNombre() + " (" + u.getRol() + ")");
        }
        System.out.println();
        
        // 5. Probar repositorio de reservas
        System.out.println("=== Test Repositorio de Reservas ===");
        MySQLReservaRepository reservaRepo = new MySQLReservaRepository();
        
        // Crear una reserva de prueba
        Reserva nuevaReserva = new Reserva(
            "TEST_" + System.currentTimeMillis(),
            "S1",
            "U1",
            LocalDateTime.now().withHour(10).withMinute(0),
            LocalDateTime.now().withHour(11).withMinute(0)
        );
        
        try {
            reservaRepo.save(nuevaReserva);
            System.out.println("✓ Reserva de prueba creada: " + nuevaReserva.getId());
        } catch (Exception e) {
            System.err.println("✗ Error al crear reserva: " + e.getMessage());
        }
        
        // Listar reservas
        List<Reserva> reservas = reservaRepo.findAll();
        System.out.println("Total de reservas: " + reservas.size());
        for (Reserva r : reservas) {
            System.out.println("  - ID: " + r.getId() + 
                             " | Sala: " + r.getSalaId() + 
                             " | Estado: " + r.getEstado());
        }
        System.out.println();
        
        System.out.println("=== Test completado exitosamente ===");
        
        DatabaseConnection.closeConnection();
    }
}
