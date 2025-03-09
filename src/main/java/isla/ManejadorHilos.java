package isla;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Maneja la ejecución de los ciclos de la simulación utilizando múltiples hilos.
 */
public class ManejadorHilos {
    
    private static final int NUMERO_HILOS = Runtime.getRuntime().availableProcessors();
    private final ExecutorService executor;
    private boolean vidaEquilibrada;

    public ManejadorHilos() {
        this.executor = Executors.newFixedThreadPool(NUMERO_HILOS);
        this.vidaEquilibrada = true;
    }

    public void ejecutarCiclo() {
        executor.execute(() -> vidaEquilibrada = Vida.recrearla());
        executor.execute(Salida::hacerEstadistica);
        try {
            Thread.sleep(Ajustes.PAUSA_ENTRE_CICLOS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isVidaEquilibrada() {
        return vidaEquilibrada;
    }
    
    public void detenerHilos() {
        executor.shutdown();
    }
}
