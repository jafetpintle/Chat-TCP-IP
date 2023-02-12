import java.math.BigInteger; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
  
// Java program to calculate MD5 hash value 
public class MD5 { 
    public String getMd5(String mensajeAleatorio ,String input) 
    { 
        try { 
            input = funcionMezcla( mensajeAleatorio , input);
            // Static getInstance method is called with hashing MD5 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
  
            // digest() method is called to calculate message digest 
            //  of an input digest() return array of byte 
            byte[] messageDigest = md.digest(input.getBytes()); 
  
            // Convert byte array into signum representation 
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext; 
        }  
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
    } 

    public String funcionMezcla(String mensajeAleatorio  , String password){
        //Obtenemos el tamanio de ambos 
        String[] mensajeAux =  mensajeAleatorio.split("");
        String[] passwordAux = password.split("");
        
        int n = 1, tope =  password.length(), indiceMensaje = 0;
        String mensajeNuevo = "";
        
        //Funcion de mezcla
        for(int i = (password.length() -1); i >= 0; i--){

            int indice_aux = indiceMensaje + tope;
            for(int j = indiceMensaje; j < indice_aux ; j++){ //Se agregan n caracteres del mensaje aleatorio
                mensajeNuevo =mensajeNuevo + mensajeAux[j];
                indiceMensaje++;
            }

            mensajeNuevo = mensajeNuevo + passwordAux[i];    //Se agrega solo un caracter de la contraseña
            
            tope--; //Se disminuye n
        }

        //Terminamos de rellenar si faltan valores del mensaje aleatorio
        for(int i = indiceMensaje; i < mensajeAleatorio.length(); i++)
            mensajeNuevo =mensajeNuevo + mensajeAux[i];

        return mensajeNuevo;

    }
  

} 