import usuarios.Usuario;

import java.util.List;
import java.util.Scanner;
public class Main {
    // Un único Scanner (no lo cierres)
    private static final Scanner SC = new Scanner(System.in);
    private static Usuario usuarioActual = null;

    public static void main(String[] args) {
        // 1) Login simple: cédula + nombre (una sola vez)
        System.out.println("=== Inicio de sesión (Chat General) ===");
        int cedula = leerEntero("Cédula (solo números): ");
        System.out.print("Nombre: ");
        String nombre = SC.nextLine();

        // Crear el usuario actual (correo/telefono temporales para no pedir más datos)
        String correoTemp = (nombre == null || nombre.isBlank())
                ? ("user" + cedula + "@temp.local")
                : (nombre.trim().toLowerCase().replace(" ", ".") + "@temp.local");
        usuarioActual = new Usuario(cedula, nombre, "", correoTemp);

        // 2) Crear el Chat General y agregar al usuario actual
        ChatGeneral chat = new ChatGeneral(101);
        chat.agregarParticipante(usuarioActual);

        // 3) Menú del Chat General (solo chat)
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== CHAT GENERAL (ID " + chat.getIdChat() + ") ===");
            System.out.println("1) Enviar mensaje (como " + usuarioActual.getNombre() + ")");
            System.out.println("2) Ver mensajes");
            System.out.println("3) Agregar participante (cédula + nombre)");
            System.out.println("4) Eliminar participante por cédula");
            System.out.println("5) Ver participantes");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            String opc = SC.nextLine().trim();

            switch (opc) {
                case "1":
                    System.out.print("Escribe tu mensaje: ");
                    String texto = SC.nextLine();
                    chat.enviarMensaje(texto, usuarioActual);
                    break;

                case "2":
                    chat.recibirMensajes();
                    break;

                case "3":
                    Usuario nuevo = crearUsuarioMinimo();
                    chat.agregarParticipante(nuevo);
                    break;

                case "4":
                    int idEliminar = leerEntero("Cédula del participante a eliminar: ");
                    Usuario u = buscarPorId(chat.getParticipantes(), idEliminar);
                    if (u == null) System.out.println("No existe ese participante.");
                    else chat.eliminarParticipante(u);
                    break;

                case "5":
                    listarParticipantes(chat);
                    break;

                case "0":
                    salir = true;
                    System.out.println("Saliendo del Chat General...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    // -------- Helpers sencillos --------

    private static Usuario crearUsuarioMinimo() {
        int cedula = leerEntero("Cédula (solo números): ");
        System.out.print("Nombre: ");
        String nombre = SC.nextLine();
        String correoTemp = (nombre == null || nombre.isBlank())
                ? ("user" + cedula + "@temp.local")
                : (nombre.trim().toLowerCase().replace(" ", ".") + "@temp.local");
        return new Usuario(cedula, nombre, "", correoTemp);
    }

    private static void listarParticipantes(ChatGeneral chat) {
        List<Usuario> ps = chat.getParticipantes();
        if (ps.isEmpty()) {
            System.out.println("No hay participantes.");
            return;
        }
        System.out.println("\n--- Participantes ---");
        for (Usuario u : ps) System.out.println(u.getId() + " - " + u.getNombre());
        System.out.println("---------------------");
    }

    private static Usuario buscarPorId(List<Usuario> lista, int id) {
        if (lista == null) return null;
        for (Usuario u : lista) if (u.getId() == id) return u;
        return null;
    }

    private static int leerEntero(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(SC.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Número inválido. Intenta de nuevo.");
            }
        }
    }
}