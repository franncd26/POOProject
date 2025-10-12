

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import chats.*;
import usuarios.*;
import eventos.*;

public class Main {
    public static void main(String[] args) {
        // === Crear Categorías ===
        Categoria catJuvenil = new Categoria("Juvenil", 15, 25);
        Categoria catAdulto = new Categoria("Adulto", 26, 40);
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(catJuvenil);
        categorias.add(catAdulto);

        // === Crear Evento ===
        Evento evento = new Evento(
                "Maratón San José",
                new Date(),
                "Carrera urbana de 10 km y 21 km",
                "Activo",
                categorias,
                new ArrayList<>()
        );

        // === Crear Tiempo (vacío por ahora) ===
        Tiempo tiempo = new Tiempo(0.0, 0, 0);

        // === Crear Corredor ===
        Corredor corredor = new Corredor(
                1001,
                "Ana López",
                "8888-8888",
                "ana@correo.com",
                new Date(2000 , 5, 15), // año base 1900
                'F',
                "O+",
                "Carlos López",
                new ArrayList<>()
        );

        // === Crear Inscripción ===
        Inscripcion inscripcion = new Inscripcion(
                9001,
                Inscripcion.Distancia.DIEZ_K,
                Inscripcion.Talla.M,
                210,
                "PENDIENTE",
                corredor,
                evento,
                tiempo
        );

        // Agregar inscripción al evento y al corredor
        evento.getInscripciones().add(inscripcion);
        corredor.getInscripciones().add(inscripcion);

        // === Crear Administrador ===
        Administrador admin = new Administrador(
                1,
                "Pedro Gómez",
                "7777-7777",
                "admin@correo.com",
                "Administrador General"
        );

        // === Crear Chat General ===
        List<Usuario> participantesChat = new ArrayList<>();
        participantesChat.add(admin);
        participantesChat.add(corredor);

        ChatGeneral chatGeneral = new ChatGeneral(
                101,
                new ArrayList<>(),
                participantesChat
        );

        // === Crear Mensajería Directa ===
        MensajeriaDirecta chatPrivado = new MensajeriaDirecta(
                501,
                admin,
                corredor,
                new ArrayList<>()
        );

        // === Simulación de interacción básica ===
        chatGeneral.enviarMensaje("¡Bienvenidos al evento!", admin);
        chatPrivado.enviarMensaje("Hola Ana, recuerda tu dorsal 210", admin);

        // === Mostrar por consola ===
        System.out.println("===== DATOS DE PRUEBA =====");
        System.out.println("Evento: " + evento.getNombre());
        System.out.println("Categorías: " + categorias.size());
        System.out.println("Corredor: " + corredor.getNombre());
        System.out.println("Inscripción #" + inscripcion.getId() + " - Estado: " + inscripcion.getEstado());
        System.out.println("Administrador: " + admin.getNombre());
        System.out.println("Chat General ID: " + chatGeneral.getIdChat());
        System.out.println("Chat Privado ID: " + chatPrivado.getIdConversacion());
        System.out.println("============================");
    }
}
