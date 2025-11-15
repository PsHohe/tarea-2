package unab.biblioteca.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Clase que representa un préstamo de libro en el sistema de biblioteca.
 * Maneja fechas automáticamente y calcula multas por retraso.
 *
 * @author Sistema Biblioteca UNAB
 */
public class Prestamo {

    private String isbn;
    private String run;
    private LocalDate fechaPrestamo;
    private int diasPrestados;
    private LocalDate fechaDevolucion;

    /**
     * Constructor de Préstamo.
     * Calcula automáticamente la fecha de préstamo (hoy) y la fecha de devolución.
     *
     * @param isbn ISBN del libro prestado
     * @param run RUN del usuario que solicitó el préstamo
     * @param diasPrestados Número de días del préstamo
     */
    public Prestamo(String isbn, String run, int diasPrestados) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío");
        }
        if (run == null || run.trim().isEmpty()) {
            throw new IllegalArgumentException("El RUN no puede estar vacío");
        }
        if (diasPrestados <= 0) {
            throw new IllegalArgumentException("Los días de préstamo deben ser mayor a cero");
        }

        this.isbn = isbn.trim();
        this.run = run.trim();
        this.diasPrestados = diasPrestados;
        this.fechaPrestamo = LocalDate.now(); // Fecha actual automática
        this.fechaDevolucion = this.fechaPrestamo.plusDays(diasPrestados); // Calcular fecha de devolución
    }

    /**
     * Genera la tarjeta de préstamo en formato texto para imprimir.
     *
     * @return String con formato de tarjeta de préstamo
     */
    public String generarTarjetaPrestamo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder tarjeta = new StringBuilder();
        tarjeta.append("\n╔═══════════════════════════════════════╗\n");
        tarjeta.append("║    TARJETA DE PRÉSTAMO - BIBLIOTECA   ║\n");
        tarjeta.append("║           UNAB ANTONIO VARAS          ║\n");
        tarjeta.append("╠═══════════════════════════════════════╣\n");
        tarjeta.append(String.format("║ ISBN: %-31s ║\n", isbn));
        tarjeta.append(String.format("║ RUN Usuario: %-24s ║\n", run));
        tarjeta.append(String.format("║ Fecha Préstamo: %-21s ║\n", fechaPrestamo.format(formatter)));
        tarjeta.append(String.format("║ Días Prestados: %-21s ║\n", diasPrestados));
        tarjeta.append(String.format("║ Fecha Devolución: %-19s ║\n", fechaDevolucion.format(formatter)));
        tarjeta.append("╚═══════════════════════════════════════╝\n");

        return tarjeta.toString();
    }

    /**
     * Calcula la multa por días de retraso.
     * Multa: $1.000 por cada día de retraso.
     *
     * @param fechaDevolucionReal Fecha en que se devuelve el libro
     * @return Monto de la multa (0 si está a tiempo o antes)
     */
    public int calcularMulta(LocalDate fechaDevolucionReal) {
        if (fechaDevolucionReal == null) {
            fechaDevolucionReal = LocalDate.now();
        }

        // Calcular días de diferencia
        long diasRetraso = ChronoUnit.DAYS.between(fechaDevolucion, fechaDevolucionReal);

        // Si diasRetraso es positivo, hay retraso
        if (diasRetraso > 0) {
            return (int) (diasRetraso * 1000); // $1.000 por día
        }

        return 0; // Sin multa si está a tiempo
    }

    // Getters

    public String getIsbn() {
        return isbn;
    }

    public String getRun() {
        return run;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public int getDiasPrestados() {
        return diasPrestados;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    /**
     * Retorna información del préstamo.
     *
     * @return String con datos del préstamo
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Prestamo{" +
                "isbn='" + isbn + '\'' +
                ", run='" + run + '\'' +
                ", fechaPrestamo=" + fechaPrestamo.format(formatter) +
                ", diasPrestados=" + diasPrestados +
                ", fechaDevolucion=" + fechaDevolucion.format(formatter) +
                '}';
    }
}
