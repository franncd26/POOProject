package usuarios;

public class Usuario {
    private int id;
    private String nombre;
    private String telefono;
    private String correo;

    public Usuario(int id, String nombre, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    public Usuario() { }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public boolean actualizarDatos(String nombre, String telefono, String correo) {
        boolean cambios = false;
        if (nombre != null && !nombre.isBlank() && !nombre.equals(this.nombre)) {
            this.nombre = nombre.trim(); cambios = true;
        }
        if (telefono != null && !telefono.isBlank() && !telefono.equals(this.telefono)) {
            this.telefono = telefono.trim(); cambios = true;
        }
        if (correo != null && !correo.isBlank() && !correo.equals(this.correo)) {
            this.correo = correo.trim(); cambios = true;
        }
        return cambios;
    }
}
