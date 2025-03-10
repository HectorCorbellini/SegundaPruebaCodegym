package isla;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;
import java.util.Map;

/**
 * Gestor de memoria personalizado para entidades del juego.
 * Implementa un sistema de pooling de objetos para reducir la creación/destrucción de entidades.
 */
public class MemoryManager {
    private static final MemoryManager instancia = new MemoryManager();
    private final Map<Class<?>, Queue<Object>> pool;
    private final Map<Class<?>, Integer> estadisticas;
    
    private MemoryManager() {
        this.pool = new ConcurrentHashMap<>();
        this.estadisticas = new ConcurrentHashMap<>();
    }
    
    public static MemoryManager obtenerInstancia() {
        return instancia;
    }
    
    /**
     * Obtiene una entidad del pool o crea una nueva si no hay disponibles.
     * @param clase Tipo de entidad requerida
     * @param x Coordenada x inicial
     * @param y Coordenada y inicial
     * @param dibujo Carácter que representa la entidad
     * @return Una instancia de la entidad solicitada
     */
    @SuppressWarnings("unchecked")
    public synchronized <T> T obtenerEntidad(Class<T> clase, int x, int y, char dibujo) {
        Queue<Object> cola = pool.computeIfAbsent(clase, k -> new ConcurrentLinkedQueue<>());
        
        T entidad = (T) cola.poll();
        if (entidad == null) {
            // Crear nueva entidad si no hay disponibles en el pool
            entidad = crearNuevaEntidad(clase, x, y, dibujo);
            incrementarContador(clase);
        } else {
            // Reiniciar estado de la entidad reciclada
            if (entidad instanceof Resettable) {
                ((Resettable) entidad).reiniciar();
            }
            if (entidad instanceof Ser) {
                Ser ser = (Ser) entidad;
                ser.setPosicion(x, y);
                ser.setDibujo(dibujo);
            }
        }
        
        return entidad;
    }
    
    /**
     * Devuelve una entidad al pool para su reutilización.
     * @param entidad Entidad a reciclar
     */
    public synchronized void devolverEntidad(Object entidad) {
        if (entidad == null) return;
        
        Class<?> clase = entidad.getClass();
        Queue<Object> cola = pool.computeIfAbsent(clase, k -> new ConcurrentLinkedQueue<>());
        
        if (entidad instanceof Resettable) {
            ((Resettable) entidad).reiniciar();
        }
        
        cola.offer(entidad);
    }
    
    /**
     * Crea una nueva instancia de la entidad especificada.
     */
    @SuppressWarnings("unchecked")
    private <T> T crearNuevaEntidad(Class<T> clase, int x, int y, char dibujo) {
        if (clase == Animal.class) {
            return (T) new Animal(x, y, dibujo);
        } else if (clase == Planta.class) {
            return (T) new Planta(x, y, dibujo);
        }
        throw new IllegalArgumentException("Tipo de entidad no soportado: " + clase.getName());
    }
    
    /**
     * Incrementa el contador de entidades creadas para una clase específica.
     */
    private void incrementarContador(Class<?> clase) {
        estadisticas.merge(clase, 1, Integer::sum);
    }
    
    /**
     * Obtiene el número total de entidades creadas para una clase específica.
     */
    public int obtenerTotalCreadas(Class<?> clase) {
        return estadisticas.getOrDefault(clase, 0);
    }
    
    /**
     * Limpia todos los pools de objetos.
     */
    public synchronized void limpiarPools() {
        pool.clear();
    }
}
