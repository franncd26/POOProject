package eventos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * La clase {@code Evento} modela una competición con fecha, categorías e inscripciones.
 * Centraliza reglas de negocio como la unicidad del dorsal por evento y el control de estado.
 *
 * <p><b>Reglas de negocio:</b></p>
 * <ul>
 *     <li>Unicidad de dorsal: en un mismo evento, dos inscripciones no pueden compartir dorsal.</li>
 *     <li>Las inscripciones asociadas deben referenciar este mismo evento.</li>
 *     <li>Estados de evento: PLANIFICADO, ABIERTO, CERRADO, FINALIZADO.</li>
 *     <li>Sin lectura/escritura por consola dentro de la entidad.</li>
 * </ul>
 *
 * @author
 * @version 1.0
 */
public class Evento {

    /**
     * Estados válidos para el ciclo de vida del evento.
     */
    public enum EstadoEvento { PLANIFICADO, ABIERTO, CERRADO, FINALIZADO }

    /** Identificador único del evento. */
    private final int id;

    /** Nombre del evento. */
    private String nombre;

    /** Fecha del evento. */
    private Date fecha;

    /** Descripción breve del evento. */
    private String descripcion;

    /** Estado actual del evento. */
    private EstadoEvento estado = EstadoEvento.PLANIFICADO;

    /** Categorías del evento. */
    private List<Categoria> categorias = new ArrayList<>();

    /** Inscripciones registradas en el evento. */
    private List<Inscripcion> inscripciones = new ArrayList<>();

    /**
     * Crea un nuevo {@code Evento}.
     *
     * @param id           identificador único (> 0).
     * @param nombre       nombre del evento (no nulo/ni vacío).
     * @param fecha        fecha del evento (no nula).
     * @param descripcion  descripción (puede ser nula o vacía).
     * @param categorias   lista inicial de categorías (puede ser nula; se copia defensivamente).
     * @throws IllegalArgumentException si {@code id <= 0}, nombre/fecha inválidos.
     */
    public Evento(int id, String nombre, Date fecha, String descripcion, List<Categoria> categorias) {
        if (id <= 0) throw new IllegalArgumentException("El id de evento debe ser > 0.");
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("El nombre no puede ser nulo/vacío.");
        if (fecha == null) throw new IllegalArgumentException("La fecha no puede ser nula.");

        this.id = id;
        this.nombre = nombre.trim();
        this.fecha = new Date(fecha.getTime()); // copia defensiva
        this.descripcion = (descripcion == null) ? "" : descripcion.trim();
        if (categorias != null) this.categorias = new ArrayList<>(categorias);
    }

    // ------------------------------------------------------------
    // Reglas de negocio / Inscripciones
    // ------------------------------------------------------------

    /**
     * Verifica si un dorsal ya está asignado dentro del evento.
     *
     * @param dorsal número de dorsal a verificar.
     * @return {@code true} si existe una inscripción con ese dorsal; {@code false} en caso contrario.
     */
    public boolean existeDorsal(int dorsal) {
        if (dorsal <= 0) return false;
        for (Inscripcion i : inscripciones) {
            if (i != null && i.getNumeroDorsal() == dorsal) return true;
        }
        return false;
    }

    /**
     * Agrega una inscripción al evento, validando unicidad de dorsal y consistencia de referencia.
     *
     * @param ins inscripción a agregar (no nula).
     * @throws IllegalArgumentException si la inscripción es nula o referencia a otro evento.
     * @throws IllegalStateException si el dorsal ya está en uso en este evento.
     */
    public void agregarInscripcion(Inscripcion ins) {
        if (ins == null) throw new IllegalArgumentException("La inscripción no puede ser nula.");
        if (ins.getEvento() != this) {
            throw new IllegalArgumentException("La inscripción debe referenciar este mismo evento.");
        }
        if (existeDorsal(ins.getNumeroDorsal())) {
            throw new IllegalStateException("Dorsal ya utilizado en este evento: " + ins.getNumeroDorsal());
        }
        inscripciones.add(ins);
    }

    /**
     * Elimina una inscripción por su ID.
     *
     * @param idInscripcion id de la inscripción a eliminar.
     * @return {@code true} si se eliminó; {@code false} si no se encontró.
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

    /**
     * Obtiene una lista inmutable de inscripciones filtrada por estado.
     *
     * @param estado estado a filtrar (no nulo).
     * @return lista inmutable con las inscripciones en el estado indicado.
     * @throws IllegalArgumentException si el estado es nulo.
     */
    public List<Inscripcion> obtenerInscripcionesPorEstado(Inscripcion.Estado estado) {
        if (estado == null) throw new IllegalArgumentException("El estado no puede ser nulo.");
        List<Inscripcion> res = inscripciones.stream()
                .filter(i -> i != null && i.getEstado() == estado)
                .collect(Collectors.toList());
        return Collections.unmodifiableList(res);
    }

    /**
     * Genera un resumen textual con métricas clave del evento.
     * Incluye: total de inscripciones y cuántas están pagadas/confirmadas.
     *
     * @return cadena resumen.
     */
    public String generarResumenInscripciones() {
        int total = inscripciones.size();
        long pagadas = inscripciones.stream()
                .filter(i -> i != null && (i.getEstado() == Inscripcion.Estado.PAGADO || i.getEstado() == Inscripcion.Estado.CONFIRMADO))
                .count();
        long confirmadas = inscripciones.stream()
                .filter(i -> i != null && i.getEstado() == Inscripcion.Estado.CONFIRMADO)
                .count();
        return "Evento '" + nombre + "' => Total: " + total +
                " | Pagadas/Confirmadas: " + pagadas +
                " | Confirmadas: " + confirmadas;
    }

    // ------------------------------------------------------------
    // Getters / Setters (defensivos)
    // ------------------------------------------------------------

    /** @return id único del evento. */
    public int getId() { return id; }

    /** @return nombre del evento. */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre del evento.
     * @param nombre nuevo nombre (no nulo/vacío).
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("El nombre no puede ser nulo/vacío.");
        this.nombre = nombre.trim();
    }

    /** @return fecha del evento (copia defensiva). */
    public Date getFecha() { return new Date(fecha.getTime()); }

    /**
     * Establece la fecha del evento.
     * @param fecha nueva fecha (no nula).
     */
    public void setFecha(Date fecha) {
        if (fecha == null) throw new IllegalArgumentException("La fecha no puede ser nula.");
        this.fecha = new Date(fecha.getTime());
    }

    /** @return descripción del evento (puede ser vacía). */
    public String getDescripcion() { return descripcion; }

    /**
     * Establece la descripción del evento.
     * @param descripcion nueva descripción (puede ser nula; se normaliza a cadena vacía).
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = (descripcion == null) ? "" : descripcion.trim();
    }

    /** @return estado actual del evento. */
    public EstadoEvento getEstado() { return estado; }

    /**
     * Establece el estado del evento.
     * @param estado nuevo estado (no nulo).
     */
    public void setEstado(EstadoEvento estado) {
        if (estado == null) throw new IllegalArgumentException("El estado no puede ser nulo.");
        this.estado = estado;
    }

    /**
     * Devuelve una vista inmutable de las categorías.
     * @return lista inmutable de categorías.
     */
    public List<Categoria> getCategorias() {
        return Collections.unmodifiableList(categorias);
    }

    /**
     * Reemplaza las categorías del evento (copia defensiva).
     * @param categorias nuevas categorías (si es nula, se deja lista vacía).
     */
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = (categorias == null) ? new ArrayList<>() : new ArrayList<>(categorias);
    }

    /**
     * Agrega una categoría (si no existe ya por igualdad).
     * @param categoria categoría a agregar (no nula).
     * @return {@code true} si se agregó; {@code false} si ya existía.
     */
    public boolean agregarCategoria(Categoria categoria) {
        if (categoria == null) throw new IllegalArgumentException("La categoría no puede ser nula.");
        if (!categorias.contains(categoria)) {
            return categorias.add(categoria);
        }
        return false;
        }

    /**
     * Devuelve una vista inmutable de las inscripciones.
     * @return lista inmutable de inscripciones.
     */
    public List<Inscripcion> getInscripciones() {
        return Collections.unmodifiableList(inscripciones);
    }

    /**
     * Reemplaza la lista completa de inscripciones (se valida consistencia y unicidad de dorsal).
     * <p>Úsalo con cuidado: si alguna inscripción viola reglas, se lanza excepción y no se modifica el estado.</p>
     *
     * @param nuevasInscripciones lista a establecer (no nula).
     * @throws IllegalArgumentException si la lista es nula o alguna inscripción referencia otro evento.
     * @throws IllegalStateException si hay dorsales duplicados.
     */
    public void setInscripciones(List<Inscripcion> nuevasInscripciones) {
        if (nuevasInscripciones == null) throw new IllegalArgumentException("La lista no puede ser nula.");
        // Validar consistencia y unicidad
        List<Inscripcion> copia = new ArrayList<>(nuevasInscripciones);
        // Consistencia de evento
        for (Inscripcion i : copia) {
            if (i == null) continue;
            if (i.getEvento() != this) {
                throw new IllegalArgumentException("Todas las inscripciones deben referenciar este evento.");
            }
        }
        // Unicidad de dorsal
        List<Integer> dorsales = copia.stream()
                .filter(Objects::nonNull)
                .map(Inscripcion::getNumeroDorsal)
                .collect(Collectors.toList());
        if (dorsales.size() != dorsales.stream().distinct().count()) {
            throw new IllegalStateException("Se detectaron dorsales duplicados.");
        }
        this.inscripciones = copia;
    }

    // ------------------------------------------------------------
    // equals / hashCode / toString
    // ------------------------------------------------------------

    /**
     * Igualdad basada en el identificador único del evento.
     * @param o objeto a comparar.
     * @return {@code true} si los IDs coinciden; de lo contrario, {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Evento)) return false;
        Evento evento = (Evento) o;
        return id == evento.id;
    }

    /**
     * Código hash basado en el ID del evento.
     * @return valor hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Representación legible del evento.
     * @return cadena con datos básicos del evento.
     */
    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", estado=" + estado +
                ", inscripciones=" + inscripciones.size() +
                '}';
    }
}
