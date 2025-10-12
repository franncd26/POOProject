package chats;

import java.util.ArrayList;
import java.util.List;

public class ChatGeneral {
    private int idChat;
    private final List<String> mensajes = new ArrayList<>();
    private final List<usuarios.Usuario> participantes = new ArrayList<>();

    public ChatGeneral(int idChat, usuarios.Usuario[] participantes) {
        this.idChat = idChat;
        // Inicializa el array con un tamaño fijo

    }
    // Getters y Setters
    public int getIdChat() {
        return idChat;
    }
    public void setIdChat(int idChat) {
        this.idChat = idChat;
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
    void agregarParticipante(usuarios.Usuario nuevoParticipante){
        // Lógica para agregar un nuevo participante al chat
    }
    void eliminarParticipante(usuarios.Usuario participante){
        // Lógica para eliminar un participante del chat
    }

}
