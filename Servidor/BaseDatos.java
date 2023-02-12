import java.io.*;
import java.util.ArrayList;

public class BaseDatos{
	
	ArrayList<Usuario> usuarios;

	public BaseDatos(){
		usuarios = leerArchivo();
	}

	public void actualizarChat(String usuario , String contacto , String chat){

		int indice = existeUsuario(usuario);

		if(indice!= -1){
			usuarios.get(indice).actualizarChat(contacto , chat);
			escribirArchivo(usuarios);
		}

	}

	public void agregarContacto(String usuario , String contacto ){

		int indice = existeUsuario(usuario);

		System.out.println("Usuario "+usuario + " agregara a " +contacto);

		if(indice!=-1){
			usuarios.get(indice).actualizarChat(contacto , "");
			escribirArchivo(usuarios);
		}
	}

	public void eliminarContacto(String usuario , String contacto){

		int indice = existeUsuario(usuario);
		System.out.println("Usuario " + usuario + " eliminara a "+ contacto);
		if(indice!=-1){
			//System.out.println("Se eliminara a " + contacto + " de " +usuarios.get(indice).getUsuario() + " ("+contacto+")" );
			usuarios.get(indice).eliminarContacto(contacto);
			escribirArchivo(usuarios);
		}
	}

	private int existeUsuario(String usuario){
		if(usuarios.size()!=0)
			for(int i = 0; i < usuarios.size(); i++) 
				if(usuarios.get(i).getUsuario().equals(usuario))					
					return i;
		return -1;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Usuario> leerArchivo(){

		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		try{
			File archivo = new File("usuarios.txt");
			if(!archivo.exists()){
				escribirArchivo(usuarios);
				return usuarios;	
			} 
			FileInputStream entrada = new FileInputStream(archivo);
			ObjectInputStream reader = new   ObjectInputStream(entrada);
			//Lectura de objetor vector de archivo
			usuarios = (ArrayList<Usuario>)reader.readObject();

			entrada.close();
			reader.close();
		}catch(IOException | ClassNotFoundException ex){}

		return usuarios;
	}

	public void escribirArchivo(ArrayList<Usuario> usuarios){

		try{
			File archivo = new File("usuarios.txt");
			FileOutputStream salida = new FileOutputStream(archivo , false);
			ObjectOutputStream writer = new ObjectOutputStream(salida);
			writer.writeObject(usuarios);
			salida.close();
			writer.close();
		}catch(IOException e){}

	}

	public String agregarUsuario(String usuario, String contrasenia){

		String mensaje = "";
		if(existeUsuario(usuario) == -1){
			usuarios.add(new Usuario(usuario, contrasenia));

			escribirArchivo(usuarios);
			mensaje = "Usuario agregado con exito";
			System.out.println("Se ha agregado "+usuario + " a la base de datos.");
		}else{
			System.out.println("Ya existe usuario en la base de datos");
			mensaje = "Ya existe usuario en la base de datos";
		}
		return mensaje;
	}

	public Usuario getUsuario(String usuario){

		int i = existeUsuario(usuario);
		if(i!=-1)
			return usuarios.get(i);
		else
			return null;
	}

}