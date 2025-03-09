package isla;

import java.io.BufferedWriter;

/**
 * Clase principal que controla la simulación del ecosistema.
 */
public class Main {
    private static final ManejadorHilos manejadorHilos = new ManejadorHilos();

    public static void main(String[] args) {
        iniciarSimulacion();
        ejecutarCicloDeVida();
        finalizarSimulacion();
    }

    private static void iniciarSimulacion() {
        Salida.iniciarCSV();
        Creacion.iniciarListasDeSeres();
    }

    private static void ejecutarCicloDeVida() {
        while (debeContinuarSimulacion()) {
            manejadorHilos.ejecutarCiclo();
            Ajustes.momento++;
        }
    }

    private static boolean debeContinuarSimulacion() {
        return Ajustes.momento < Ajustes.DURACION_MAXIMA_SIMULACION && manejadorHilos.isVidaEquilibrada();
    }

    private static void finalizarSimulacion() {
        manejadorHilos.detenerHilos();
        Salida.accionesDeCierre();
    }
}