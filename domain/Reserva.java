
package app.domain;
import java.time.LocalDateTime;
public class Reserva {
    public enum Estado { PENDIENTE, APROBADA, RECHAZADA }
    private String id, salaId, usuarioId;
    private LocalDateTime inicio, fin;
    private Estado estado = Estado.PENDIENTE;
    public Reserva(String id, String salaId, String usuarioId, LocalDateTime inicio, LocalDateTime fin){
        this.id=id; this.salaId=salaId; this.usuarioId=usuarioId; this.inicio=inicio; this.fin=fin;
    }
    public String getId(){ return id; }
    public String getSalaId(){ return salaId; }
    public String getUsuarioId(){ return usuarioId; }
    public LocalDateTime getInicio(){ return inicio; }
    public LocalDateTime getFin(){ return fin; }
    public Estado getEstado(){ return estado; }
    public void aprobar(){ this.estado = Estado.APROBADA; }
    public void rechazar(){ this.estado = Estado.RECHAZADA; }
}
