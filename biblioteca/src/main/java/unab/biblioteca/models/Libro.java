package unab.biblioteca.models;

/**
 * Clase que representa un libro en el sistema de biblioteca.
 *
 * @author Sistema Biblioteca UNAB
 */
public class Libro {

    private String isbn; // Código único del libro
    private String titulo;
    private String autor;
    private int cantidadBiblioteca; // Total de ejemplares en biblioteca
    private int cantidadDisponible; // Ejemplares disponibles para préstamo
    private String imagen; // Ruta o descripción de la imagen

    /**
     * Constructor de Libro con validaciones.
     *
     * @param isbn ISBN del libro (debe ser único)
     * @param titulo Título del libro
     * @param autor Autor del libro
     * @param cantidadBiblioteca Cantidad total en biblioteca
     * @param cantidadDisponible Cantidad disponible para préstamo
     * @param imagen Imagen del libro
     * @throws IllegalArgumentException si alguna validación falla
     */
    public Libro(String isbn, String titulo, String autor, int cantidadBiblioteca, int cantidadDisponible, String imagen) {
        // Validar ISBN
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío");
        }

        // Validar título
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }

        // Validar autor
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }

        // Validar cantidad en biblioteca > 0
        if (cantidadBiblioteca <= 0) {
            throw new IllegalArgumentException("La cantidad en biblioteca debe ser mayor a cero");
        }

        // Validar cantidad disponible > 0
        if (cantidadDisponible <= 0) {
            throw new IllegalArgumentException("La cantidad disponible debe ser mayor a cero");
        }

        // Validar que cantidad disponible <= cantidad en biblioteca
        if (cantidadDisponible > cantidadBiblioteca) {
            throw new IllegalArgumentException("La cantidad disponible no puede ser mayor a la cantidad en biblioteca");
        }

        this.isbn = isbn.trim();
        this.titulo = titulo.trim();
        this.autor = autor.trim();
        this.cantidadBiblioteca = cantidadBiblioteca;
        this.cantidadDisponible = cantidadDisponible;
        this.imagen = (imagen != null) ? imagen.trim() : "";
    }

    /**
     * Verifica si hay ejemplares disponibles para préstamo.
     *
     * @return true si hay al menos un ejemplar disponible, false en caso contrario
     */
    public boolean hayDisponible() {
        return cantidadDisponible > 0;
    }

    /**
     * Reduce la cantidad disponible en 1 (cuando se realiza un préstamo).
     * Valida que haya disponibilidad antes de reducir.
     *
     * @throws IllegalStateException si no hay ejemplares disponibles
     */
    public void prestar() {
        if (!hayDisponible()) {
            throw new IllegalStateException("No hay ejemplares disponibles para préstamo");
        }
        cantidadDisponible--;
    }

    /**
     * Aumenta la cantidad disponible en 1 (cuando se devuelve un libro).
     * Valida que no exceda la cantidad total en biblioteca.
     *
     * @throws IllegalStateException si la devolución excedería la cantidad en biblioteca
     */
    public void devolver() {
        if (cantidadDisponible >= cantidadBiblioteca) {
            throw new IllegalStateException("Error: la cantidad disponible ya está al máximo");
        }
        cantidadDisponible++;
    }

    // Getters y Setters

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío");
        }
        this.isbn = isbn.trim();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        this.titulo = titulo.trim();
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
        this.autor = autor.trim();
    }

    public int getCantidadBiblioteca() {
        return cantidadBiblioteca;
    }

    public void setCantidadBiblioteca(int cantidadBiblioteca) {
        if (cantidadBiblioteca <= 0) {
            throw new IllegalArgumentException("La cantidad en biblioteca debe ser mayor a cero");
        }
        this.cantidadBiblioteca = cantidadBiblioteca;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        if (cantidadDisponible < 0) {
            throw new IllegalArgumentException("La cantidad disponible no puede ser negativa");
        }
        if (cantidadDisponible > cantidadBiblioteca) {
            throw new IllegalArgumentException("La cantidad disponible no puede ser mayor a la cantidad en biblioteca");
        }
        this.cantidadDisponible = cantidadDisponible;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = (imagen != null) ? imagen.trim() : "";
    }

    /**
     * Retorna información completa del libro.
     *
     * @return String con datos del libro
     */
    @Override
    public String toString() {
        return "Libro{" +
                "isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", cantidadBiblioteca=" + cantidadBiblioteca +
                ", cantidadDisponible=" + cantidadDisponible +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
