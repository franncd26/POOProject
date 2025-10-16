package usuarios;

import eventos.Evento;
import eventos.Inscripcion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * La clase {@code Corredor} representa a un usuario participante en eventos.
 * Administra sus inscripciones y utilidades para consultar su estado.
 *
 * <p><b>Reglas/consideraciones:</b></p>
 * <ul>
 *   <li>Sin I/O (nada de Scanner/println) dentro de la entidad.</li>
 *   <li>Evitar inscripciones duplicadas del mismo evento para el mismo corredor.</li>
 *   <li>La unicidad del dorsal se valida en {@link Evento}.</li>
 * </ul>
 *
 * @author
 * @version 1.0
 */
public class Corredor extends Usuario {

    /** Lista de inscripciones del corredor. */
    private final List<Inscripcion> inscripciones = new ArrayList<>();

    /**
     * Crea un nuevo {@code Corredor}.
     *
     * @param id       identificador único (&gt; 0).
     * @param nombre   nombre completo (no nulo/ni vacío).
     * @param telefono teléfono (no nulo; puede ser vacío).
     * @param correo   correo (no nulo; puede ser vacío).
     */
    public Corredor(int id, String nombre, String telefono, String correo) {
        super(id, nombre, telefono, correo);
    }

    // ------------------------------------------------------------
    // Gestión de inscripciones (sin I/O)
    // ------------------------------------------------------------

    /**
     * Agrega una inscripción a la lista del corredor.
     * <p>Reglas:</p>
     * <ul>
     *   <li>No se permiten inscripciones nulas.</li>
     *   <li>La inscripción debe pertenecer a este corredor.</li>
     *   <li>No se permiten dos inscripciones al <b>mismo evento</b> para el mismo corredor.</li>
     * </ul>
     *
     * @param inscripcion inscripción a agregar.
     * @throws IllegalArgumentException si la inscripción es nula o pertenece a otro corredor.
     * @throws IllegalStateException si ya existe una inscripción a ese evento.
     */
    public void agregarInscripcion(Inscripcion inscripcion) {
        if (inscripcion == null) throw new IllegalArgumentException("La inscripción no puede ser nula.");
        if (inscripcion.getCorredor() != this)
            throw new IllegalArgumentException("La inscripción debe pertenecer a este corredor.");

        Evento ev = inscripcion.getEvento();
        if (ev == null) throw new IllegalArgumentException("La inscripción debe referenciar un evento.");

        if (tieneInscripcionEnEvento(ev)) {
            throw new IllegalStateException("Ya existe una inscripción del corredor en este evento.");
        }
        inscripciones.add(inscripcion);
    }

    /**
     * Indica si el corredor ya posee una inscripción para un evento dado.
     * @param evento evento a verificar (no nulo).
     * @return {@code true} si ya está inscrito; de lo contrario, {@code false}.
     * @throws IllegalArgumentException si {@code evento} es nulo.
     */
    public boolean tieneInscripcionEnEvento(Evento evento) {
        if (evento == null) throw new IllegalArgumentException("Evento no puede ser nulo.");
        for (Inscripcion i : inscripciones) {
            if (i != null && i.getEvento() == evento) return true;
        }
        return false;
    }

    /**
     * Devuelve una vista inmutable de las inscripciones del corredor.
     * @return lista inmutable de inscripciones.
     */
    public List<Inscripcion> getInscripciones() {
        return Collections.unmodifiableList(inscripciones);
    }

    /**
     * Obtiene las inscripciones filtradas por estado.
     * @param estado estado a filtrar (no nulo).
     * @return lista inmutable con las inscripciones en ese estado.
     */
    public List<Inscripcion> getInscripcionesPorEstado(Inscripcion.Estado estado) {
        if (estado == null) throw new IllegalArgumentException("Estado no puede ser nulo.");
        List<Inscripcion> res = new ArrayList<>();
        for (Inscripcion i : inscripciones) {
            if (i != null && i.getEstado() == estado) res.add(i);
        }
        return Collections.unmodifiableList(res);
    }

    /**
     * Elimina una inscripción del corredor por ID.
     * @param idInscripcion id a eliminar.
     * @return {@code true} si se eliminó; {@code false} si no existía.
     */
    public boolean removerInscripcionPorId(int idInscripcion) {
        for (int idx = 0; idx < inscripciones.size(); idx++) {
            Inscripcion i = inscripciones.get(idx);
            if (i != null && i.getId() == idInscripcion) {
                inscripciones.remove(idx);
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------
    // equals / hashCode / toString (hereda de Usuario)
    // ------------------------------------------------------------

    /**
     * Representación legible del corredor.
     * @return cadena con id y nombre.
     */
    @Override
    public String toString() {
        return "Corredor{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", inscripciones=" + inscripciones.size() +
                '}';
    }
}
