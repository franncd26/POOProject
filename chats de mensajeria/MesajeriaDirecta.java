package chats de mensajeria;

public class MesajeriaDirecta extends Usuario {
    // Atributos y métodos para la mensajería directa
    private int idConversacion;
    private Usuarios.Usuario usuario1;
    private Usuario.Usuario usuario2;
    private String[] mensajes; // Array para almacenar mensajes

    public MesajeriaDirecta(int id, String nombre, String telefono, String correo, int idConversacion, usuarios.Usuario usuario1, usuarios.Usuario usuario2) {
        super(id, nombre, telefono, correo);
        this.idConversacion = idConversacion;
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.mensajes = new String[100]; // Inicializa el array con un tamaño fijo
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
    public String[] getMensajes() {
        return mensajes;
    }
    public void setMensajes(String[] mensajes) {
        this.mensajes = mensajes;
    }
    void enviarMensaje(String mensaje, Usuario.usuario remitente){ {
        // Lógica para enviar un mensaje
        if (index >= 0 && index < mensajes.length) {
            mensajes[index] = mensaje;
        }

    }

}
    void recibirMensaje(){
        // Lógica para recibir un mensaje
    }
    void obtenerReceptor(){
        // Lógica para obtener el receptor del mensaje
    }
}