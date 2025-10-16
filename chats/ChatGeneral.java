package chats;

import usuarios.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * La clase {@code ChatGeneral} modela un canal de conversación compartido por múltiples usuarios.
 * Mantiene una lista de participantes y un historial de mensajes con marca de tiempo.
 *
 * <p><b>Convenciones de diseño:</b></p>
 * <ul>
 *   <li>Sin I/O de consola: esta clase no imprime ni lee de consola.</li>
 *   <li>Los mensajes se representan con el DTO interno {@link Mensaje} (fecha, remitente, texto).</li>
 *   <li>Solo los participantes del chat pueden enviar mensajes.</li>
 *   <li>Se exponen vistas inmutables para no romper encapsulamiento.</li>
 * </ul>
 *
 * <p><b>Reglas de negocio:</b></p>
 * <ul>
 *   <li>No se admiten usuarios nulos.</li>
 *   <li>No se agregan participantes duplicados.</li>
 *   <li>El texto del mensaje no puede ser nulo/ni vacío.</li>
 * </ul>
 *
 * @author
 * @version 1.0
 */
public class ChatGeneral {

    /** Identificador único del chat general. */
    private final int idChat;

    /** Participantes actuales del chat. */
    private final List<Usuario> participantes = new ArrayList<>();

    /** Historial de mensajes del chat. */
    private final List<Mensaje> mensajes = new ArrayList<>();

    /**
     * DTO inmutable para representar un mensaje del chat general.
     */
    public static final class Mensaje {
        private final Date fecha;
        private final Usuario remitente;
        private final String texto;

        /**
         * Crea un mensaje.
         * @param fecha fecha del mensaje (no nula).
         * @param remitente remitente (no nulo).
         * @param texto contenido (no nulo/ni vacío).
         * @throws IllegalArgumentException si algún parámetro es inválido.
         */
        public Mensaje(Date fecha, Usuario remitente, String texto) {
            if (fecha == null) throw new IllegalArgumentException("La fecha no puede ser nula.");
            if (remitente == null) throw new IllegalArgumentException("El remitente no puede ser nulo.");
            if (texto == null || texto.trim().isEmpty())
                throw new IllegalArgumentException("El texto no puede ser nulo/vacío.");
            // Copia defensiva de fecha
            this.fecha = new Date(fecha.getTime());
            this.remitente = remitente;
            this.texto = texto.trim();
        }

        /** @return copia defensiva de la fecha del mensaje. */
        public Date getFecha() { return new Date(fecha.getTime()); }

        /** @return remitente del mensaje. */
        public Usuario getRemitente() { return remitente; }

        /** @return texto del mensaje. */
        public String getTexto() { return texto; }

        @Override
        public String toString() {
            return "Mensaje{" +
                    "fecha=" + fecha +
                    ", remitente=" + remitente.getNombre() +
                    ", texto='" + texto + '\'' +
                    '}';
        }
    }

    /**
     * Crea un {@code ChatGeneral}.
     * @param idChat identificador único (&gt; 0).
     * @throws IllegalArgumentException si {@code idChat <= 0}.
     */
    public ChatGeneral(int idChat) {
        if (idChat <= 0) throw new IllegalArgumentException("idChat debe ser > 0.");
        this.idChat = idChat;
    }

    // ------------------------------------------------------------
    // Participantes
    // ------------------------------------------------------------

    /**
     * Agrega un participante al chat si aún no pertenece.
     * @param usuario usuario a agregar (no nulo).
     * @return {@code true} si fue agregado; {@code false} si ya estaba.
     * @throws IllegalArgumentException si {@code usuario} es nulo.
     */
    public boolean agregarParticipante(Usuario usuario) {
        if (usuario == null) throw new IllegalArgumentException("Usuario no puede ser nulo.");
        if (!participantes.contains(usuario)) {
            return participantes.add(usuario);
        }
        return false;
    }

    /**
     * Elimina un participante del chat.
     * @param usuario usuario a eliminar (no nulo).
     * @return {@code true} si fue eliminado; {@code false} si no estaba.
     * @throws IllegalArgumentException si {@code usuario} es nulo.
     */
    public boolean eliminarParticipante(Usuario usuario) {
        if (usuario == null) throw new IllegalArgumentException("Usuario no puede ser nulo.");
        return participantes.remove(usuario);
    }

    /**
     * Verifica si un usuario es participante del chat.
     * @param usuario usuario a verificar (no nulo).
     * @return {@code true} si participa; de lo contrario {@code false}.
     */
    public boolean estaParticipando(Usuario usuario) {
        if (usuario == null) throw new IllegalArgumentException("Usuario no puede ser nulo.");
        return participantes.contains(usuario);
    }

    /**
     * Devuelve una vista inmutable de los participantes.
     * @return lista inmutable de participantes.
     */
    public List<Usuario> getParticipantes() {
        return Collections.unmodifiableList(participantes);
    }

    // ------------------------------------------------------------
    // Mensajes
    // ------------------------------------------------------------

    /**
     * Envía un mensaje al chat desde un participante.
     * @param remitente usuario que envía (debe ser participante).
     * @param texto contenido del mensaje (no nulo/ni vacío).
     * @return el {@link Mensaje} creado y agregado al historial.
     * @throws IllegalArgumentException si parámetros inválidos.
     * @throws IllegalStateException si el remitente no es participante del chat.
     */
    public Mensaje enviarMensaje(Usuario remitente, String texto) {
        Objects.requireNonNull(remitente, "Remitente no puede ser nulo.");
        if (!estaParticipando(remitente)) {
            throw new IllegalStateException("El remitente no participa en este chat.");
        }
        Mensaje msg = new Mensaje(new Date(), remitente, texto);
        mensajes.add(msg);
        return msg;
    }

    /**
     * Devuelve una vista inmutable del historial completo de mensajes.
     * @return lista inmutable con el historial.
     */
    public List<Mensaje> getMensajes() {
        return Collections.unmodifiableList(mensajes);
    }

    /**
     * Devuelve una vista inmutable desde un índice dado (útil para “cargar más”).
     * @param desde índice inicial (&ge; 0).
     * @return lista inmutable desde {@code desde} hasta el final.
     * @throws IndexOutOfBoundsException si {@code desde} es inválido.
     */
    public List<Mensaje> getMensajesDesde(int desde) {
        if (desde < 0 || desde > mensajes.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango.");
        }
        return Collections.unmodifiableList(mensajes.subList(desde, mensajes.size()));
    }

    // ------------------------------------------------------------
    // Propiedades básicas / utilidades
    // ------------------------------------------------------------

    /** @return id único del chat. */
    public int getIdChat() { return idChat; }

    @Override
    public String toString() {
        return "ChatGeneral{" +
                "idChat=" + idChat +
                ", participantes=" + participantes.size() +
                ", mensajes=" + mensajes.size() +
                '}';
    }
}
