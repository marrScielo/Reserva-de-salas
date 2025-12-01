package app;

import app.domain.*;
import app.repo.*;
import app.validation.*;
import app.notification.*;
import app.usecase.CrearReservaUseCase;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        ReservaRepository repo = new InMemoryReservaRepository();

        // Java 8 no tiene List.of(), usamos Arrays.asList()
        List<ReglaValidacion> reglas = Arrays.asList(
                new ReglaConflictoHorario(),
                new ReglaHorarioLaboral()
        );

        Notifier notifier = new ConsoleNotifier();
        CrearReservaUseCase usecase = new CrearReservaUseCase(repo, reglas, notifier);

        Usuario usuario = new Usuario("U1", "Ana", "Empleado");
        Sala sala = new Sala("S1", "Sala A", 6);

        Reserva rOk = new Reserva("R1", sala.getId(), usuario.getId(),
                LocalDateTime.of(2025, 10, 20, 9, 0),
                LocalDateTime.of(2025, 10, 20, 10, 0));
        usecase.ejecutar(rOk, usuario.getId());

        Reserva rBad = new Reserva("R2", sala.getId(), usuario.getId(),
                LocalDateTime.of(2025, 10, 20, 9, 30),
                LocalDateTime.of(2025, 10, 20, 10, 30));

        try {
            usecase.ejecutar(rBad, usuario.getId());
        } catch (IllegalArgumentException ex) {
            System.out.println("❌ " + ex.getMessage());
        }
    }
}
