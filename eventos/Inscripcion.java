package eventos;

import usuarios.Corredor;

public class Inscripcion {
    public enum Distancia { CINCO_K, DIEZ_K, VEINTIUNO_K, CUARENTA_Y_DOS_K }
    public enum Talla { XS, S, M, L, XL }
    public enum Estado { PENDIENTE, PAGADA, CONFIRMADA, CANCELADA }

    private int id;
    private Distancia distancia;
    private Talla tallaCamiseta;
    private int numeroDorsal;
    private Estado estado;

    private Corredor corredor;   // 0..1
    private Evento evento;       // 1
    private Categoria categoria; // 1
    private Tiempo tiempo;       // 0..1

    public Inscripcion(int id, Distancia distancia, Talla tallaCamiseta, int numeroDorsal,
                       Estado estado, Corredor corredor, Evento evento, Categoria categoria) {
        this.id = id;
        this.distancia = distancia;
        this.tallaCamiseta = tallaCamiseta;
        this.numeroDorsal = numeroDorsal;
        this.estado = estado;
        this.corredor = corredor;
        this.evento = evento;
        this.categoria = categoria;
    }

    public Inscripcion() { }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Distancia getDistancia() { return distancia; }
    public void setDistancia(Distancia distancia) { this.distancia = distancia; }

    public Talla getTallaCamiseta() { return tallaCamiseta; }
    public void setTallaCamiseta(Talla tallaCamiseta) { this.tallaCamiseta = tallaCamiseta; }

    public int getNumeroDorsal() { return numeroDorsal; }
    public void setNumeroDorsal(int numeroDorsal) { this.numeroDorsal = numeroDorsal; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Corredor getCorredor() { return corredor; }
    public void setCorredor(Corredor corredor) { this.corredor = corredor; }

    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Tiempo getTiempo() { return tiempo; }
    public void setTiempo(Tiempo tiempo) { this.tiempo = tiempo; }

    public boolean confirmarPago() {
        if (estado == Estado.PENDIENTE) { estado = Estado.PAGADA; return true; }
        return false;
    }

    public boolean confirmarInscripcion() {
        if (estado == Estado.PAGADA) { estado = Estado.CONFIRMADA; return true; }
        return false;
    }

    public boolean cancelar() {
        if (estado == Estado.PENDIENTE || estado == Estado.PAGADA) {
            estado = Estado.CANCELADA; return true;
        }
        return false;
    }
}
