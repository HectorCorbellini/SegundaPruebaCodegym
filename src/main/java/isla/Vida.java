package isla;

public class Vida {

    static boolean recrearla()  {
        boolean hayVidaSuficiente;
        hayVidaSuficiente = moverVida();
        if (hayVidaSuficiente)
            actualizarVidaAEsteMomento();
        else
            Salida.evento("no hay vida suficiente");
        int topeDeAnimales = (Ajustes.ANCHO_TABLERO * Ajustes.ALTO_TABLERO) - GestorPoblacion.plantas.size();
        boolean hayDemasiadaVida = false;
        if (GestorPoblacion.animales.size() > topeDeAnimales) {
            hayDemasiadaVida = true;
            Salida.evento("vida supera el tablero");
        }  // if
        return hayVidaSuficiente && !hayDemasiadaVida;
    }  // method

    static boolean moverVida() {
        boolean hayVidaSuficiente;
        if (!GestorPoblacion.animales.isEmpty()) {
            hayVidaSuficiente = true;
            Animal.moverlosTodosUnaCelda();
        } // if
        else hayVidaSuficiente = false;
        return hayVidaSuficiente;
    }  // method

    static void actualizarVidaAEsteMomento() {
        GestorPoblacion.aumentarUnidadEdadDeAnimales();
        GestorPoblacion.eliminarAnimalesMuertos();
        if (!GestorPoblacion.animales.isEmpty())
            GestorPoblacion.eliminarAnimalesDesenergizados();
        GestorPoblacion.eliminarPlantasMuertas();
        if (!GestorPoblacion.plantas.isEmpty())
            GestorPoblacion.aumentarUnidadEnergiaDePlantas();
    }  // method

} // class