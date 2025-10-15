package chats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import usuarios.Usuario;

/**
 * Conversación 1 a 1 entre dos usuarios.
 * Envío y recepción se manejan por consola.
 */
public class MensajeriaDirecta {
    private int idConversacion;
    private Usuario usuario1;
    private Usuario usuario2;
    private List<String> mensajes;

    // ======= Constructores =======
    public MensajeriaDirecta(int idConversacion, Usuario usuario1, Usuario usuario2, List<String> mensajes) {
        setIdConversacion(idConversacion);
        setUsuario1(usuario1);
        setUsuario2(usuario2);
        if (usuario1.getId() == usuario2.getId()) {
            throw new IllegalArgumentException("Los usuarios de la conversación deben ser distintos.");
        }
        this.mensajes = (mensajes != null) ? mensajes : new ArrayList<>();
    }

    // (opcional, mismo diagrama/firmas) conversación con lista vacía
    public MensajeriaDirecta(int idConversacion, Usuario usuario1, Usuario usuario2) {
        this(idConversacion, usuario1, usuario2, new ArrayList<>());
    }

    // ======= Getters / Setters =======
    public int getIdConversacion() { return idConversacion; }

    public void setIdConversacion(int idConversacion) {
        if (idConversacion <= 0) throw new IllegalArgumentException("idConversacion debe ser > 0");
        this.idConversacion = idConversacion;
    }

    public Usuario getUsuario1() { return usuario1; }

    public void setUsuario1(Usuario usuario1) {
        if (usuario1 == null) throw new IllegalArgumentException("usuario1 no puede ser nulo");
        this.usuario1 = usuario1;
    }

    public Usuario getUsuario2() { return usuario2; }

    public void setUsuario2(Usuario usuario2) {
        if (usuario2 == null) throw new IllegalArgumentException("usuario2 no puede ser nulo");
        this.usuario2 = usuario2;
    }

    public List<String> getMensajes() {
        // proteger el historial de cambios externos sin cambiar la firma
        return Collections.unmodifiableList(mensajes);
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = (mensajes != null) ? mensajes : new ArrayList<>();
    }

    // ======= Métodos del diagrama =======

    /**
     * Agrega un mensaje al historial validando remitente y contenido.
     * El formato guardado es: "[Nombre]: contenido".
     */
    public void enviarMensaje(String mensaje, Usuario remitente) {
        if (remitente == null) {
            System.out.println("❌ Error: remitente nulo.");
            return;
        }
        if (!pertenece(remitente)) {
            System.out.println("❌ Error: el remitente no pertenece a esta conversación.");
            return;
        }
        if (mensaje == null || mensaje.trim().isEmpty()) {
            System.out.println("⚠️ No se puede enviar un mensaje vacío.");
            return;
        }
        String linea = "[" + remitente.getNombre() + "]: " + mensaje.trim();
        mensajes.add(linea);
    }

    /**
     * Muestra en consola todo el historial de la conversación.
     * (Se mantiene la firma void sin parámetros como en tu base/diagrama).
     */
    public void recibirMensajes() {
        if (mensajes.isEmpty()) {
            System.out.println("No hay mensajes en esta conversación.");
            return;
        }
        System.out.println("=== Historial de la conversación #" + idConversacion + " ===");
        for (String m : mensajes) System.out.println(m);
    }

    /**
     * Devuelve el otro participante (receptor) o null si el remitente no pertenece.
     */
    public Usuario obtenerReceptor(Usuario remitente) {
        if (remitente == null) return null;
        if (remitente.getId() == usuario1.getId()) return usuario2;
        if (remitente.getId() == usuario2.getId()) return usuario1;
        return null;
    }

    // ======= Utilidad interna =======
    private boolean pertenece(Usuario u) {
        if (u == null || usuario1 == null || usuario2 == null) return false;
        // comparar por id evita depender de equals()
        return u.getId() == usuario1.getId() || u.getId() == usuario2.getId();
    }

    @Override
    public String toString() {
        return "MensajeriaDirecta{idConversacion=" + idConversacion +
                ", usuario1=" + (usuario1 != null ? usuario1.getNombre() : "null") +
                ", usuario2=" + (usuario2 != null ? usuario2.getNombre() : "null") +
                ", totalMensajes=" + mensajes.size() + "}";
    }
}
