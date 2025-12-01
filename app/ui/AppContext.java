// src/main/java/app/ui/AppContext.java
package app.ui;

import app.database.DatabaseInitializer;
import app.domain.Sala;
import app.domain.Usuario;
import app.notification.ConsoleNotifier;
import app.notification.Notifier;
import app.repo.InMemoryReservaRepository;
import app.repo.MySQLReservaRepository;
import app.repo.ReservaRepository;
import app.usecase.CrearReservaUseCase;
import app.validation.ReglaConflictoHorario;
import app.validation.ReglaHorarioLaboral;
import app.validation.ReglaValidacion;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class AppContext {
    public final Usuario usuario = new Usuario("U1","Ana","Empleado");
    public final Sala sala = new Sala("S1","Sala A", 6);

    // Inicializar repositorio según disponibilidad de MySQL
    public final ReservaRepository repo;
    public final List<ReglaValidacion> reglas = Arrays.asList(
            new ReglaConflictoHorario(),
            new ReglaHorarioLaboral()
    );
    public final Notifier notifier = new ConsoleNotifier();
    public final CrearReservaUseCase usecase;
    
    public AppContext() {
        ReservaRepository tempRepo;
        
        // Intentar conectar a MySQL
        if (DatabaseInitializer.testConnection()) {
            try {
                DatabaseInitializer.initialize();
                tempRepo = new MySQLReservaRepository();
                System.out.println("✓ Usando MySQL como base de datos");
            } catch (Exception e) {
                System.err.println("✗ Error al inicializar MySQL: " + e.getMessage());
                System.out.println("⚠ Usando memoria en lugar de MySQL");
                tempRepo = new InMemoryReservaRepository();
                mostrarAdvertenciaMySQL();
            }
        } else {
            System.out.println("⚠ MySQL no disponible, usando memoria");
            tempRepo = new InMemoryReservaRepository();
            mostrarAdvertenciaMySQL();
        }
        
        repo = tempRepo;
        usecase = new CrearReservaUseCase(repo, reglas, notifier);
    }
    
    private void mostrarAdvertenciaMySQL() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null,
                "No se pudo conectar a MySQL.\n" +
                "La aplicación funcionará en modo memoria (los datos no se guardarán al cerrar).\n\n" +
                "Para usar MySQL:\n" +
                "1. Asegúrate de que MySQL esté ejecutándose\n" +
                "2. Verifica las credenciales en DatabaseConnection.java\n" +
                "3. Ejecuta el script database/schema.sql",
                "Advertencia - MySQL no disponible",
                JOptionPane.WARNING_MESSAGE);
        });
    }
}
