import chats.ChatGeneral;
import chats.MensajeriaDirecta;
import usuarios.Usuario;

import java.util.List;
import java.util.Scanner;

public class Main {
    // Un único Scanner (no lo cierres)
    private static final Scanner SC = new Scanner(System.in);
    private static Usuario usuarioActual = null;

    public static void main(String[] args) {
        // --- Login simple: cédula + nombre ---
        System.out.println("=== Inicio de sesión ===");
        int cedula = leerEntero("Cédula (solo números): ");
        System.out.print("Nombre: ");
        String nombre = SC.nextLine();

        String correoTemp = (nombre == null || nombre.isBlank())
                ? ("user" + cedula + "@temp.local")
                : (nombre.trim().toLowerCase().replace(" ", ".") + "@temp.local");
        usuarioActual = new Usuario(cedula, nombre, "", correoTemp);

        // --- Instancia única del Chat General y registro del usuario actual ---
        ChatGeneral chatGeneral = new ChatGeneral(101);
        chatGeneral.agregarParticipante(usuarioActual);

        // --- Menú principal ---
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENU1 PRINCIPAL ===");
            System.out.println("1) Entrar al CHAT GENERAL");
            System.out.println("2) Entrar a MENSAJERÍA DIRECTA (1 a 1)");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            String opc = SC.nextLine().trim();

            switch (opc) {
                case "1" -> submenuChatGeneral(chatGeneral);
                case "2" -> submenuMensajeriaDirecta(chatGeneral);
                case "0" -> {
                    salir = true;
                    System.out.println("Saliendo del sistema…");
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // =================== SUBMENÚ: CHAT GENERAL ===================
    private static void submenuChatGeneral(ChatGeneral chat) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== CHAT GENERAL (ID " + chat.getIdChat() + ") ===");
            System.out.println("1) Enviar mensaje (como " + usuarioActual.getNombre() + ")");
            System.out.println("2) Ver mensajes");
            System.out.println("3) Agregar participante (cédula + nombre)");
            System.out.println("4) Eliminar participante por cédula");
            System.out.println("5) Ver participantes");
            System.out.println("0) Volver al menú principal");
            System.out.print("Opción: ");
            String opc = SC.nextLine().trim();

            switch (opc) {
                case "1" -> {
                    System.out.print("Escribe tu mensaje: ");
                    String texto = SC.nextLine();
                    chat.enviarMensaje(texto, usuarioActual);
                }
                case "2" -> chat.recibirMensajes();
                case "3" -> {
                    Usuario nuevo = crearUsuarioMinimo();
                    chat.agregarParticipante(nuevo);
                }
                case "4" -> {
                    int idEliminar = leerEntero("Cédula del participante a eliminar: ");
                    Usuario u = buscarPorId(chat.getParticipantes(), idEliminar);
                    if (u == null) System.out.println("No existe ese participante.");
                    else chat.eliminarParticipante(u);
                }
                case "5" -> listarParticipantes(chat);
                case "0" -> volver = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // =================== SUBMENÚ: MENSAJERÍA DIRECTA ===================
    private static void submenuMensajeriaDirecta(ChatGeneral chat) {
        List<Usuario> participantes = chat.getParticipantes();
        if (participantes.size() < 2) {
            System.out.println("Se requieren al menos 2 participantes en el Chat General para abrir mensajería directa.");
            System.out.println("Ve al Chat General y agrega más participantes (opción 3).");
            return;
        }

        // Seleccionar dos participantes existentes
        listarParticipantes(chat);
        int id1 = leerEntero("Cédula del primer participante: ");
        int id2 = leerEntero("Cédula del segundo participante: ");

        if (id1 == id2) {
            System.out.println("Las cédulas deben ser distintas.");
            return;
        }

        Usuario u1 = buscarPorId(participantes, id1);
        Usuario u2 = buscarPorId(participantes, id2);

        if (u1 == null || u2 == null) {
            System.out.println("Alguno de los participantes no existe.");
            return;
        }

        // Crear la conversación 1 a 1
        int idConversacion = Math.abs(u1.getId() * 31 + u2.getId());
        MensajeriaDirecta md = new MensajeriaDirecta(idConversacion, u1, u2);

        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== MENSAJERÍA DIRECTA #" + md.getIdConversacion() + " ===");
            System.out.println("Participantes: " + u1.getNombre() + " ↔ " + u2.getNombre());
            System.out.println("1) Enviar mensaje como " + u1.getNombre());
            System.out.println("2) Enviar mensaje como " + u2.getNombre());
            System.out.println("3) Ver historial");
            System.out.println("0) Volver al menú principal");
            System.out.print("Opción: ");
            String op = SC.nextLine().trim();

            switch (op) {
                case "1" -> {
                    System.out.print("Mensaje de " + u1.getNombre() + ": ");
                    String m1 = SC.nextLine();
                    md.enviarMensaje(m1, u1); // Solo métodos definidos en tu clase
                }
                case "2" -> {
                    System.out.print("Mensaje de " + u2.getNombre() + ": ");
                    String m2 = SC.nextLine();
                    md.enviarMensaje(m2, u2);
                }
                case "3" -> md.recibirMensajes();
                case "0" -> volver = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    // =================== HELPERS ===================

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
