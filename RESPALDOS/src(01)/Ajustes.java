package com.codegym.modulo2;

public class Ajustes {
    static final int maxTiempo = 30;
    static final int largoTablero = 30;
    static final int altoTablero = 10;
    static int cantidadInicialAnimales = 3;
    static int cantidadInicialPlantas = 5;
    static final int lentitudDeJuego = 1000; // milisegundos
    static int energiaInicialDeSeres = 10;
 //   static int energiaInicialDePlantas = 6;
 //   static int energiaInicialDeAnimales = 8;
    static final int edadReproductivaAnimal = 1;
    static final int edadDeMuerteAnimal = 10;
    static final int energiaTopePlanta = energiaInicialDeSeres + 4;
    static final int energiaTopeAnimal = energiaInicialDeSeres + 1;
    static final int energiaIntercambiadaEntrePlantaYAnimal = 3;
    static final boolean moverConVecindadMoore = false;

    static void transcurreUnMomento() {
    // enlentecimiento de la corrida para que la ejecución se pueda ver
        try {
            Thread.sleep(Ajustes.lentitudDeJuego);
        } catch (InterruptedException e) {
            throw new RuntimeException(e); }
    } // method
}  // class
