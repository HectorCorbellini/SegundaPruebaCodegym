package isla;

/**
 * Clase que gestiona el ciclo de vida del ecosistema.
 */
public class Vida {

    /**
     * Método principal que gestiona un ciclo de vida completo del ecosistema.
     * @return true si la vida en el ecosistema está equilibrada, false si no hay suficiente vida o hay demasiada.
     */
    static boolean recrearla() {
        boolean hayVidaSuficiente;
        hayVidaSuficiente = moverVida();
        
        if (hayVidaSuficiente) {
            // Ahora las plantas realizan sus acciones
            if (!GestorPoblacion.plantas.isEmpty()) {
                GestorPoblacion.aumentarUnidadEnergiaDePlantas();
            }
        } else {
            Salida.evento("no hay vida suficiente");
        }
        
        // Verificar si hay demasiada vida en el ecosistema
        int topeDeAnimales = (Ajustes.ANCHO_TABLERO * Ajustes.ALTO_TABLERO) - GestorPoblacion.plantas.size();
        boolean hayDemasiadaVida = false;
        if (GestorPoblacion.animales.size() > topeDeAnimales) {
            hayDemasiadaVida = true;
            Salida.evento("vida supera el tablero");
        }
        
        return hayVidaSuficiente && !hayDemasiadaVida;
    }

    /**
     * Gestiona el movimiento de los animales en el ecosistema.
     * @return true si hay animales en el ecosistema, false en caso contrario.
     */
    static boolean moverVida() {
        if (!GestorPoblacion.animales.isEmpty()) {
            // El método moverlosTodosUnaCelda ahora maneja el envejecimiento y las acciones de los animales
            Animal.moverlosTodosUnaCelda();
            return true;
        }
        return false;
    }
} // class