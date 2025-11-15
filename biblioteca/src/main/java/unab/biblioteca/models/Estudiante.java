package unab.biblioteca.models;

/**
 * Clase que representa un usuario de tipo Estudiante.
 * Los estudiantes tienen período máximo de préstamo de 10 días.
 *
 * @author @author Alan, Francisco, Sandrino y Sebastián
 */
public class Estudiante extends Usuario {

    private String carrera;

    /**
     * Constructor de Estudiante.
     *
     * @param nombreCompleto Nombre completo del estudiante
     * @param run RUN del estudiante
     * @param genero Género del estudiante ('M' o 'F')
     * @param carrera Carrera que está estudiando
     */
    public Estudiante(String nombreCompleto, String run, char genero, String carrera) {
        super(nombreCompleto, run, genero);

        if (carrera == null || carrera.trim().isEmpty()) {
            throw new IllegalArgumentException("La carrera no puede estar vacía");
        }

        this.carrera = carrera.trim();
    }

    /**
     * Retorna el período máximo de préstamo para estudiantes: 10 días.
     *
     * @return 10 días
     */
    @Override
    public int getPeriodoMaximoPrestamo() {
        return 10;
    }

    // Getters y Setters

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        if (carrera == null || carrera.trim().isEmpty()) {
            throw new IllegalArgumentException("La carrera no puede estar vacía");
        }
        this.carrera = carrera.trim();
    }

    /**
     * Retorna información completa del estudiante.
     *
     * @return String con datos del estudiante
     */
    @Override
    public String toString() {
        return "Estudiante{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", run='" + run + '\'' +
                ", genero=" + genero +
                ", carrera='" + carrera + '\'' +
                ", prestamo='" + prestamo + '\'' +
                ", periodoMaximo=" + getPeriodoMaximoPrestamo() + " días" +
                '}';
    }
}
