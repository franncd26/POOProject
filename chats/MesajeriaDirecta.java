package chats;

import java.util.ArrayList;
import java.util.List;

public class MesajeriaDirecta {
    // Atributos y métodos para la mensajería directa
    private int idConversacion;
    private usuarios.Usuario usuario1;
    private usuarios.Usuario usuario2;
    private final List<String> mensajes = new ArrayList<>();
    private final List<usuarios.Usuario> participantes = new ArrayList<>();

    public MesajeriaDirecta(int idConversacion, usuarios.Usuario usuario1, usuarios.Usuario usuario2) {
        this.idConversacion = idConversacion;
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        
        // Inicializa el array con un tamaño fijo
    }
    // Getters y Setters
    public int getIdConversacion() {
        return idConversacion;
    }
    public void setIdConversacion(int idConversacion) {
        this.idConversacion = idConversacion;
    }
    public usuarios.Usuario getUsuario1() {
        return usuario1;
    }
    public void setUsuario1(usuarios.Usuario usuario1) {
        this.usuario1 = usuario1;
    }
    public usuarios.Usuario getUsuario2() {
        return usuario2;
    }
    public void setUsuario2(usuarios.Usuario usuario2) {
        this.usuario2 = usuario2;
    }
    public List<String> getMensajes() {
        return mensajes;
    }
    public List<usuarios.Usuario> getParticipantes() {
        return participantes;
    }

    
   
    void enviarMensaje(String mensaje, usuarios.Usuario remitente){
        // Lógica para enviar un mensaje
        }
    void recibirMensaje(){
        // Lógica para recibir un mensaje
    }
    void obtenerReceptor(){
        // Lógica para obtener el receptor del mensaje
    }
}