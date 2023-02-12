import java.io.IOException;
import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.SealedObject;

//Hilo que se crea cada vez que llega un cliente
public class HiloUsuario extends Thread{
    
    private Socket socket; 
    private InputStream inputStream;
    private Usuario usuario; //Almacenar tu cliente
    private Mensaje mensaje; //Mensaje recibido
    private Mensaje mensaje_enviar; //Mensaje que se enviara a su cliente o al otro hilo
    private EncryDes encriptador;
    private OutputStream outputStream;
    private BaseDatos bd;
    private MD5 md5;
    private boolean conectado = false; //Variable que quedara activada si el logeo es correcto
    private Tuberia tuberia;
    

    
    public HiloUsuario (Socket socket , BaseDatos bd , Tuberia tuberia){
        this.socket = socket;
        this.tuberia = tuberia;
        this.bd = bd;
        try {
            encriptador = new EncryDes();
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            md5 = new MD5();
        } catch (IOException ex) {
            System.out.println("Error al crear los stream de entrada y salida : " + ex.getMessage());
        }
    }

    public Usuario getUsuario(){
        return usuario;
    }
    
    @Override
    public void run(){
        mensaje = leerMensaje();
        if(mensaje.getTipo()==4){ //El usuario es de tipo registro (implementado y funciona)
            if(bd.agregarUsuario(mensaje.getUsuario().getUsuario(), mensaje.getUsuario().getContrasenia()).equals("Usuario agregado con exito")){
                System.out.println("Se ha registrado "+mensaje.getUsuario().getUsuario()
                 + " con contrasenia "+mensaje.getUsuario().getContrasenia()); //Impresion de mensaje con exito
                mensaje_enviar = new Mensaje(4, "Se ha registrado con exito!"); //Se manda el mensaje de vuelta al cliente, donde avisa que se ha registradocon exito
            }else{
                mensaje_enviar = new Mensaje(4, "El nombre de usuario ya se encuentra en uso");//Se manda el mensaje de vuelta al cliente, donde avisa que NO se ha registradocon exito
            }
            enviarMensaje(mensaje_enviar);
            conectado = false;
            try{
                outputStream.close();
                inputStream.close();
            }catch(IOException e){;}
        }else{
            //Aqui se debe de implementar el MD5 para verificar (como arriba, pero con md5)
            usuario = bd.getUsuario(mensaje.getUsuario().getUsuario());
            if(usuario== null){
                System.out.println("Usuario no encontrado en la base de datos");
                mensaje_enviar = new Mensaje(5 , "Usuario no encontrado");
                enviarMensaje(mensaje_enviar);
            }else{

            //Generamos mensaje aleatorio y lo enviamos
            String mensajeAleatorio = getMensajeAleatorio();
            mensaje_enviar = new Mensaje(5 , mensajeAleatorio);
            enviarMensaje(mensaje_enviar);

            //Esperamos respuesta para comparar
            mensaje = leerMensaje();

                if( md5.getMd5( mensajeAleatorio ,usuario.getContrasenia()).equals(mensaje.getUsuario().getContrasenia()) ){
                    Usuario aux = usuario;
                    aux.setContrasenia(null);
                    mensaje_enviar = new Mensaje(5, aux ,"Inicio exitoso"); //Se manda la informacion del usuario al usuario, sin su contraseña
                    System.out.println(usuario.getUsuario() + " ha iniciado sesion correctamente.");
                    enviarMensaje(mensaje_enviar);
                    tuberia.agregarHiloUsuario(this); //Se agrega el hilo a la tuberia
                    conectado = true;
                    tuberia.liberar();
                }else{
                    mensaje_enviar = new Mensaje(5,"Contrasena incorrecta");
                    System.out.println(usuario.getUsuario() + " ingreso una contrasena incorrecta");
                    enviarMensaje(mensaje_enviar);
                }
            }

        }

        while (conectado) {
                //Aqui el hilo del server esparara por los mensajes que mande el cliente
                // Lee un mensaje enviado por el cliente
                    mensaje = leerMensaje();
                if(mensaje!=null){
                    switch(mensaje.getTipo()){
                        case 1:{ //Enviar mensaje a usuario
                            System.out.println(usuario.getUsuario() + " ha mandado un mensaje a " + mensaje.getUsuario().getUsuario());
                            bd.actualizarChat( usuario.getUsuario() , mensaje.getUsuario().getUsuario() , mensaje.getMensaje() );
                            tuberia.enviarMensaje( usuario.getUsuario() , mensaje.getUsuario().getUsuario() , mensaje);
                            tuberia.liberar();
                            break;
                        }
                        case 3:{ //Agregar contacto
                            bd.agregarContacto(usuario.getUsuario() , mensaje.getUsuario().getUsuario() );
                            tuberia.enviarMensaje( usuario.getUsuario() , mensaje.getUsuario().getUsuario() , mensaje);
                            tuberia.liberar();
                            break;
                        }
                        case 6:{ //Eliminar usuario`
                            //System.out.println("Usuario: "+ usuario.getUsuario() + " ha solicitado eliminar "+ mensaje.getUsuario().getUsuario());
                            bd.eliminarContacto( usuario.getUsuario() , mensaje.getUsuario().getUsuario() );
                            tuberia.enviarMensaje( usuario.getUsuario() , mensaje.getUsuario().getUsuario() , mensaje);
                            tuberia.liberar();
                            break;
                        }
                        case 7:{
                            tuberia.eliminarHiloUsuario(usuario.getUsuario());
                            tuberia.liberar();
                            System.out.println(usuario.getUsuario() + " se ha desconectado.");
                            break;
                        }

                    }
                }
        }
    }

    //Lee el mensaje codificado que llega del usuario
    private Mensaje leerMensaje(){
        Mensaje mensaje = null;
        SealedObject sealed = null;
        try{
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        sealed = (SealedObject)  objectInputStream.readObject();
        }catch(IOException | ClassNotFoundException e){
                 System.out.println("Cliente con la IP " + socket.getInetAddress().getHostName() + " desconectado.");
                conectado = false;
        }
        if(sealed!= null)
            mensaje = (Mensaje) encriptador.desdencriptarObjeto(sealed);
        return mensaje;
    }

    private void enviarMensaje(Mensaje mensaje){
        try{
            SealedObject mensaje_Encriptado = encriptador.encriptarObjeto(mensaje);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(mensaje_Encriptado);
        }catch(IOException e1){System.out.println("Error al enviar por stream");}
    }

    //Mensaje o solicitud que llega a este hilo de otro
    public void recibirMensaje( Mensaje mensaje_emisor ){

        switch(mensaje_emisor.getTipo()){

            case 1:{//Mensaje recibido
                bd.actualizarChat(usuario.getUsuario() , mensaje_emisor.getUsuario().getUsuario() , mensaje_emisor.getMensaje());
                enviarMensaje( mensaje_emisor );
                break;
            }
            case 2:{//Actualizacion de usuarios activos
                enviarMensaje(new Mensaje(2 , mensaje_emisor.getUsuariosActivos()));
                break;
            }
            case 3:{ //Solicitud de amigo
                bd.agregarContacto(usuario.getUsuario() , mensaje_emisor.getUsuario().getUsuario() );
                enviarMensaje( mensaje_emisor );
                break;
            }
            case 6:{ //Eliminar amigo
                //System.out.println("Usuario: "+ usuario.getUsuario() + "    eliminara a "+ mensaje_emisor.getUsuario().getUsuario());
                bd.eliminarContacto( usuario.getUsuario() , mensaje_emisor.getUsuario().getUsuario() );
                enviarMensaje( mensaje_emisor );
                break;
            }
        }
    }

    private String getMensajeAleatorio(){
        java.util.Random random = new java.util.Random();
        StringBuilder buffer = new StringBuilder(4095);

        //Se creara un mensaje aleatorio de 4Kbytes con los valores ascii de 33 a 122
        for (int i = 0;i < 4096; i++) {
            int randomLimitedInt = 33 + (int) (random.nextFloat() * (122 - 33 + 1));
            buffer.append((char) randomLimitedInt);
        }

        String generatedString = buffer.toString();
     

        return generatedString;
    }
    
}