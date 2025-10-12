/**
 * Resultado oficial de una inscripción en un evento.
 *
 * Propósito:
 *  - Guardar el tiempoIndividual y posiciones (general y por categoría).
 *
 * Invariantes:
 *  - tiempoIndividual > 0 cuando el tiempo esté registrado.
 *  - posicionGeneral y posicionCategoria ≥ 1 cuando existan.
 *  - Pertenece a una Inscripcion y al mismo Evento de esa Inscripcion;
 *    la Categoría debe corresponder a la edad/sexo del corredor.
 *
 * Responsabilidades:
 *  - getTiempo(): devolver el valor real del tiempo registrado (no un placeholder).
 */
package eventos;

public class Tiempo {
    // Atributos de Tiempo
    private double tiempoIndividual;
    private int posicionGeneral;
    private int posicionCategoria;

    // Constructor
    public Tiempo(double tiempoIndividual, int posicionGeneral, int posicionCategoria) {
        this.tiempoIndividual = tiempoIndividual;
        this.posicionGeneral = posicionGeneral;
        this.posicionCategoria = posicionCategoria;
    }

    // Getters y Setters
    public double getTiempoIndividual() {
        return tiempoIndividual;
    }

    public void setTiempoIndividual(double tiempoIndividual) {
        this.tiempoIndividual = tiempoIndividual;
    }

    public int getPosicionGeneral() {
        return posicionGeneral;
    }

    public void setPosicionGeneral(int posicionGeneral) {
        this.posicionGeneral = posicionGeneral;
    }

    public int getPosicionCategoria() {
        return posicionCategoria;
    }

    public void setPosicionCategoria(int posicionCategoria) {
        this.posicionCategoria = posicionCategoria;
    }

    // Métodos específicos de Tiempo
    /**
     * Devuelve el tiempo registrado para la inscripción.
     * - Si el tiempo aún no está registrado, se debe manejar (p.ej., devolver null/Optional o
     *   documentar el comportamiento en el proyecto; nunca retornar un 0.0 “falso”).
     */
    public double getTiempo() { return 0.0; }
}
