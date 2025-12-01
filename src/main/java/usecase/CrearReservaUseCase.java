package usecase;

import domain.Reserva;
import repo.ReservaRepository;
import validation.ReglaValidacion;
import notification.Notifier;
import java.util.List;

public class CrearReservaUseCase {
    private final ReservaRepository repo;
    private final List<ReglaValidacion> reglas;
    private final Notifier notifier;

    public CrearReservaUseCase(ReservaRepository repo, List<ReglaValidacion> reglas, Notifier notifier) {
        this.repo = repo;
        this.reglas = reglas;
        this.notifier = notifier;
    }

    public void ejecutar(Reserva r, String usuarioId) {
        List<Reserva> existentes = repo.findBySala(r.getSalaId());
        for (ReglaValidacion regla : reglas) {
            regla.validar(r, existentes); // Aplica TODAS las reglas
        }
        repo.save(r);
        notifier.notify(usuarioId, "Reserva creada y aprobada");
    }
}
