// src/main/java/app/ui/AppContext.java
package app.ui;

import app.domain.Sala;
import app.domain.Usuario;
import app.notification.ConsoleNotifier;
import app.notification.Notifier;
import app.repo.InMemoryReservaRepository;
import app.repo.ReservaRepository;
import app.usecase.CrearReservaUseCase;
import app.validation.ReglaConflictoHorario;
import app.validation.ReglaHorarioLaboral;
import app.validation.ReglaValidacion;

import java.util.Arrays;
import java.util.List;

public class AppContext {
    public final Usuario usuario = new Usuario("U1","Ana","Empleado");
    public final Sala sala = new Sala("S1","Sala A", 6);

    public final ReservaRepository repo = new InMemoryReservaRepository();
    public final List<ReglaValidacion> reglas = Arrays.asList(
            new ReglaConflictoHorario(),
            new ReglaHorarioLaboral()
    );
    public final Notifier notifier = new ConsoleNotifier();
    public final CrearReservaUseCase usecase = new CrearReservaUseCase(repo, reglas, notifier);
}
