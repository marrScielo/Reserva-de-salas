package validation;

import domain.Reserva;
import java.time.LocalTime;
import java.util.List;

public class ReglaHorarioLaboral implements ReglaValidacion {
    private final LocalTime inicio = LocalTime.of(8, 0);
    private final LocalTime fin = LocalTime.of(18, 0);

    @Override
    public void validar(Reserva nueva, List<Reserva> existentes) {
        if (nueva.getInicio().toLocalTime().isBefore(inicio) || nueva.getFin().toLocalTime().isAfter(fin))
            throw new IllegalArgumentException("Fuera de horario laboral (08:00–18:00).");
    }
}
