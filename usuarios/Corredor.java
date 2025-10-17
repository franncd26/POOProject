package usuarios;

import eventos.Categoria;
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
 * @version 1.2
 */
public class Corredor extends Usuario {

    /** Lista de inscripciones del corredor. */
    private final List<Inscripcion> inscripciones = new ArrayList<>();

    /** Edad del corredor en años (0–127). Usa {@code byte} para menor huella de memoria. */
    private byte edad;

    /** Nombre del contacto de emergencia del corredor. */
    private String nombreContactoEmergencia;

    /** Parentesco del contacto de emergencia con el corredor (p. ej. Madre, Padre, Esposo/a). */
    private String parentescoContactoEmergencia;

    /** Teléfono del contacto de emergencia del corredor. */
    private String telefonoContactoEmergencia;

    // =======================
    // Constructores
    // =======================

    /**
     * Crea un nuevo {@code Corredor}.
     *
     * @param id       identificador único (&gt; 0).
     * @param nombre   nombre del corredor (no nulo/ni en blanco).
     * @param correo   correo del corredor (no nulo/ni en blanco).
     * @param telefono teléfono del corredor (opcional).
     * @param edad     edad en años (0–127).
     */
    public Corredor(int id, String nombre, String correo, String telefono, byte edad) {
        super(id, nombre, correo, telefono);
        setEdad(edad);
    }

    /**
     * Constructor alterno sin contacto de emergencia (se puede completar luego con setters).
     *
     * @param id       identificador único (&gt; 0).
     * @param nombre   nombre del corredor.
     * @param correo   correo del corredor.
     * @param edad     edad en años (0–127).
     */
    public Corredor(int id, String nombre, String correo, byte edad) {
        super(id, nombre, correo, null);
        setEdad(edad);
    }

    /**
     * Constructor de compatibilidad para no romper flujos existentes donde aún no se solicita la edad.
     * <p>La edad deberá establecerse luego con {@link #setEdad(byte)}.</p>
     *
     * @param id       identificador único (&gt; 0).
     * @param nombre   nombre del corredor.
     * @param telefono teléfono del corredor (opcional).
     * @param correo   correo del corredor (opcional).
     */
    public Corredor(int id, String nombre, String telefono, String correo) {
        super(id, nombre, correo, telefono);
        // La edad quedará con su valor por defecto (0) hasta que se llame a setEdad(...)
    }

    // =======================
    // Getters / Setters
    // =======================

    /**
     * Devuelve la edad del corredor.
     * @return edad en años (0–127).
     */
    public byte getEdad() {
        return edad;
    }

    /**
     * Establece la edad del corredor.
     * <p>Debe estar en el rango 0–127.</p>
     * @param edad edad en años.
     * @throws IllegalArgumentException si la edad es negativa.
     */
    public void setEdad(byte edad) {
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa.");
        }
        this.edad = edad;
    }

    /**
     * Nombre del contacto de emergencia.
     * @return nombre (puede ser {@code null} si no se ha definido).
     */
    public String getNombreContactoEmergencia() {
        return nombreContactoEmergencia;
    }

    /**
     * Define el nombre del contacto de emergencia.
     * @param nombreContactoEmergencia nombre del contacto (puede ser {@code null}).
     */
    public void setNombreContactoEmergencia(String nombreContactoEmergencia) {
        this.nombreContactoEmergencia = nombreContactoEmergencia;
    }

    /**
     * Parentesco del contacto de emergencia.
     * @return parentesco (puede ser {@code null} si no se ha definido).
     */
    public String getParentescoContactoEmergencia() {
        return parentescoContactoEmergencia;
    }

    /**
     * Define el parentesco del contacto de emergencia.
     * @param parentescoContactoEmergencia parentesco (p. ej. Madre, Padre, Hermano, Esposo/a).
     */
    public void setParentescoContactoEmergencia(String parentescoContactoEmergencia) {
        this.parentescoContactoEmergencia = parentescoContactoEmergencia;
    }

    /**
     * Teléfono del contacto de emergencia.
     * @return teléfono (puede ser {@code null} si no se ha definido).
     */
    public String getTelefonoContactoEmergencia() {
        return telefonoContactoEmergencia;
    }

    /**
     * Define el teléfono del contacto de emergencia.
     * @param telefonoContactoEmergencia número telefónico del contacto (puede ser {@code null}).
     */
    public void setTelefonoContactoEmergencia(String telefonoContactoEmergencia) {
        this.telefonoContactoEmergencia = telefonoContactoEmergencia;
    }

    /**
     * Devuelve una vista inmutable de las inscripciones del corredor.
     * @return lista inmutable de inscripciones.
     */
    public List<Inscripcion> getInscripciones() {
        return Collections.unmodifiableList(inscripciones);
    }

    // =======================
    // Lógica de validación de categoría por edad
    // =======================

    /**
     * Verifica si el corredor puede inscribirse a un rango de edad dado.
     *
     * @param edadMin edad mínima inclusive.
     * @param edadMax edad máxima inclusive.
     * @return {@code true} si {@code edadMin <= edad <= edadMax}; {@code false} en caso contrario.
     */
    public boolean puedeInscribirseEn(int edadMin, int edadMax) {
        int e = Byte.toUnsignedInt(this.edad);
        return e >= edadMin && e <= edadMax;
    }

    /**
     * Verifica si el corredor puede inscribirse a la {@link Categoria} indicada según su edad.
     * <p>Usa los getters {@code getEdadMin()} y {@code getEdadMax()} esperados en {@link Categoria}.</p>
     *
     * @param categoria categoría objetivo (no {@code null}).
     * @return {@code true} si la edad del corredor está en el rango de la categoría.
     * @throws IllegalArgumentException si {@code categoria} es {@code null}.
     */
    public boolean puedeInscribirseEn(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría no puede ser null.");
        }
        int min = categoria.getEdadMin();
        int max = categoria.getEdadMax();
        return puedeInscribirseEn(min, max);
    }

    // =======================
    // (Opcional) Utilidades internas de inscripciones
    // =======================

    /**
     * Agrega una inscripción si no existe otra del mismo evento para este corredor.
     * <p>No realiza I/O ni valida pagos; la unicidad de dorsal corresponde al {@link Evento}.</p>
     *
     * @param inscripcion la inscripción a agregar.
     * @return {@code true} si se agregó; {@code false} si ya existía una inscripción al mismo evento.
     * @throws IllegalArgumentException si {@code inscripcion} es {@code null}.
     */
    public boolean agregarInscripcion(Inscripcion inscripcion) {
        if (inscripcion == null) throw new IllegalArgumentException("La inscripción no puede ser null.");
        for (Inscripcion i : inscripciones) {
            if (i.getEvento() != null && i.getEvento().equals(inscripcion.getEvento())) {
                return false; // ya existe una inscripción para ese evento
            }
        }
        return inscripciones.add(inscripcion);
    }
}
