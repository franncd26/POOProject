import chats.ChatGeneral;
import chats.MensajeriaDirecta;
import eventos.Categoria;
import eventos.Evento;
import eventos.Inscripcion;
import eventos.Tiempo;
import usuarios.Administrador;
import usuarios.Corredor;
import usuarios.Usuario;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Punto de entrada de la aplicación de consola.
 *
 * <p><b>Características principales:</b></p>
 * <ul>
 *   <li>Login por cédula: asigna rol por pertenencia a un set de cédulas de administrador.</li>
 *   <li>Menú de <b>Administrador</b>: crear eventos (con fecha real), cambiar estado (transiciones válidas),
 *       inscribir corredores (creación en caliente), confirmar pago/inscripción y registrar tiempos.</li>
 *   <li>Menú de <b>Corredor</b>: ver eventos, ver inscripciones propias y participar en chats.</li>
 *   <li><b>Chat General</b> y <b>Mensajería Directa</b> con validaciones de participación.</li>
 *   <li>Persistencia simple en memoria: Map de usuarios, eventos, DMs y tiempos por evento.</li>
 * </ul>
 *
 * <p><b>Convenciones:</b> no se cierra el {@link Scanner} global (no cerrar System.in); toda la interacción
 * de consola ocurre aquí (las entidades no realizan I/O).</p>
 */
public class Main {

    /** Scanner global (no cerrar). */
    private static final Scanner SC = new Scanner(System.in);

    /** Cédulas reconocidas como administradores (ajustar a tu entorno). */
    private static final Set<Integer> ADMIN_CEDULAS = new HashSet<>(Arrays.asList(1010, 2020));

    /** Almacenamiento en memoria de usuarios por cédula. */
    private static final Map<Integer, Usuario> USUARIOS = new HashMap<>();

    /** Almacenamiento en memoria de eventos por ID. */
    private static final Map<Integer, Evento> EVENTOS = new HashMap<>();

    /** Almacenamiento en memoria de hilos de DM por ID. */
    private static final Map<Integer, MensajeriaDirecta> DMS = new HashMap<>();

    /** Contadores simples de IDs. */
    private static final AtomicInteger SEQ_EVENTO = new AtomicInteger(1);
    private static final AtomicInteger SEQ_INSCRIPCION = new AtomicInteger(1);
    private static final AtomicInteger SEQ_DM = new AtomicInteger(1);

    /** Chat general único. */
    private static final ChatGeneral CHAT_GENERAL = new ChatGeneral(1);

    /** Tiempos por evento: Map<EventoId, Map<InscripcionId, Tiempo>> */
    private static final Map<Integer, Map<Integer, Tiempo>> TIEMPOS_POR_EVENTO = new HashMap<>();

    /** Usuario autenticado en la sesión actual. */
    private static Usuario usuarioActual = null;

    /** Catálogo base de categorías para selección rápida. */
    private static final List<Categoria> CATEGORIAS_BASE = new ArrayList<>();

    /**
     * Método principal.
     * <p>Precarga categorías de ejemplo y entra al bucle de login/ruteo de menús por rol.</p>
     *
     * @param args argumentos de línea de comandos (no usados).
     */
    public static void main(String[] args) {
        seedCategoriasDeEjemplo();
        loopLogin();
        // Nota: No cerramos SC para no cerrar System.in.
    }

    // =====================================================================================
    // Login y ruteo por rol
    // =====================================================================================

    /**
     * Bucle de autenticación y ruteo a menús de Administrador o Corredor.
     * <p>Permite cerrar sesión y reingresar con otra cédula.</p>
     */
    private static void loopLogin() {
        while (true) {
            limpiarPantalla();
            titulo("Inicio de sesión");
            int cedula = leerEntero("Cédula (números): ");
            Usuario u = USUARIOS.get(cedula);

            if (u == null) {
                String nombre = leerObligatorio("Nombre: ");
                String tel = leerLinea("Teléfono (opcional): ");
                String correo = leerLinea("Correo (opcional): ");

                if (esAdminPorCedula(cedula)) {
                    u = new Administrador(cedula, nombre, tel, correo, "ADMIN");
                    USUARIOS.put(cedula, u);
                    println("Creado y autenticado como Administrador.");
                } else {
                    u = new Corredor(cedula, nombre, tel, correo);
                    USUARIOS.put(cedula, u);
                    println("Creado y autenticado como Corredor.");
                }
            } else {
                println("Bienvenido de nuevo, " + u.getNombre() + " (" + (u instanceof Administrador ? "Admin" : "Corredor") + ")");
            }

            usuarioActual = u;
            pausa();

            if (usuarioActual instanceof Administrador) {
                menuAdministrador((Administrador) usuarioActual);
            } else {
                menuCorredor((Corredor) usuarioActual);
            }
        }
    }

    /**
     * Determina si una cédula pertenece a un administrador.
     *
     * @param cedula cédula a validar.
     * @return {@code true} si la cédula está registrada como admin; {@code false} en caso contrario.
     */
    private static boolean esAdminPorCedula(int cedula) {
        return ADMIN_CEDULAS.contains(cedula);
    }

    // =====================================================================================
    // Menú Administrador
    // =====================================================================================

    /**
     * Menú para usuarios administradores.
     *
     * @param admin instancia autenticada de {@link Administrador}.
     */
    private static void menuAdministrador(Administrador admin) {
        int opt;
        do {
            limpiarPantalla();
            titulo("Menú Administrador - " + admin.getNombre());
            println("1) Crear evento");
            println("2) Cambiar estado de evento (transiciones válidas)");
            println("3) Listar eventos y resumen");
            println("4) Crear inscripción para corredor");
            println("5) Confirmar pago / Confirmar inscripción");
            println("6) Registrar tiempo");
            println("7) Mostrar resumen general (mejores tiempos)");
            println("8) Mostrar resumen por categoría (Distancia)");
            println("9) Chat General");
            println("10) Mensajería Directa");
            println("11) Cerrar sesión");
            println("0) Salir");
            opt = leerEntero("Opción: ");

            switch (opt) {
                case 1 -> accionCrearEvento(admin);
                case 2 -> accionCambiarEstadoEvento(admin);
                case 3 -> accionListarEventosConResumen();
                case 4 -> accionCrearInscripcion(admin);
                case 5 -> accionConfirmaciones();
                case 6 -> accionRegistrarTiempo(admin);
                case 7 -> accionResumenGeneral();
                case 8 -> accionResumenPorDistancia();
                case 9 -> submenuChatGeneral();
                case 10 -> submenuMensajeriaDirecta();
                case 11 -> { usuarioActual = null; return; }
                case 0 -> salida();
                default -> println("Opción inválida.");
            }
            pausa();
        } while (true);
    }

    // =====================================================================================
    // Menú Corredor
    // =====================================================================================

    /**
     * Menú para usuarios corredores.
     *
     * @param corredor instancia autenticada de {@link Corredor}.
     */
    private static void menuCorredor(Corredor corredor) {
        int opt;
        do {
            limpiarPantalla();
            titulo("Menú Corredor - " + corredor.getNombre());
            println("1) Ver eventos disponibles");
            println("2) Ver mis inscripciones");
            println("3) Mostrar resumen general (mejores tiempos)");
            println("4) Mostrar resumen por categoría (Distancia)");
            println("5) Chat General");
            println("6) Mensajería Directa");
            println("7) Cerrar sesión");
            println("0) Salir");
            opt = leerEntero("Opción: ");

            switch (opt) {
                case 1 -> accionListarEventosConResumen();
                case 2 -> accionVerInscripcionesCorredor(corredor);
                case 3 -> accionResumenGeneral();
                case 4 -> accionResumenPorDistancia();
                case 5 -> submenuChatGeneral();
                case 6 -> submenuMensajeriaDirecta();
                case 7 -> { usuarioActual = null; return; }
                case 0 -> salida();
                default -> println("Opción inválida.");
            }
            pausa();
        } while (true);
    }

    // =====================================================================================
    // Acciones: Eventos / Inscripciones / Tiempos
    // =====================================================================================

    /**
     * Crea un evento solicitando nombre, descripción, fecha (AAAA-MM-DD) y categorías.
     *
     * @param admin administrador actual.
     */
    private static void accionCrearEvento(Administrador admin) {
        titulo("Crear evento");
        String nombre = leerObligatorio("Nombre: ");
        String descripcion = leerLinea("Descripción (opcional): ");

        // Pedir fecha real en formato AAAA-MM-DD
        Date fecha = null;
        while (fecha == null) {
            String f = leerLinea("Fecha (AAAA-MM-DD): ");
            try {
                String[] p = f.split("-");
                Calendar cal = Calendar.getInstance();
                cal.setLenient(false);
                cal.set(Integer.parseInt(p[0]), Integer.parseInt(p[1]) - 1, Integer.parseInt(p[2]), 0, 0, 0);
                cal.set(Calendar.MILLISECOND, 0);
                fecha = cal.getTime();
            } catch (Exception e) {
                println("Formato inválido. Ej: 2025-11-03");
            }
        }

        List<Categoria> categorias = seleccionarCategorias();

        int id = SEQ_EVENTO.getAndIncrement();
        Evento ev = admin.crearEvento(id, nombre, fecha, descripcion, categorias);
        EVENTOS.put(ev.getId(), ev);

        println("Evento creado con ID: " + ev.getId() + " (estado: " + ev.getEstado() + ")");
        println("Recuerda ABRIR el evento para permitir inscripciones.");
    }

    /**
     * Cambia el estado de un evento respetando transiciones válidas:
     * PLANIFICADO→ABIERTO→CERRADO→FINALIZADO.
     *
     * @param admin administrador actual.
     */
    private static void accionCambiarEstadoEvento(Administrador admin) {
        titulo("Cambiar estado de evento");
        Evento ev = seleccionarEvento();
        if (ev == null) { println("No hay eventos."); return; }

        println("Estado actual: " + ev.getEstado());
        println("Transiciones permitidas:");
        switch (ev.getEstado()) {
            case PLANIFICADO -> println("1) ABRIR");
            case ABIERTO     -> println("2) CERRAR");
            case CERRADO     -> println("3) FINALIZAR");
            case FINALIZADO  -> println("No hay transiciones desde FINALIZADO.");
        }

        int o = leerEntero("Opción: ");
        try {
            switch (ev.getEstado()) {
                case PLANIFICADO -> { if (o == 1) admin.abrirEvento(ev); else println("Opción inválida."); }
                case ABIERTO     -> { if (o == 2) admin.cerrarEvento(ev); else println("Opción inválida."); }
                case CERRADO     -> { if (o == 3) admin.finalizarEvento(ev); else println("Opción inválida."); }
                case FINALIZADO  -> { /* sin transición */ }
            }
        } catch (Exception e) {
            println("Error: " + e.getMessage());
        }
        println("Estado actual: " + ev.getEstado());
    }

    /**
     * Lista eventos con un resumen de inscripciones y cantidad de tiempos registrados.
     */
    private static void accionListarEventosConResumen() {
        titulo("Eventos y resumen");
        if (EVENTOS.isEmpty()) {
            println("No hay eventos registrados.");
            return;
        }
        EVENTOS.values().stream()
                .sorted(Comparator.comparing(Evento::getId))
                .forEach(ev -> {
                    println("[" + ev.getId() + "] " + ev.getNombre() + " | " + ev.getEstado());
                    println("   " + ev.generarResumenInscripciones());
                    Map<Integer, Tiempo> mapT = TIEMPOS_POR_EVENTO.get(ev.getId());
                    int cantTiempos = (mapT == null) ? 0 : mapT.size();
                    println("   Tiempos registrados: " + cantTiempos);
                });
    }

    /**
     * Crea una inscripción para un corredor (crea al corredor “en caliente” si no existe),
     * valida estado del evento y unicidad de dorsal.
     *
     * @param admin administrador actual.
     */
    private static void accionCrearInscripcion(Administrador admin) {
        titulo("Crear inscripción");
        Evento ev = seleccionarEvento();
        if (ev == null) { println("No hay eventos."); return; }

        if (ev.getEstado() != Evento.EstadoEvento.ABIERTO) {
            println("El evento no está ABIERTO. Ábrelo antes de inscribir.");
            return;
        }

        int ced = leerEntero("Cédula del corredor: ");
        Usuario u = USUARIOS.get(ced);
        Corredor cor;
        if (u == null) {
            println("No existe esa cédula. Creemos al corredor:");
            String nombre = leerObligatorio("Nombre: ");
            String tel = leerLinea("Tel (opcional): ");
            String correo = leerLinea("Correo (opcional): ");
            cor = new Corredor(ced, nombre, tel, correo);
            USUARIOS.put(ced, cor);
            println("Corredor creado.");
        } else if (u instanceof Corredor) {
            cor = (Corredor) u;
        } else {
            println("Esa cédula pertenece a un Administrador. Usa una cédula de corredor.");
            return;
        }

        mostrarDistancias();
        int dOpt = leerEntero("Distancia (1..4): ");
        Inscripcion.Distancia dist = switch (dOpt) {
            case 1 -> Inscripcion.Distancia.CINCO_K;
            case 2 -> Inscripcion.Distancia.DIEZ_K;
            case 3 -> Inscripcion.Distancia.MEDIA_MARATON;
            case 4 -> Inscripcion.Distancia.MARATON;
            default -> null;
        };
        if (dist == null) { println("Distancia inválida."); return; }

        mostrarTallas();
        int tOpt = leerEntero("Talla (1..5): ");
        Inscripcion.Talla talla = switch (tOpt) {
            case 1 -> Inscripcion.Talla.XS;
            case 2 -> Inscripcion.Talla.S;
            case 3 -> Inscripcion.Talla.M;
            case 4 -> Inscripcion.Talla.L;
            case 5 -> Inscripcion.Talla.XL;
            default -> null;
        };
        if (talla == null) { println("Talla inválida."); return; }

        int dorsal = leerEntero("Dorsal (>0 y único en el evento): ");
        int insId = SEQ_INSCRIPCION.getAndIncrement();

        try {
            Inscripcion ins = admin.crearInscripcionParaCorredor(insId, cor, ev, dist, talla, dorsal);
            println("Inscripción creada: " + ins);
        } catch (Exception ex) {
            println("Error: " + ex.getMessage());
        }
    }

    /**
     * Permite confirmar pago (PENDIENTE→PAGADO) o confirmar inscripción (PAGADO→CONFIRMADO).
     */
    private static void accionConfirmaciones() {
        titulo("Confirmaciones");
        Evento ev = seleccionarEvento();
        if (ev == null || ev.getInscripciones().isEmpty()) {
            println("No hay inscripciones en este evento.");
            return;
        }
        listarInscripciones(ev);
        int idIns = leerEntero("ID de inscripción: ");
        Inscripcion target = ev.getInscripciones().stream()
                .filter(i -> i.getId() == idIns)
                .findFirst().orElse(null);
        if (target == null) { println("No existe esa inscripción."); return; }

        println("Estado actual: " + target.getEstado());
        println("1) Confirmar pago (PENDIENTE→PAGADO)");
        println("2) Confirmar inscripción (PAGADO→CONFIRMADO)");
        int o = leerEntero("Opción: ");
        try {
            if (o == 1) {
                target.confirmarPago();
            } else if (o == 2) {
                target.confirmarInscripcion();
            } else {
                println("Opción inválida.");
                return;
            }
            println("Nuevo estado: " + target.getEstado());
        } catch (Exception ex) {
            println("Error: " + ex.getMessage());
        }
    }

    /**
     * Registra un tiempo para una inscripción y lo almacena en memoria bajo {@code TIEMPOS_POR_EVENTO}.
     * <p><b>Cambio solicitado:</b> ahora solo pide el tiempo y posiciona general/categoría en 0.</p>
     *
     * @param admin administrador actual.
     */
    /**
 * Registra un tiempo para una inscripción y lo almacena en memoria bajo {@code TIEMPOS_POR_EVENTO}.
 * <p><b>Cambio solicitado:</b> ahora solo pide el tiempo y posiciona general/categoría en 0.</p>
 *
 * @param admin administrador actual.
 */
private static void accionRegistrarTiempo(Administrador admin) {
    titulo("Registrar tiempo");
    Evento ev = seleccionarEvento();
    if (ev == null || ev.getInscripciones().isEmpty()) {
        println("No hay inscripciones en este evento.");
        return;
    }

    // ✅ Nueva validación: no permitir registrar si el evento está CERRADO
    if (ev.getEstado() == Evento.EstadoEvento.CERRADO) {
        println("No se pueden registrar tiempos: el evento está CERRADO.");
        return;
    }

    listarInscripciones(ev);
    int idIns = leerEntero("ID de inscripción: ");
    Inscripcion ins = ev.getInscripciones().stream()
            .filter(i -> i.getId() == idIns)
            .findFirst().orElse(null);
    if (ins == null) { println("No existe esa inscripción."); return; }

    // ✅ Nueva validación: solo inscripciones CONFIRMADAS
    if (ins.getEstado() != Inscripcion.Estado.CONFIRMADO) {
        println("Solo se pueden registrar tiempos para inscripciones en estado CONFIRMADO.");
        return;
    }

    double tiempoSeg = leerDouble("Tiempo (segundos ≥ 0): ");

    try {
        // Posiciones en 0 como acordado
        int posGen = 0;
        int posCat = 0;
        Tiempo t = admin.registrarTiempoParaInscripcion(ins, tiempoSeg, posGen, posCat);
        TIEMPOS_POR_EVENTO
                .computeIfAbsent(ev.getId(), k -> new HashMap<>())
                .put(ins.getId(), t);
        println("Tiempo registrado y guardado: " + t);
    } catch (Exception ex) {
        println("Error: " + ex.getMessage());
    }
}


    /**
     * Muestra en consola las inscripciones del corredor actual.
     *
     * @param c corredor autenticado.
     */
    private static void accionVerInscripcionesCorredor(Corredor c) {
        titulo("Mis inscripciones");
        List<Inscripcion> lista = c.getInscripciones();
        if (lista.isEmpty()) {
            println("No tienes inscripciones.");
            return;
        }
        for (Inscripcion i : lista) {
            println(i.toString());
        }
    }

    // =====================================================================================
    // Resúmenes (NUEVO)
    // =====================================================================================

    /**
     * Muestra el Top N (por defecto 10) de mejores tiempos de un evento.
     * Orden ascendente por tiempo (menor = mejor).
     */
    private static void accionResumenGeneral() {
        titulo("Resumen general - Mejores tiempos");
        Evento ev = seleccionarEvento();
        if (ev == null) { println("No hay eventos."); return; }

        Map<Integer, Tiempo> mapa = TIEMPOS_POR_EVENTO.get(ev.getId());
        if (mapa == null || mapa.isEmpty()) {
            println("No hay tiempos registrados para este evento.");
            return;
        }

        int topN = leerEntero("¿Cuántos mostrar? (ej. 10): ");
        // Construir pares (Inscripcion, Tiempo)
        List<Map.Entry<Inscripcion, Tiempo>> pares = new ArrayList<>();
        for (Inscripcion ins : ev.getInscripciones()) {
            Tiempo t = mapa.get(ins.getId());
            if (t != null) pares.add(new AbstractMap.SimpleEntry<>(ins, t));
        }
        if (pares.isEmpty()) {
            println("No hay tiempos vinculados a inscripciones de este evento.");
            return;
        }

        pares.sort(Comparator.comparingDouble(e -> e.getValue().getTiempoIndividual()));
        imprimirTablaTiempos(pares, Math.max(1, topN));
    }

    /**
     * Muestra el Top N de mejores tiempos filtrado por “categoría” entendida como Distancia.
     * (5K, 10K, Media Maratón, Maratón).
     */
    private static void accionResumenPorDistancia() {
        titulo("Resumen por categoría (Distancia)");
        Evento ev = seleccionarEvento();
        if (ev == null) { println("No hay eventos."); return; }

        Map<Integer, Tiempo> mapa = TIEMPOS_POR_EVENTO.get(ev.getId());
        if (mapa == null || mapa.isEmpty()) {
            println("No hay tiempos registrados para este evento.");
            return;
        }

        mostrarDistancias();
        int dOpt = leerEntero("Selecciona la distancia (1..4): ");
        Inscripcion.Distancia dist = switch (dOpt) {
            case 1 -> Inscripcion.Distancia.CINCO_K;
            case 2 -> Inscripcion.Distancia.DIEZ_K;
            case 3 -> Inscripcion.Distancia.MEDIA_MARATON;
            case 4 -> Inscripcion.Distancia.MARATON;
            default -> null;
        };
        if (dist == null) { println("Opción inválida."); return; }

        int topN = leerEntero("¿Cuántos mostrar? (ej. 10): ");

        List<Map.Entry<Inscripcion, Tiempo>> pares = new ArrayList<>();
        for (Inscripcion ins : ev.getInscripciones()) {
            if (ins.getDistancia() == dist) {
                Tiempo t = mapa.get(ins.getId());
                if (t != null) pares.add(new AbstractMap.SimpleEntry<>(ins, t));
            }
        }
        if (pares.isEmpty()) {
            println("No hay tiempos para la distancia seleccionada.");
            return;
        }

        pares.sort(Comparator.comparingDouble(e -> e.getValue().getTiempoIndividual()));
        imprimirTablaTiempos(pares, Math.max(1, topN));
    }

    /**
     * Imprime tabla compacta de tiempos: Pos, Corredor, Dorsal, Distancia y Tiempo formateado.
     *
     * @param pares lista de pares (Inscripcion, Tiempo) ordenada ascendente por tiempo.
     * @param topN  cantidad a mostrar.
     */
    private static void imprimirTablaTiempos(List<Map.Entry<Inscripcion, Tiempo>> pares, int topN) {
        println(String.format("%-4s %-20s %-8s %-12s %-10s", "Pos", "Corredor", "Dorsal", "Distancia", "Tiempo"));
        println("---------------------------------------------------------------");
        for (int i = 0; i < Math.min(topN, pares.size()); i++) {
            var e = pares.get(i);
            Inscripcion ins = e.getKey();
            Tiempo t = e.getValue();
            String tiempoFmt = formatearSegundos(t.getTiempoIndividual());
            println(String.format("%-4d %-20s %-8d %-12s %-10s",
                    (i + 1),
                    ins.getCorredor().getNombre(),
                    ins.getNumeroDorsal(),
                    nombrarDistancia(ins.getDistancia()),
                    tiempoFmt
            ));
        }
    }

    /**
     * Convierte segundos a formato mm:ss (o hh:mm:ss si aplica).
     */
    private static String formatearSegundos(double segundos) {
        long total = (long) Math.floor(segundos);
        long h = total / 3600;
        long m = (total % 3600) / 60;
        long s = total % 60;
        if (h > 0) return String.format("%d:%02d:%02d", h, m, s);
        return String.format("%02d:%02d", m, s);
    }

    /**
     * Nombre legible para la distancia.
     */
    private static String nombrarDistancia(Inscripcion.Distancia d) {
        return switch (d) {
            case CINCO_K -> "5K";
            case DIEZ_K -> "10K";
            case MEDIA_MARATON -> "Media";
            case MARATON -> "Maratón";
        };
    }

    // =====================================================================================
    // Chat General
    // =====================================================================================

    /**
     * Submenú del chat general: unirse/salir, enviar y ver últimos mensajes.
     */
    private static void submenuChatGeneral() {
        int opt;
        do {
            limpiarPantalla();
            titulo("Chat General (ID=" + CHAT_GENERAL.getIdChat() + ")");
            println("Participantes: " + CHAT_GENERAL.getParticipantes().size() + " | Mensajes: " + CHAT_GENERAL.getMensajes().size());
            println("1) Unirme (si no estoy)");
            println("2) Salir del chat");
            println("3) Enviar mensaje");
            println("4) Ver últimos 10 mensajes");
            println("9) Volver");
            opt = leerEntero("Opción: ");

            switch (opt) {
                case 1 -> {
                    try {
                        boolean added = CHAT_GENERAL.agregarParticipante(usuarioActual);
                        println(added ? "Te uniste al chat." : "Ya estabas en el chat.");
                    } catch (Exception ex) {
                        println("Error: " + ex.getMessage());
                    }
                }
                case 2 -> {
                    try {
                        boolean removed = CHAT_GENERAL.eliminarParticipante(usuarioActual);
                        println(removed ? "Saliste del chat." : "No estabas en el chat.");
                    } catch (Exception ex) {
                        println("Error: " + ex.getMessage());
                    }
                }
                case 3 -> {
                    if (!CHAT_GENERAL.getParticipantes().contains(usuarioActual)) {
                        println("Debes unirte al chat antes de enviar mensajes.");
                    } else {
                        String texto = leerLinea("Mensaje: ");
                        if (texto.isBlank()) {
                            println("El mensaje no puede estar vacío.");
                        } else {
                            try {
                                CHAT_GENERAL.enviarMensaje(usuarioActual, texto);
                                println("Mensaje enviado.");
                            } catch (Exception ex) {
                                println("Error: " + ex.getMessage());
                            }
                        }

                    }
                }
                case 4 -> {
                    List<ChatGeneral.Mensaje> all = CHAT_GENERAL.getMensajes();
                    int from = Math.max(0, all.size() - 10);
                    List<ChatGeneral.Mensaje> ultimos = all.subList(from, all.size());
                    if (ultimos.isEmpty()) {
                        println("No hay mensajes.");
                    } else {
                        ultimos.forEach(m -> println(
                                String.format("[%tF %<tT] %s: %s", m.getFecha(), m.getRemitente().getNombre(), m.getTexto())
                        ));
                    }
                }
                case 9 -> { return; }
                default -> println("Opción inválida.");
            }
            pausa();
        } while (true);
    }

    // =====================================================================================
    // Mensajería Directa
    // =====================================================================================

    /**
     * Submenú de mensajería directa: iniciar/seleccionar DM, enviar y ver últimos mensajes.
     */
    private static void submenuMensajeriaDirecta() {
        int opt;
        do {
            limpiarPantalla();
            titulo("Mensajería Directa");
            println("1) Iniciar DM con cédula");
            println("2) Enviar mensaje a DM existente");
            println("3) Ver últimos 10 mensajes de un DM");
            println("9) Volver");
            opt = leerEntero("Opción: ");

            switch (opt) {
                case 1 -> accionIniciarDM();
                case 2 -> accionEnviarDM();
                case 3 -> accionVerDM();
                case 9 -> { return; }
                default -> println("Opción inválida.");
            }
            pausa();
        } while (true);
    }

    /**
     * Inicia (o reutiliza) un DM entre el usuario actual y otra cédula.
     */
    private static void accionIniciarDM() {
        if (usuarioActual == null) { println("Debes iniciar sesión."); return; }
        int cedReceptor = leerEntero("Cédula del otro usuario: ");
        Usuario otro = USUARIOS.get(cedReceptor);
        if (otro == null) { println("No existe ese usuario."); return; }
        if (otro.equals(usuarioActual)) { println("No puedes abrir DM contigo mismo."); return; }

        MensajeriaDirecta dm = buscarDMEntre(usuarioActual, otro);
        if (dm == null) {
            dm = new MensajeriaDirecta(SEQ_DM.getAndIncrement(), usuarioActual, otro);
            DMS.put(dm.getIdDM(), dm);
            println("DM creado con ID: " + dm.getIdDM());
        } else {
            println("Ya existía un DM: ID " + dm.getIdDM());
        }
    }

    /**
     * Envía un mensaje en un DM existente donde participa el usuario actual.
     */
    private static void accionEnviarDM() {
        if (usuarioActual == null) { println("Debes iniciar sesión."); return; }
        MensajeriaDirecta dm = seleccionarDMDelUsuarioActual();
        if (dm == null) { println("No tienes DMs activos."); return; }
        String texto = leerLinea("Mensaje: ");
        if (texto.isBlank()) {
            println("El mensaje no puede estar vacío.");
        } else {
            try {
                CHAT_GENERAL.enviarMensaje(usuarioActual, texto);
                println("Mensaje enviado.");
            } catch (Exception ex) {
                println("Error: " + ex.getMessage());
            }
        }

    }

    /**
     * Muestra los últimos 10 mensajes de un DM donde participa el usuario actual.
     */
    private static void accionVerDM() {
        if (usuarioActual == null) { println("Debes iniciar sesión."); return; }
        MensajeriaDirecta dm = seleccionarDMDelUsuarioActual();
        if (dm == null) { println("No tienes DMs activos."); return; }
        List<MensajeriaDirecta.Mensaje> all = dm.getMensajes();
        int from = Math.max(0, all.size() - 10);
        List<MensajeriaDirecta.Mensaje> ultimos = all.subList(from, all.size());
        if (ultimos.isEmpty()) {
            println("No hay mensajes en este DM.");
        } else {
            Usuario receptor = dm.obtenerReceptor(usuarioActual);
            println("DM con: " + receptor.getNombre() + " (ID DM: " + dm.getIdDM() + ")");
            ultimos.forEach(m -> println(
                    String.format("[%tF %<tT] %s: %s", m.getFecha(), m.getRemitente().getNombre(), m.getTexto())
            ));
        }
    }

    // =====================================================================================
    // Utilidades auxiliares
    // =====================================================================================

    /**
     * Busca un DM existente entre dos usuarios.
     *
     * @param a usuario A.
     * @param b usuario B.
     * @return instancia de DM si existe; {@code null} en caso contrario.
     */
    private static MensajeriaDirecta buscarDMEntre(Usuario a, Usuario b) {
        for (MensajeriaDirecta dm : DMS.values()) {
            if (dm.esParticipante(a) && dm.esParticipante(b)) return dm;
        }
        return null;
    }

    /**
     * Permite seleccionar un DM donde participa el usuario actual, validando pertenencia.
     *
     * @return DM elegido o {@code null} si no hay/selección inválida.
     */
    private static MensajeriaDirecta seleccionarDMDelUsuarioActual() {
        List<MensajeriaDirecta> mis = new ArrayList<>();
        for (MensajeriaDirecta dm : DMS.values()) {
            if (dm.esParticipante(usuarioActual)) mis.add(dm);
        }
        if (mis.isEmpty()) return null;
        println("Mis DMs:");
        for (MensajeriaDirecta dm : mis) {
            Usuario otro = dm.obtenerReceptor(usuarioActual);
            println("[" + dm.getIdDM() + "] con " + otro.getNombre() + " (cédula " + otro.getId() + ")");
        }
        int id = leerEntero("ID DM: ");
        MensajeriaDirecta elegido = DMS.get(id);
        if (elegido == null || !elegido.esParticipante(usuarioActual)) {
            println("ID inválido o no eres participante.");
            return null;
        }
        return elegido;
    }

    /**
     * Permite seleccionar un evento por ID mostrando lista disponible.
     *
     * @return evento elegido o {@code null} si no hay eventos.
     */
    private static Evento seleccionarEvento() {
        if (EVENTOS.isEmpty()) return null;
        println("Eventos:");
        EVENTOS.values().stream()
                .sorted(Comparator.comparing(Evento::getId))
                .forEach(ev -> println("[" + ev.getId() + "] " + ev.getNombre() + " | " + ev.getEstado()));
        int id = leerEntero("ID evento: ");
        return EVENTOS.get(id);
    }

    /**
     * Lista inscripciones de un evento en consola (ID, corredor, dorsal, estado).
     *
     * @param ev evento del cual listar inscripciones.
     */
    private static void listarInscripciones(Evento ev) {
        if (ev.getInscripciones().isEmpty()) {
            println("No hay inscripciones.");
            return;
        }
        println("Inscripciones:");
        ev.getInscripciones().forEach(i ->
                println("ID=" + i.getId() + " | Corredor=" + i.getCorredor().getNombre()
                        + " | Dorsal=" + i.getNumeroDorsal() + " | Estado=" + i.getEstado())
        );
    }

    /**
     * Muestra opciones de distancias disponibles.
     */
    private static void mostrarDistancias() {
        println("Distancias: 1) 5K  2) 10K  3) Media Maratón  4) Maratón");
    }

    /**
     * Muestra opciones de tallas disponibles.
     */
    private static void mostrarTallas() {
        println("Tallas: 1) XS  2) S  3) M  4) L  5) XL");
    }

    /**
     * Permite seleccionar múltiples categorías desde el catálogo base.
     *
     * @return lista de categorías elegidas (posiblemente vacía).
     */
    private static List<Categoria> seleccionarCategorias() {
        List<Categoria> oferta = CATEGORIAS_BASE;
        if (oferta.isEmpty()) return new ArrayList<>();
        println("Categorías disponibles:");
        for (int i = 0; i < oferta.size(); i++) {
            Categoria c = oferta.get(i);
            println((i + 1) + ") " + c.getNombre() + " (" + c.getEdadMin() + "-" + c.getEdadMax() + ")");
        }
        String sel = leerLinea("Ingrese índices separados por coma (ej: 1,2): ");
        String[] toks = sel.split(",");
        List<Categoria> res = new ArrayList<>();
        for (String t : toks) {
            try {
                int idx = Integer.parseInt(t.trim()) - 1;
                if (idx >= 0 && idx < oferta.size()) res.add(oferta.get(idx));
            } catch (NumberFormatException ignored) {}
        }
        return res;
    }

    // =====================================================================================
    // Categorías de ejemplo (catálogo base)
    // =====================================================================================

    /**
     * Precarga categorías típicas para facilitar la creación de eventos.
     * <p>Evita duplicación si ya fueron precargadas.</p>
     */
    private static void seedCategoriasDeEjemplo() {
        if (!CATEGORIAS_BASE.isEmpty()) return;
        CATEGORIAS_BASE.add(new Categoria(1, "Juvenil", 15, 25));
        CATEGORIAS_BASE.add(new Categoria(2, "Adulto", 26, 40));
        CATEGORIAS_BASE.add(new Categoria(3, "Máster", 41, 60));
    }

    // =====================================================================================
    // Lectura robusta y helpers de UI de consola
    // =====================================================================================

    /**
     * Lee una línea de texto desde consola (con trim).
     *
     * @param prompt mensaje de prompt.
     * @return cadena (posiblemente vacía si el usuario solo presiona ENTER).
     */
    private static String leerLinea(String prompt) {
        System.out.print(prompt);
        String s = SC.nextLine();
        return s == null ? "" : s.trim();
    }

    /**
     * Lee un entero robustamente, reintentando hasta que sea válido.
     *
     * @param prompt mensaje de prompt.
     * @return entero leído.
     */
    private static int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine();
            try {
                return Integer.parseInt(s.trim());
            } catch (Exception e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    /**
     * Lee un double robustamente, reintentando hasta que sea válido y ≥ 0.
     *
     * @param prompt mensaje de prompt.
     * @return valor double leído (≥ 0).
     */
    private static double leerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = SC.nextLine();
            try {
                double v = Double.parseDouble(s.trim());
                if (v < 0) System.out.println("Debe ser >= 0.");
                else return v;
            } catch (Exception e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }
        /**
     * Lee una línea obligatoria (no vacía). Repite hasta que haya texto.
     *
     * @param prompt mensaje de prompt.
     * @return texto no vacío.
     */
    private static String leerObligatorio(String prompt) {
        while (true) {
            String s = leerLinea(prompt);
            if (s != null && !s.isBlank()) {
                return s;
            }
            System.out.println("Este campo es obligatorio. Intenta de nuevo.");
        }
    }


    /**
     * Imprime un título seccionado.
     *
     * @param t texto del título.
     */
    private static void titulo(String t) {
        System.out.println("=== " + t + " ===");
    }

    /**
     * Imprime una línea de texto.
     *
     * @param s texto a imprimir.
     */
    private static void println(String s) {
        System.out.println(s);
    }

    /**
     * Pausa la consola hasta que el usuario presione ENTER.
     */
    private static void pausa() {
        System.out.print("Continuar [ENTER]...");
        SC.nextLine();
    }

    /**
     * Limpieza visual simple (no borra pantalla; solo separa).
     */
    private static void limpiarPantalla() {
        System.out.println();
    }

    /**
     * Termina la aplicación sin cerrar el {@link Scanner} global explícitamente.
     */
    private static void salida() {
        System.out.println("¡Hasta pronto!");
        System.exit(0);
    }
}
