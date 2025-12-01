package app.repo;
import app.domain.Reserva;
import java.util.*;
public class InMemoryReservaRepository implements ReservaRepository {
  private final List<Reserva> data = new ArrayList<>();
  @Override public void save(Reserva r){ data.add(r); }
  @Override public List<Reserva> findBySala(String salaId){
    return data.stream()
           .filter(x -> x.getSalaId().equals(salaId))
           .collect(java.util.stream.Collectors.toList());

  }
}
