import chats.ChatGeneral;
import chats.MensajeriaDirecta;
import eventos.Categoria;
import eventos.Evento;
import eventos.Inscripcion;
import eventos.Tiempo;
import usuarios.Administrador;
import usuarios.Corredor;
import usuarios.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static final Scanner SC = new Scanner(System.in);
    private static final List<Evento> EVENTOS = new ArrayList<>();
    private static final Map<Integer, Usuario> USUARIOS = new HashMap<>(); // cedula -> Usuario
    private static final List<Corredor> CORREDORES = new ArrayList<>();
    private static final Administrador ADMIN = new Administrador(99999999, "Admin", "0000-0000", "admin@tec.ac.cr", "ADMIN");

    // Chats
    private static final ChatGeneral CHAT_GENERAL = new ChatGeneral(1);
    private static final List<MensajeriaDirecta> CONVERSACIONES = new ArrayList<>();

    // estado actual para sub-menús
    private static Usuario usuarioActual = ADMIN;
    private static MensajeriaDirecta mdActual = null;

    public static void main(String[] args) {
        // bootstrap admin
        USUARIOS.put(ADMIN.getId(), ADMIN);
        CHAT_GENERAL.agregarParticipante(ADMIN);
        loopMenu();
        System.out.println("¡Hasta luego!");
    }

    private static void loopMenu() {
    int op;
    do {
        System.out.println("\n=== SISTEMA DE EVENTOS DE RUNNING ===");
        System.out.println("1) Registrar corredor");
        System.out.println("2) Crear evento (admin)");
        System.out.println("3) Inscribir corredor en evento");
        System.out.println("4) Confirmar pago de inscripción");
        System.out.println("5) Registrar tiempo de corredor (admin)");
        System.out.println("6) Ver inscripciones de un corredor");
        System.out.println("7) Chat general: enviar/leer");
        System.out.println("8) Chat directo: enviar/leer");
        System.out.println("9) Ver resumen general de un evento");           // 👈 NUEVO
        System.out.println("10) Ver resumen por categoría de un evento");     // 👈 NUEVO
        System.out.println("0) Salir");
        System.out.print("Opción: ");
        op = leerEntero();

        switch (op) {
            case 1 -> registrarCorredor();
            case 2 -> crearEventoComoAdmin();
            case 3 -> inscribirCorredorEnEvento();
            case 4 -> confirmarPagoInscripcion();
            case 5 -> registrarTiempoComoAdmin();
            case 6 -> verInscripcionesDeCorredor();
            case 7 -> submenuChatGeneral();
            case 8 -> submenuChatDirecto();
            case 9 -> verResumenGeneralDeEvento();            // 👈 NUEVO
            case 10 -> verResumenPorCategoriaDeEvento();      // 👈 NUEVO
            case 0 -> { /* salir */ }
            default -> System.out.println("Opción inválida.");
        }
    } while (op != 0);
}


    // ======== Opción 1 ========
    private static void registrarCorredor() {
        System.out.println("\n== Registrar corredor ==");
        int ced = leerEntero("Cédula: ");
        if (USUARIOS.containsKey(ced)) { System.out.println("Ya existe esa cédula."); return; }
        String nombre = leerLineaNoVacia("Nombre: ");
        String tel = leerLineaNoVacia("Teléfono: ");
        String correo = leerLineaNoVacia("Correo: ");
        Date fn = leerFecha("Fecha de nacimiento (dd/MM/yyyy): ");
        char sexo = leerChar("Sexo (M/F): ");
        String sangre = leerLineaNoVacia("Tipo de sangre (ej. O+): ");
        String emergencia = leerLineaNoVacia("Contacto de emergencia: ");
        Corredor c = new Corredor(ced, nombre, tel, correo, fn, sexo, sangre, emergencia);
        CORREDORES.add(c);
        USUARIOS.put(ced, c);
        System.out.println("Corredor registrado.");
    }

    // ======== Opción 2 (admin) ========
    private static void crearEventoComoAdmin() {
        if (!esAdminAutenticado()) return;
        System.out.println("\n== Crear evento ==");
        String nombre = leerLineaNoVacia("Nombre del evento: ");
        Date fecha = leerFecha("Fecha (dd/MM/yyyy): ");
        String desc = leerLinea("Descripción (opcional): ");
        Evento e = ADMIN.crearEvento(EVENTOS, nombre, fecha, desc);
        if (e == null) return;

        System.out.println("Agregar categorías (mínimo 1). Deja vacío para terminar.");
        while (true) {
            String ncat = leerLinea("Nombre categoría: ");
            if (ncat == null || ncat.isBlank()) break;
            int min = leerEntero("Edad mínima: ");
            int max = leerEntero("Edad máxima: ");
            ADMIN.agregarCategoriaAEvento(e, ncat, min, max);
        }
        System.out.println("Evento creado:\n" + e.generarResumenGeneral());
    }

    // ======== Opción 3 ========
    private static void inscribirCorredorEnEvento() {
        System.out.println("\n== Inscribir corredor en evento ==");
        Corredor c = seleccionarCorredorPorCedula();
        if (c == null) return;

        Evento e = seleccionarEvento();
        if (e == null) return;

        Categoria cat = seleccionarCategoriaPorNombre(e);
        if (cat == null) return;

        Inscripcion.Distancia dist = seleccionarDistancia();
        Inscripcion.Talla talla = seleccionarTalla();
        int dorsal = leerEntero("Número de dorsal: ");
        int idIns = generarIdInscripcion(e);

        Inscripcion ins = new Inscripcion(
                idIns, dist, talla, dorsal,
                Inscripcion.Estado.PENDIENTE, c, e, cat
        );

        boolean ok = ADMIN.registrarInscripcionEnEvento(e, ins);
        System.out.println(ok ? "Inscripción creada." : "No se pudo (dorsal repetido o datos inválidos).");
    }

    // ======== Opción 4 ========
    private static void confirmarPagoInscripcion() {
        System.out.println("\n== Confirmar pago ==");
        Evento e = seleccionarEvento();
        if (e == null) return;
        int dorsal = leerEntero("Dorsal: ");
        Inscripcion ins = e.buscarInscripcionPorDorsal(dorsal);
        if (ins == null) { System.out.println("No existe esa inscripción."); return; }
        boolean ok = ADMIN.confirmarPagoInscripcion(ins);
        System.out.println(ok ? "Pago confirmado (PAGADA)." : "No fue posible (revise estado actual).");
    }

    // ======== Opción 5 (admin) ========
    private static void registrarTiempoComoAdmin() {
        if (!esAdminAutenticado()) return;
        System.out.println("\n== Registrar tiempo ==");
        Evento e = seleccionarEvento();
        if (e == null) return;
        int dorsal = leerEntero("Dorsal: ");
        double tMin = leerDouble("Tiempo (minutos, ej. 23.5): ");
        int posGen = leerEntero("Posición general: ");
        int posCat = leerEntero("Posición categoría: ");
        boolean ok = ADMIN.registrarTiempos(e, dorsal, tMin, posGen, posCat);
        System.out.println(ok ? "Tiempo registrado." : "No se pudo registrar (revise dorsal/evento).");

        System.out.println("\nResumen general:");
        System.out.println(e.generarResumenGeneral());
        System.out.println("\nTop 3 general:");
        for (String s : e.obtenerPodioGeneral()) System.out.println(" - " + s);
    }

    // ======== Opción 6 ========
    private static void verInscripcionesDeCorredor() {
        System.out.println("\n== Ver inscripciones del corredor ==");
        Corredor c = seleccionarCorredorPorCedula();
        if (c == null) return;
        c.consultarResultados();
    }

    // ======== Opción 7: Chat general ========
    private static void submenuChatGeneral() {
        System.out.println("\n== Chat General ==");
        int op;
        do {
            System.out.println("""
                    1) Entrar como usuario por cédula
                    2) Enviar mensaje
                    3) Ver mensajes
                    4) Agregar participante por cédula
                    0) Volver""");
            System.out.print("Opción: ");
            op = leerEntero();
            switch (op) {
                case 1 -> setUsuarioActualPorCedula();
                case 2 -> enviarMensajeChatGeneral();
                case 3 -> verMensajesChatGeneral();
                case 4 -> agregarParticipanteChatGeneral();
                case 0 -> { /* volver */ }
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

    private static void enviarMensajeChatGeneral() {
        if (usuarioActual == null) { System.out.println("Seleccione usuario (opción 1)."); return; }
        String msg = leerLineaNoVacia("Mensaje: ");
        boolean ok = CHAT_GENERAL.enviarMensaje(msg, usuarioActual);
        if (!ok) System.out.println("No se pudo (¿estás en el chat?).");
    }

    private static void verMensajesChatGeneral() {
        System.out.println("\n--- Chat General ---");
        for (String m : CHAT_GENERAL.recibirMensajes()) System.out.println(m);
        System.out.println("--------------------");
    }

    private static void agregarParticipanteChatGeneral() {
        int ced = leerEntero("Cédula a agregar: ");
        Usuario u = USUARIOS.get(ced);
        if (u == null) { System.out.println("No existe."); return; }
        boolean ok = CHAT_GENERAL.agregarParticipante(u);
        System.out.println(ok ? "Agregado." : "Ya estaba o no se pudo.");
    }

    // ======== Opción 8: Chat directo 1-a-1 ========
    private static void submenuChatDirecto() {
        System.out.println("\n== Chat Directo ==");
        int op;
        do {
            System.out.println("""
                    1) Seleccionar usuario actual por cédula
                    2) Abrir/crear conversación (cédula del otro)
                    3) Enviar mensaje en conversación actual
                    4) Ver mensajes de conversación actual
                    5) Listar mis conversaciones
                    0) Volver""");
            System.out.print("Opción: ");
            op = leerEntero();
            switch (op) {
                case 1 -> setUsuarioActualPorCedula();
                case 2 -> abrirOCrearConversacion();
                case 3 -> enviarMensajeMD();
                case 4 -> verMensajesMD();
                case 5 -> listarMisConversaciones();
                case 0 -> { /* volver */ }
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

    private static void abrirOCrearConversacion() {
        if (usuarioActual == null) { System.out.println("Seleccione usuario (opción 1)."); return; }
        int cedOtro = leerEntero("Cédula del otro usuario: ");
        Usuario otro = USUARIOS.get(cedOtro);
        if (otro == null) { System.out.println("No existe ese usuario."); return; }
        mdActual = buscarConversacion(usuarioActual, otro);
        if (mdActual == null) {
            mdActual = new MensajeriaDirecta(generarIdConversacion(), usuarioActual, otro);
            CONVERSACIONES.add(mdActual);
            System.out.println("Conversación creada (#" + mdActual.getIdConversacion() + ").");
        } else {
            System.out.println("Conversación abierta (#" + mdActual.getIdConversacion() + ").");
        }
    }

    private static void enviarMensajeMD() {
        if (usuarioActual == null) { System.out.println("Seleccione usuario (opción 1)."); return; }
        if (mdActual == null) { System.out.println("Abra/cree una conversación (opción 2)."); return; }
        String msg = leerLineaNoVacia("Mensaje: ");
        boolean ok = mdActual.enviarMensaje(msg, usuarioActual);
        if (!ok) System.out.println("No se pudo enviar.");
    }

    private static void verMensajesMD() {
        if (mdActual == null) { System.out.println("No hay conversación activa."); return; }
        System.out.println("\n--- Chat Directo #" + mdActual.getIdConversacion() + " ---");
        for (String m : mdActual.recibirMensajes()) System.out.println(m);
        System.out.println("---------------------------------------");
    }

    private static void listarMisConversaciones() {
        if (usuarioActual == null) { System.out.println("Seleccione usuario (opción 1)."); return; }
        System.out.println("Conversaciones de " + usuarioActual.getNombre() + ":");
        int count = 0;
        for (MensajeriaDirecta md : CONVERSACIONES) {
            if (md.getUsuario1() == usuarioActual || md.getUsuario2() == usuarioActual) {
                Usuario otro = (md.getUsuario1() == usuarioActual) ? md.getUsuario2() : md.getUsuario1();
                System.out.println(" - #" + md.getIdConversacion() + " con " +
                        (otro != null ? otro.getNombre() + " (" + otro.getId() + ")" : "(desconocido)"));
                count++;
            }
        }
        if (count == 0) System.out.println(" (sin conversaciones)");
    }

    private static MensajeriaDirecta buscarConversacion(Usuario a, Usuario b) {
        for (MensajeriaDirecta md : CONVERSACIONES) {
            if ((md.getUsuario1() == a && md.getUsuario2() == b) ||
                (md.getUsuario1() == b && md.getUsuario2() == a)) {
                return md;
            }
        }
        return null;
    }

    private static int generarIdConversacion() {
        int mx = 0;
        for (MensajeriaDirecta md : CONVERSACIONES)
            if (md.getIdConversacion() > mx) mx = md.getIdConversacion();
        return mx + 1;
    }

    // ======== Selección / utilitarios ========
    private static Evento seleccionarEvento() {
        if (EVENTOS.isEmpty()) { System.out.println("No hay eventos."); return null; }
        System.out.println("Eventos disponibles:");
        for (int i = 0; i < EVENTOS.size(); i++) {
            System.out.println((i + 1) + ") " + EVENTOS.get(i).getNombre() + " - " +
                    new SimpleDateFormat("dd/MM/yyyy").format(EVENTOS.get(i).getFecha()));
        }
        int idx = leerEntero("Seleccione (número): ") - 1;
        if (idx < 0 || idx >= EVENTOS.size()) { System.out.println("Selección inválida."); return null; }
        return EVENTOS.get(idx);
    }

    private static Categoria seleccionarCategoriaPorNombre(Evento e) {
        if (e.getCategorias().isEmpty()) { System.out.println("El evento no tiene categorías."); return null; }
        System.out.println("Categorías del evento:");
        for (Categoria c : e.getCategorias()) System.out.println(" - " + c.getNombre());
        String nombre = leerLineaNoVacia("Nombre exacto de la categoría: ");
        for (Categoria c : e.getCategorias())
            if (c.getNombre().equalsIgnoreCase(nombre)) return c;
        System.out.println("No existe esa categoría.");
        return null;
    }

    private static Corredor seleccionarCorredorPorCedula() {
        int ced = leerEntero("Cédula del corredor: ");
        Usuario u = USUARIOS.get(ced);
        if (u instanceof Corredor) return (Corredor) u;
        System.out.println("No existe corredor con esa cédula.");
        return null;
    }

    private static int generarIdInscripcion(Evento e) {
        int mx = 0;
        for (Inscripcion i : e.getInscripciones()) if (i.getId() > mx) mx = i.getId();
        return mx + 1;
    }

    private static void setUsuarioActualPorCedula() {
        int ced = leerEntero("Cédula: ");
        Usuario u = USUARIOS.get(ced);
        if (u == null) { System.out.println("No existe usuario con esa cédula."); return; }
        usuarioActual = u;
        System.out.println("Ahora estás como: " + u.getNombre());
    }

    private static boolean esAdminAutenticado() {
        System.out.println("== Autenticación de administrador ==");
        int ced = leerEntero("Cédula: ");
        if (ced != ADMIN.getId()) { System.out.println("Solo el administrador puede realizar esta acción."); return false; }
        return true;
    }

    // ======== Entrada segura ========
    private static int leerEntero() {
        while (true) {
            try { return Integer.parseInt(SC.nextLine().trim()); }
            catch (Exception e) { System.out.print("Ingrese un número válido: "); }
        }
    }

    private static int leerEntero(String prompt) { System.out.print(prompt); return leerEntero(); }

    private static double leerDouble(String prompt) {
        System.out.print(prompt);
        while (true) {
            try { return Double.parseDouble(SC.nextLine().trim().replace(",", ".")); }
            catch (Exception e) { System.out.print("Ingrese un número (ej. 23.5): "); }
        }
    }

    private static String leerLinea(String prompt) { System.out.print(prompt); return SC.nextLine(); }

    private static String leerLineaNoVacia(String prompt) {
        String s;
        do { System.out.print(prompt); s = SC.nextLine(); }
        while (s == null || s.isBlank());
        return s.trim();
    }

    private static Date leerFecha(String prompt) {
        System.out.print(prompt);
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy"); f.setLenient(false);
        while (true) {
            try { return f.parse(SC.nextLine().trim()); }
            catch (ParseException e) { System.out.print("Formato inválido. Use dd/MM/yyyy: "); }
        }
    }

    private static char leerChar(String prompt) {
        System.out.print(prompt);
        while (true) {
            String s = SC.nextLine().trim();
            if (!s.isEmpty()) return Character.toUpperCase(s.charAt(0));
            System.out.print("Ingrese un caracter: ");
        }
    }

    private static Inscripcion.Distancia seleccionarDistancia() {
        System.out.println("Distancias:");
        for (Inscripcion.Distancia d : Inscripcion.Distancia.values())
            System.out.println(" - " + d.name());
        while (true) {
            String s = leerLineaNoVacia("Elija (ej. DIEZ_K): ").toUpperCase(Locale.ROOT);
            try { return Inscripcion.Distancia.valueOf(s); }
            catch (Exception e) { System.out.println("Valor inválido."); }
        }
    }

    private static Inscripcion.Talla seleccionarTalla() {
        System.out.println("Tallas:");
        for (Inscripcion.Talla t : Inscripcion.Talla.values())
            System.out.println(" - " + t.name());
        while (true) {
            String s = leerLineaNoVacia("Elija (ej. M): ").toUpperCase(Locale.ROOT);
            try { return Inscripcion.Talla.valueOf(s); }
            catch (Exception e) { System.out.println("Valor inválido."); }
        }
    }
    // 9) Resumen general + podio general
    private static void verResumenGeneralDeEvento() {
        System.out.println("\n== Resumen general del evento ==");
        Evento e = seleccionarEvento();
        if (e == null) return;

        System.out.println(e.generarResumenGeneral());

        // Mostrar podio general (top 3)
        var podio = e.obtenerPodioGeneral();
        System.out.println("\nPodio general:");
        if (podio.isEmpty()) {
            System.out.println(" (sin llegadas aún)");
        } else {
            for (String s : podio) System.out.println(" - " + s);
        }
    }

// 10) Resumen por categoría + podio de esa categoría
    private static void verResumenPorCategoriaDeEvento() {
        System.out.println("\n== Resumen por categoría ==");
        Evento e = seleccionarEvento();
        if (e == null) return;

        if (e.getCategorias().isEmpty()) {
            System.out.println("El evento no tiene categorías.");
            return;
        }

        // listar categorías y pedir nombre exacto (ya tienes helper similar)
        System.out.println("Categorías del evento:");
        for (var c : e.getCategorias()) System.out.println(" - " + c.getNombre());
        String nombreCat = leerLineaNoVacia("Nombre exacto de la categoría: ");

        System.out.println(e.generarResumenPorCategoria(nombreCat));

        // Mostrar podio por categoría (top 3)
        var podioCat = e.obtenerPodioCategoria(nombreCat);
        System.out.println("\nPodio de la categoría \"" + nombreCat + "\":");
        if (podioCat.isEmpty()) {
            System.out.println(" (sin llegadas en esta categoría)");
        } else {
            for (String s : podioCat) System.out.println(" - " + s);
        }
    }

}
