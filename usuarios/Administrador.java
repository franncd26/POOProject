package usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import eventos.Categoria;
import eventos.Evento;
import eventos.Inscripcion;
import eventos.Tiempo;

public class Administrador extends Usuario {
    private String rol;

    public Administrador(int id, String nombre, String telefono, String correo, String rol) {
        super(id, nombre, telefono, correo);
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void crearEvento() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== CREAR NUEVO EVENTO ===");

        System.out.print("Ingrese el nombre del evento: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese la descripción: ");
        String descripcion = sc.nextLine();

        // Fecha actual como ejemplo
        Date fecha = new Date();
        System.out.println("La fecha será: " + fecha);

        System.out.print("Ingrese el estado del evento (Programado, En curso, Finalizado): ");
        String estado = sc.nextLine();

        // Por simplicidad, no pedimos categorías ni inscripciones aún
        List<Categoria> categorias = new ArrayList<>();
        List<Inscripcion> inscripciones = new ArrayList<>();

        Evento nuevoEvento = new Evento(nombre, fecha, descripcion, estado, categorias, inscripciones);

        System.out.println(" Evento creado exitosamente:");
        System.out.println("Nombre: " + nuevoEvento.getNombre());
        System.out.println("Descripción: " + nuevoEvento.getDescripcion());
        System.out.println("Estado: " + nuevoEvento.getEstado());
        System.out.println();
        sc.close();
    }

    // ---------------- MÉTODO 2 ----------------
    public void gestionarInscripciones() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== GESTIONAR INSCRIPCIONES ===");

        System.out.print("Ingrese el nombre del corredor: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese el estado de la inscripción (Pendiente, Pagada, Confirmada): ");
        String estado = sc.nextLine();

        // Aquí solo simulamos la acción
        System.out.println("Procesando inscripción de " + nombre + "...");
        System.out.println("Estado de la inscripción: " + estado);
        System.out.println(" Inscripción gestionada correctamente.\n");
        sc.close();
    }

    // ---------------- MÉTODO 3 ----------------
    public void registrarTiempos() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== REGISTRAR TIEMPOS ===");

        System.out.print("Ingrese el ID del corredor: ");
        int idCorredor = sc.nextInt();

        System.out.print("Ingrese el tiempo individual (en minutos): ");
        double tiempoIndividual = sc.nextDouble();

        Tiempo tiempo = new Tiempo(tiempoIndividual, 0, 0);
        System.out.println(" Registro de tiempo completado.\n");
        sc.close();
    }
}
