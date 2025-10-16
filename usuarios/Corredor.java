package usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import eventos.Inscripcion;

public class Corredor extends Usuario {
    private Date fechaNacimiento;
    private char sexo; // 'M','F', etc.
    private String tipoSangre;
    private String contactoEmergencia;

    private List<Inscripcion> inscripciones;

    public Corredor(int id, String nombre, String telefono, String correo,
                    Date fechaNacimiento, char sexo, String tipoSangre, String contactoEmergencia) {
        super(id, nombre, telefono, correo);
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.tipoSangre = tipoSangre;
        this.contactoEmergencia = contactoEmergencia;
        this.inscripciones = new ArrayList<>();
    }

   

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public char getSexo() { return sexo; }
    public void setSexo(char sexo) { this.sexo = sexo; }

    public String getTipoSangre() { return tipoSangre; }
    public void setTipoSangre(String tipoSangre) { this.tipoSangre = tipoSangre; }

    public String getContactoEmergencia() { return contactoEmergencia; }
    public void setContactoEmergencia(String contactoEmergencia) { this.contactoEmergencia = contactoEmergencia; }

    public List<Inscripcion> getInscripciones() { return inscripciones; }

    public void agregarInscripcion(Inscripcion ins) {
        if (ins != null && !inscripciones.contains(ins)) inscripciones.add(ins);
    }

    public void consultarResultados() {
        System.out.println("Resultados de " + getNombre() + ":");
        for (Inscripcion i : inscripciones) {
            String ev = (i.getEvento() != null) ? i.getEvento().getNombre() : "(sin evento)";
            String t = (i.getTiempo() != null) ? String.valueOf(i.getTiempo().getTiempoIndividual()) : "N/A";
            System.out.println(" - Evento: " + ev + " | Dorsal: " + i.getNumeroDorsal() +
                    " | Estado: " + i.getEstado() + " | Tiempo: " + t);
        }
    }

    public void verInscripciones() {
        System.out.println("Inscripciones de " + getNombre() + ": " + inscripciones.size());
    }
}
