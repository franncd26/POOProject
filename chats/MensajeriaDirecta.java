package chats;

import java.util.ArrayList;
import java.util.List;
import usuarios.Usuario;

public class MensajeriaDirecta {
    private int idConversacion;
    private Usuario usuario1;
    private Usuario usuario2;
    private List<String> mensajes;

    public MensajeriaDirecta(int idConversacion, Usuario usuario1, Usuario usuario2) {
        this.idConversacion = idConversacion;
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
        this.mensajes = new ArrayList<>();
    }

    public MensajeriaDirecta() { this(0, null, null); }

    public int getIdConversacion() { return idConversacion; }
    public void setIdConversacion(int idConversacion) { this.idConversacion = idConversacion; }

    public Usuario getUsuario1() { return usuario1; }
    public void setUsuario1(Usuario usuario1) { this.usuario1 = usuario1; }

    public Usuario getUsuario2() { return usuario2; }
    public void setUsuario2(Usuario usuario2) { this.usuario2 = usuario2; }

    public List<String> getMensajes() { return mensajes; }

    public boolean enviarMensaje(String mensaje, Usuario remitente) {
        if (mensaje == null || remitente == null) return false;
        if (remitente != usuario1 && remitente != usuario2) return false;
        Usuario receptor = (remitente == usuario1) ? usuario2 : usuario1;
        if (receptor == null) return false;
        return mensajes.add(remitente.getNombre() + " -> " + receptor.getNombre() + ": " + mensaje);
    }

    public List<String> recibirMensajes() {
        return new ArrayList<>(mensajes);
    }
}
