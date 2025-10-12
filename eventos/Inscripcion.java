package eventos;

import usuarios.Corredor;

public class Inscripcion {
    public enum Distancia { CINCO_K, DIEZ_K, VEINTIUNO_K, TREINTA_Y_DOS_K, CUARENTA_Y_DOS_K }
    public enum Talla { XS, S, M, L, XL }

    private int id;
    private Distancia distancia;
    private Talla tallaCamiseta;
    private int numeroDorsal;
    private String estado;
    private Corredor corredor;
    private Evento evento;
    private Tiempo tiempo;

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

    public void setTiempo(Tiempo tiempo) {
        this.tiempo = tiempo;
    }

    public void confirmarPago() {}
}
