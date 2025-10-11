import java.time.LocalDate;

public class Corredor {

    // Atributos
    private LocalDate fechaNacimiento;
    private char sexo;
    private String tipoSangre;
    private String contactoEmergencia;

    // Constructor
    public Corredor(LocalDate fechaNacimiento, char sexo, String tipoSangre, String contactoEmergencia) {
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.tipoSangre = tipoSangre;
        this.contactoEmergencia = contactoEmergencia;
    }

    // Getters
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public char getSexo() {
        return sexo;
    }
    public String getTipoSangre() {
        return tipoSangre;
    }
    public String getContactoEmergencia() {
        return contactoEmergencia;
    }

    // Setters
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }
    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    //Metodo para consultar los resultados del corredor
    public void consultarResultados() {
        // Lógica para consultar resultados
        System.out.println("Consultando resultados del corredor...");
    }

    // Metodo para ver las inscripciones del corredor
    public void verInscripciones() {
        // Lógica para ver inscripciones
        System.out.println("Viendo inscripciones del corredor...");
    }
    
    
}
