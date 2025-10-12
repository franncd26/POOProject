package eventos;

public class Tiempo {
    private double tiempoIndividual;
    private int posicionGeneral;
    private int posicionCategoria;

    public Tiempo(double tiempoIndividual, int posicionGeneral, int posicionCategoria) {
        this.tiempoIndividual = tiempoIndividual;
        this.posicionGeneral = posicionGeneral;
        this.posicionCategoria = posicionCategoria;
    }

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

    public double getTiempo() { return 0.0; }
}
