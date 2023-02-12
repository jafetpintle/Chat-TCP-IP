import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;

 
public class EncryDes {
 
    private Cipher ecipher;
    private Cipher dcipher;
    private SecretKey key;
    String desKey = "0123456789abcdef"; // value from user  
    byte[] keyBytes;


    public EncryDes(){
      try {
        keyBytes = DatatypeConverter.parseHexBinary(desKey);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        key = factory.generateSecret(new DESKeySpec(keyBytes));
        ecipher = Cipher.getInstance("DES");
        dcipher = Cipher.getInstance("DES");

        }
        catch (NoSuchAlgorithmException e) {
             System.out.println("No Such Algorithm:" + e.getMessage());
            return;
        }
        catch (NoSuchPaddingException e) {
             System.out.println("No Such Padding:" + e.getMessage());
            return;
        }catch(InvalidKeySpecException e){
          System.out.println("spec no valida");
        }catch(InvalidKeyException e){
          System.out.println("Key no valida");
        }

    }

    public SealedObject encriptarObjeto( Mensaje mensaje ){
      SealedObject sealed = null;
       try{
          ecipher.init(Cipher.ENCRYPT_MODE, key);
      
          sealed = new SealedObject(mensaje, ecipher);
         
      }catch (InvalidKeyException e) {
            System.out.println("Invalid Key:" + e.getMessage());
                return null;
        }
        catch (IllegalBlockSizeException e) {
            System.out.println("Illegal Block:" + e.getMessage());
                return null;
        }
        catch (IOException e) {
            System.out.println("I/O Error:" + e.getMessage());
            return null;
        }
        return sealed;
    }

    public Mensaje desdencriptarObjeto( SealedObject sealed ){
     Mensaje mensaje = null;
      try{
          dcipher.init(Cipher.DECRYPT_MODE, key);
         
          // unseal (decrypt) the object
          
          mensaje = (Mensaje) sealed.getObject(dcipher);
        
      }catch (BadPaddingException e) {
          System.out.println("Bad Padding:" + e.getMessage());
          return null;
      }catch (InvalidKeyException e) {
            System.out.println("Invalid Key:" + e.getMessage());
                return null;
        }
        catch (IllegalBlockSizeException e) {
            System.out.println("Illegal Block:" + e.getMessage());
                return null;
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class Not Found:" + e.getMessage());
             return null;
        }
        catch (IOException e) {
            System.out.println("I/O Error:" + e.getMessage());
            return null;
        }

        return mensaje;

    }
 
}