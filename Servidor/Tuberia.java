import java.util.ArrayList;

public class Tuberia{

	private ArrayList<HiloUsuario> hiloUsuario; //Lista de hilos de usuarios conectados
	private ArrayList<Usuario> usuariosActivos;
	private boolean libre; //Variable para liberar recurso

	public Tuberia(){
		hiloUsuario = new ArrayList<HiloUsuario>();
		usuariosActivos = new ArrayList<Usuario>();
		libre = true;
	}

	//Agregamos usuario cuando se conecta
	public synchronized void agregarHiloUsuario(HiloUsuario usuario){
		while(!libre){
			try{
				wait();
			}catch(InterruptedException e){;}
		}
		libre = false;

		Mensaje aux = new Mensaje(2 , usuariosActivos );

		hiloUsuario.add(usuario);
		usuariosActivos.add( usuario.getUsuario() );

		for(int i = 0; i < usuariosActivos.size() ; i++)
					hiloUsuario.get(i).recibirMensaje( aux );
	}

	//Cuando se desconecta un usuario, se quita de la lista
	public synchronized void eliminarHiloUsuario(String usuario){
		while(!libre){
			try{
				wait();
			}catch(InterruptedException e){;}
		}
		libre = false;
		for(int i = 0; i < hiloUsuario.size() ; i ++)
			if(usuario.equals(hiloUsuario.get(i).getUsuario().getUsuario())){
				usuariosActivos.remove(i);
				hiloUsuario.remove(i);
				break;
			}
		Mensaje aux = new Mensaje(2 , usuariosActivos );

		for(int i = 0; i < usuariosActivos.size() ; i++)
			hiloUsuario.get(i).recibirMensaje( aux );
	}
	
	//Se libera el recurso
	public synchronized void liberar(){
		libre = true;
		notifyAll();
	}

	public synchronized void enviarMensaje( String emisor , String receptor , Mensaje mensaje  ){
		while(!libre){
			try{
				wait();
			}catch(InterruptedException e){;}
		}
		libre = false;
		//Buscamos el otro usuario para mandarle solicitud
		mensaje.setUsuario(new Usuario(emisor));
		for(int i = 0; i < hiloUsuario.size(); i++)
			if(hiloUsuario.get(i).getUsuario().getUsuario().equals(receptor))
					hiloUsuario.get(i).recibirMensaje( mensaje );
	}

}