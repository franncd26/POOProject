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
    public void consultarResultados() {}
    
    /**
    * Lista las inscripciones del corredor. Si no tiene, debe informar un mensaje claro.
    * Debe incluir datos útiles: evento, estado actual y número de dorsal.
    */
    public void verInscripciones() {}
}
