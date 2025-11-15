package unab.biblioteca.controllers;

import unab.biblioteca.models.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Controlador principal del sistema de biblioteca.
 * Gestiona usuarios, libros, préstamos y devoluciones.
 *
 * @author Sistema Biblioteca UNAB
 */
public class SistemaBiblioteca {

    private ArrayList<Usuario> usuarios;
    private ArrayList<Libro> libros;
    private ArrayList<Prestamo> prestamos;

    /**
     * Constructor del sistema de biblioteca.
     * Inicializa las listas de usuarios, libros y préstamos.
     */
    public SistemaBiblioteca() {
        this.usuarios = new ArrayList<>();
        this.libros = new ArrayList<>();
        this.prestamos = new ArrayList<>();
    }

    // ==================== MÉTODOS CRUD USUARIOS ====================

    /**
     * Crea un nuevo usuario en el sistema.
     * Valida que el RUN sea único.
     *
     * @param usuario Usuario a crear
     * @return true si se creó exitosamente, false si el RUN ya existe
     */
    public boolean crearUsuario(Usuario usuario) {
        if (usuario == null) {
            return false;
        }

        // Validar que el RUN no esté repetido
        if (buscarUsuarioPorRUN(usuario.getRun()) != null) {
            return false;
        }

        return usuarios.add(usuario);
    }

    /**
     * Edita un usuario existente.
     * Si se cambia el RUN, valida que el nuevo RUN no exista.
     *
     * @param runActual RUN del usuario a editar
     * @param datosNuevos Usuario con los datos nuevos
     * @return true si se editó exitosamente, false en caso contrario
     */
    public boolean editarUsuario(String runActual, Usuario datosNuevos) {
        Usuario usuario = buscarUsuarioPorRUN(runActual);

        if (usuario == null || datosNuevos == null) {
            return false;
        }

        // Si se cambia el RUN, validar que el nuevo no exista
        if (!runActual.equals(datosNuevos.getRun())) {
            if (buscarUsuarioPorRUN(datosNuevos.getRun()) != null) {
                return false;
            }
        }

        // Actualizar datos
        usuario.setNombreCompleto(datosNuevos.getNombreCompleto());
        usuario.setRun(datosNuevos.getRun());
        usuario.setGenero(datosNuevos.getGenero());

        // Actualizar datos específicos según tipo
        if (usuario instanceof Docente && datosNuevos instanceof Docente) {
            Docente docente = (Docente) usuario;
            Docente nuevosDocente = (Docente) datosNuevos;
            docente.setProfesion(nuevosDocente.getProfesion());
            docente.setGrados(nuevosDocente.getGrados());
        } else if (usuario instanceof Estudiante && datosNuevos instanceof Estudiante) {
            Estudiante estudiante = (Estudiante) usuario;
            Estudiante nuevosEstudiante = (Estudiante) datosNuevos;
            estudiante.setCarrera(nuevosEstudiante.getCarrera());
        }

        return true;
    }

    /**
     * Elimina un usuario del sistema.
     * Valida que el usuario exista.
     *
     * @param run RUN del usuario a eliminar
     * @return true si se eliminó exitosamente, false si no existe
     */
    public boolean eliminarUsuario(String run) {
        Usuario usuario = buscarUsuarioPorRUN(run);

        if (usuario == null) {
            return false;
        }

        return usuarios.remove(usuario);
    }

    /**
     * Busca un usuario por su RUN.
     *
     * @param run RUN del usuario a buscar
     * @return Usuario encontrado o null si no existe
     */
    public Usuario buscarUsuarioPorRUN(String run) {
        if (run == null) {
            return null;
        }

        for (Usuario usuario : usuarios) {
            if (usuario.getRun().equals(run)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Lista todos los usuarios del sistema.
     *
     * @return ArrayList con todos los usuarios
     */
    public ArrayList<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    // ==================== MÉTODOS CRUD LIBROS ====================

    /**
     * Crea un nuevo libro en el sistema.
     * Valida que el ISBN sea único.
     *
     * @param libro Libro a crear
     * @return true si se creó exitosamente, false si el ISBN ya existe
     */
    public boolean crearLibro(Libro libro) {
        if (libro == null) {
            return false;
        }

        // Validar que el ISBN no esté repetido
        if (buscarLibroPorISBN(libro.getIsbn()) != null) {
            return false;
        }

        return libros.add(libro);
    }

    /**
     * Elimina un libro del sistema.
     * Valida que el libro exista.
     *
     * @param isbn ISBN del libro a eliminar
     * @return true si se eliminó exitosamente, false si no existe
     */
    public boolean eliminarLibro(String isbn) {
        Libro libro = buscarLibroPorISBN(isbn);

        if (libro == null) {
            return false;
        }

        return libros.remove(libro);
    }

    /**
     * Busca un libro por su ISBN.
     *
     * @param isbn ISBN del libro a buscar
     * @return Libro encontrado o null si no existe
     */
    public Libro buscarLibroPorISBN(String isbn) {
        if (isbn == null) {
            return null;
        }

        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }

    /**
     * Lista todos los libros del sistema.
     *
     * @return ArrayList con todos los libros
     */
    public ArrayList<Libro> listarLibros() {
        return new ArrayList<>(libros);
    }

    // ==================== MÉTODOS DE PRÉSTAMO ====================

    /**
     * Realiza un préstamo de libro.
     * Valida todas las condiciones del enunciado.
     *
     * @param isbn ISBN del libro a prestar
     * @param run RUN del usuario que solicita el préstamo
     * @param diasPrestados Días por los que se presta el libro
     * @return Objeto Prestamo si fue exitoso, null si falló alguna validación
     */
    public Prestamo realizarPrestamo(String isbn, String run, int diasPrestados) {
        // Validación 1: El libro debe existir
        Libro libro = buscarLibroPorISBN(isbn);
        if (libro == null) {
            System.out.println("Error: El libro con ISBN " + isbn + " no existe.");
            return null;
        }

        // Validación 2: El libro debe tener al menos un ejemplar disponible
        if (!libro.hayDisponible()) {
            System.out.println("Error: No hay ejemplares disponibles del libro " + libro.getTitulo() + ".");
            return null;
        }

        // Validación 3: El usuario debe existir
        Usuario usuario = buscarUsuarioPorRUN(run);
        if (usuario == null) {
            System.out.println("Error: El usuario con RUN " + run + " no existe.");
            return null;
        }

        // Validación 4: El usuario debe estar habilitado para préstamo (no tener préstamo activo)
        if (usuario.tienePrestamo()) {
            System.out.println("Error: El usuario " + usuario.getNombreCompleto() + " ya tiene un préstamo activo.");
            return null;
        }

        // Validación 5: Los días prestados no deben exceder el período máximo según tipo de usuario
        if (diasPrestados > usuario.getPeriodoMaximoPrestamo()) {
            System.out.println("Error: Los días solicitados (" + diasPrestados +
                             ") exceden el período máximo de " + usuario.getPeriodoMaximoPrestamo() +
                             " días para este tipo de usuario.");
            return null;
        }

        // Todas las validaciones pasaron, realizar el préstamo
        try {
            // Actualizar estado del usuario
            usuario.setPrestamo(isbn);

            // Actualizar disponibilidad del libro
            libro.prestar();

            // Crear objeto Prestamo
            Prestamo prestamo = new Prestamo(isbn, run, diasPrestados);
            prestamos.add(prestamo);

            return prestamo;

        } catch (Exception e) {
            System.out.println("Error al realizar el préstamo: " + e.getMessage());
            return null;
        }
    }

    // ==================== MÉTODOS DE DEVOLUCIÓN ====================

    /**
     * Realiza la devolución de un libro.
     * Valida todas las condiciones del enunciado.
     * Calcula y retorna la multa si corresponde.
     *
     * @param isbn ISBN del libro a devolver
     * @param run RUN del usuario que devuelve
     * @return Multa a pagar (0 si está a tiempo), -1 si falló alguna validación
     */
    public int realizarDevolucion(String isbn, String run) {
        // Validación 1: El libro debe existir
        Libro libro = buscarLibroPorISBN(isbn);
        if (libro == null) {
            System.out.println("Error: El libro con ISBN " + isbn + " no existe.");
            return -1;
        }

        // Validación 2: El usuario debe existir
        Usuario usuario = buscarUsuarioPorRUN(run);
        if (usuario == null) {
            System.out.println("Error: El usuario con RUN " + run + " no existe.");
            return -1;
        }

        // Validación 3: El ISBN del libro a devolver debe coincidir con el préstamo del usuario
        if (!usuario.getPrestamo().equals(isbn)) {
            System.out.println("Error: El usuario no tiene prestado el libro con ISBN " + isbn + ".");
            return -1;
        }

        // Buscar el préstamo correspondiente
        Prestamo prestamoActual = null;
        for (Prestamo p : prestamos) {
            if (p.getIsbn().equals(isbn) && p.getRun().equals(run)) {
                prestamoActual = p;
                break;
            }
        }

        if (prestamoActual == null) {
            System.out.println("Error: No se encontró el registro del préstamo.");
            return -1;
        }

        try {
            // Calcular multa (usa fecha actual)
            int multa = prestamoActual.calcularMulta(LocalDate.now());

            // Habilitar usuario para nuevo préstamo
            usuario.setPrestamo("0");

            // Incrementar disponibilidad del libro
            libro.devolver();

            return multa;

        } catch (Exception e) {
            System.out.println("Error al realizar la devolución: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Busca un préstamo por ISBN y RUN.
     *
     * @param isbn ISBN del libro
     * @param run RUN del usuario
     * @return Prestamo encontrado o null
     */
    public Prestamo buscarPrestamo(String isbn, String run) {
        for (Prestamo p : prestamos) {
            if (p.getIsbn().equals(isbn) && p.getRun().equals(run)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Lista todos los préstamos del sistema.
     *
     * @return ArrayList con todos los préstamos
     */
    public ArrayList<Prestamo> listarPrestamos() {
        return new ArrayList<>(prestamos);
    }
}
