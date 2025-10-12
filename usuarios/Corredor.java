package usuarios;

import java.util.Date;
import java.util.List;
import eventos.Inscripcion;

public class Corredor extends Usuario {
    private Date fechaNacimiento;
    private char sexo;
    private String tipoSangre;
    private String contactoEmergencia;
    private List<Inscripcion> inscripciones;

    public Corredor(int id, String nombre, String telefono, String correo, Date fechaNacimiento,
                    char sexo, String tipoSangre, String contactoEmergencia, List<Inscripcion> inscripciones) {
        super(id, nombre, telefono, correo);
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.tipoSangre = tipoSangre;
        this.contactoEmergencia = contactoEmergencia;
        this.inscripciones = inscripciones;
    }

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

    public void consultarResultados() {}
    public void verInscripciones() {}
}
