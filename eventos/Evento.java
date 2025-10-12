package eventos;

import java.util.Date;
import java.util.List;

public class Evento {
    private String nombre;
    private Date fecha;
    private String descripcion;
    private String estado;
    private List<Categoria> categorias;
    private List<Inscripcion> inscripciones;

    public Evento(String nombre, Date fecha, String descripcion, String estado,
                  List<Categoria> categorias, List<Inscripcion> inscripciones) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.estado = estado;
        this.categorias = categorias;
        this.inscripciones = inscripciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public void generarResultados() {}
    public void generarResumen() {}
}
