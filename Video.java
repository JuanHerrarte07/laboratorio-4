/*
 * Autor: Pablo Andres Fuentes
 * Descripción: Contenido especializado para videos.
 */
import java.util.List;

public class Video extends Contenido {
    private String url;
    private int duracionSeg;
    private String resolucion;

    public Video(String titulo, String autor, String categoria, List<String> etiquetas, String url, int duracionSeg, String resolucion) {
        super(titulo, autor, categoria, etiquetas);
        this.url = url;
        this.duracionSeg = duracionSeg;
        this.resolucion = resolucion;
    }

    @Override
    public String publicar() {
        setEstado(EstadoPublicacion.PUBLICADO);
        return "Video publicado: " + getTitulo();
    }

    @Override
    public String previsualizar() {
        return "Preview video: " + duracionSeg + "s | " + resolucion;
    }
}