package unab.biblioteca.models;

import unab.biblioteca.utils.ValidadorRUN;

/**
 * Clase abstracta que representa un usuario del sistema de biblioteca.
 * Puede ser un Docente o un Estudiante.
 *
 * @author @author Alan, Francisco, Sandrino y Sebastián
 */
public abstract class Usuario {

    protected String nombreCompleto;
    protected String run; // Formato: XXXXXXXX-X
    protected char genero; // 'M' o 'F'
    protected String prestamo; // "0" si no tiene préstamo, ISBN si tiene préstamo activo

    /**
     * Constructor de Usuario con validaciones.
     *
     * @param nombreCompleto Nombre completo del usuario
     * @param run RUN del usuario (formato: XXXXXXXX-X)
     * @param genero Género del usuario ('M' o 'F')
     * @throws IllegalArgumentException si alguna validación falla
     */
    public Usuario(String nombreCompleto, String run, char genero) {
        // Validar nombre completo
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo no puede estar vacío");
        }

        // Validar formato de RUN
        if (!ValidadorRUN.validarFormato(run)) {
            throw new IllegalArgumentException("Formato de RUN inválido. Use formato: XXXXXXXX-X");
        }

        // Validar dígito verificador de RUN
        if (!ValidadorRUN.validarDigitoVerificador(run)) {
            throw new IllegalArgumentException("Dígito verificador de RUN inválido");
        }

        // Validar género
        if (genero != 'M' && genero != 'F') {
            throw new IllegalArgumentException("Género debe ser 'M' o 'F'");
        }

        this.nombreCompleto = nombreCompleto.trim();
        this.run = run;
        this.genero = genero;
        this.prestamo = "0"; // Inicialmente sin préstamo
    }

    /**
     * Método abstracto que retorna el período máximo de préstamo según tipo de usuario.
     * Docente: 20 días, Estudiante: 10 días
     *
     * @return Número de días máximo de préstamo
     */
    public abstract int getPeriodoMaximoPrestamo();

    /**
     * Verifica si el usuario tiene un préstamo activo.
     *
     * @return true si tiene préstamo activo, false en caso contrario
     */
    public boolean tienePrestamo() {
        return !prestamo.equals("0");
    }

    // Getters y Setters

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo no puede estar vacío");
        }
        this.nombreCompleto = nombreCompleto.trim();
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        if (!ValidadorRUN.validarFormato(run)) {
            throw new IllegalArgumentException("Formato de RUN inválido");
        }
        if (!ValidadorRUN.validarDigitoVerificador(run)) {
            throw new IllegalArgumentException("Dígito verificador de RUN inválido");
        }
        this.run = run;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        if (genero != 'M' && genero != 'F') {
            throw new IllegalArgumentException("Género debe ser 'M' o 'F'");
        }
        this.genero = genero;
    }

    public String getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(String prestamo) {
        this.prestamo = prestamo;
    }

    /**
     * Retorna información básica del usuario.
     *
     * @return String con datos del usuario
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", run='" + run + '\'' +
                ", genero=" + genero +
                ", prestamo='" + prestamo + '\'' +
                '}';
    }
}
