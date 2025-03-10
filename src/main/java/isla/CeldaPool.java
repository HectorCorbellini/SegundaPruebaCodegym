package isla;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Pool de objetos Celda para reducir la creación de objetos y la recolección de basura.
 * Implementa el patrón Object Pool para reciclar instancias de Celda.
 */
public class CeldaPool {
    private static final CeldaPool instancia = new CeldaPool();
    private final Map<String, Queue<Celda>> pool = new HashMap<>();
    
    private CeldaPool() {
        // Constructor privado para singleton
    }
    
    public static CeldaPool obtenerInstancia() {
        return instancia;
    }
    
    /**
     * Obtiene una Celda del pool o crea una nueva si no hay disponibles.
     * @param x Coordenada x de la celda
     * @param y Coordenada y de la celda
     * @return Una instancia de Celda con las coordenadas especificadas
     */
    public synchronized Celda obtenerCelda(int x, int y) {
        String clave = generarClave(x, y);
        Queue<Celda> cola = pool.computeIfAbsent(clave, k -> new LinkedList<>());
        
        Celda celda = cola.poll();
        if (celda == null) {
            // Si no hay celdas disponibles en el pool, crear una nueva
            celda = new Celda(x, y);
        } else {
            // Reiniciar el estado de la celda reciclada
            celda.reiniciar();
        }
        
        return celda;
    }
    
    /**
     * Devuelve una Celda al pool para su reutilización.
     * @param celda La celda a reciclar
     */
    public synchronized void devolverCelda(Celda celda) {
        String clave = generarClave(celda.getX(), celda.getY());
        Queue<Celda> cola = pool.computeIfAbsent(clave, k -> new LinkedList<>());
        cola.offer(celda);
    }
    
    /**
     * Genera una clave única para cada par de coordenadas.
     * @param x Coordenada x
     * @param y Coordenada y
     * @return Clave única para el pool
     */
    private String generarClave(int x, int y) {
        return x + "," + y;
    }
    
    /**
     * Limpia el pool, eliminando todas las celdas almacenadas.
     * Útil para liberar memoria cuando sea necesario.
     */
    public synchronized void limpiarPool() {
        pool.clear();
    }
}
