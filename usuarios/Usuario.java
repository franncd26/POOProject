package usuarios;
import java.util.Scanner;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
    if (id > 0) {
        this.id = id;
    }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        }
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono != null && !telefono.trim().isEmpty()) {
            this.telefono = telefono;
        }
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        if (correo != null && correo.contains("@")) {
            this.correo = correo;
        }
    }

    @SuppressWarnings("resource")
    public void actualizarDatos() {
        // Lógica para actualizar datos del usuario
        // Actulizacion de datos de un Usuario en consola
        Scanner recibirDatos = new Scanner(System.in);

        System.out.print("Nuevo nombre: ");
        setNombre(recibirDatos.nextLine());

        System.out.print("Nuevo teléfono: ");
        setTelefono(recibirDatos.nextLine());

        System.out.print("Nuevo correo: ");
        setCorreo(recibirDatos.nextLine());

        System.out.println("✅ Datos actualizados correctamente."); 

    }
}