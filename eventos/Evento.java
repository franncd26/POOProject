package eventos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Evento {
    public enum Estado { PROGRAMADA, EN_CURSO, FINALIZADA }

    private String nombre;
    private Date fecha;
    private String descripcion;
    private Estado estado;

    private List<Categoria> categorias;
    private List<Tiempo> tiempos;
    private List<Inscripcion> inscripciones;

    public Evento(String nombre, Date fecha, String descripcion, Estado estado) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.estado = estado;
        this.categorias = new ArrayList<>();
        this.tiempos = new ArrayList<>();
        this.inscripciones = new ArrayList<>();
    }

    public Evento() {
        this.categorias = new ArrayList<>();
        this.tiempos = new ArrayList<>();
        this.inscripciones = new ArrayList<>();
        this.estado = Estado.PROGRAMADA;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public List<Categoria> getCategorias() { return categorias; }
    public List<Tiempo> getTiempos() { return tiempos; }
    public List<Inscripcion> getInscripciones() { return inscripciones; }

    // -------- Gestión básica --------
    public void agregarCategoria(Categoria c) {
        if (c != null && !categorias.contains(c)) categorias.add(c);
    }

    public void agregarTiempo(Tiempo t) {
        if (t != null) tiempos.add(t);
    }

    public void agregarInscripcion(Inscripcion i) {
        if (i != null && !inscripciones.contains(i)) inscripciones.add(i);
    }

    public boolean eliminarInscripcionPorDorsal(int dorsal) {
        Inscripcion obj = buscarInscripcionPorDorsal(dorsal);
        if (obj != null) { inscripciones.remove(obj); return true; }
        return false;
    }

    public Inscripcion buscarInscripcionPorDorsal(int dorsal) {
        for (Inscripcion i : inscripciones)
            if (i != null && i.getNumeroDorsal() == dorsal) return i;
        return null;
    }

    public List<Inscripcion> obtenerInscripcionesPorCategoria(String nombreCategoria) {
        List<Inscripcion> lista = new ArrayList<>();
        if (nombreCategoria == null) return lista;
        for (Inscripcion i : inscripciones) {
            if (i != null && i.getCategoria() != null &&
                nombreCategoria.equalsIgnoreCase(i.getCategoria().getNombre())) {
                lista.add(i);
            }
        }
        return lista;
    }

    // -------- Helpers internos para tiempos --------
    private List<Inscripcion> finalizadas() {
        List<Inscripcion> fin = new ArrayList<>();
        for (Inscripcion i : inscripciones)
            if (i != null && i.getTiempo() != null) fin.add(i);
        return fin;
    }

    private List<Inscripcion> finalizadasPorCategoria(String nombreCategoria) {
        List<Inscripcion> fin = new ArrayList<>();
        if (nombreCategoria == null) return fin;
        for (Inscripcion i : inscripciones) {
            if (i != null && i.getTiempo() != null && i.getCategoria() != null &&
                nombreCategoria.equalsIgnoreCase(i.getCategoria().getNombre())) {
                fin.add(i);
            }
        }
        return fin;
    }

    private void ordenarPorTiempoAsc(List<Inscripcion> lista) {
        lista.sort((a, b) -> Double.compare(
                a.getTiempo().getTiempoIndividual(),
                b.getTiempo().getTiempoIndividual()));
    }

    private double promedioTiempo(List<Inscripcion> lista) {
        if (lista.isEmpty()) return 0.0;
        double suma = 0.0;
        for (Inscripcion i : lista) suma += i.getTiempo().getTiempoIndividual();
        return suma / lista.size();
    }

    private String formatoMinutos(double minutos) {
        int mm = (int) minutos;
        int ss = (int) Math.round((minutos - mm) * 60.0);
        if (ss == 60) { ss = 0; mm += 1; }
        return String.format("%02d:%02d", mm, ss);
    }

    // -------- Resúmenes con tiempos --------
    public String generarResumenGeneral() {
        int totalIns = inscripciones.size();
        int totalCats = categorias.size();

        List<Inscripcion> fin = finalizadas();
        int llegados = fin.size();

        String mejorTxt = "N/A";
        String promedioTxt = "N/A";

        if (!fin.isEmpty()) {
            ordenarPorTiempoAsc(fin);
            double mejor = fin.get(0).getTiempo().getTiempoIndividual();
            mejorTxt = formatoMinutos(mejor);
            promedioTxt = formatoMinutos(promedioTiempo(fin));
        }

        return "Evento: " + nombre +
               " | Estado: " + estado +
               " | Categorías: " + totalCats +
               " | Inscripciones: " + totalIns +
               " | Llegadas: " + llegados +
               " | Mejor tiempo (general): " + mejorTxt +
               " | Promedio (general): " + promedioTxt;
    }

    public String generarResumenPorCategoria(String nombreCategoria) {
        if (nombreCategoria == null || nombreCategoria.isBlank()) {
            return "Categoría inválida.";
        }

        int inscritosCat = 0;
        for (Inscripcion i : inscripciones) {
            if (i != null && i.getCategoria() != null &&
                nombreCategoria.equalsIgnoreCase(i.getCategoria().getNombre())) {
                inscritosCat++;
            }
        }

        List<Inscripcion> finCat = finalizadasPorCategoria(nombreCategoria);
        int llegadosCat = finCat.size();

        String mejorTxt = "N/A";
        String promedioTxt = "N/A";

        if (!finCat.isEmpty()) {
            ordenarPorTiempoAsc(finCat);
            double mejor = finCat.get(0).getTiempo().getTiempoIndividual();
            mejorTxt = formatoMinutos(mejor);
            promedioTxt = formatoMinutos(promedioTiempo(finCat));
        }

        return "Evento: " + nombre +
               " | Categoría: " + nombreCategoria +
               " | Inscritos: " + inscritosCat +
               " | Llegadas: " + llegadosCat +
               " | Mejor tiempo: " + mejorTxt +
               " | Promedio: " + promedioTxt;
    }

    // -------- Podios --------
    public List<String> obtenerPodioGeneral() {
        List<String> out = new ArrayList<>();
        List<Inscripcion> fin = finalizadas();
        if (fin.isEmpty()) return out;
        ordenarPorTiempoAsc(fin);
        int n = Math.min(3, fin.size());
        for (int i = 0; i < n; i++) {
            Inscripcion x = fin.get(i);
            String nom = (x.getCorredor() != null) ? x.getCorredor().getNombre() : "(sin nombre)";
            out.add("#" + (i + 1) + " " + nom +
                    " | Dorsal " + x.getNumeroDorsal() +
                    " | " + formatoMinutos(x.getTiempo().getTiempoIndividual()));
        }
        return out;
    }

    public List<String> obtenerPodioCategoria(String nombreCategoria) {
        List<String> out = new ArrayList<>();
        List<Inscripcion> fin = finalizadasPorCategoria(nombreCategoria);
        if (fin.isEmpty()) return out;
        ordenarPorTiempoAsc(fin);
        int n = Math.min(3, fin.size());
        for (int i = 0; i < n; i++) {
            Inscripcion x = fin.get(i);
            String nom = (x.getCorredor() != null) ? x.getCorredor().getNombre() : "(sin nombre)";
            out.add("#" + (i + 1) + " " + nom +
                    " | Dorsal " + x.getNumeroDorsal() +
                    " | " + formatoMinutos(x.getTiempo().getTiempoIndividual()));
        }
        return out;
    }
}
