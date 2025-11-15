package unab.biblioteca;

import unab.biblioteca.controllers.SistemaBiblioteca;
import unab.biblioteca.models.*;
import unab.biblioteca.views.MenuPrincipal;
import java.util.ArrayList;

/**
 * Clase principal del Sistema de Biblioteca UNAB.
 * Punto de entrada de la aplicación.
 * Incluye datos de demostración y ejecución del menú principal.
 *
 * @author Sistema Biblioteca UNAB
 */
public class Biblioteca {

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════╗");
        System.out.println("║  SISTEMA DE BIBLIOTECA                                ║");
        System.out.println("║  UNIVERSIDAD NACIONAL ANDRÉS BELLO                    ║");
        System.out.println("║  SEDE ANTONIO VARAS                                   ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
        System.out.println();

        // Crear instancia del sistema de biblioteca
        SistemaBiblioteca sistema = new SistemaBiblioteca();

        // Cargar datos de demostración
        cargarDatosDemostracion(sistema);

        // Mostrar datos iniciales
        mostrarDatosIniciales(sistema);

        // Ejecutar demostración de funcionalidades
        ejecutarDemostracion(sistema);

        // Iniciar menú interactivo
        System.out.println("\n\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║  INICIANDO MENÚ INTERACTIVO                           ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");

        MenuPrincipal menu = new MenuPrincipal(sistema);
        menu.iniciar();
    }

    /**
     * Carga datos de demostración en el sistema.
     * Incluye usuarios (docentes y estudiantes) y libros.
     *
     * @param sistema Sistema de biblioteca
     */
    private static void cargarDatosDemostracion(SistemaBiblioteca sistema) {
        System.out.println("Cargando datos de demostración...\n");

        try {
            // Crear Docente con Magíster
            Docente docente1 = new Docente(
                    "Dr. Juan Carlos Pérez González",
                    "12345678-5",
                    'M',
                    "Ingeniero Civil en Informática"
            );
            docente1.agregarGrado("Magíster");
            docente1.agregarGrado("Doctor");
            sistema.crearUsuario(docente1);
            System.out.println("✓ Docente creado: " + docente1.getNombreCompleto());

            // Crear Estudiante 1
            Estudiante estudiante1 = new Estudiante(
                    "María José Rodríguez Silva",
                    "19876543-2",
                    'F',
                    "Ingeniería en Informática"
            );
            sistema.crearUsuario(estudiante1);
            System.out.println("✓ Estudiante creado: " + estudiante1.getNombreCompleto());

            // Crear Estudiante 2
            Estudiante estudiante2 = new Estudiante(
                    "Carlos Alberto Muñoz Flores",
                    "20123456-K",
                    'M',
                    "Ingeniería Civil"
            );
            sistema.crearUsuario(estudiante2);
            System.out.println("✓ Estudiante creado: " + estudiante2.getNombreCompleto());

            System.out.println();

            // Crear Libros
            Libro libro1 = new Libro(
                    "978-0-13-468599-1",
                    "Clean Code: A Handbook of Agile Software Craftsmanship",
                    "Robert C. Martin",
                    5,
                    5,
                    "clean_code.jpg"
            );
            sistema.crearLibro(libro1);
            System.out.println("✓ Libro creado: " + libro1.getTitulo());

            Libro libro2 = new Libro(
                    "978-0-13-235088-4",
                    "Clean Architecture",
                    "Robert C. Martin",
                    3,
                    3,
                    "clean_architecture.jpg"
            );
            sistema.crearLibro(libro2);
            System.out.println("✓ Libro creado: " + libro2.getTitulo());

            Libro libro3 = new Libro(
                    "978-0-13-597870-5",
                    "The Pragmatic Programmer",
                    "David Thomas, Andrew Hunt",
                    4,
                    4,
                    "pragmatic_programmer.jpg"
            );
            sistema.crearLibro(libro3);
            System.out.println("✓ Libro creado: " + libro3.getTitulo());

            Libro libro4 = new Libro(
                    "978-0-20161-622-4",
                    "The Art of Computer Programming, Vol. 1",
                    "Donald E. Knuth",
                    2,
                    2,
                    "knuth_vol1.jpg"
            );
            sistema.crearLibro(libro4);
            System.out.println("✓ Libro creado: " + libro4.getTitulo());

        } catch (Exception e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
    }

    /**
     * Muestra los datos iniciales cargados en el sistema.
     *
     * @param sistema Sistema de biblioteca
     */
    private static void mostrarDatosIniciales(SistemaBiblioteca sistema) {
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("USUARIOS REGISTRADOS:");
        System.out.println("═══════════════════════════════════════════════════════");

        ArrayList<Usuario> usuarios = sistema.listarUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            System.out.println((i + 1) + ". " + u.getNombreCompleto() + " (RUN: " + u.getRun() + ")");
            if (u instanceof Docente) {
                Docente d = (Docente) u;
                System.out.println("   Tipo: Docente - Profesión: " + d.getProfesion());
                System.out.println("   Grados: " + d.getGrados());
                System.out.println("   Período máximo de préstamo: " + d.getPeriodoMaximoPrestamo() + " días");
            } else if (u instanceof Estudiante) {
                Estudiante e = (Estudiante) u;
                System.out.println("   Tipo: Estudiante - Carrera: " + e.getCarrera());
                System.out.println("   Período máximo de préstamo: " + e.getPeriodoMaximoPrestamo() + " días");
            }
            System.out.println();
        }

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("LIBROS REGISTRADOS:");
        System.out.println("═══════════════════════════════════════════════════════");

        ArrayList<Libro> libros = sistema.listarLibros();
        for (int i = 0; i < libros.size(); i++) {
            Libro l = libros.get(i);
            System.out.println((i + 1) + ". " + l.getTitulo());
            System.out.println("   ISBN: " + l.getIsbn());
            System.out.println("   Autor: " + l.getAutor());
            System.out.println("   Disponibles: " + l.getCantidadDisponible() + "/" + l.getCantidadBiblioteca());
            System.out.println();
        }
    }

    /**
     * Ejecuta una demostración de las funcionalidades del sistema.
     * Muestra casos de uso exitosos y validaciones de error.
     *
     * @param sistema Sistema de biblioteca
     */
    private static void ejecutarDemostracion(SistemaBiblioteca sistema) {
        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║  DEMOSTRACIÓN DE FUNCIONALIDADES                      ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝\n");

        // Caso 1: Préstamo válido para estudiante
        System.out.println("--- CASO 1: Préstamo válido (Estudiante) ---");
        Prestamo prestamo1 = sistema.realizarPrestamo("978-0-13-468599-1", "19876543-2", 7);
        if (prestamo1 != null) {
            System.out.println(prestamo1.generarTarjetaPrestamo());
        }

        // Caso 2: Intentar préstamo cuando usuario ya tiene libro
        System.out.println("\n--- CASO 2: Préstamo inválido (Usuario ya tiene préstamo) ---");
        Prestamo prestamo2 = sistema.realizarPrestamo("978-0-13-235088-4", "19876543-2", 5);

        // Caso 3: Préstamo válido para docente
        System.out.println("\n--- CASO 3: Préstamo válido (Docente) ---");
        Prestamo prestamo3 = sistema.realizarPrestamo("978-0-13-235088-4", "12345678-5", 15);
        if (prestamo3 != null) {
            System.out.println(prestamo3.generarTarjetaPrestamo());
        }

        // Caso 4: Intentar préstamo excediendo período máximo
        System.out.println("\n--- CASO 4: Préstamo inválido (Excede período máximo) ---");
        Prestamo prestamo4 = sistema.realizarPrestamo("978-0-13-597870-5", "20123456-K", 15);

        // Caso 5: Devolución a tiempo (sin multa)
        System.out.println("\n--- CASO 5: Devolución a tiempo (Sin multa) ---");
        int multa1 = sistema.realizarDevolucion("978-0-13-468599-1", "19876543-2");
        if (multa1 >= 0) {
            System.out.println("Multa: $" + multa1);
        }

        // Caso 6: Préstamo con días máximos permitidos para estudiante
        System.out.println("\n--- CASO 6: Préstamo válido con período máximo (Estudiante - 10 días) ---");
        Prestamo prestamo5 = sistema.realizarPrestamo("978-0-13-468599-1", "20123456-K", 10);
        if (prestamo5 != null) {
            System.out.println(prestamo5.generarTarjetaPrestamo());
        }

        // Caso 7: Intentar préstamo de libro que no existe
        System.out.println("\n--- CASO 7: Préstamo inválido (Libro no existe) ---");
        Prestamo prestamo6 = sistema.realizarPrestamo("999-9-99-999999-9", "19876543-2", 5);

        // Caso 8: Intentar préstamo con usuario que no existe
        System.out.println("\n--- CASO 8: Préstamo inválido (Usuario no existe) ---");
        Prestamo prestamo7 = sistema.realizarPrestamo("978-0-13-235088-4", "11111111-1", 5);

        System.out.println("\n╔═══════════════════════════════════════════════════════╗");
        System.out.println("║  FIN DE LA DEMOSTRACIÓN                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════╝");
    }
}
