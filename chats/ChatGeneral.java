package chats;

import java.util.ArrayList;
import java.util.List;
import usuarios.Usuario;

public class ChatGeneral {
    private int idChat;
    private List<String> mensajes;
    private List<Usuario> participantes;

    public ChatGeneral(int idChat) {
        this.idChat = idChat;
        this.mensajes = new ArrayList<>();
        this.participantes = new ArrayList<>();
    }

    public ChatGeneral() { this(0); }

    public int getIdChat() { return idChat; }
    public void setIdChat(int idChat) { this.idChat = idChat; }

    public List<String> getMensajes() { return mensajes; }
    public List<Usuario> getParticipantes() { return participantes; }

    public boolean agregarParticipante(Usuario usuario) {
        if (usuario == null) return false;
        if (!participantes.contains(usuario)) return participantes.add(usuario);
        return false;
    }

    public boolean eliminarParticipante(Usuario usuario) {
        if (usuario == null) return false;
        return participantes.remove(usuario);
    }

    public boolean enviarMensaje(String mensaje, Usuario remitente) {
        if (mensaje == null || remitente == null) return false;
        if (!participantes.contains(remitente)) return false;
        return mensajes.add(remitente.getNombre() + ": " + mensaje);
    }

    public List<String> recibirMensajes() {
        return new ArrayList<>(mensajes);
    }
}
