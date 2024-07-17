package com.codegym.modulo2;

public class Vida {

    static boolean recrearla()  {
        boolean hayVidaSuficiente;
        hayVidaSuficiente = moverVida();
        if (hayVidaSuficiente)
            actualizarVidaAEsteMomento();
        else
            Salida.evento("no hay vida suficiente");
        int topeDeAnimales = (Ajustes.largoTablero * Ajustes.altoTablero) - Lista.plantas.size();
        boolean hayDemasiadaVida = false;
        if (!(Lista.animales.size() < topeDeAnimales)) {
            hayDemasiadaVida = true;
            Salida.evento("vida supera el tablero");
        }  // if
        return hayVidaSuficiente && !hayDemasiadaVida;
    }  // method

    static boolean moverVida() {
        boolean hayVidaSuficiente;
        if (Lista.animales.size() > 0) {
            hayVidaSuficiente = true;
            Animal.moverlosTodosUnaCelda();
        } // if
        else hayVidaSuficiente = false;
        return hayVidaSuficiente;
    }  // method

    static void actualizarVidaAEsteMomento() {
        Lista.aumentarUnidadEdadDeAnimales();
        Lista.matarAnimalesViejos();
        if (Lista.animales.size() > 0)
            Lista.matarAnimalesDesenergizados();
        Lista.matarPlantasDesenergizadas();
        if (Lista.plantas.size() > 0)
            Lista.aumentarUnidadEnergiaDePlantas();
    }  // method

} // class
