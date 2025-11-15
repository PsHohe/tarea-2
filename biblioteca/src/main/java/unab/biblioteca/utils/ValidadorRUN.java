package unab.biblioteca.utils;

/**
 * Clase utilitaria para validar RUN chileno.
 * Implementa validación de formato y dígito verificador mediante algoritmo módulo 11.
 *
 * @author Sistema Biblioteca UNAB
 */
public class ValidadorRUN {

    /**
     * Valida el formato del RUN.
     * Acepta formatos: XXXXXXXX-X o XX.XXX.XXX-X
     *
     * @param run RUN a validar
     * @return true si el formato es válido, false en caso contrario
     */
    public static boolean validarFormato(String run) {
        if (run == null || run.isEmpty()) {
            return false;
        }

        // Formato: 7-8 dígitos, seguido de guión y dígito verificador (número o K)
        String regex = "^\\d{1,2}\\.?\\d{3}\\.?\\d{3}-[\\dkK]$";
        return run.matches(regex);
    }

    /**
     * Limpia el RUN eliminando puntos y guiones para procesamiento interno.
     *
     * @param run RUN a limpiar
     * @return RUN sin puntos ni guiones
     */
    public static String limpiarRUN(String run) {
        if (run == null) {
            return "";
        }
        return run.replace(".", "").replace("-", "");
    }

    /**
     * Valida el dígito verificador del RUN usando algoritmo módulo 11.
     *
     * Algoritmo:
     * 1. Limpiar RUN de puntos y guiones
     * 2. Multiplicar cada dígito de derecha a izquierda por la serie: 2,3,4,5,6,7,2,3,4...
     * 3. Sumar todos los productos
     * 4. Calcular: 11 - (suma % 11)
     * 5. Si resultado es 11 → '0', si es 10 → 'K', sino → el número
     *
     * @param run RUN a validar (con o sin formato)
     * @return true si el dígito verificador es correcto, false en caso contrario
     */
    public static boolean validarDigitoVerificador(String run) {
        if (!validarFormato(run)) {
            return false;
        }

        try {
            // Limpiar y separar RUN del dígito verificador
            String runLimpio = limpiarRUN(run);
            String runSinDV = runLimpio.substring(0, runLimpio.length() - 1);
            char dvIngresado = runLimpio.charAt(runLimpio.length() - 1);

            // Calcular dígito verificador esperado
            char dvCalculado = calcularDigitoVerificador(runSinDV);

            // Comparar (convertir a mayúscula para caso de 'k' vs 'K')
            return Character.toUpperCase(dvIngresado) == Character.toUpperCase(dvCalculado);

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Calcula el dígito verificador esperado para un RUN dado.
     * Método auxiliar privado que implementa el algoritmo módulo 11.
     *
     * @param runSinDV RUN sin el dígito verificador
     * @return Dígito verificador calculado
     */
    private static char calcularDigitoVerificador(String runSinDV) {
        int suma = 0;
        int multiplicador = 2;

        // Recorrer de derecha a izquierda
        for (int i = runSinDV.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(runSinDV.charAt(i));
            suma += digito * multiplicador;

            // Serie: 2,3,4,5,6,7,2,3,4,5,6,7...
            multiplicador++;
            if (multiplicador > 7) {
                multiplicador = 2;
            }
        }

        // Calcular dígito verificador
        int resto = suma % 11;
        int resultado = 11 - resto;

        // Casos especiales
        if (resultado == 11) {
            return '0';
        } else if (resultado == 10) {
            return 'K';
        } else {
            return (char) (resultado + '0'); // Convertir número a carácter
        }
    }

    /**
     * Valida completamente un RUN (formato y dígito verificador).
     *
     * @param run RUN a validar
     * @return true si el RUN es válido, false en caso contrario
     */
    public static boolean validarRUN(String run) {
        return validarFormato(run) && validarDigitoVerificador(run);
    }
}
