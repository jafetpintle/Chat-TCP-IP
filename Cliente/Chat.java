import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.DataOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import javax.crypto.SealedObject;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

//Clase para la gestion del chat en el usuario
public class Chat extends javax.swing.JFrame implements Runnable {

    private Usuario usuario = null;
    private Socket socket;
    private OutputStream outputStream;
    private EncryDes encriptador;
    private InputStream inputStream;
    private ArrayList<Usuario> usuariosActivos;

    /**
     * Creates new form Chat
     */
    public Chat(String usr ,String ip) {
        //Unimos al servidor
        try {
            socket = new Socket(ip, 9876);
            //salida = new DataOutputStream(socket.getOutputStream());
            outputStream = socket.getOutputStream();  //Stream para salida al servidor
            inputStream = socket.getInputStream();  //Stream de llegada de mensajes del servidor
            encriptador = new EncryDes();   //Encriptador - Desencriptador
        } catch (IOException ex) {
            System.out.println("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        }
        
        initComponents();
        usuario = new Usuario(usr);
        usuariosActivos = new ArrayList<Usuario>();
        this.addWindowListener(new WindowAdapter() { //Metodo para avisar cuando se desconecta

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Cerrando sesion");
                enviarMensaje(new Mensaje(7 , new Usuario( usuario.getUsuario() )));
            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }

        });
        //labelUsuario.setText(usr);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        labelUsuario = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        notificaciones = new javax.swing.JLabel();
        setResizable(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(80, 81, 96));
        jPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Amigos");

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CHAT");

        labelUsuario.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        labelUsuario.setForeground(new java.awt.Color(255, 255, 255));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Bienvenido");

        jButton2.setText("Agregar contacto");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        notificaciones.setFont(new java.awt.Font("Segoe UI Black", 0, 11)); // NOI18N
        notificaciones.setForeground(new java.awt.Color(240, 240, 240));
        notificaciones.setText("");


        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(notificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(35, 35, 35))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(notificaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3))))
                .addContainerGap())
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(104, 130, 158));
        jPanel3.setForeground(new java.awt.Color(104, 130, 158));

        jButton1.setText("ENVIAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    //Enviar mensaje a un usuario
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(jComboBox1.getSelectedItem()!=null && !jTextField1.getText().equals("")){
        String chatTexto = jTextArea1.getText() + "\n"+usuario.getUsuario()+": "+jTextField1.getText(); //Se muestra mensaje en text area
        jTextArea1.setText(chatTexto);
        usuario.actualizarChat(String.valueOf( jComboBox1.getSelectedItem()) , chatTexto ); //Se actualiza chat del usuario
        enviarMensaje(new Mensaje(1 , new Usuario(String.valueOf(jComboBox1.getSelectedItem())) , chatTexto )); //Se manda el mensaje a su hilo por el socket
        }
        jTextField1.setText(""); //Borramos el texto del textfield
    }//GEN-LAST:event_jButton1ActionPerformed


    //Boton que hace visible la ventana "Agregar"
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Agregar agregar = new Agregar( usuariosActivos , usuario, this );
        agregar.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    //Boton para eliminar contactos
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        eliminarContacto();
    }//GEN-LAST:event_jButton2ActionPerformed


    //Metodo para verificar el login con el servidor
    public String inicioExitoso(String contrasenia){
        MD5 md5 = new MD5();
        enviarMensaje(new Mensaje(5 , new Usuario(usuario.getUsuario()) )); //Se envia el usuario a su hilo del servidor
        Mensaje aux = leerMensaje();
        if(!aux.getMensaje().equals("Usuario no encontrado")){ //Si no se ha encontrado, no existe en el server
            //Reenviamos con MD5
            enviarMensaje(new Mensaje(5, new Usuario( usuario.getUsuario() , md5.getMd5( aux.getMensaje() , contrasenia ) ) ));
            //Esperamos la respuesta del server al comprobar la contrasena
            aux = leerMensaje();
            if(aux.getMensaje().equals("Inicio exitoso")){
                Thread thread = new Thread(this);//Si se logea con exito, se inicia el hilo para recibir mensajes
                usuario = aux.getUsuario();
                thread.start(); //Iniciamos metodo run
                labelUsuario.setText(usuario.getUsuario());
            }
        }
        return aux.getMensaje(); //Retornamos el mensaje del servidor
    }

    //Lee el mensaje que llega del servidor y lo desencripta
    private Mensaje leerMensaje(){
        Mensaje mensaje = null;
        SealedObject sealed = null;
        try{
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        sealed = (SealedObject)  objectInputStream.readObject(); 
        }catch(IOException | ClassNotFoundException e){
         System.out.println("Error");
        }

        mensaje = (Mensaje) encriptador.desdencriptarObjeto(sealed); //Desencriptamos el mensaje
        return mensaje;
    }

    //Se envia mensaje al server y encripta el mensaje
    void enviarMensaje(Mensaje mensaje){
        try{
            SealedObject mensaje_Encriptado = encriptador.encriptarObjeto(mensaje);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(mensaje_Encriptado);
        }catch(IOException e1){System.out.println("Error al enviar por stream");}
    }

    public void agregarContacto( String contacto ){
        usuario.actualizarChat( contacto , "" );
        setComboBox();
        enviarMensaje(new Mensaje( 3 , new Usuario(contacto) , "" ));
    }

    //Elimina contacto de su lista
    private void eliminarContacto(){
        if(jComboBox1.getSelectedItem()!= null){
            usuario.eliminarContacto(String.valueOf(jComboBox1.getSelectedItem()));
            enviarMensaje(new Mensaje( 6 , new Usuario( String.valueOf(jComboBox1.getSelectedItem()) ) ));
            setComboBox();
        }
    }


    //Actualiza el combobox de contactos, con los que estan activos y son sus contactos
    private void setComboBox(){

        jComboBox1.removeAllItems();
        for(int i = 0; i < usuariosActivos.size(); i++)
            for(int j = 0; j < usuario.getListaContactos().size() ; j++  )
                if(usuariosActivos.get(i).getUsuario().equals(usuario.getListaContactos().get(j).getUsuario()) )
                    jComboBox1.addItem(usuariosActivos.get(i).getUsuario());
    }

    //Muestra los chat segun el usuario activo en el combobox
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
    //("Nick seleccionado:"+clientes.get(jComboBox1.getSelectedIndex()).getNick()+"@");                                        
        if(jComboBox1.getSelectedItem()!=null)
            jTextArea1.setText(usuario.getChat(String.valueOf(jComboBox1.getSelectedItem())));
        else
            jTextArea1.setText("");
    } 

    //Espera los mensajes que llegan de su hilo del servidor
    public void run(){
        Mensaje mensaje = null;
        while(true){
            mensaje = leerMensaje();

            switch(mensaje.getTipo()){
                case 1: {//Otro cliente le ha mandado un mensaje
                    usuario.actualizarChat( mensaje.getUsuario().getUsuario() , mensaje.getMensaje() );
                    //Mostramos el mensaje si es que tiene el usuario en el combobox seleccionado
                    if(jComboBox1.getSelectedItem()!=null && String.valueOf(jComboBox1.getSelectedItem()).equals(mensaje.getUsuario().getUsuario())){
                        jTextArea1.setText(usuario.getChat(String.valueOf(jComboBox1.getSelectedItem())));
                    }else{
                        notificaciones.setText(mensaje.getUsuario().getUsuario() + " te ha mandado un mensaje.");
                    }
                    break;
                }
                case 2: { //Actualizacion de usuarios activos
                    usuariosActivos = mensaje.getUsuariosActivos();
                    setComboBox();
                    break;
                }
                case 3: { // Solicitud de amistad
                    notificaciones.setText(mensaje.getUsuario().getUsuario() + " te ha agregado.");
                    usuario.actualizarChat( mensaje.getUsuario().getUsuario() , "" );
                    setComboBox();
                    break;
                }
                case 6:{//Eliminar amigo
                    notificaciones.setText(mensaje.getUsuario().getUsuario() + " te ha eliminado.");
                    usuario.eliminarContacto( mensaje.getUsuario().getUsuario() );
                    setComboBox();
                }
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JLabel notificaciones;
    // End of variables declaration//GEN-END:variables

    
}
