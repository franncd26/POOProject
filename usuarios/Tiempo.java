package usuarios;
public class Tiempo {
 
    private double tiempoIndividual; // Tiempo en segundos
    private int posicionGeneral;
    private int posicionCategoria;

    // Constructor
    public Tiempo(double tiempoIndividual, int posicionGeneral, int posicionCategoria) {
        this.tiempoIndividual = tiempoIndividual;
        this.posicionGeneral = posicionGeneral;
        this.posicionCategoria = posicionCategoria;
    }

    // Getters
    public double getTiempoIndividual() {
        return tiempoIndividual;
    }
    public int getPosicionGeneral() {
        return posicionGeneral;
    }
    public int getPosicionCategoria() {
        return posicionCategoria;
    }

    // Setters
    public void setTiempoIndividual(double tiempoIndividual) {
        this.tiempoIndividual = tiempoIndividual;
    }
    public void setPosicionGeneral(int posicionGeneral) {
        this.posicionGeneral = posicionGeneral;
    }
    public void setPosicionCategoria(int posicionCategoria) {
        this.posicionCategoria = posicionCategoria;
    }

    // Metodo mostrarTiempo
    public double getTiempo() {
        return tiempoIndividual;
    }
}