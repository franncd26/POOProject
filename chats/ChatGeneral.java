package chats;

import java.util.List;
import usuarios.Usuario;

public class ChatGeneral {
    private int idChat;
    private List<String> mensajes;
    private List<Usuario> participantes;

    public ChatGeneral(int idChat, List<String> mensajes, List<Usuario> participantes) {
        this.idChat = idChat;
        this.mensajes = mensajes;
        this.participantes = participantes;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }

    public List<Usuario> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Usuario> participantes) {
        this.participantes = participantes;
    }

    public void enviarMensaje(String mensaje, Usuario remitente) {}
    public void recibirMensajes() {}
    public void agregarParticipante(Usuario usuario) {}
    public void eliminarParticipante(Usuario usuario) {}
}
