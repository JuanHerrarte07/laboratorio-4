/*
 * Autor: Pablo Andres Fuentes
 * Descripción: Contenido especializado para imágenes.
 */
import java.util.List;

public class Imagen extends Contenido {
    private String url;
    private int anchoPx;
    private int altoPx;

    public Imagen(String titulo, String autor, String categoria, List<String> etiquetas, String url, int anchoPx, int altoPx) {
        super(titulo, autor, categoria, etiquetas);
        this.url = url;
        this.anchoPx = anchoPx;
        this.altoPx = altoPx;
    }

    @Override
    public String publicar() {
        setEstado(EstadoPublicacion.PUBLICADO);
        return "Imagen publicada: " + getTitulo();
    }

    @Override
    public String previsualizar() {
        return "Preview imagen: " + anchoPx + "x" + altoPx;
    }
}