package isla;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Sistema de particionamiento espacial para encontrar rápidamente entidades cercanas.
 * Divide el espacio en celdas y asigna entidades a estas celdas para búsquedas eficientes.
 */
public class SpatialGrid {
    private static final SpatialGrid instancia = new SpatialGrid();
    
    // Tamaño de cada celda en la cuadrícula espacial
    private final int cellSize;
    
    // Mapa que asocia cada celda con las entidades que contiene
    private final Map<String, List<Ser>> grid = new ConcurrentHashMap<>();
    
    // Cache de vecinos para cada celda
    private final Map<String, Map<Integer, Set<String>>> neighborCache = new ConcurrentHashMap<>();
    
    // Cache de resultados de búsqueda recientes
    private final Map<SearchKey, List<Ser>> searchCache = new ConcurrentHashMap<>();
    
    // Tiempo máximo de vida para entradas en caché (en milisegundos)
    private static final long CACHE_TTL = 100; // 100ms
    
    private static class SearchKey {
        final int x, y, radius;
        final long timestamp;
        
        SearchKey(int x, int y, int radius) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.timestamp = System.currentTimeMillis();
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SearchKey)) return false;
            SearchKey key = (SearchKey) o;
            return x == key.x && y == key.y && radius == key.radius;
        }
        
        @Override
        public int hashCode() {
            return 31 * (31 * x + y) + radius;
        }
        
        boolean isExpired() {
            return System.currentTimeMillis() - timestamp > CACHE_TTL;
        }
    }
    
    private SpatialGrid() {
        // El tamaño de celda puede ajustarse según las necesidades de rendimiento
        this.cellSize = 2; // Cada celda cubre un área de 2x2
    }
    
    public static SpatialGrid obtenerInstancia() {
        return instancia;
    }
    
    /**
     * Actualiza la posición de una entidad en la cuadrícula espacial.
     * @param ser La entidad a actualizar
     * @param oldX Coordenada X anterior (o -1 si es nueva)
     * @param oldY Coordenada Y anterior (o -1 si es nueva)
     */
    public void actualizarPosicion(Ser ser, int oldX, int oldY) {
        // Si la entidad ya estaba en la cuadrícula, eliminarla de su celda anterior
        if (oldX >= 0 && oldY >= 0) {
            String oldCell = calcularClave(oldX, oldY);
            List<Ser> oldCellEntities = grid.get(oldCell);
            if (oldCellEntities != null) {
                oldCellEntities.remove(ser);
            }
        }
        
        // Agregar la entidad a su nueva celda
        String newCell = calcularClave(ser.getX(), ser.getY());
        List<Ser> cellEntities = grid.computeIfAbsent(newCell, k -> new CopyOnWriteArrayList<>());
        if (!cellEntities.contains(ser)) {
            cellEntities.add(ser);
        }
    }
    
    /**
     * Elimina una entidad de la cuadrícula espacial.
     * @param ser La entidad a eliminar
     */
    public synchronized void eliminarEntidad(Ser ser) {
        String cell = calcularClave(ser.getX(), ser.getY());
        List<Ser> cellEntities = grid.get(cell);
        if (cellEntities != null) {
            cellEntities.remove(ser);
        }
    }
    
    /**
     * Encuentra todas las entidades en un radio específico alrededor de un punto.
     * @param x Coordenada X central
     * @param y Coordenada Y central
     * @param radius Radio de búsqueda
     * @return Lista de entidades dentro del radio especificado
     */
    public List<Ser> encontrarEntidadesCercanas(int x, int y, int radius) {
        // Limpiar entradas expiradas del caché
        searchCache.entrySet().removeIf(entry -> entry.getKey().isExpired());
        
        // Intentar obtener del caché
        SearchKey key = new SearchKey(x, y, radius);
        List<Ser> cachedResult = searchCache.get(key);
        if (cachedResult != null) {
            return new ArrayList<>(cachedResult);
        }
        
        // Si no está en caché, calcular y almacenar
        List<Ser> result = new ArrayList<>();
        
        // Calcular el rango de celdas a verificar
        int minCellX = (x - radius) / cellSize;
        int maxCellX = (x + radius) / cellSize;
        int minCellY = (y - radius) / cellSize;
        int maxCellY = (y + radius) / cellSize;
        
        // Obtener o calcular las celdas vecinas del caché
        Set<String> neighborCells = obtenerCeldasVecinas(x, y, radius);
        
        // Usar filtrado por distancia Manhattan primero (más rápido)
        neighborCells.stream()
            .map(grid::get)
            .filter(cellEntities -> cellEntities != null)
            .flatMap(List::stream)
            .filter(ser -> {
                // Filtro rápido usando distancia Manhattan
                int dx = Math.abs(ser.getX() - x);
                int dy = Math.abs(ser.getY() - y);
                int manhattanDist = dx + dy;
                
                if (manhattanDist > radius * 1.5) { // Factor de seguridad
                    return false;
                }
                
                // Solo calcular distancia euclidiana si pasa el filtro Manhattan
                double euclideanDist = Math.sqrt(dx * dx + dy * dy);
                return euclideanDist <= radius;
            })
            .collect(Collectors.toCollection(() -> result));
        
        searchCache.put(key, result);
        return result;
    }
    
    /**
     * Encuentra todas las entidades de un tipo específico en un radio dado.
     * @param x Coordenada X central
     * @param y Coordenada Y central
     * @param radius Radio de búsqueda
     * @param tipo Clase de las entidades a buscar
     * @return Lista de entidades del tipo especificado dentro del radio
     */
    public synchronized <T extends Ser> List<T> encontrarEntidadesPorTipo(int x, int y, int radius, Class<T> tipo) {
        List<T> result = new ArrayList<>();
        List<Ser> cercanas = encontrarEntidadesCercanas(x, y, radius);
        
        for (Ser ser : cercanas) {
            if (tipo.isInstance(ser)) {
                result.add(tipo.cast(ser));
            }
        }
        
        return result;
    }
    
    /**
     * Calcula la clave de celda para unas coordenadas dadas.
     * @param x Coordenada X
     * @param y Coordenada Y
     * @return Clave única para la celda
     */
    private String calcularClave(int x, int y) {
        return (x / cellSize) + ":" + (y / cellSize);
    }
    
    /**
     * Obtiene o calcula el conjunto de celdas vecinas para un punto y radio dados.
     */
    private Set<String> obtenerCeldasVecinas(int x, int y, int radius) {
        String centerCell = calcularClave(x, y);
        
        return neighborCache.computeIfAbsent(centerCell, k -> new ConcurrentHashMap<>())
            .computeIfAbsent(radius, r -> {
                Set<String> cells = ConcurrentHashMap.newKeySet();
                int minCellX = (x - radius) / cellSize;
                int maxCellX = (x + radius) / cellSize;
                int minCellY = (y - radius) / cellSize;
                int maxCellY = (y + radius) / cellSize;
                
                for (int cellY = minCellY; cellY <= maxCellY; cellY++) {
                    for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
                        cells.add(cellX + ":" + cellY);
                    }
                }
                return cells;
            });
    }
    
    /**
     * Limpia la cuadrícula espacial, eliminando todas las entidades.
     */
    public void limpiar() {
        grid.clear();
    }
    
    /**
     * Reconstruye la cuadrícula espacial con todas las entidades actuales.
     * Útil después de cambios masivos en las posiciones.
     */
    public synchronized void reconstruir() {
        limpiar();
        
        // Agregar todos los seres a la cuadrícula
        for (Ser ser : GestorPoblacion.obtenerInstancia().obtenerSeres()) {
            actualizarPosicion(ser, -1, -1);
        }
    }
}
