/**
 * Representa a un atleta del sistema.
 *
 * Propósito:
 *  - Permitir gestionar y consultar sus inscripciones y resultados.
 *
 * Invariantes:
 *  - fechaNacimiento, sexo, tipoSangre y contactoEmergencia no deben ser nulos/visiblemente vacíos.
 *  - Mantiene una colección de Inscripcion (un Corredor puede tener muchas Inscripciones).
 *
 * Responsabilidades:
 *  - Mantener/entregar sus inscripciones.
 *  - verInscripciones(): listar inscripciones (evento, estado, dorsal).
 *  - consultarResultados(): mostrar tiempos/posiciones por inscripción (si no hay, informar).
 */

package usuarios;
// Importaciones necesarias
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import eventos.Inscripcion;
import eventos.Tiempo;

public class Corredor extends Usuario {
    // Atributos específicos de Corredor
    private Date fechaNacimiento;
    private char sexo;
    private String tipoSangre;
    private String contactoEmergencia;

    private final List<Inscripcion> inscripciones = new ArrayList<>();

    public List<Inscripcion> getInscripciones() {
        return Collections.unmodifiableList(inscripciones);
    }

    public void agregarInscripcion(Inscripcion inscripcion) {
    if (inscripcion == null) return;

    // 1) No permitir dos inscripciones del MISMO corredor al MISMO evento
    for (Inscripcion i : inscripciones) {
        if (i.getEvento() != null && inscripcion.getEvento() != null && i.getEvento() == inscripcion.getEvento()) {
            throw new IllegalStateException("El corredor ya tiene una inscripción para este evento.");
        }
    }

    // 2) Agregar si no estaba aún
    if (!inscripciones.contains(inscripcion)) {
        inscripciones.add(inscripcion);
    }

    // 3) Mantener relación inversa coherente
    if (inscripcion.getCorredor() != this) {
        inscripcion.setCorredor(this);
    }
}


    public void eliminarInscripcion(Inscripcion inscripcion) {
        if (inscripcion == null) return;
        inscripciones.remove(inscripcion);
        if (inscripcion.getCorredor() == this) {
            inscripcion.setCorredor(null);
        }
    }

    // Constructor
    public Corredor(int id, String nombre, String telefono, String correo, Date fechaNacimiento,
                    char sexo, String tipoSangre, String contactoEmergencia) {
        super(id, nombre, telefono, correo);
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.tipoSangre = tipoSangre;
        this.contactoEmergencia = contactoEmergencia;
    }
    // Getters y Setters

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getContactoEmergencia() {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    // Métodos específicos de Corredor
    /**
     * Muestra los resultados del corredor por inscripción:
     *  - Si existe un Tiempo, se reporta tiempoIndividual y posiciones (general/categoría).
     *  - Si no existe Tiempo, se informa explícitamente que no hay registro.
     */
    public void consultarResultados() {
        if (inscripciones == null || inscripciones.isEmpty()) {
            System.out.println("No tienes inscripciones registradas.");
            return;
        }

        boolean mostroAlgo = false;

        for (Inscripcion ins : inscripciones) {
            if (ins == null) continue;

            // Datos base
            String nombreEvento = (ins.getEvento() != null) ? ins.getEvento().getNombre() : "(evento no asignado)";
            String fechaEvento  = (ins.getEvento() != null && ins.getEvento().getFecha() != null)
                    ? ins.getEvento().getFecha().toString()
                    : "(fecha no disponible)";
            String estado       = (ins.getEstado() != null) ? ins.getEstado() : "(estado no disponible)";
            String distancia    = (ins.getDistancia() != null) ? ins.getDistancia().name() : "(distancia no definida)";
            int dorsal          = ins.getNumeroDorsal();

            System.out.println("--------------------------------------------------");
            System.out.println("Evento: " + nombreEvento + " | Fecha: " + fechaEvento);
            System.out.println("Distancia: " + distancia + " | Dorsal: " + dorsal + " | Estado: " + estado);

            if (ins.getTiempo() == null) {
                System.out.println("Resultado: sin tiempo registrado.");
            } else {
                Tiempo t = ins.getTiempo();
                System.out.println("Tiempo: " + t.getTiempoIndividual());
                if (t.getPosicionGeneral() > 0) {
                    System.out.println("Posición general: " + t.getPosicionGeneral());
                } else {
                    System.out.println("Posición general: (no asignada)");
                }
                if (t.getPosicionCategoria() > 0) {
                    System.out.println("Posición por categoría: " + t.getPosicionCategoria());
                } else {
                    System.out.println("Posición por categoría: (no asignada)");
                }
            }
            mostroAlgo = true;
        }

        if (!mostroAlgo) {
            System.out.println("No hay resultados disponibles para tus inscripciones.");
        }
    }
    
    /**
    * Lista las inscripciones del corredor. Si no tiene, debe informar un mensaje claro.
    * Debe incluir datos útiles: evento, estado actual y número de dorsal.
    */
    public void verInscripciones() {
        if (inscripciones == null || inscripciones.isEmpty()) {
            System.out.println("No tienes inscripciones registradas.");
            return;
        }

        System.out.println("===== Tus inscripciones =====");
        for (Inscripcion ins : inscripciones) {
            if (ins == null) continue;

            String nombreEvento = (ins.getEvento() != null) ? ins.getEvento().getNombre() : "(evento no asignado)";
            String fechaEvento  = (ins.getEvento() != null && ins.getEvento().getFecha() != null)
                    ? ins.getEvento().getFecha().toString()
                    : "(fecha no disponible)";
            String estado       = (ins.getEstado() != null) ? ins.getEstado() : "(estado no disponible)";
            String distancia    = (ins.getDistancia() != null) ? ins.getDistancia().name() : "(distancia no definida)";
            int dorsal          = ins.getNumeroDorsal();

            System.out.println("- Evento: " + nombreEvento + " | Fecha: " + fechaEvento);
            System.out.println("  Distancia: " + distancia + " | Dorsal: " + dorsal + " | Estado: " + estado);

            if (ins.getTiempo() == null) {
                System.out.println("  Resultado: sin tiempo registrado.");
            } else {
                System.out.println("  Resultado: tiempo registrado.");
            }
        }
    }
}
