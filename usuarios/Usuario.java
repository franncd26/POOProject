package usuarios;

import java.util.Objects;

/**
 * La clase {@code Usuario} representa una persona registrada en el sistema.
 * Es la base para perfiles como {@link Corredor} o {@link Administrador}.
 *
 * <p><b>Reglas/convenciones:</b></p>
 * <ul>
 *   <li>Sin operaciones de entrada/salida (I/O) dentro de la entidad.</li>
 *   <li>Validaciones mínimas en setters/constructor para mantener invariantes.</li>
 *   <li>Identidad basada en {@code id} (entero positivo y estable).</li>
 * </ul>
 *
 * @author
 * @version 1.0
 */
public class Usuario {

    /** Identificador único del usuario (&gt; 0). */
    private int id;

    /** Nombre completo del usuario (no nulo/ni vacío). */
    private String nombre;

    /** Teléfono de contacto (permitido vacío, pero no nulo). */
    private String telefono;

    /** Correo electrónico (permitido vacío, pero no nulo). */
    private String correo;

    /**
     * Crea un nuevo {@code Usuario}.
     *
     * @param id       identificador único (&gt; 0).
     * @param nombre   nombre completo (no nulo/ni vacío).
     * @param telefono teléfono (puede ser vacío, no nulo).
     * @param correo   correo (puede ser vacío, no nulo).
     * @throws IllegalArgumentException si algún parámetro viola las reglas.
     */
    public Usuario(int id, String nombre, String telefono, String correo) {
        setId(id);
        setNombre(nombre);
        setTelefono(telefono == null ? "" : telefono);
        setCorreo(correo == null ? "" : correo);
    }

    // ------------------------------------------------------------
    // Getters / Setters (con validación)
    // ------------------------------------------------------------

    /** @return identificador único del usuario. */
    public int getId() { return id; }

    /**
     * Establece el identificador único del usuario.
     * @param id valor &gt; 0.
     * @throws IllegalArgumentException si {@code id <= 0}.
     */
    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("El id debe ser > 0.");
        this.id = id;
    }

    /** @return nombre del usuario. */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre del usuario.
     * @param nombre no nulo/ni vacío.
     * @throws IllegalArgumentException si es nulo/vacío.
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre no puede ser nulo/vacío.");
        this.nombre = nombre.trim();
    }

    /** @return teléfono (posible cadena vacía). */
    public String getTelefono() { return telefono; }

    /**
     * Establece el teléfono del usuario.
     * @param telefono no nulo (permitido vacío).
     * @throws IllegalArgumentException si es nulo.
     */
    public void setTelefono(String telefono) {
        if (telefono == null) throw new IllegalArgumentException("El teléfono no puede ser nulo (use \"\").");
        this.telefono = telefono.trim();
    }

    /** @return correo (posible cadena vacía). */
    public String getCorreo() { return correo; }

    /**
     * Establece el correo del usuario.
     * @param correo no nulo (permitido vacío).
     * @throws IllegalArgumentException si es nulo.
     */
    public void setCorreo(String correo) {
        if (correo == null) throw new IllegalArgumentException("El correo no puede ser nulo (use \"\").");
        this.correo = correo.trim();
    }

    // ------------------------------------------------------------
    // Métodos de dominio (sin I/O)
    // ------------------------------------------------------------

    /**
     * Actualiza datos básicos del usuario.
     * <p>No realiza I/O; la validación se delega a los setters.</p>
     *
     * @param nombre   nuevo nombre (no nulo/ni vacío).
     * @param telefono nuevo teléfono (no nulo).
     * @param correo   nuevo correo (no nulo).
     */
    public void actualizarDatos(String nombre, String telefono, String correo) {
        setNombre(nombre);
        setTelefono(telefono);
        setCorreo(correo);
    }

    // ------------------------------------------------------------
    // equals / hashCode / toString
    // ------------------------------------------------------------

    /**
     * Igualdad basada en el identificador único {@code id}.
     * @param o objeto a comparar.
     * @return true si los IDs coinciden.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id;
    }

    /**
     * Código hash consistente con {@link #equals(Object)}.
     * @return valor hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Representación legible del usuario (sin datos sensibles).
     * @return cadena con campos clave.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
