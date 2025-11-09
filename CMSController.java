/*
 * Autor: Juan Jose Herrarte
 * Descripción: Controlador del sistema. Gestiona creación, edición, búsqueda y publicación.
 */
import java.util.*;
import java.util.stream.Collectors;

public class CMSController {
    private Map<UUID, Contenido> contenidos = new LinkedHashMap<>();
    private Map<String, Usuario> usuarios = new LinkedHashMap<>();

    // Gestión de usuarios
    public void registrarUsuario(Usuario u) { if (u != null) usuarios.put(u.getNombre(), u); }
    public Usuario obtenerUsuario(String nombre) { return usuarios.get(nombre); }

    // Creación de contenidos
    public UUID crearArticulo(String titulo, String autor, String categoria, List<String> etiquetas, String cuerpo) {
        Articulo a = new Articulo(titulo, autor, categoria, etiquetas, cuerpo);
        contenidos.put(a.getId(), a);
        return a.getId();
    }

    public UUID crearVideo(String titulo, String autor, String categoria, List<String> etiquetas, String url, int duracion, String resolucion) {
        Video v = new Video(titulo, autor, categoria, etiquetas, url, duracion, resolucion);
        contenidos.put(v.getId(), v);
        return v.getId();
    }

    public UUID crearImagen(String titulo, String autor, String categoria, List<String> etiquetas, String url, int ancho, int alto) {
        Imagen i = new Imagen(titulo, autor, categoria, etiquetas, url, ancho, alto);
        contenidos.put(i.getId(), i);
        return i.getId();
    }

    // Edición
    public boolean editarContenido(UUID id, Map<String, Object> cambios, Usuario u) {
        if (u == null || !u.puedeEditar()) return false;
        Contenido c = contenidos.get(id);
        if (c == null) return false;

        if (cambios.containsKey("titulo")) c.setTitulo((String) cambios.get("titulo"));
        if (cambios.containsKey("categoria")) c.setCategoria((String) cambios.get("categoria"));
        if (cambios.containsKey("etiquetas")) c.setEtiquetas((List<String>) cambios.get("etiquetas"));
        return true;
    }

    // Eliminación
    public boolean eliminarContenido(UUID id, Usuario u) {
        if (u == null || !u.puedeEliminar()) return false;
        return contenidos.remove(id) != null;
    }

    // Publicación / previsualización (polimorfismo)
    public Optional<String> publicarContenido(UUID id, Usuario u) {
        if (u == null || !u.puedePublicar()) return Optional.empty();
        Contenido c = contenidos.get(id);
        return c != null ? Optional.of(c.publicar()) : Optional.empty();
    }

    public Optional<String> previsualizarContenido(UUID id) {
        Contenido c = contenidos.get(id);
        return c != null ? Optional.of(c.previsualizar()) : Optional.empty();
    }

    // Filtros y búsquedas
    public List<Contenido> filtrarPorTipo(Class<? extends Contenido> tipo) {
        return contenidos.values().stream().filter(c -> tipo.isAssignableFrom(c.getClass())).collect(Collectors.toList());
    }

    public List<Contenido> filtrarPorCategoria(String cat) {
        return contenidos.values().stream().filter(c -> c.getCategoria().equals(cat)).collect(Collectors.toList());
    }

    public List<Contenido> buscarPorEtiqueta(String tag) {
        return contenidos.values().stream().filter(c -> c.getEtiquetas().contains(tag)).collect(Collectors.toList());
    }

    // Reporte
    public String generarReporte() {
        long total = contenidos.size();
        long publicados = contenidos.values().stream().filter(c -> c.getEstado() == EstadoPublicacion.PUBLICADO).count();
        return "Total: " + total + " | Publicados: " + publicados + " | Borradores: " + (total - publicados);
    }

    // Utilidad para listar IDs
    public List<UUID> listarIds() { return new ArrayList<>(contenidos.keySet()); }
}