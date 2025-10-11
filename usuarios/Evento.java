package usuarios;

public class Evento {
    private String nombre;
    private String fecha;
    private String ubicacion;
    private Categoria categoria;
    private int maxParticipantes;
    private int participantesInscritos;
    private boolean estado; // "abierto" o "cerrado"
    private String resultados;
    public Evento(String nombre, String fecha, String ubicacion, Categoria categoria ) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.categoria = categoria;    
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public int getMaxParticipantes() {
        return maxParticipantes;
    }
    public void setMaxParticipantes(int maxParticipantes) {
        this.maxParticipantes = maxParticipantes;
    }
    public int getParticipantesInscritos() {
        return participantesInscritos;
    }
    public void setParticipantesInscritos(int participantesInscritos) {
        this.participantesInscritos = participantesInscritos;
    }
    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public String getResultados() {
        return resultados;
    }
    public void setResultados(String resultados) {
        this.resultados = resultados;
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
