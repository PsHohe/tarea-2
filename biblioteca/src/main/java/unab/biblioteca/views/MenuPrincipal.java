package unab.biblioteca.views;

import unab.biblioteca.controllers.SistemaBiblioteca;
import unab.biblioteca.models.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que maneja la interfaz de consola del sistema de biblioteca.
 * Presenta menús y captura inputs del usuario.
 *
 * @author @author Alan, Francisco, Sandrino y Sebastián
 */
public class MenuPrincipal {

    private SistemaBiblioteca sistema;
    private Scanner scanner;

    /**
     * Constructor del menú principal.
     *
     * @param sistema Sistema de biblioteca a controlar
     */
    public MenuPrincipal(SistemaBiblioteca sistema) {
        this.sistema = sistema;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia el menú principal del sistema.
     */
    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    menuGestionUsuarios();
                    break;
                case 2:
                    menuGestionLibros();
                    break;
                case 3:
                    realizarPrestamo();
                    break;
                case 4:
                    realizarDevolucion();
                    break;
                case 5:
                    listarUsuarios();
                    break;
                case 6:
                    listarLibros();
                    break;
                case 0:
                    salir = true;
                    System.out.println("\nGracias por usar el Sistema de Biblioteca UNAB.");
                    break;
                default:
                    System.out.println("\nOpción inválida. Intente nuevamente.");
            }
        }
    }

    /**
     * Muestra el menú principal.
     */
    private void mostrarMenuPrincipal() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║  SISTEMA BIBLIOTECA UNAB           ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║  1. Gestión de Usuarios            ║");
        System.out.println("║  2. Gestión de Libros              ║");
        System.out.println("║  3. Realizar Préstamo              ║");
        System.out.println("║  4. Realizar Devolución            ║");
        System.out.println("║  5. Listar Usuarios                ║");
        System.out.println("║  6. Listar Libros                  ║");
        System.out.println("║  0. Salir                          ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Submenú de gestión de usuarios.
     */
    private void menuGestionUsuarios() {
        System.out.println("\n--- Gestión de Usuarios ---");
        System.out.println("1. Crear Docente");
        System.out.println("2. Crear Estudiante");
        System.out.println("3. Editar Usuario");
        System.out.println("4. Eliminar Usuario");
        System.out.println("5. Volver");
        System.out.print("Seleccione una opción: ");

        int opcion = leerOpcion();

        switch (opcion) {
            case 1:
                crearDocente();
                break;
            case 2:
                crearEstudiante();
                break;
            case 3:
                editarUsuario();
                break;
            case 4:
                eliminarUsuario();
                break;
            case 5:
                return;
            default:
                System.out.println("Opción inválida.");
        }
    }

    /**
     * Submenú de gestión de libros.
     */
    private void menuGestionLibros() {
        System.out.println("\n--- Gestión de Libros ---");
        System.out.println("1. Crear Libro");
        System.out.println("2. Eliminar Libro");
        System.out.println("3. Volver");
        System.out.print("Seleccione una opción: ");

        int opcion = leerOpcion();

        switch (opcion) {
            case 1:
                crearLibro();
                break;
            case 2:
                eliminarLibro();
                break;
            case 3:
                return;
            default:
                System.out.println("Opción inválida.");
        }
    }

    /**
     * Crea un nuevo docente en el sistema.
     */
    private void crearDocente() {
        System.out.println("\n--- Crear Docente ---");

        try {
            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine();

            System.out.print("RUN (formato: XXXXXXXX-X): ");
            String run = scanner.nextLine();

            System.out.print("Género (M/F): ");
            char genero = scanner.nextLine().toUpperCase().charAt(0);

            System.out.print("Profesión: ");
            String profesion = scanner.nextLine();

            Docente docente = new Docente(nombre, run, genero, profesion);

            System.out.print("¿Desea agregar grados académicos? (S/N): ");
            String respuesta = scanner.nextLine().toUpperCase();

            if (respuesta.equals("S")) {
                System.out.print("¿Tiene Magíster? (S/N): ");
                if (scanner.nextLine().toUpperCase().equals("S")) {
                    docente.agregarGrado("Magíster");
                }

                System.out.print("¿Tiene Doctorado? (S/N): ");
                if (scanner.nextLine().toUpperCase().equals("S")) {
                    docente.agregarGrado("Doctor");
                }
            }

            if (sistema.crearUsuario(docente)) {
                System.out.println("✓ Docente creado exitosamente.");
            } else {
                System.out.println("✗ Error: El RUN ya existe en el sistema.");
            }

        } catch (Exception e) {
            System.out.println("✗ Error al crear docente: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo estudiante en el sistema.
     */
    private void crearEstudiante() {
        System.out.println("\n--- Crear Estudiante ---");

        try {
            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine();

            System.out.print("RUN (formato: XXXXXXXX-X): ");
            String run = scanner.nextLine();

            System.out.print("Género (M/F): ");
            char genero = scanner.nextLine().toUpperCase().charAt(0);

            System.out.print("Carrera: ");
            String carrera = scanner.nextLine();

            Estudiante estudiante = new Estudiante(nombre, run, genero, carrera);

            if (sistema.crearUsuario(estudiante)) {
                System.out.println("✓ Estudiante creado exitosamente.");
            } else {
                System.out.println("✗ Error: El RUN ya existe en el sistema.");
            }

        } catch (Exception e) {
            System.out.println("✗ Error al crear estudiante: " + e.getMessage());
        }
    }

    /**
     * Edita un usuario existente.
     */
    private void editarUsuario() {
        System.out.println("\n--- Editar Usuario ---");
        System.out.print("Ingrese RUN del usuario a editar: ");
        String run = scanner.nextLine();

        Usuario usuario = sistema.buscarUsuarioPorRUN(run);

        if (usuario == null) {
            System.out.println("✗ Error: Usuario no encontrado.");
            return;
        }

        System.out.println("Usuario encontrado: " + usuario.getNombreCompleto());
        System.out.println("Nota: La edición completa requeriría implementación adicional.");
    }

    /**
     * Elimina un usuario del sistema.
     */
    private void eliminarUsuario() {
        System.out.println("\n--- Eliminar Usuario ---");
        System.out.print("Ingrese RUN del usuario a eliminar: ");
        String run = scanner.nextLine();

        if (sistema.eliminarUsuario(run)) {
            System.out.println("✓ Usuario eliminado exitosamente.");
        } else {
            System.out.println("✗ Error: Usuario no encontrado.");
        }
    }

    /**
     * Crea un nuevo libro en el sistema.
     */
    private void crearLibro() {
        System.out.println("\n--- Crear Libro ---");

        try {
            System.out.print("ISBN: ");
            String isbn = scanner.nextLine();

            System.out.print("Título: ");
            String titulo = scanner.nextLine();

            System.out.print("Autor: ");
            String autor = scanner.nextLine();

            System.out.print("Cantidad en biblioteca: ");
            int cantidadBiblioteca = Integer.parseInt(scanner.nextLine());

            System.out.print("Cantidad disponible: ");
            int cantidadDisponible = Integer.parseInt(scanner.nextLine());

            System.out.print("Imagen (opcional): ");
            String imagen = scanner.nextLine();

            Libro libro = new Libro(isbn, titulo, autor, cantidadBiblioteca, cantidadDisponible, imagen);

            if (sistema.crearLibro(libro)) {
                System.out.println("✓ Libro creado exitosamente.");
            } else {
                System.out.println("✗ Error: El ISBN ya existe en el sistema.");
            }

        } catch (Exception e) {
            System.out.println("✗ Error al crear libro: " + e.getMessage());
        }
    }

    /**
     * Elimina un libro del sistema.
     */
    private void eliminarLibro() {
        System.out.println("\n--- Eliminar Libro ---");
        System.out.print("Ingrese ISBN del libro a eliminar: ");
        String isbn = scanner.nextLine();

        if (sistema.eliminarLibro(isbn)) {
            System.out.println("✓ Libro eliminado exitosamente.");
        } else {
            System.out.println("✗ Error: Libro no encontrado.");
        }
    }

    /**
     * Realiza un préstamo de libro.
     */
    private void realizarPrestamo() {
        System.out.println("\n--- Realizar Préstamo ---");

        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine();

        System.out.print("RUN del usuario: ");
        String run = scanner.nextLine();

        System.out.print("Días de préstamo: ");
        int dias = Integer.parseInt(scanner.nextLine());

        Prestamo prestamo = sistema.realizarPrestamo(isbn, run, dias);

        if (prestamo != null) {
            System.out.println("\n✓ Préstamo realizado exitosamente.");
            System.out.println(prestamo.generarTarjetaPrestamo());
        } else {
            System.out.println("\n✗ No se pudo realizar el préstamo. Verifique los mensajes de error anteriores.");
        }
    }

    /**
     * Realiza una devolución de libro.
     */
    private void realizarDevolucion() {
        System.out.println("\n--- Realizar Devolución ---");

        System.out.print("ISBN del libro: ");
        String isbn = scanner.nextLine();

        System.out.print("RUN del usuario: ");
        String run = scanner.nextLine();

        int multa = sistema.realizarDevolucion(isbn, run);

        if (multa >= 0) {
            System.out.println("\n✓ Devolución realizada exitosamente.");
            if (multa > 0) {
                System.out.println("⚠ Multa por retraso: $" + multa);
            } else {
                System.out.println("✓ Devolución a tiempo, sin multa.");
            }
        } else {
            System.out.println("\n✗ No se pudo realizar la devolución. Verifique los mensajes de error anteriores.");
        }
    }

    /**
     * Lista todos los usuarios del sistema.
     */
    private void listarUsuarios() {
        System.out.println("\n--- Lista de Usuarios ---");
        ArrayList<Usuario> usuarios = sistema.listarUsuarios();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i));
        }
    }

    /**
     * Lista todos los libros del sistema.
     */
    private void listarLibros() {
        System.out.println("\n--- Lista de Libros ---");
        ArrayList<Libro> libros = sistema.listarLibros();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        for (int i = 0; i < libros.size(); i++) {
            System.out.println((i + 1) + ". " + libros.get(i));
        }
    }

    /**
     * Lee una opción numérica del usuario.
     *
     * @return Opción ingresada o -1 si hay error
     */
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
