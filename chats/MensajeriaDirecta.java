package chats;

import usuarios.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * La clase {@code MensajeriaDirecta} representa una conversación privada entre dos usuarios.
 * Solo los dos participantes pueden enviar mensajes y consultar el historial.
 *
 * <p><b>Convenciones de diseño:</b></p>
 * <ul>
 *   <li>Sin I/O de consola.</li>
 *   <li>Mensajes representados por el DTO interno {@link Mensaje}.</li>
 *   <li>Se valida que el remitente pertenezca a la conversación.</li>
 * </ul>
 *
 * @author
 * @version 1.0
 */
public class MensajeriaDirecta {

    /** Identificador único del hilo de mensajería directa. */
    private final int idDM;

    /** Participante A. */
    private final Usuario usuarioA;

    /** Participante B. */
    private final Usuario usuarioB;

    /** Historial de mensajes. */
    private final List<Mensaje> mensajes = new ArrayList<>();

    /**
     * DTO inmutable de mensaje para mensajería directa.
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
         */
        public Mensaje(Date fecha, Usuario remitente, String texto) {
            if (fecha == null) throw new IllegalArgumentException("La fecha no puede ser nula.");
            if (remitente == null) throw new IllegalArgumentException("El remitente no puede ser nulo.");
            if (texto == null || texto.trim().isEmpty())
                throw new IllegalArgumentException("El texto no puede ser nulo/vacío.");
            this.fecha = new Date(fecha.getTime());
            this.remitente = remitente;
            this.texto = texto.trim();
        }

        /** @return copia defensiva de la fecha. */
        public Date getFecha() { return new Date(fecha.getTime()); }

        /** @return remitente. */
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
     * Crea una conversación de mensajería directa entre dos usuarios.
     * @param idDM identificador único (&gt; 0).
     * @param usuarioA primer participante (no nulo).
     * @param usuarioB segundo participante (no nulo y distinto de A por identidad).
     */
    public MensajeriaDirecta(int idDM, Usuario usuarioA, Usuario usuarioB) {
        if (idDM <= 0) throw new IllegalArgumentException("idDM debe ser > 0.");
        if (usuarioA == null || usuarioB == null)
            throw new IllegalArgumentException("Los usuarios no pueden ser nulos.");
        if (usuarioA.equals(usuarioB))
            throw new IllegalArgumentException("Los usuarios deben ser distintos.");
        this.idDM = idDM;
        this.usuarioA = usuarioA;
        this.usuarioB = usuarioB;
    }

    // ------------------------------------------------------------
    // Envío y consulta de mensajes
    // ------------------------------------------------------------

    /**
     * Envía un mensaje desde uno de los dos participantes.
     * @param remitente quien envía (debe ser {@code usuarioA} o {@code usuarioB}).
     * @param texto contenido (no nulo/ni vacío).
     * @return el {@link Mensaje} creado.
     * @throws IllegalArgumentException si parámetros inválidos.
     * @throws IllegalStateException si el remitente no pertenece a la conversación.
     */
    public Mensaje enviarMensaje(Usuario remitente, String texto) {
        Objects.requireNonNull(remitente, "Remitente no puede ser nulo.");
        if (!esParticipante(remitente)) {
            throw new IllegalStateException("El remitente no pertenece a esta conversación.");
        }
        Mensaje msg = new Mensaje(new Date(), remitente, texto);
        mensajes.add(msg);
        return msg;
    }

    /**
     * Devuelve una vista inmutable del historial de mensajes.
     * @return lista inmutable de mensajes.
     */
    public List<Mensaje> getMensajes() {
        return Collections.unmodifiableList(mensajes);
    }

    /**
     * Obtiene el “otro” participante de la conversación, dado un emisor válido.
     * @param emisor usuario emisor.
     * @return el receptor (el otro participante).
     * @throws IllegalArgumentException si {@code emisor} es nulo o no pertenece a la conversación.
     */
    public Usuario obtenerReceptor(Usuario emisor) {
        if (emisor == null) throw new IllegalArgumentException("Emisor no puede ser nulo.");
        if (emisor.equals(usuarioA)) return usuarioB;
        if (emisor.equals(usuarioB)) return usuarioA;
        throw new IllegalArgumentException("El emisor no pertenece a esta conversación.");
    }

    // ------------------------------------------------------------
    // Utilidades
    // ------------------------------------------------------------

    /**
     * Verifica si un usuario pertenece a esta conversación.
     * @param u usuario a verificar.
     * @return {@code true} si es A o B.
     */
    public boolean esParticipante(Usuario u) {
        if (u == null) return false;
        return u.equals(usuarioA) || u.equals(usuarioB);
    }

    /** @return id único del hilo de mensajería directa. */
    public int getIdDM() { return idDM; }

    /** @return participante A. */
    public Usuario getUsuarioA() { return usuarioA; }

    /** @return participante B. */
    public Usuario getUsuarioB() { return usuarioB; }

    @Override
    public String toString() {
        return "MensajeriaDirecta{" +
                "idDM=" + idDM +
                ", usuarioA=" + usuarioA.getNombre() +
                ", usuarioB=" + usuarioB.getNombre() +
                ", mensajes=" + mensajes.size() +
                '}';
    }
}
