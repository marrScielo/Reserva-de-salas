package repo;

import domain.Reserva;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryReservaRepository implements ReservaRepository {
    private final List<Reserva> data = new ArrayList<>();

    @Override
    public void save(Reserva r) {
        data.add(r);
    }

    @Override
    public List<Reserva> findBySala(String salaId) {
        return data.stream()
                   .filter(x -> x.getSalaId().equals(salaId))
                   .collect(Collectors.toList());
    }
}
