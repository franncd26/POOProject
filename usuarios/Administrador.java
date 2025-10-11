package usuarios;

public class Administrador extends Usuario {
    private String rol;

    public Administrador(int id, String nombre, String correo, String rol) {
        super (id, nombre, correo);
        this.rol = rol;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    @Override
    public void crearEvento() {
        // Lógica para crear un evento
    }
    @Override
    public void getionarInscripciones() {
        // Lógica para eliminar un evento
    }
    @Override
    public void registrarTiempo() {
        // Lógica para gestionar usuarios
    }
}
