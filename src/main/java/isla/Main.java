package isla;

import java.io.BufferedWriter;

/**
 * Clase principal que controla la simulación del ecosistema.
 */
public class Main {
    static BufferedWriter bufferCSV = Salida.iniciarCSV();
    static int momento = 1;
    static ManejadorHilos manejadorHilos = new ManejadorHilos();

    public static void main(String[] args) {
        iniciarSimulacion();
        ejecutarCicloDeVida();
        finalizarSimulacion();
    }

    private static void iniciarSimulacion() {
        Creacion.iniciarListasDeSeres();
    }

    private static void ejecutarCicloDeVida() {
        while (continuarSimulacion()) {
            manejadorHilos.ejecutarCiclo();
            momento++;
        }
    }

    private static boolean continuarSimulacion() {
        return momento < Ajustes.DURACION_MAXIMA_SIMULACION && manejadorHilos.isVidaEquilibrada();
    }

    private static void finalizarSimulacion() {
        Salida.accionesDeCierre();
    }
}