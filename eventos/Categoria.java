package eventos;

import java.util.Objects;

/**
 * La clase {@code Categoria} representa un rango etario para clasificar participantes
 * dentro de un evento (por ejemplo: Juvenil, Adulto, Máster).
 *
 * <p><b>Reglas de negocio:</b></p>
 * <ul>
 *   <li>El nombre no puede ser nulo ni vacío.</li>
 *   <li>Los límites de edad deben ser válidos: {@code edadMin >= 0}, {@code edadMax >= edadMin}.</li>
 *   <li>La categoría se considera igual si coincide su identidad estable (ver equals/hashCode).</li>
 * </ul>
 *
 * <p><b>Uso típico:</b> asociar múltiples categorías a un {@link Evento} para segmentar la
 * competencia por edades.</p>
 *
 * @author
 * @version 1.0
 */
public class Categoria {

    /** Identificador único de la categoría (opcional si no lo usas). */
    private final int id;

    /** Nombre de la categoría (p. ej., "Juvenil", "Adulto"). */
    private String nombre;

    /** Edad mínima (inclusive). Debe ser ≥ 0. */
    private int edadMin;

    /** Edad máxima (inclusive). Debe ser ≥ edadMin. */
    private int edadMax;

    /**
     * Crea una nueva {@code Categoria}.
     *
     * @param id       identificador único (&gt; 0).
     * @param nombre   nombre de la categoría (no nulo/ni vacío).
     * @param edadMin  edad mínima (≥ 0).
     * @param edadMax  edad máxima (≥ edadMin).
     * @throws IllegalArgumentException si alguno de los parámetros es inválido.
     */
    public Categoria(int id, String nombre, int edadMin, int edadMax) {
        if (id <= 0) throw new IllegalArgumentException("El id de la categoría debe ser > 0.");
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre de la categoría no puede ser nulo/vacío.");
        if (edadMin < 0) throw new IllegalArgumentException("La edad mínima debe ser ≥ 0.");
        if (edadMax < edadMin) throw new IllegalArgumentException("La edad máxima debe ser ≥ la edad mínima.");

        this.id = id;
        this.nombre = nombre.trim();
        this.edadMin = edadMin;
        this.edadMax = edadMax;
    }

    // ------------------------------------------------------------
    // Getters / Setters (con validación)
    // ------------------------------------------------------------

    /** @return id único de la categoría. */
    public int getId() { return id; }

    /** @return nombre de la categoría. */
    public String getNombre() { return nombre; }

    /**
     * Establece el nombre de la categoría.
     * @param nombre nuevo nombre (no nulo/ni vacío).
     * @throws IllegalArgumentException si {@code nombre} es nulo o vacío.
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre de la categoría no puede ser nulo/vacío.");
        this.nombre = nombre.trim();
    }

    /** @return edad mínima (inclusive). */
    public int getEdadMin() { return edadMin; }

    /**
     * Establece la edad mínima.
     * @param edadMin nueva edad mínima (≥ 0 y ≤ edadMax).
     * @throws IllegalArgumentException si {@code edadMin < 0} o {@code edadMin > edadMax}.
     */
    public void setEdadMin(int edadMin) {
        if (edadMin < 0) throw new IllegalArgumentException("La edad mínima debe ser ≥ 0.");
        if (edadMin > this.edadMax)
            throw new IllegalArgumentException("La edad mínima no puede superar a la edad máxima.");
        this.edadMin = edadMin;
    }

    /** @return edad máxima (inclusive). */
    public int getEdadMax() { return edadMax; }

    /**
     * Establece la edad máxima.
     * @param edadMax nueva edad máxima (≥ edadMin).
     * @throws IllegalArgumentException si {@code edadMax < edadMin}.
     */
    public void setEdadMax(int edadMax) {
        if (edadMax < this.edadMin)
            throw new IllegalArgumentException("La edad máxima debe ser ≥ la edad mínima.");
        this.edadMax = edadMax;
    }

    // ------------------------------------------------------------
    // Utilidad
    // ------------------------------------------------------------

    /**
     * Indica si una edad dada pertenece al rango de esta categoría.
     * @param edad edad a evaluar.
     * @return {@code true} si {@code edadMin ≤ edad ≤ edadMax}; {@code false} en caso contrario.
     */
    public boolean aceptaEdad(int edad) {
        return edad >= edadMin && edad <= edadMax;
    }

    // ------------------------------------------------------------
    // equals / hashCode / toString
    // ------------------------------------------------------------

    /**
     * Igualdad basada en el identificador de la categoría.
     * Si en tu proyecto no usas {@code id}, puedes ajustar a (nombre, edadMin, edadMax).
     *
     * @param o objeto a comparar.
     * @return {@code true} si los IDs coinciden; de lo contrario, {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        Categoria that = (Categoria) o;
        return id == that.id;
    }

    /**
     * Código hash basado en el ID.
     * @return valor hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Representación legible de la categoría.
     * @return cadena con los datos básicos.
     */
    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edadMin=" + edadMin +
                ", edadMax=" + edadMax +
                '}';
    }
}
