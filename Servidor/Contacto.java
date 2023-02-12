import java.io.Serializable;

public class Contacto implements Serializable{ //Clase donde se almacenara algun contacto del usuario, con su conversacion
    String usuario;
    String chat;
    
    public Contacto(String usuario , String chat){
        this.usuario = usuario;
        this.chat = chat;
    }
    
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    
    public String getUsuario(){
        return usuario;
    }
    
    public void setChat(String chat){
        this.chat = chat;
    }
    
    public String getChat(){
        return chat;
    }
}
