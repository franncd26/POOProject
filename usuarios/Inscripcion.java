package usuarios;

public class Inscripcion {

    // Enums ANIDADOS (públicos para usarlos desde afuera)
    public enum Distancia { CINCO_K, DIEZ_K, MEDIA_MARATON, MARATON }
    public enum Talla { XS, S, M, L, XL, XXL }

    private int id;
    private Distancia distancia;
    private Talla tallaCamiseta;
    private int numeroDorsal;
    private String estado;

    // Relaciones
    private Corredor corredor; // 0..1
    private Evento evento;     // 1

    // ⬇⬇⬇ FIRMA QUE DEBE MATCH CON TU NEW Inscripcion(...) ⬇⬇⬇
    public Inscripcion(int id,
                       Distancia distancia,
                       Talla tallaCamiseta,
                       int numeroDorsal,
                       String estado,
                       Corredor corredor,
                       Evento evento) {
        this.id = id;
        this.distancia = distancia;
        this.tallaCamiseta = tallaCamiseta;
        this.numeroDorsal = numeroDorsal;
        this.estado = estado;
        this.corredor = corredor;
        this.evento = evento;
    }

    // Getters/Setters mínimos (si los necesitas)
    public int getId() { return id; }
    public Distancia getDistancia() { return distancia; }
    public Talla getTallaCamiseta() { return tallaCamiseta; }
    public int getNumeroDorsal() { return numeroDorsal; }
    public String getEstado() { return estado; }
    public Corredor getCorredor() { return corredor; }
    public Evento getEvento() { return evento; }

    public void confirmarPago() { this.estado = "PAGADA"; }
}
