import java.io.Serializable;
import java.util.ArrayList;

public class Usuario  implements Serializable{
    
    String usuario;
    String contrasenia;
    ArrayList<Contacto> listaContactos;
    
    
    public Usuario(String usuario ){
           this.usuario = usuario;
           listaContactos = new ArrayList<Contacto>();
    }
    
    public Usuario(String usuario , String contrasenia ){
           this.usuario = usuario;
           this.contrasenia = contrasenia;
           listaContactos = new ArrayList<Contacto>();
    }
     
    
    public void setListaContactos(ArrayList<Contacto> listaContactos){
        this.listaContactos = listaContactos;
    }
    
    public ArrayList<Contacto> getListaContactos(){
        return listaContactos;
    }

    public String getUsuario(){
        return usuario;
    }

    public String getContrasenia(){
        return contrasenia;
    }

    public void setContrasenia(String contrasenia){
        this.contrasenia = contrasenia;
    }


    //Metodo para actualizar el chat que se tiene con algun usuario o crearlo
    public void actualizarChat(String usuario_receptor , String chat){
        int indice = buscarChat(usuario_receptor);

        if(indice!= -1)
            listaContactos.get(indice).setChat(chat);
        else
            listaContactos.add(new Contacto(usuario_receptor, ""));
    
    }

    //Metodo para buscar contacto
    public int buscarChat(String usuario_receptor){
        int indice = -1;
        for(int i = 0; i < listaContactos.size() ; i ++ ){
            if(listaContactos.get(i).getUsuario().equals(usuario_receptor)){
                indice = i;
                break;
            }
        }
        return indice;
    }

    //Metodo para eliminar amigos con usuario
    public void eliminarContacto(String usuario_receptor){
        int indice = buscarChat(usuario_receptor);

        if(indice!= -1)
            listaContactos.remove(indice);

    }

    public String getChat(String contacto){
        int indice = buscarChat(contacto);

        if(indice != -1)
            return listaContactos.get(indice).getChat();
        return "";
    }
    
    
}
