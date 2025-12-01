package repo;

import domain.Reserva;
import java.util.List;

public interface ReservaRepository {
    void save(Reserva r);
    List<Reserva> findBySala(String salaId);
}
