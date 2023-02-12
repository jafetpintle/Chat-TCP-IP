import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

public static void main(String[] args) {
        //Puerto que se utilizara
        int puerto = 9876;
        ServerSocket servidor = null; 
        Socket socket = null;
        BaseDatos bd = new BaseDatos();
        Tuberia tuberia = new Tuberia();

         try {
            // Se crea el serverSocket
            servidor = new ServerSocket(puerto);
            
            // Bucle infinito para esperar conexiones
            while (true) {
                System.out.println("Servidor a la espera de conexiones.");
                socket = servidor.accept(); //Espera por conexion de cliente
                System.out.println("Cliente con la IP " + socket.getInetAddress().getHostName() + " conectado.");
                HiloUsuario cc = new HiloUsuario(socket , bd , tuberia); //Crea hilo con el socket y la base de datos
                cc.start();
            }
            
        } catch (IOException e) {
        } finally{
            try {
                socket.close();
                servidor.close();
            } catch (IOException ex) {
                System.out.println("Error al cerrar el servidor: " + ex.getMessage());
            }
        }
         
    }
    
}
