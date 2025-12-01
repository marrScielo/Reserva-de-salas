package app.usecase;
import app.domain.Reserva;
import app.repo.ReservaRepository;
import app.validation.ReglaValidacion;
import app.notification.Notifier;
import java.util.List;

public class CrearReservaUseCase {
  private final ReservaRepository repo;
  private final List<ReglaValidacion> reglas;
  private final Notifier notifier;

  public CrearReservaUseCase(ReservaRepository repo, List<ReglaValidacion> reglas, Notifier notifier){
    this.repo=repo; this.reglas=reglas; this.notifier=notifier;
  }

  public void ejecutar(Reserva r, String usuarioId){
    List<Reserva> existentes = repo.findBySala(r.getSalaId());
    for (ReglaValidacion regla: reglas) regla.validar(r, existentes); // Aplica TODAS las reglas
    repo.save(r);
    notifier.notify(usuarioId, "Reserva creada y aprobada");
  }
}
