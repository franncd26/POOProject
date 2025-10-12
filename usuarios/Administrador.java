package usuarios;

public class Administrador extends Usuario {
    private String rol;

    public Administrador(int id, String nombre, String telefono,String correo, String rol) {
        super (id, nombre, telefono, correo);
        this.rol = rol;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public void crearEvento() {
        // Lógica para crear un evento
    }
    
    public void gestionarInscripciones() {
        // Lógica para eliminar un evento
    }
   
    public void registrarTiempo() {
        // Lógica para gestionar usuarios
    }
}
