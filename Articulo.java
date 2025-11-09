/*
 * Autor: Juan Jose Herrarte
 * Descripción: Contenido especializado para artículos de texto.
 */
import java.util.List;

public class Articulo extends Contenido {
    private String cuerpo;

    public Articulo(String titulo, String autor, String categoria, List<String> etiquetas, String cuerpo) {
        super(titulo, autor, categoria, etiquetas);
        this.cuerpo = cuerpo;
    }

    @Override
    public String publicar() {
        setEstado(EstadoPublicacion.PUBLICADO);
        return "Artículo publicado: " + getTitulo();
    }

    @Override
    public String previsualizar() {
        String prev = cuerpo != null && cuerpo.length() > 80 ? cuerpo.substring(0, 80) + "..." : (cuerpo == null ? "" : cuerpo);
        return "Vista previa artículo: " + prev;
    }
}