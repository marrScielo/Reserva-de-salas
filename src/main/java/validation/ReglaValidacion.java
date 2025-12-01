package validation;

import domain.Reserva;
import java.util.List;

public interface ReglaValidacion {
    void validar(Reserva nueva, List<Reserva> existentes) throws IllegalArgumentException;
}

