package eventos;

import usuarios.Corredor;
import java.util.Objects;

/**
 * La clase {@code Inscripcion} representa el registro de un corredor en un evento específico.
 * Contiene información sobre la distancia seleccionada, la talla de camiseta, el número de dorsal,
 * el estado del proceso de inscripción, y las referencias al corredor y evento correspondientes.
 *
 * <p><b>Reglas de negocio:</b></p>
 * <ul>
 *     <li>El flujo de estado permitido es: PENDIENTE → PAGADO → CONFIRMADO.</li>
 *     <li>El número de dorsal debe ser mayor que cero y único dentro del evento (se valida en {@link Evento}).</li>
 *     <li>No se permite retroceder en los estados ni realizar operaciones inválidas.</li>
 *     <li>No se deben incluir operaciones de entrada/salida de consola dentro de esta clase.</li>
 * </ul>
 *
 * @author 
 * @version 1.0
 */
public class Inscripcion {

    /**
     * Enumeración que define las posibles distancias que puede elegir un corredor.
     */
    public enum Distancia { CINCO_K, DIEZ_K, MEDIA_MARATON, MARATON }

    /**
     * Enumeración que define las tallas disponibles para las camisetas de los participantes.
     */
    public enum Talla { XS, S, M, L, XL }

    /**
     * Enumeración que representa los estados válidos de una inscripción.
     */
    public enum Estado { PENDIENTE, PAGADO, CONFIRMADO }

    /** Identificador único de la inscripción. */
    private final int id;

    /** Distancia seleccionada por el corredor. */
    private Distancia distancia;

    /** Talla de camiseta seleccionada por el corredor. */
    private Talla talla;

    /** Número de dorsal asignado al corredor. Debe ser único dentro del evento. */
    private int numeroDorsal;

    /** Estado actual de la inscripción. */
    private Estado estado = Estado.PENDIENTE;

    /** Corredor asociado a la inscripción. */
    private Corredor corredor;

    /** Evento al que pertenece la inscripción. */
    private Evento evento;

    /**
     * Constructor de la clase {@code Inscripcion}.
     *
     * @param id identificador único de la inscripción.
     * @param distancia distancia seleccionada.
     * @param talla talla seleccionada.
     * @param numeroDorsal número de dorsal asignado.
     * @param corredor corredor asociado.
     * @param evento evento asociado.
     * @throws IllegalArgumentException si alguno de los parámetros es inválido o nulo.
     */
    public Inscripcion(int id,
                       Distancia distancia,
                       Talla talla,
                       int numeroDorsal,
                       Corredor corredor,
                       Evento evento) {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        if (distancia == null) throw new IllegalArgumentException("La distancia no puede ser nula.");
        if (talla == null) throw new IllegalArgumentException("La talla no puede ser nula.");
        if (numeroDorsal <= 0) throw new IllegalArgumentException("El número de dorsal debe ser mayor que 0.");
        if (corredor == null) throw new IllegalArgumentException("El corredor no puede ser nulo.");
        if (evento == null) throw new IllegalArgumentException("El evento no puede ser nulo.");

        this.id = id;
        this.distancia = distancia;
        this.talla = talla;
        this.numeroDorsal = numeroDorsal;
        this.corredor = corredor;
        this.evento = evento;
    }

    // ------------------------------------------------------------
    // MÉTODOS DE NEGOCIO (FLUJO DE ESTADOS)
    // ------------------------------------------------------------

    /**
     * Cambia el estado de la inscripción de {@code PENDIENTE} a {@code PAGADO}.
     *
     * @throws IllegalStateException si el estado actual no es {@code PENDIENTE}.
     */
    public void confirmarPago() {
        if (estado != Estado.PENDIENTE) {
            throw new IllegalStateException("Solo se puede pagar una inscripción pendiente.");
        }
        this.estado = Estado.PAGADO;
    }

    /**
     * Cambia el estado de la inscripción de {@code PAGADO} a {@code CONFIRMADO}.
     *
     * @throws IllegalStateException si el estado actual no es {@code PAGADO}.
     */
    public void confirmarInscripcion() {
        if (estado != Estado.PAGADO) {
            throw new IllegalStateException("Debe estar PAGADO para poder confirmar la inscripción.");
        }
        this.estado = Estado.CONFIRMADO;
    }

    // ------------------------------------------------------------
    // GETTERS Y SETTERS
    // ------------------------------------------------------------

    /** @return el identificador único de la inscripción. */
    public int getId() { return id; }

    /** @return la distancia seleccionada. */
    public Distancia getDistancia() { return distancia; }

    /**
     * Establece la distancia seleccionada.
     * @param distancia nueva distancia.
     * @throws IllegalArgumentException si la distancia es nula.
     */
    public void setDistancia(Distancia distancia) {
        if (distancia == null) throw new IllegalArgumentException("La distancia no puede ser nula.");
        this.distancia = distancia;
    }

    /** @return la talla de camiseta. */
    public Talla getTalla() { return talla; }

    /**
     * Establece la talla seleccionada.
     * @param talla nueva talla.
     * @throws IllegalArgumentException si la talla es nula.
     */
    public void setTalla(Talla talla) {
        if (talla == null) throw new IllegalArgumentException("La talla no puede ser nula.");
        this.talla = talla;
    }

    /** @return el número de dorsal asignado. */
    public int getNumeroDorsal() { return numeroDorsal; }

    /**
     * Establece el número de dorsal (sin validar unicidad aquí).
     * @param numeroDorsal nuevo número de dorsal (> 0).
     * @throws IllegalArgumentException si el número de dorsal es menor o igual a 0.
     */
    public void setNumeroDorsal(int numeroDorsal) {
        if (numeroDorsal <= 0) throw new IllegalArgumentException("El número de dorsal debe ser mayor que 0.");
        this.numeroDorsal = numeroDorsal;
    }

    /** @return el estado actual de la inscripción. */
    public Estado getEstado() { return estado; }

    /** @return el corredor asociado. */
    public Corredor getCorredor() { return corredor; }

    /**
     * Asocia un nuevo corredor a la inscripción.
     * @param corredor nuevo corredor.
     * @throws IllegalArgumentException si el corredor es nulo.
     */
    public void setCorredor(Corredor corredor) {
        if (corredor == null) throw new IllegalArgumentException("El corredor no puede ser nulo.");
        this.corredor = corredor;
    }

    /** @return el evento asociado. */
    public Evento getEvento() { return evento; }

    /**
     * Asocia un nuevo evento a la inscripción.
     * @param evento nuevo evento.
     * @throws IllegalArgumentException si el evento es nulo.
     */
    public void setEvento(Evento evento) {
        if (evento == null) throw new IllegalArgumentException("El evento no puede ser nulo.");
        this.evento = evento;
    }

    // ------------------------------------------------------------
    // MÉTODOS DE UTILIDAD
    // ------------------------------------------------------------

    /**
     * Determina la igualdad de dos inscripciones a partir de su ID único.
     *
     * @param o objeto a comparar.
     * @return {@code true} si los IDs coinciden; {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inscripcion)) return false;
        Inscripcion that = (Inscripcion) o;
        return id == that.id;
    }

    /**
     * Calcula el código hash de la inscripción basado en su ID.
     * @return el valor hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Devuelve una representación legible de la inscripción.
     * @return cadena con los datos básicos de la inscripción.
     */
    @Override
    public String toString() {
        return "Inscripcion{" +
                "id=" + id +
                ", distancia=" + distancia +
                ", talla=" + talla +
                ", dorsal=" + numeroDorsal +
                ", estado=" + estado +
                ", corredor=" + (corredor != null ? corredor.getNombre() : "null") +
                ", evento=" + (evento != null ? evento.getNombre() : "null") +
                '}';
    }
}
