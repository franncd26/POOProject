package chats;

import java.util.ArrayList;
import java.util.List;
import usuarios.Usuario;

/**
 * La clase {@code ChatGeneral} representa un chat grupal dentro del sistema.
 * Permite que varios usuarios participen enviando y recibiendo mensajes en un mismo espacio común.
 * 
 * <p>Forma parte del paquete {@code chats}, encargado de manejar la comunicación entre usuarios.</p>
 * 
 * <p>Incluye métodos para agregar o eliminar participantes, enviar mensajes y mostrar el historial del chat.</p>
 */
public class ChatGeneral {

    /** Identificador único del chat general. */
    private int idChat;

    /** Lista de mensajes enviados en el chat. */
    private List<String> mensajes;

    /** Lista de usuarios participantes en el chat. */
    private List<Usuario> participantes;

    /**
     * Crea una nueva instancia de {@code ChatGeneral} con el identificador especificado.
     * Inicializa las listas de mensajes y participantes como vacías.
     *
     * @param idChat identificador único del chat
     */
    public ChatGeneral(int idChat) {
        this.idChat = idChat;
        this.mensajes = new ArrayList<>();
        this.participantes = new ArrayList<>();
    }

    /**
     * Obtiene el identificador del chat.
     *
     * @return el ID del chat
     */
    public int getIdChat() {
        return idChat;
    }

    /**
     * Asigna un nuevo identificador al chat.
     *
     * @param idChat nuevo ID del chat
     */
    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    /**
     * Obtiene la lista de mensajes enviados en el chat.
     *
     * @return lista de mensajes
     */
    public List<String> getMensajes() {
        return mensajes;
    }

    /**
     * Obtiene la lista de usuarios participantes en el chat.
     *
     * @return lista de participantes
     */
    public List<Usuario> getParticipantes() {
        return participantes;
    }

    /**
     * Agrega un nuevo participante al chat general.
     * 
     * <p>Solo se agrega si el usuario no es nulo y no se encuentra ya en la lista de participantes.</p>
     *
     * @param usuario el usuario que se desea agregar al chat
     */
    public void agregarParticipante(Usuario usuario) {
        if (usuario != null && !participantes.contains(usuario)) {
            participantes.add(usuario);
            System.out.println(usuario.getNombre() + " se unió al chat.");
        } else {
            System.out.println("El usuario ya está en el chat o es inválido.");
        }
    }

    /**
     * Elimina un participante existente del chat.
     * 
     * <p>Si el usuario no se encuentra en la lista de participantes, muestra un mensaje de advertencia.</p>
     *
     * @param usuario el usuario que se desea eliminar del chat
     */
    public void eliminarParticipante(Usuario usuario) {
        if (participantes.contains(usuario)) {
            participantes.remove(usuario);
            System.out.println(usuario.getNombre() + " salió del chat.");
        } else {
            System.out.println("El usuario no está en el chat.");
        }
    }

    /**
     * Envía un mensaje al chat en nombre del usuario remitente.
     * 
     * <p>El mensaje solo se envía si el remitente pertenece al chat. El texto se almacena
     * en la lista de mensajes con el formato: {@code "Nombre: mensaje"}.</p>
     *
     * @param mensaje  contenido del mensaje a enviar
     * @param remitente usuario que envía el mensaje
     */
    public void enviarMensaje(String mensaje, Usuario remitente) {
        if (participantes.contains(remitente)) {
            String texto = remitente.getNombre() + ": " + mensaje;
            mensajes.add(texto);
            System.out.println("Mensaje enviado.");
        } else {
            System.out.println("El usuario no pertenece al chat.");
        }
    }

    /**
     * Muestra en consola todos los mensajes del chat general.
     * 
     * <p>Si no existen mensajes, se muestra un mensaje indicando que el chat está vacío.</p>
     * 
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * chatGeneral.recibirMensajes();
     * </pre>
     */
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
