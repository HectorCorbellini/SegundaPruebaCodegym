package isla;

/**
 * Clase responsable de manejar la creación y sincronización de hilos para la simulación.
 */
public class ManejadorHilos {
    private Thread hiloEstadistica;
    private Thread hiloVida;
    private boolean vidaEquilibrada;

    public ManejadorHilos() {
        this.vidaEquilibrada = true;
    }

    public boolean isVidaEquilibrada() {
        return vidaEquilibrada;
    }

    public void crearHilos() {
        hiloEstadistica = new Thread(Salida::hacerEstadistica);
        hiloVida = new Thread(() -> {
            vidaEquilibrada = Vida.recrearla();
        });
    }

    public void iniciarHilos() {
        Tablero.colocarLosSeres();
        hiloEstadistica.start(); // manejo de archivo CVS
        hiloVida.start();        // manejo lógico de la vida
        Ajustes.transcurreEsteMomento();
    }

    public void esperarFinalizacionHilos() {
        try {
            hiloEstadistica.join();  // Se supone el manejo de archivo demorará más
            hiloVida.join();         // que los cálculos lógicos
        } catch (InterruptedException e) {
            manejarErrorInterrupcion(e);
        }
    }

    private void manejarErrorInterrupcion(InterruptedException e) {
        System.err.println("Error en la sincronización de hilos: " + e.getMessage());
        e.printStackTrace();
        Thread.currentThread().interrupt(); // Restaurar el estado de interrupción
    }

    public void ejecutarCiclo() {
        crearHilos();
        iniciarHilos();
        esperarFinalizacionHilos();
    }
}
