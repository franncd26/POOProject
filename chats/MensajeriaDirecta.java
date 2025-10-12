package chats;

import java.util.List;
import usuarios.Usuario;

public class MensajeriaDirecta {
    private int idConversacion;
    private Usuario usuario1;
    private Usuario usuario2;
    private List<String> mensajes;

    public MensajeriaDirecta(int idConversacion, Usuario usuario1, Usuario usuario2, List<String> mensajes) {
        this.idConversacion = idConversacion;
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.mensajes = mensajes;
    }

    public int getIdConversacion() {
        return idConversacion;
    }

    public void setIdConversacion(int idConversacion) {
        this.idConversacion = idConversacion;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    public Usuario getUsuario2() {
        return usuario2;
    }

    public void setUsuario2(Usuario usuario2) {
        this.usuario2 = usuario2;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }

    public void enviarMensaje(String mensaje, Usuario remitente) {}
    public void recibirMensajes() {}
    public Usuario obtenerReceptor(Usuario remitente) { return null; }
}
