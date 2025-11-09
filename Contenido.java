/*
 * Autor: Juan Jose Herrarte
 * Descripción: Clase abstracta base para todos los contenidos.
 */
import java.time.LocalDateTime;
import java.util.*;

public abstract class Contenido {
    private final UUID id;
    private String titulo;
    private String autor;
    private LocalDateTime fechaCreacion;
    private String categoria;
    private List<String> etiquetas;
    private EstadoPublicacion estado;

    public Contenido(String titulo, String autor, String categoria, List<String> etiquetas) {
        this.id = UUID.randomUUID();
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.etiquetas = etiquetas != null ? new ArrayList<>(etiquetas) : new ArrayList<>();
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoPublicacion.BORRADOR;
    }

    public UUID getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String t) { this.titulo = t; }
    public String getAutor() { return autor; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String c) { this.categoria = c; }
    public List<String> getEtiquetas() { return etiquetas; }
    public void setEtiquetas(List<String> et) { this.etiquetas = et; }
    public EstadoPublicacion getEstado() { return estado; }
    protected void setEstado(EstadoPublicacion e) { this.estado = e; }

    public abstract String publicar();
    public abstract String previsualizar();
}