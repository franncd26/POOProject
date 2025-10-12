package usuarios;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Evento {

    public enum Estado { PROGRAMADA, EN_CURSO, FINALIZADA, CANCELADA }
    public enum Tipo   { CALLE, TRAIL, PISTA } 

    private String nombre;
    private LocalDate fecha;            
    private String descripcion;       
    private Tipo tipo;                  
    private Estado estado = Estado.PROGRAMADA;

    private int maxParticipantes;      
    private final List<Categoria> categorias = new ArrayList<>();
    private final List<Inscripcion> inscripciones = new ArrayList<>();

    public Evento(String nombre, LocalDate fecha, String descripcion, Tipo tipo) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Tipo getTipo() {
        return tipo;
    }
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public int getMaxParticipantes() {
        return maxParticipantes;
    }
    public void setMaxParticipantes(int maxParticipantes) {
        this.maxParticipantes = maxParticipantes;
    }
    public List<Categoria> getCategorias() {
        return categorias;
    }
    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    
    public void generarResultadosGenerales() {
        // Lógica para generar resultados del evento
    }
    public void generarResultadosPorCategoria() {
        // Lógica para generar resultados por categoría
    }
    public void cerrarInscripciones() {
        // Lógica para cerrar inscripciones del evento
    }
    public void abrirInscripciones() {
        // Lógica para abrir inscripciones del evento
    }
    


}
