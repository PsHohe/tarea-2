package unab.biblioteca.models;

import java.util.ArrayList;

/**
 * Clase que representa un usuario de tipo Docente.
 * Los docentes tienen período máximo de préstamo de 20 días.
 *
 * @author Sistema Biblioteca UNAB
 */
public class Docente extends Usuario {

    private String profesion;
    private ArrayList<String> grados; // Puede contener "Magíster", "Doctor", o ambos

    /**
     * Constructor de Docente.
     *
     * @param nombreCompleto Nombre completo del docente
     * @param run RUN del docente
     * @param genero Género del docente ('M' o 'F')
     * @param profesion Profesión del docente
     */
    public Docente(String nombreCompleto, String run, char genero, String profesion) {
        super(nombreCompleto, run, genero);

        if (profesion == null || profesion.trim().isEmpty()) {
            throw new IllegalArgumentException("La profesión no puede estar vacía");
        }

        this.profesion = profesion.trim();
        this.grados = new ArrayList<>();
    }

    /**
     * Constructor de Docente con grados.
     *
     * @param nombreCompleto Nombre completo del docente
     * @param run RUN del docente
     * @param genero Género del docente ('M' o 'F')
     * @param profesion Profesión del docente
     * @param grados Lista de grados académicos
     */
    public Docente(String nombreCompleto, String run, char genero, String profesion, ArrayList<String> grados) {
        this(nombreCompleto, run, genero, profesion);

        if (grados != null) {
            this.grados = new ArrayList<>(grados);
        }
    }

    /**
     * Retorna el período máximo de préstamo para docentes: 20 días.
     *
     * @return 20 días
     */
    @Override
    public int getPeriodoMaximoPrestamo() {
        return 20;
    }

    /**
     * Agrega un grado académico al docente.
     *
     * @param grado Grado académico a agregar (ej: "Magíster", "Doctor")
     */
    public void agregarGrado(String grado) {
        if (grado != null && !grado.trim().isEmpty() && !grados.contains(grado.trim())) {
            grados.add(grado.trim());
        }
    }

    // Getters y Setters

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        if (profesion == null || profesion.trim().isEmpty()) {
            throw new IllegalArgumentException("La profesión no puede estar vacía");
        }
        this.profesion = profesion.trim();
    }

    public ArrayList<String> getGrados() {
        return new ArrayList<>(grados);
    }

    public void setGrados(ArrayList<String> grados) {
        this.grados = new ArrayList<>();
        if (grados != null) {
            for (String grado : grados) {
                if (grado != null && !grado.trim().isEmpty()) {
                    this.grados.add(grado.trim());
                }
            }
        }
    }

    /**
     * Retorna información completa del docente.
     *
     * @return String con datos del docente
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Docente{");
        sb.append("nombreCompleto='").append(nombreCompleto).append('\'');
        sb.append(", run='").append(run).append('\'');
        sb.append(", genero=").append(genero);
        sb.append(", profesion='").append(profesion).append('\'');
        sb.append(", grados=").append(grados);
        sb.append(", prestamo='").append(prestamo).append('\'');
        sb.append(", periodoMaximo=").append(getPeriodoMaximoPrestamo()).append(" días");
        sb.append('}');
        return sb.toString();
    }
}
