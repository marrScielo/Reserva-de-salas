
package app.domain;
public class Usuario {
    private String id; private String nombre; private String rol;
    public Usuario(String id, String nombre, String rol){ this.id=id; this.nombre=nombre; this.rol=rol; }
    public String getId(){ return id; }
    public String getNombre(){ return nombre; }
    public String getRol(){ return rol; }
}
