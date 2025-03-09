package isla;

import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Representa la cuadrícula donde se desarrolla la simulación del ecosistema.
 * Thread-safe implementation of the ecosystem grid.
 */
public class Tablero {
    private final Celda[][] celdas;
    private final int ancho;
    private final int alto;
    private final ConcurrentHashMap<String, List<Celda>> neighborCache;

    public Tablero(int ancho, int alto) {
        validarDimensiones(ancho, alto);
        this.ancho = ancho;
        this.alto = alto;
        this.celdas = new Celda[ancho][alto];
        this.neighborCache = new ConcurrentHashMap<>();
        inicializarCeldas();
    }

    private void validarDimensiones(int ancho, int alto) {
        if (ancho <= 0 || alto <= 0) {
            throw new IllegalArgumentException("Las dimensiones del tablero deben ser positivas");
        }
        if (ancho > 1000 || alto > 1000) {
            throw new IllegalArgumentException("Las dimensiones del tablero son demasiado grandes");
        }
    }

    private void inicializarCeldas() {
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                celdas[x][y] = new Celda(x, y);
            }
        }
    }

    public synchronized Celda obtenerCelda(int x, int y) {
        if (!esPosicionValida(x, y)) {
            throw new IllegalArgumentException(
                String.format("Posición fuera de los límites del tablero: x=%d, y=%d", x, y));
        }
        return celdas[x][y];
    }

    public boolean esPosicionValida(int x, int y) {
        return x >= 0 && x < ancho && y >= 0 && y < alto;
    }

    /**
     * Obtiene las celdas vecinas de una posición dada.
     * Utiliza caché para mejorar el rendimiento.
     */
    public List<Celda> obtenerVecinos(int x, int y) {
        String key = x + "," + y;
        return neighborCache.computeIfAbsent(key, k -> calcularVecinos(x, y));
    }

    private List<Celda> calcularVecinos(int x, int y) {
        List<Celda> vecinos = new ArrayList<>();
        int[][] direcciones = {{-1,0}, {1,0}, {0,-1}, {0,1}};
        
        for (int[] dir : direcciones) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (esPosicionValida(newX, newY)) {
                vecinos.add(celdas[newX][newY]);
            }
        }
        return Collections.unmodifiableList(vecinos);
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    /**
     * Limpia la caché de vecinos cuando sea necesario
     */
    public void limpiarCache() {
        neighborCache.clear();
    }

    static void cargarloVacio() {
        Tablero tablero = new Tablero(Ajustes.ANCHO_TABLERO, Ajustes.ALTO_TABLERO);
        for (int x = 0; x < Ajustes.ANCHO_TABLERO; x++)
            for (int y = 0; y < Ajustes.ALTO_TABLERO; y++) {
                Celda celda = tablero.obtenerCelda(x, y);
            } // for
    } // cargarlo

    static private void agregar (Ser ser) {
        int x = ser.getX();
        int y = ser.getY();
        Tablero tablero = new Tablero(Ajustes.ANCHO_TABLERO, Ajustes.ALTO_TABLERO);
        Celda celda = tablero.obtenerCelda(x, y);
        celda.agregarSerVivo(ser);
        celda.agregarSimbolo();
    } // method

    static void colocarLosSeres()  {
        cargarloVacio();
        agregarLosAnimales();
        agregarLasPlantas();
        limpiarPantalla();
        mostrarlo();
    } // method

    static void mostrarlo() {  
        System.out.println();
        Tablero tablero = new Tablero(Ajustes.ANCHO_TABLERO, Ajustes.ALTO_TABLERO);
        for (int y = 0; y < Ajustes.ALTO_TABLERO; y++) {
            for (int x = 0; x < Ajustes.ANCHO_TABLERO; x++) { 
                Celda celda = tablero.obtenerCelda(x, y);
                System.out.print(celda.getSimbolo());
            } 
            System.out.println();  
        } 
        System.out.println();  
    } // method

    static void agregarLosAnimales () {
        if (!GestorPoblacion.animales.isEmpty())
            for (Animal animal : GestorPoblacion.animales) {
                agregar(animal);
            } 
        else Salida.evento("no hay animales");
    }  

    static void agregarLasPlantas() {  
        if (!GestorPoblacion.plantas.isEmpty())
            for (Planta planta : GestorPoblacion.plantas) {
                agregar(planta);
            } 
        else Salida.evento("no hay plantas");
    }  

    private static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
} // class