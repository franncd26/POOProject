package usuarios;


public class Inscripcion {
    public enum Distancia { CINCO_K, DIEZ_K, MEDIA_MARATON, MARATON }
    public enum Talla { XS, S, M, L, XL, XXL }

    // =========================
    // Atributos (del diagrama)
    // =========================
    private int id;
    private Distancia distancia;       // Distancia: "5K", "10K", etc.
    private Talla tallaCamiseta;   // Talla: "S", "M", "L", etc.
    private int numDorsal;          // Número de dorsal
    private String estado;          // Estado: "pendiente", "pagada", etc.

    // =========================
    // Asociaciones
    // =========================
    private Corredor corredor;      // 1 corredor por inscripción
    private Evento evento;          // 1 evento por inscripción
    private Tiempo tiempo;          // 0..1 tiempo asociado (puede ser null)

    // =========================
    // Constructores
    // =========================


    public Inscripcion(int id, String distancia, String tallaCamiseta,
                       int numDorsal, String estado,
                       Corredor corredor, Evento evento) {
        this.id = id;
        this.numDorsal = numDorsal;
        this.estado = estado;
        this.corredor = corredor;
        this.evento = evento;
    }

    // =========================
    // Métodos del diagrama
    // =========================

    public void confirmarPago() {
        this.estado = "pagada";
    }
    
    public void gestionarInscripciones() {
        // Constructor vacío (por compatibilidad con frameworks o inicializaciones)
    }

    // =========================
    // Getters y Setters
    // =========================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getNumDorsal() { return numDorsal; }
    public void setNumDorsal(int numDorsal) { this.numDorsal = numDorsal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Corredor getCorredor() { return corredor; }
    public void setCorredor(Corredor corredor) { this.corredor = corredor; }

    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }

    public Tiempo getTiempo() { return tiempo; }
    public void setTiempo(Tiempo tiempo) { this.tiempo = tiempo; }

    public Distancia getDistancia() { return distancia; }
    public void setDistancia(Distancia distancia) { this.distancia = distancia; }
    
    public Talla getTallaCamiseta() { return tallaCamiseta; }
    public void setTallaCamiseta(Talla tallaCamiseta) { this.tallaCamiseta = tallaCamiseta; }
}
