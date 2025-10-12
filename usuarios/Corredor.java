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
import java.util.Date;
import java.util.List;
import eventos.Inscripcion;

public class Corredor extends Usuario {
    // Atributos específicos de Corredor
    private Date fechaNacimiento;
    private char sexo;
    private String tipoSangre;
    private String contactoEmergencia;
    private List<Inscripcion> inscripciones;

    // Constructor
    public Corredor(int id, String nombre, String telefono, String correo, Date fechaNacimiento,
                    char sexo, String tipoSangre, String contactoEmergencia, List<Inscripcion> inscripciones) {
        super(id, nombre, telefono, correo);
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.tipoSangre = tipoSangre;
        this.contactoEmergencia = contactoEmergencia;
        this.inscripciones = inscripciones;
    }
    // Getters y Setters
    @Override
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

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
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
