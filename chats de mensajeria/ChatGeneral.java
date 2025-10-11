package chats de mensajeria;

public class ChatGeneral {
    private int idChat;
    private String [] mensajes; // Array para almacenar mensajes
    private usuarios.Usuario[] participantes; // Array para almacenar participantes

    public ChatGeneral(int idChat, usuarios.Usuario[] participantes) {
        this.idChat = idChat;
        this.participantes = participantes;
        this.mensajes = new String[100]; // Inicializa el array con un tamaño fijo
    }
    // Getters y Setters
    public int getIdChat() {
        return idChat;
    }
    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }
    public String[] getMensajes() {
        return mensajes;
    }
    public void setMensajes(String[] mensajes) {
        this.mensajes = mensajes;
    }
    public usuarios.Usuario[] getParticipantes() {
        return participantes;
    }
    public void setParticipantes(usuarios.Usuario[] participantes) {
        this.participantes = participantes;
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
