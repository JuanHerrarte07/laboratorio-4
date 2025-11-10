/*
 * Autor: Pablo Andres Fuentes
 * Descripción: Vista en consola, interfaz para el usuario.
 */
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CMSController ctrl = new CMSController();

        // Usuarios de ejemplo
        ctrl.registrarUsuario(new Usuario("admin", Rol.ADMIN));
        ctrl.registrarUsuario(new Usuario("editor", Rol.EDITOR));

        Usuario actual = ctrl.obtenerUsuario("admin");

        int op = -1;
        while (op != 0) {
            System.out.println("\n--- MENU CMS ---");
            System.out.println("Usuario actual: " + (actual != null ? actual.getNombre() + " (" + actual.getRol() + ")" : "ninguno"));
            System.out.println("1) Crear Artículo");
            System.out.println("2) Crear Video");
            System.out.println("3) Crear Imagen");
            System.out.println("4) Editar Contenido");
            System.out.println("5) Eliminar Contenido");
            System.out.println("6) Publicar Contenido");
            System.out.println("7) Previsualizar Contenido");
            System.out.println("8) Filtrar por Tipo");
            System.out.println("9) Filtrar por Categoría");
            System.out.println("10) Buscar por Etiqueta");
            System.out.println("11) Generar Reporte");
            System.out.println("12) Cambiar Usuario (admin/editor)");
            System.out.println("0) Salir");
            System.out.print("Opción: ");
            try { op = Integer.parseInt(sc.nextLine()); } catch (Exception e) { op = -1; }

            switch (op) {
                case 1: {
                    System.out.print("Titulo: "); String t = sc.nextLine();
                    System.out.print("Autor: "); String a = sc.nextLine();
                    System.out.print("Categoria: "); String c = sc.nextLine();
                    System.out.print("Etiquetas (coma): "); String et = sc.nextLine();
                    List<String> etiquetas = et.isEmpty() ? new ArrayList<>() : Arrays.asList(et.split(","));
                    System.out.print("Cuerpo: "); String cuerpo = sc.nextLine();
                    UUID id = ctrl.crearArticulo(t, a, c, etiquetas, cuerpo);
                    System.out.println("Creado Artículo con id: " + id);
                    break;
                }
                case 2: {
                    System.out.print("Titulo: "); String t = sc.nextLine();
                    System.out.print("Autor: "); String a = sc.nextLine();
                    System.out.print("Categoria: "); String cat = sc.nextLine();
                    System.out.print("Etiquetas (coma): "); String et = sc.nextLine();
                    List<String> etiquetas = et.isEmpty() ? new ArrayList<>() : Arrays.asList(et.split(","));
                    System.out.print("URL: "); String url = sc.nextLine();
                    System.out.print("Duración (s): "); int dur = Integer.parseInt(sc.nextLine());
                    System.out.print("Resolución: "); String res = sc.nextLine();
                    UUID id = ctrl.crearVideo(t, a, cat, etiquetas, url, dur, res);
                    System.out.println("Creado Video con id: " + id);
                    break;
                }
                case 3: {
                    System.out.print("Titulo: "); String t = sc.nextLine();
                    System.out.print("Autor: "); String a = sc.nextLine();
                    System.out.print("Categoria: "); String cat = sc.nextLine();
                    System.out.print("Etiquetas (coma): "); String et = sc.nextLine();
                    List<String> etiquetas = et.isEmpty() ? new ArrayList<>() : Arrays.asList(et.split(","));
                    System.out.print("URL: "); String url = sc.nextLine();
                    System.out.print("Ancho(px): "); int an = Integer.parseInt(sc.nextLine());
                    System.out.print("Alto(px): "); int al = Integer.parseInt(sc.nextLine());
                    UUID id = ctrl.crearImagen(t, a, cat, etiquetas, url, an, al);
                    System.out.println("Creada Imagen con id: " + id);
                    break;
                }
                case 4: {
                    System.out.print("ID a editar: "); UUID id = UUID.fromString(sc.nextLine());
                    Map<String, Object> cambios = new HashMap<>();
                    System.out.print("Nuevo título (enter para omitir): "); String nt = sc.nextLine();
                    if (!nt.isEmpty()) cambios.put("titulo", nt);
                    System.out.print("Nueva categoría (enter para omitir): "); String nc = sc.nextLine();
                    if (!nc.isEmpty()) cambios.put("categoria", nc);
                    System.out.print("Nuevas etiquetas (coma, enter para omitir): "); String ne = sc.nextLine();
                    if (!ne.isEmpty()) cambios.put("etiquetas", Arrays.asList(ne.split(",")));
                    boolean ok = ctrl.editarContenido(id, cambios, actual);
                    System.out.println(ok ? "Editado" : "No se pudo editar (permisos/ID).");
                    break;
                }
                case 5: {
                    System.out.print("ID a eliminar: "); UUID id = UUID.fromString(sc.nextLine());
                    boolean ok = ctrl.eliminarContenido(id, actual);
                    System.out.println(ok ? "Eliminado" : "No se pudo eliminar (permisos/ID).");
                    break;
                }
                case 6: {
                    System.out.print("ID a publicar: "); UUID id = UUID.fromString(sc.nextLine());
                    System.out.println(ctrl.publicarContenido(id, actual).orElse("No se pudo publicar (permisos/ID)."));
                    break;
                }
                case 7: {
                    System.out.print("ID a previsualizar: "); UUID id = UUID.fromString(sc.nextLine());
                    System.out.println(ctrl.previsualizarContenido(id).orElse("No existe el contenido."));
                    break;
                }
                case 8: {
                    System.out.print("Tipo (Articulo/Video/Imagen): "); String tipo = sc.nextLine();
                    Class<? extends Contenido> cls = tipo.equalsIgnoreCase("Articulo") ? Articulo.class :
                                                     tipo.equalsIgnoreCase("Video") ? Video.class : Imagen.class;
                    List<Contenido> res = ctrl.filtrarPorTipo(cls);
                    System.out.println("Encontrados: " + res.size());
                    break;
                }
                case 9: {
                    System.out.print("Categoría: "); String cat = sc.nextLine();
                    List<Contenido> res = ctrl.filtrarPorCategoria(cat);
                    System.out.println("Encontrados: " + res.size());
                    break;
                }
                case 10: {
                    System.out.print("Etiqueta: "); String tag = sc.nextLine();
                    List<Contenido> res = ctrl.buscarPorEtiqueta(tag);
                    System.out.println("Encontrados: " + res.size());
                    break;
                }
                case 11: {
                    System.out.println(ctrl.generarReporte());
                    break;
                }
                case 12: {
                    System.out.print("Cambiar a (admin/editor): "); String nombre = sc.nextLine();
                    Usuario u = ctrl.obtenerUsuario(nombre);
                    if (u != null) actual = u; else System.out.println("Usuario no existe.");
                    break;
                }
                case 0: {
                    System.out.println("Saliendo...");
                    break;
                }
                default:
                    System.out.println("Opción inválida.");
            }
        }
        sc.close();
    }
}