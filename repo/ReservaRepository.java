package app.repo;
import app.domain.Reserva;
import java.util.List;
public interface ReservaRepository {
  void save(Reserva r);
  List<Reserva> findBySala(String salaId);
}
