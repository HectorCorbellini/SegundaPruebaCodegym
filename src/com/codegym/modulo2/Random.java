package com.codegym.modulo2;

public class Random {
    // VER DE CAMBIAR ESTO POR UN ENUM DE CARDINALES CON HASHMAP
    
    static boolean arrojarMonedaAlAire() {
     return Math.random() < 0.5;
     //   boolean cara = Math.random() < 0.5;  cruz = !cara;
     //   return (cara) ? true : false;
     } // method

    static int cambiarCoordenada(int coord, int tope) {
        boolean coordDebeAumentar = Random.arrojarMonedaAlAire();
        if (coordDebeAumentar)  {
            coord++;
            if (coord > tope-1) coord = coord-2;  // rebote contra borde
        } else  {  // coord debe disminuir
            coord--;
            if (coord < 0) coord = coord+2;  // rebote contra borde
        } // if-else
        return coord;
    } // method

} // class

/* Moore tambien podia hacerse asi:
    static int cambiarCoordenadaMoore(int coord, int tope) {
        int max = 1; int min = -1;
        int rango = max - min + 1;
        int random_coord = (int)(Math.random() * rango) + min;
        coord += random_coord;
        coord = (coord > tope-1) ? coord-1 : coord;
        coord = (coord < 0) ? coord+1 : coord;
        return coord;
    } // method

 ejemplo random:
   int max = 10;
   int min = 1;
   int range = max - min + 1;
   // generate random numbers within 1 to 10
     for (int i = 0; i < 10; i++)
       int rand = (int)(Math.random() * range) + min;
 */