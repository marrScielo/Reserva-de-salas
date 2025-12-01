// Conflictos de horario
package app.validation;
import app.domain.Reserva;
import java.util.List;
public class ReglaConflictoHorario implements ReglaValidacion {
  @Override public void validar(Reserva nueva, List<Reserva> existentes){
    for (Reserva r: existentes){
      boolean solapado = !(nueva.getFin().isBefore(r.getInicio()) || nueva.getInicio().isAfter(r.getFin()));
      if (solapado) throw new IllegalArgumentException("Conflicto: ya existe una reserva en ese horario.");
    }
  }
}
