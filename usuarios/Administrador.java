package usuarios;

import eventos.Categoria;
import eventos.Evento;
import eventos.Inscripcion;
import eventos.Tiempo;

import java.util.Date;
import java.util.List;

public class Administrador extends Usuario {
    private String rol;

    public Administrador(int id, String nombre, String telefono, String correo, String rol) {
        super(id, nombre, telefono, correo);
        this.rol = rol;
    }

   

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Evento crearEvento(List<Evento> lista, String nombre, Date fecha, String descripcion) {
        if (lista == null || nombre == null || fecha == null) return null;
        for (Evento e : lista) {
            if (e != null && nombre.equalsIgnoreCase(e.getNombre()) && fecha.equals(e.getFecha())) {
                System.out.println("Ya existe un evento con ese nombre y fecha.");
                return null;
            }
        }
        Evento nuevo = new Evento(nombre, fecha, descripcion, Evento.Estado.PROGRAMADA);
        lista.add(nuevo);
        return nuevo;
    }

    public boolean cambiarEstadoEvento(Evento evento, Evento.Estado nuevoEstado) {
        if (evento == null || nuevoEstado == null) return false;
        evento.setEstado(nuevoEstado);
        return true;
    }

    public boolean agregarCategoriaAEvento(Evento evento, String nombreCat, int edadMin, int edadMax) {
        if (evento == null || nombreCat == null) return false;
        evento.agregarCategoria(new Categoria(nombreCat, edadMin, edadMax));
        return true;
    }

    public boolean registrarInscripcionEnEvento(Evento evento, Inscripcion inscripcion) {
        if (evento == null || inscripcion == null) return false;
        if (evento.buscarInscripcionPorDorsal(inscripcion.getNumeroDorsal()) != null) return false;
        evento.agregarInscripcion(inscripcion);
        if (inscripcion.getCorredor() != null) inscripcion.getCorredor().agregarInscripcion(inscripcion);
        return true;
    }

    public boolean confirmarPagoInscripcion(Inscripcion inscripcion) {
        if (inscripcion == null) return false;
        return inscripcion.confirmarPago();
    }

    public boolean registrarTiempos(Evento evento, int dorsal, double tIndividual, int posGeneral, int posCategoria) {
        if (evento == null) return false;
        Inscripcion ins = evento.buscarInscripcionPorDorsal(dorsal);
        if (ins == null) return false;
        Tiempo t = new Tiempo(tIndividual, posGeneral, posCategoria);
        ins.setTiempo(t);
        evento.agregarTiempo(t);
        return true;
    }
}
