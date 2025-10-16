package eventos;

public class Categoria {
    private String nombre;
    private int edadMinima;
    private int edadMaxima;

    public Categoria(String nombre, int edadMinima, int edadMaxima) {
        this.nombre = nombre;
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
    }

    public Categoria() { }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdadMinima() { return edadMinima; }
    public void setEdadMinima(int edadMinima) { this.edadMinima = edadMinima; }

    public int getEdadMaxima() { return edadMaxima; }
    public void setEdadMaxima(int edadMaxima) { this.edadMaxima = edadMaxima; }
}
