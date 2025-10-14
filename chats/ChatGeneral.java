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

    public int getIdChat() { return idChat; }
    public void setIdChat(int idChat) { this.idChat = idChat; }

    public List<String> getMensajes() { return mensajes; }
    public List<Usuario> getParticipantes() { return participantes; }

    public void agregarParticipante(Usuario usuario) {
        if (usuario != null && !participantes.contains(usuario)) {
            participantes.add(usuario);
            System.out.println(usuario.getNombre() + " se unió al chat.");
        } else {
            System.out.println("El usuario ya está en el chat o es inválido.");
        }
    }

    public void eliminarParticipante(Usuario usuario) {
        if (participantes.contains(usuario)) {
            participantes.remove(usuario);
            System.out.println(usuario.getNombre() + " salió del chat.");
        } else {
            System.out.println("El usuario no está en el chat.");
        }
    }

    public void enviarMensaje(String mensaje, Usuario remitente) {
        if (participantes.contains(remitente)) {
            String texto = remitente.getNombre() + ": " + mensaje;
            mensajes.add(texto);
            System.out.println("Mensaje enviado.");
        } else {
            System.out.println("El usuario no pertenece al chat.");
        }
    }

    public void recibirMensajes() {
        System.out.println("\n--- Mensajes del Chat ---");
        if (mensajes.isEmpty()) {
            System.out.println("No hay mensajes todavía.");
        } else {
            for (String mensaje : mensajes) {
                System.out.println(mensaje);
            }
        }
        System.out.println("--------------------------\n");
    }
}

