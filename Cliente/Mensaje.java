import java.io.Serializable;
import java.util.ArrayList;

public class Mensaje  implements Serializable{
                    

    int tipo;   
                        // 1) Mensaje
                        // 2) Actualizacion usuarios activos
                        // 3) Agregar contacto
                        // 4) Registro
                        // 5) Inicio Sesion
                        // 6) Eliminar amigo
                        // 7) Usuario desconectado
    String mensaje;
    Usuario usuario;
    ArrayList<Usuario> usuariosActivos;

    public Mensaje (int tipo, Usuario usuario){
        this.tipo = tipo;
        this.usuario = usuario;
    }

    public Mensaje(int tipo, Usuario usuario , String mensaje ){
        this.tipo = tipo;
        this.usuario = usuario;
        this.mensaje = mensaje;
    }

    public Mensaje(int tipo, ArrayList<Usuario> usuariosActivos){
        this.tipo = tipo;
        this.usuariosActivos = usuariosActivos;
    }

    public Mensaje(int tipo, String mensaje){
        this.tipo = tipo;
        this.mensaje = mensaje;
    }

    public int getTipo(){
        return tipo;
    }

    public void setTipo(int tipo){
        this.tipo= tipo;
    }

    public String getMensaje(){
        return mensaje;
    }

    public void setMensaje(String mensaje){
        this.mensaje = mensaje;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public ArrayList<Usuario> getUsuariosActivos(){
        return usuariosActivos;
    }

    public void setUsuariosActivos(ArrayList<Usuario>  usuariosActivos){
        this.usuariosActivos = usuariosActivos;
    }

}