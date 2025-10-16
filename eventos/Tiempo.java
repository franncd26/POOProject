package eventos;

import java.util.Objects;

/**
 * La clase {@code Tiempo} modela el desempeño cronometrado de un participante
 * en un evento: tiempo individual total y posiciones (general y por categoría).
 *
 * <p><b>Reglas de negocio / validaciones:</b></p>
 * <ul>
 *   <li>{@code tiempoIndividual} debe ser &ge; 0 (en segundos).</li>
 *   <li>{@code posicionGeneral} y {@code posicionCategoria} deben ser &ge; 1 cuando existan; si no aplica aún, usar 0.</li>
 *   <li>No hay I/O de consola en esta entidad.</li>
 * </ul>
 *
 * <p><b>Notas:</b> Esta clase es deliberadamente simple y autónoma. Si en tu
 * diseño el tiempo está asociado a una {@link Inscripcion} o a un corredor,
 * ese vínculo se gestiona fuera de esta clase (en la entidad contenedora).</p>
 *
 * @author
 * @version 1.0
 */
public class Tiempo implements Comparable<Tiempo> {

    /** Tiempo total individual en segundos (&ge; 0). */
    private double tiempoIndividual;

    /** Posición general (1 = primero). Usar 0 mientras no esté asignada. */
    private int posicionGeneral;

    /** Posición dentro de la categoría (1 = primero). Usar 0 mientras no esté asignada. */
    private int posicionCategoria;

    /**
     * Crea un nuevo {@code Tiempo}.
     *
     * @param tiempoIndividual tiempo total en segundos (&ge; 0).
     * @param posicionGeneral  posición general (&ge; 1) o 0 si no aplica aún.
     * @param posicionCategoria posición por categoría (&ge; 1) o 0 si no aplica aún.
     * @throws IllegalArgumentException si los parámetros violan las reglas de validación.
     */
    public Tiempo(double tiempoIndividual, int posicionGeneral, int posicionCategoria) {
        setTiempoIndividual(tiempoIndividual);
        setPosicionGeneral(posicionGeneral);
        setPosicionCategoria(posicionCategoria);
    }

    // ------------------------------------------------------------
    // Getters / Setters (con validación)
    // ------------------------------------------------------------

    /**
     * @return tiempo total individual en segundos.
     */
    public double getTiempoIndividual() {
        return tiempoIndividual;
    }

    /**
     * Establece el tiempo total individual (en segundos).
     *
     * @param tiempoIndividual valor &ge; 0.
     * @throws IllegalArgumentException si {@code tiempoIndividual < 0}.
     */
    public void setTiempoIndividual(double tiempoIndividual) {
        if (tiempoIndividual < 0) {
            throw new IllegalArgumentException("El tiempo individual debe ser >= 0 segundos.");
        }
        this.tiempoIndividual = tiempoIndividual;
    }

    /**
     * @return posición general (1 = primero). 0 si aún no asignada.
     */
    public int getPosicionGeneral() {
        return posicionGeneral;
    }

    /**
     * Establece la posición general.
     *
     * @param posicionGeneral &ge; 1, o 0 si aún no asignada.
     * @throws IllegalArgumentException si {@code posicionGeneral < 0} o {@code posicionGeneral == 0} cuando ya pretendas fijarla.
     */
    public void setPosicionGeneral(int posicionGeneral) {
        if (posicionGeneral < 0) {
            throw new IllegalArgumentException("La posición general no puede ser negativa.");
        }
        // 0 es permitido como "no asignada" (pendiente).
        if (posicionGeneral == 0) {
            this.posicionGeneral = 0;
        } else if (posicionGeneral < 1) {
            throw new IllegalArgumentException("La posición general debe ser >= 1.");
        } else {
            this.posicionGeneral = posicionGeneral;
        }
    }

    /**
     * @return posición por categoría (1 = primero). 0 si aún no asignada.
     */
    public int getPosicionCategoria() {
        return posicionCategoria;
    }

    /**
     * Establece la posición por categoría.
     *
     * @param posicionCategoria &ge; 1, o 0 si aún no asignada.
     * @throws IllegalArgumentException si {@code posicionCategoria < 0} o {@code posicionCategoria == 0} cuando ya pretendas fijarla.
     */
    public void setPosicionCategoria(int posicionCategoria) {
        if (posicionCategoria < 0) {
            throw new IllegalArgumentException("La posición por categoría no puede ser negativa.");
        }
        // 0 es permitido como "no asignada" (pendiente).
        if (posicionCategoria == 0) {
            this.posicionCategoria = 0;
        } else if (posicionCategoria < 1) {
            throw new IllegalArgumentException("La posición por categoría debe ser >= 1.");
        } else {
            this.posicionCategoria = posicionCategoria;
        }
    }

    // ------------------------------------------------------------
    // Utilidades
    // ------------------------------------------------------------

    /**
     * Devuelve el tiempo formateado como {@code HH:MM:SS.mmm} (con milisegundos).
     *
     * @return cadena formateada del tiempo individual.
     */
    public String getTiempoFormateado() {
        return formatearSegundos(tiempoIndividual);
    }

    /**
     * Convierte segundos a una representación {@code HH:MM:SS.mmm}.
     *
     * @param segundos valor en segundos (&ge; 0).
     * @return cadena formateada.
     * @throws IllegalArgumentException si {@code segundos < 0}.
     */
    public static String formatearSegundos(double segundos) {
        if (segundos < 0) throw new IllegalArgumentException("Los segundos deben ser >= 0.");
        long totalMillis = Math.round(segundos * 1000.0);
        long hours = totalMillis / 3_600_000;
        long rem = totalMillis % 3_600_000;
        long minutes = rem / 60_000;
        rem = rem % 60_000;
        long secs = rem / 1000;
        long millis = rem % 1000;
        return String.format("%02d:%02d:%02d.%03d", hours, minutes, secs, millis);
    }

    // ------------------------------------------------------------
    // Comparable / equals / hashCode / toString
    // ------------------------------------------------------------

    /**
     * Orden natural por {@code tiempoIndividual} ascendente.
     * @param o otro tiempo.
     * @return negativo/0/positivo según corresponda.
     */
    @Override
    public int compareTo(Tiempo o) {
        return Double.compare(this.tiempoIndividual, o.tiempoIndividual);
    }

    /**
     * Igualdad basada en los tres atributos básicos.
     * Si luego agregas un ID estable, cambia esta definición a ese ID.
     *
     * @param o objeto a comparar.
     * @return {@code true} si todos los campos relevantes coinciden.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tiempo)) return false;
        Tiempo tiempo = (Tiempo) o;
        return Double.compare(tiempo.tiempoIndividual, tiempoIndividual) == 0 &&
               posicionGeneral == tiempo.posicionGeneral &&
               posicionCategoria == tiempo.posicionCategoria;
    }

    /**
     * Código hash consistente con {@link #equals(Object)}.
     * @return valor hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(tiempoIndividual, posicionGeneral, posicionCategoria);
    }

    /**
     * Representación legible del tiempo.
     * @return cadena con campos clave.
     */
    @Override
    public String toString() {
        return "Tiempo{" +
                "tiempoIndividual=" + getTiempoFormateado() +
                ", posicionGeneral=" + posicionGeneral +
                ", posicionCategoria=" + posicionCategoria +
                '}';
    }
}
