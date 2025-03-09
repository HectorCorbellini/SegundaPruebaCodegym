package isla;

/**
 * Clase que contiene todas las configuraciones y constantes del sistema.
 * Organiza los parámetros en categorías lógicas para mejor mantenimiento.
 */
public class Ajustes {
    // Configuración de tiempo y simulación
    static final int DURACION_MAXIMA_SIMULACION = 30;  // Antes maxTiempo
    static final int PAUSA_ENTRE_CICLOS = 1000;        // Milisegundos, antes lentitudDeJuego

    // Dimensiones del tablero
    static final int ANCHO_TABLERO = 30;  // Antes largoTablero
    static final int ALTO_TABLERO = 10;   // Antes altoTablero

    // Configuración inicial de seres vivos
    static final boolean USAR_AREA_RECTANGULAR = true;  // Antes iniciarSeresEnAreasRectangulares
    static final Celda ESQUINA_SUPERIOR_IZQUIERDA = celdaValidadaDeSeres(2, 1);  // Antes primeraCeldaSeres
    static final Celda ESQUINA_INFERIOR_DERECHA = celdaValidadaDeSeres(5, 3);    // Antes ultimaCeldaSeres
    
    // Población inicial
    static int POBLACION_INICIAL_ANIMALES = 3;  // Antes cantidadInicialAnimales
    static int POBLACION_INICIAL_PLANTAS = 5;   // Antes cantidadInicialPlantas

    // Configuración de energía
    static int ENERGIA_INICIAL = 10;            // Antes energiaInicialDeSeres
    static final int ENERGIA_MAXIMA_PLANTA = ENERGIA_INICIAL + 4;  // Antes energiaTopePlanta
    static final int ENERGIA_MAXIMA_ANIMAL = ENERGIA_INICIAL + 1;  // Antes energiaTopeAnimal
    static final int ENERGIA_TRANSFERIDA = 3;   // Antes energiaIntercambiadaEntrePlantaYAnimal

    // Ciclo de vida animal
    static final int EDAD_REPRODUCTIVA = 1;     // Antes edadReproductivaAnimal
    static final int EDAD_MAXIMA = 8;          // Antes edadDeMuerteAnimal

    // Comportamiento
    static final boolean USAR_VECINDAD_MOORE = false;  // Antes moverConVecindadMoore

    /**
     * Valida y crea una celda si las coordenadas están dentro del tablero.
     * @param x Coordenada X
     * @param y Coordenada Y
     * @return Nueva Celda si es válida, null si está fuera del tablero
     */
    static Celda celdaValidadaDeSeres(int x, int y) {
        if (x < ANCHO_TABLERO && y < ALTO_TABLERO) {
            return new Celda(x, y);
        }
        return null;
    }


    /**
     * Genera una línea de estadísticas para el archivo CSV.
     * @param animales Cantidad actual de animales
     * @param plantas Cantidad actual de plantas
     * @return Línea de estadísticas en formato CSV
     */
    static String traerEstadistica(int animales, int plantas) {
        System.out.println("Tiempo;Animales;Plantas;Nacimientos;Muertes;Eventos");
        String datos = Main.momento + ";" + animales + ";" + plantas +
                ";" + Salida.nacimientos + ";" + Salida.muertes + ";" + Salida.eventos;
        System.out.println(datos);
        return datos;
    }

    /**
     * Introduce una pausa en la ejecución para visualizar la simulación.
     * @throws RuntimeException si la pausa es interrumpida
     */
    static void transcurreEsteMomento() {
        try {
            Thread.sleep(PAUSA_ENTRE_CICLOS);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error durante la pausa de simulación", e);
        }
    }
}  // class