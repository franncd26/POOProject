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

    public void agregarInscripcion(Inscripcion inscripcion) {
        if (inscripcion == null) return;

        // Inicializar la lista si vino nula por constructor/setter
        if (this.inscripciones == null) {
            this.inscripciones = new java.util.ArrayList<>();
        }

        // Validar dorsal único dentro de este evento
        for (Inscripcion i : this.inscripciones) {
            if (i != null && i.getNumeroDorsal() == inscripcion.getNumeroDorsal()) {
                throw new IllegalStateException(
                    "El número de dorsal " + inscripcion.getNumeroDorsal() + " ya está asignado en este evento."
                );
            }
        }

        // Agregar y mantener relación bidireccional
        this.inscripciones.add(inscripcion);
        if (inscripcion.getEvento() != this) {
            inscripcion.setEvento(this);
        }
    }
    
    public void generarResultadosGenerales() {
        if (inscripciones == null || inscripciones.isEmpty()) {
            System.out.println(" No hay inscripciones registradas.");
            return;
        }

        // Verificar que haya tiempos
        boolean hayTiempos = false;
        for (Inscripcion ins : inscripciones) {
            if (ins != null && ins.getTiempo() != null) {
                hayTiempos = true;
                break;
            }
        }

        if (!hayTiempos) {
            System.out.println(" Ninguna inscripción tiene tiempo registrado.");
            return;
        }

        // Ordenar por tiempo (menor a mayor)
        for (int i = 0; i < inscripciones.size() - 1; i++) {
            for (int j = i + 1; j < inscripciones.size(); j++) {
                Inscripcion a = inscripciones.get(i);
                Inscripcion b = inscripciones.get(j);

                if (a == null || b == null || a.getTiempo() == null || b.getTiempo() == null) {
                    continue;
                }

                if (a.getTiempo().getTiempoIndividual() > b.getTiempo().getTiempoIndividual()) {
                    inscripciones.set(i, b);
                    inscripciones.set(j, a);
                }
            }
        }

        // Asignar posición general
        int posicion = 1;
        for (Inscripcion ins : inscripciones) {
            if (ins != null && ins.getTiempo() != null) {
                ins.getTiempo().setPosicionGeneral(posicion);
                posicion++;
            }
        }

        System.out.println(" Resultados generales generados correctamente.");
    }

    public void generarResultadosPorCategoria() {
        if (inscripciones == null || inscripciones.isEmpty()) {
            System.out.println(" No hay inscripciones registradas.");
            return;
        }

        // Recorrer cada distancia 
        for (Inscripcion.Distancia distancia : Inscripcion.Distancia.values()) {
            System.out.println(" Resultados para la distancia: " + distancia);
            int posicion = 1;

            // Ordenar por tiempo dentro de cada distancia
            for (int i = 0; i < inscripciones.size() - 1; i++) {
                for (int j = i + 1; j < inscripciones.size(); j++) {
                    Inscripcion ins1 = inscripciones.get(i);
                    Inscripcion ins2 = inscripciones.get(j);

                    if (ins1 == null || ins2 == null) continue;
                    if (ins1.getDistancia() != distancia || ins2.getDistancia() != distancia) continue;
                    if (ins1.getTiempo() == null || ins2.getTiempo() == null) continue;

                    if (ins1.getTiempo().getTiempoIndividual() > ins2.getTiempo().getTiempoIndividual()) {
                        inscripciones.set(i, ins2);
                        inscripciones.set(j, ins1);
                    }
                }
            }

            // Asignar posiciones dentro de cada distancia
            for (Inscripcion ins : inscripciones) {
                if (ins != null && ins.getDistancia() == distancia && ins.getTiempo() != null) {
                    ins.getTiempo().setPosicionCategoria(posicion);
                    posicion++;
                }
            }
        }

        System.out.println(" Resultados por categoría generados correctamente.");
    }

}
