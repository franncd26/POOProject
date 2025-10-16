package usuarios;

import eventos.Categoria;
import eventos.Evento;
import eventos.Inscripcion;
import eventos.Tiempo;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * La clase {@code Administrador} representa a un usuario con permisos de gestión:
 * creación y administración de eventos, manejo de inscripciones y registro de tiempos.
 *
 * <p><b>Convenciones de diseño:</b></p>
 * <ul>
 *   <li>Sin operaciones de entrada/salida (I/O) dentro de la entidad.</li>
 *   <li>Métodos por parámetros; el {@code main} u otra capa orquestan la interacción.</li>
 *   <li>Se reutilizan las reglas de negocio encapsuladas en {@link Evento} e {@link Inscripcion}.</li>
 * </ul>
 *
 * @author
 * @version 1.0
 */
public class Administrador extends Usuario {

    /** Rol o descripción del perfil administrativo (p. ej., "ADMIN_GLOBAL", "ORGANIZADOR"). */
    private String rol;

    /**
     * Crea un {@code Administrador}.
     *
     * @param id       identificador único (&gt; 0).
     * @param nombre   nombre del administrador (no nulo/ni vacío).
     * @param telefono teléfono (no nulo; puede ser vacío).
     * @param correo   correo (no nulo; puede ser vacío).
     * @param rol      rol/descripción (no nulo/ni vacío).
     * @throws IllegalArgumentException si algún parámetro viola las reglas.
     */
    public Administrador(int id, String nombre, String telefono, String correo, String rol) {
        super(id, nombre, telefono, correo);
        setRol(rol);
    }

    // ------------------------------------------------------------
    // Getters / Setters
    // ------------------------------------------------------------

    /** @return rol del administrador. */
    public String getRol() { return rol; }

    /**
     * Establece el rol del administrador.
     * @param rol valor no nulo/ni vacío.
     * @throws IllegalArgumentException si {@code rol} es nulo/vacío.
     */
    public void setRol(String rol) {
        if (rol == null || rol.trim().isEmpty())
            throw new IllegalArgumentException("El rol no puede ser nulo/vacío.");
        this.rol = rol.trim();
    }

    // ------------------------------------------------------------
    // Gestión de eventos
    // ------------------------------------------------------------

    /**
     * Crea un nuevo {@link Evento} con parámetros explícitos.
     * <p>El estado inicial del evento será {@link Evento.EstadoEvento#PLANIFICADO}.</p>
     *
     * @param id          id del evento (&gt; 0).
     * @param nombre      nombre (no nulo/ni vacío).
     * @param fecha       fecha (no nula).
     * @param descripcion descripción (puede ser nula; se normaliza a "").
     * @param categorias  categorías iniciales (puede ser nula).
     * @return instancia de {@code Evento} creada.
     * @throws IllegalArgumentException si parámetros inválidos.
     */
    public Evento crearEvento(int id, String nombre, Date fecha, String descripcion, List<Categoria> categorias) {
        return new Evento(id, nombre, fecha, descripcion, categorias);
    }

    /**
     * Cambia el estado del evento a {@code ABIERTO}.
     * @param evento evento a abrir (no nulo).
     */
    public void abrirEvento(Evento evento) {
        validarEventoNoNulo(evento);
        evento.setEstado(Evento.EstadoEvento.ABIERTO);
    }

    /**
     * Cambia el estado del evento a {@code CERRADO}.
     * @param evento evento a cerrar (no nulo).
     */
    public void cerrarEvento(Evento evento) {
        validarEventoNoNulo(evento);
        evento.setEstado(Evento.EstadoEvento.CERRADO);
    }

    /**
     * Cambia el estado del evento a {@code FINALIZADO}.
     * @param evento evento a finalizar (no nulo).
     */
    public void finalizarEvento(Evento evento) {
        validarEventoNoNulo(evento);
        evento.setEstado(Evento.EstadoEvento.FINALIZADO);
    }

    // ------------------------------------------------------------
    // Gestión de inscripciones (sin I/O)
    // ------------------------------------------------------------

    /**
     * Crea y registra una inscripción para un corredor en un evento.
     * <p>Valida que la inscripción referencie el mismo evento, y que el dorsal sea único
     * mediante {@link Evento#agregarInscripcion(Inscripcion)}.</p>
     *
     * @param inscripcionId id de la inscripción (&gt; 0).
     * @param corredor      corredor dueño de la inscripción (no nulo).
     * @param evento        evento destino (no nulo).
     * @param distancia     distancia seleccionada (no nula).
     * @param talla         talla de camiseta (no nula).
     * @param dorsal        dorsal &gt; 0 (unicidad por evento la valida {@code Evento}).
     * @return la inscripción creada y ya agregada al {@code Evento} y al {@code Corredor}.
     * @throws IllegalArgumentException / IllegalStateException según reglas violadas.
     */
    public Inscripcion crearInscripcionParaCorredor(
            int inscripcionId,
            Corredor corredor,
            Evento evento,
            Inscripcion.Distancia distancia,
            Inscripcion.Talla talla,
            int dorsal
    ) {
        Objects.requireNonNull(corredor, "corredor no puede ser nulo.");
        validarEventoNoNulo(evento);

        // Construir la inscripción (referenciando el mismo evento)
        Inscripcion ins = new Inscripcion(inscripcionId, distancia, talla, dorsal, corredor, evento);

        // Agregar al evento (valida unicidad de dorsal y consistencia de referencia)
        evento.agregarInscripcion(ins);

        // Agregar al corredor (evita doble inscripción del mismo evento para el corredor)
        corredor.agregarInscripcion(ins);

        return ins;
    }

    /**
     * Confirma el pago de una inscripción (PENDIENTE → PAGADO).
     * @param ins inscripción objetivo (no nula).
     */
    public void confirmarPagoInscripcion(Inscripcion ins) {
        Objects.requireNonNull(ins, "inscripción no puede ser nula.");
        ins.confirmarPago();
    }

    /**
     * Confirma la inscripción ya pagada (PAGADO → CONFIRMADO).
     * @param ins inscripción objetivo (no nula).
     */
    public void confirmarInscripcion(Inscripcion ins) {
        Objects.requireNonNull(ins, "inscripción no puede ser nula.");
        ins.confirmarInscripcion();
    }

    /**
     * Elimina una inscripción del evento por ID (si existe).
     * <p>Importante: remueve solo del evento. Si deseas simetría total, también
     * remueve del corredor en la capa orquestadora.</p>
     *
     * @param evento         evento (no nulo).
     * @param idInscripcion  id a eliminar.
     * @return {@code true} si se eliminó; {@code false} en caso contrario.
     */
    public boolean eliminarInscripcionDeEvento(Evento evento, int idInscripcion) {
        validarEventoNoNulo(evento);
        return evento.removerInscripcionPorId(idInscripcion);
    }

    // ------------------------------------------------------------
    // Registro de tiempos
    // ------------------------------------------------------------

    /**
     * Registra un tiempo para una inscripción (crea el objeto {@link Tiempo}).
     * <p><b>Nota:</b> Por diseño actual, {@code Tiempo} no está acoplado a {@code Inscripcion}.
     * Este método retorna la instancia para que la capa superior decida dónde almacenarla
     * (por ejemplo, en una estructura de resultados o dentro de otra entidad).</p>
     *
     * @param inscripcion inscripción objetivo (no nula).
     * @param tiempoSeg    tiempo total en segundos (&ge; 0).
     * @param posGeneral   posición general (&ge; 1, o 0 si no asignada aún).
     * @param posCategoria posición por categoría (&ge; 1, o 0 si no asignada aún).
     * @return instancia de {@code Tiempo} creada.
     */
    public Tiempo registrarTiempoParaInscripcion(Inscripcion inscripcion,
                                                 double tiempoSeg,
                                                 int posGeneral,
                                                 int posCategoria) {
        Objects.requireNonNull(inscripcion, "inscripción no puede ser nula.");
        return new Tiempo(tiempoSeg, posGeneral, posCategoria);
    }

    // ------------------------------------------------------------
    // Utilidades privadas
    // ------------------------------------------------------------

    /**
     * Valida que el evento no sea nulo.
     * @param evento evento a validar.
     * @throws IllegalArgumentException si {@code evento} es nulo.
     */
    private void validarEventoNoNulo(Evento evento) {
        if (evento == null) throw new IllegalArgumentException("El evento no puede ser nulo.");
    }

    /**
     * Representación legible del administrador.
     * @return cadena con id, nombre y rol.
     */
    @Override
    public String toString() {
        return "Administrador{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
