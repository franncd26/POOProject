package usuarios;

import java.util.Scanner;

/**
 * La clase {@code Usuario} representa a un usuario dentro del sistema.
 * Contiene información básica como el ID, nombre, teléfono y correo electrónico.
 * Además, permite actualizar estos datos desde la consola.
 * 
 * <p>Forma parte del paquete {@code usuarios}, utilizado para manejar la información
 * personal y los datos de acceso de los distintos tipos de usuarios del sistema.</p>
 */
public class Usuario {

    /** Identificador único del usuario. */
    private int id;

    /** Nombre completo del usuario. */
    private String nombre;

    /** Número de teléfono del usuario. */
    private String telefono;

    /** Correo electrónico del usuario. */
    private String correo;

    /**
     * Constructor que inicializa un nuevo objeto {@code Usuario} con los datos proporcionados.
     *
     * @param id        identificador único del usuario (debe ser mayor que cero)
     * @param nombre    nombre completo del usuario
     * @param telefono  número de teléfono del usuario
     * @param correo    dirección de correo electrónico del usuario
     */
    public Usuario(int id, String nombre, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    /**
     * Obtiene el identificador del usuario.
     *
     * @return el ID del usuario
     */
    public int getId() {
        return id;
    }

    /**
     * Establece un nuevo ID para el usuario.
     * Solo se asigna si el valor es mayor a cero.
     *
     * @param id nuevo identificador del usuario
     */
    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        }
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return el nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna un nuevo nombre al usuario.
     * Solo se asigna si el valor no es nulo ni está vacío.
     *
     * @param nombre nuevo nombre del usuario
     */
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        }
    }

    /**
     * Obtiene el número de teléfono del usuario.
     *
     * @return el teléfono del usuario
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Asigna un nuevo número de teléfono al usuario.
     * Solo se asigna si el valor no es nulo ni está vacío.
     *
     * @param telefono nuevo número de teléfono del usuario
     */
    public void setTelefono(String telefono) {
        if (telefono != null && !telefono.trim().isEmpty()) {
            this.telefono = telefono;
        }
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return el correo del usuario
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Asigna un nuevo correo electrónico al usuario.
     * Solo se asigna si el valor no es nulo y contiene el carácter '@'.
     *
     * @param correo nuevo correo del usuario
     */
    public void setCorreo(String correo) {
        if (correo != null && correo.contains("@")) {
            this.correo = correo;
        }
    }

    /**
     * Permite actualizar los datos del usuario a través de la consola.
     * 
     * <p>Solicita al usuario que introduzca un nuevo nombre, teléfono y correo electrónico.
     * Cada valor se valida antes de asignarse.</p>
     * 
     * <p><b>Ejemplo de uso:</b></p>
     * <pre>
     * Usuario u = new Usuario(1, "Juan Pérez", "8888-8888", "juan@mail.com");
     * u.actualizarDatos(); // Permite modificar los valores desde consola
     * </pre>
     */
    @SuppressWarnings("resource")
    public void actualizarDatos() {
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
