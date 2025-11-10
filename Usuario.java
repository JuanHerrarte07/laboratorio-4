/*
 * Autor: Pablo Andres Fuentes
 * Descripción: Representa un usuario con permisos según su rol.
 */
public class Usuario {
    private String nombre;
    private Rol rol;

    public Usuario(String nombre, Rol rol) {
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getNombre() { return nombre; }
    public Rol getRol() { return rol; }

    public boolean puedePublicar() { return rol == Rol.ADMIN; }
    public boolean puedeEliminar() { return rol == Rol.ADMIN; }
    public boolean puedeEditar() { return rol == Rol.ADMIN || rol == Rol.EDITOR; }
}