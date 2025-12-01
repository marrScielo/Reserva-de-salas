package ui;

import domain.Sala;
import domain.Usuario;
import notification.ConsoleNotifier;
import notification.Notifier;
import repo.InMemoryReservaRepository;
import repo.ReservaRepository;
import usecase.CrearReservaUseCase;
import validation.ReglaConflictoHorario;
import validation.ReglaHorarioLaboral;
import validation.ReglaValidacion;

import java.util.Arrays;
import java.util.List;

public class AppContext {
    public final Usuario usuario = new Usuario("U1", "Ana", "Empleado");
    public final Sala sala = new Sala("S1", "Sala A", 6);

    public final ReservaRepository repo = new InMemoryReservaRepository();
    public final List<ReglaValidacion> reglas = Arrays.asList(
            new ReglaConflictoHorario(),
            new ReglaHorarioLaboral()
    );
    public final Notifier notifier = new ConsoleNotifier();
    public final CrearReservaUseCase usecase = new CrearReservaUseCase(repo, reglas, notifier);
}
