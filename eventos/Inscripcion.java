/**
 * Vínculo entre un Corredor y un Evento, con datos operativos de participación.
 *
 * Propósito:
 *  - Registrar la distancia, talla de camiseta, dorsal y estado del pago.
 *
 * Invariantes:
 *  - corredor != null y evento != null.
 *  - distancia y tallaCamiseta definidos (enums).
 *  - numeroDorsal > 0 y único dentro del mismo Evento.
 *  - Puede tener 0 o 1 Tiempo asociado (antes/después de la carrera).
 *
 * Responsabilidades:
 *  - confirmarPago(): transición válida de estado (PENDIENTE → PAGADO), evitando doble pago y validando datos.
 */
package eventos;

// Importaciones necesarias
import usuarios.Corredor;

public class Inscripcion {
    // Enums para distancia y talla de camiseta
    public enum Distancia { CINCO_K, DIEZ_K, VEINTIUNO_K, TREINTA_Y_DOS_K, CUARENTA_Y_DOS_K }
    public enum Talla { XS, S, M, L, XL }

    // Atributos de Inscripcion
    private int id;
    private Distancia distancia;
    private Talla tallaCamiseta;
    private int numeroDorsal;
    private String estado;
    private Corredor corredor;
    private Evento evento;
    private Tiempo tiempo;

    // Estados válidos de la inscripción 
    public static final String ESTADO_PENDIENTE = "PENDIENTE";
    public static final String ESTADO_PAGADO    = "PAGADO";
    public static final String ESTADO_CANCELADO = "CANCELADO";

    // Relaciones con Corredor, Evento y Tiempo 

    public Corredor getCorredor() {
        return corredor;
    }

    public void setCorredor(Corredor corredor) {
        this.corredor = corredor;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Tiempo getTiempo() {
        return tiempo;
    }

    /** Mantiene coherencia con Tiempo (relación bidireccional). */
    public void setTiempo(Tiempo tiempo) {
        this.tiempo = tiempo;

        if (tiempo != null) {
            if (tiempo.getInscripcion() != this) {
                tiempo.setInscripcion(this);
            }
            if (this.evento != null && tiempo.getEvento() != this.evento) {
                tiempo.setEvento(this.evento);
            }
        }
    }

    // Constructor
    public Inscripcion(int id, Distancia distancia, Talla tallaCamiseta, int numeroDorsal,
                       String estado, Corredor corredor, Evento evento, Tiempo tiempo) {
        this.id = id;
        this.distancia = distancia;
        this.tallaCamiseta = tallaCamiseta;
        this.numeroDorsal = numeroDorsal;
        this.estado = estado;
        this.corredor = corredor;
        this.evento = evento;
        this.tiempo = tiempo;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Distancia getDistancia() {
        return distancia;
    }

    public void setDistancia(Distancia distancia) {
        this.distancia = distancia;
    }

    public Talla getTallaCamiseta() {
        return tallaCamiseta;
    }

    public void setTallaCamiseta(Talla tallaCamiseta) {
        this.tallaCamiseta = tallaCamiseta;
    }

    public int getNumeroDorsal() {
        return numeroDorsal;
    }

    public void setNumeroDorsal(int numeroDorsal) {
        this.numeroDorsal = numeroDorsal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Métodos de Inscripcion
    /**
     * Confirma el pago de la inscripción.
     * Reglas:
     *  - Requiere: corredor y evento asignados; distancia y talla definidos; numeroDorsal válido.
     *  - Si ya está PAGADO, no vuelve a confirmar (idempotente).
     *  - Si está CANCELADO, no permite confirmar.
     *  - Si todo es válido, cambia el estado a PAGADO.
     */
    public void confirmarPago() {
        // Validaciones mínimas obligatorias (invariantes previas al pago)
        if (corredor == null || evento == null) {
            throw new IllegalStateException("Inscripción sin corredor o evento asignado.");
        }
        if (distancia == null || tallaCamiseta == null) {
            throw new IllegalStateException("Distancia y talla de camiseta son obligatorias.");
        }
        if (numeroDorsal <= 0) {
            throw new IllegalStateException("El número de dorsal debe ser mayor a 0.");
        }

        // Normalizar estado actual
        String estadoActual = (estado == null || estado.isBlank())
                ? ESTADO_PENDIENTE
                : estado.trim().toUpperCase();

        // Casos no permitidos / idempotencia
        if (ESTADO_PAGADO.equals(estadoActual)) {
            // Ya estaba pagada: operación idempotente (no hace nada)
            return;
        }
        if (ESTADO_CANCELADO.equals(estadoActual)) {
            throw new IllegalStateException("No se puede confirmar el pago de una inscripción cancelada.");
        }

        // Transición válida: (PENDIENTE o vacío) -> PAGADO
        this.estado = ESTADO_PAGADO;
    }
}
