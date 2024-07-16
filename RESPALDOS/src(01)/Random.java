package com.codegym.modulo2;

import java.util.*;

public class Random {
    // AÑADIR randomizacion en la creacion de seres
   static enum Destino {
        haciaUnLado,
        haciaElOtro
    } // enum
    //
    static List<Destino> posiciones = Arrays.asList(Destino.values());
    static Set<Destino> posicionesAleatorias = new HashSet<>(posiciones);
    static Iterator<Destino> it = posicionesAleatorias.iterator();

    static int cambiarCoordenadaVonNeu(int coord) {  // int tope ?
        boolean disminuirCoordenada = Math.random() < 0.5;
        // boolean aumentarCoordenada = !disminuirCoordenada;
        return (disminuirCoordenada) ? coord-1 : coord+1;
        } // method
    
    static boolean arrojarMonedaAlAire() {
        boolean cara = Math.random() < 0.5; 
        // cruz = !cara;
        return (cara) ? true : false;
        } // method
    
      //  posicionesAleatorias.add(Destino.haciaUnLado);
     //   posicionesAleatorias.add(Destino.haciaElOtro);
      //  Destino posicion = Destino.haciaUnLado;
       // if (it.hasNext()) posicion = it.next();
      //  else System.out.println("MAL");
      //  return (posicion.equals(Destino.haciaElOtro)) ? coord-- : coord++;
    
    static int cambiarCoordenadaMoore(int tope, int coord) {
        int max = 1; int min = -1;
        int rango = max - min + 1;
        int random_coord = (int)(Math.random() * rango) + min;
        coord += random_coord;
        coord = (coord > tope-1) ? coord-1 : coord;
        coord = (coord < 0) ? coord+1 : coord;
        return coord;
    } // method
} // class

/*    static int cambiarCoordenadaConVonN(int tope, int coord) {
        do  {
        }
        while (coord < 0 || coord > tope);
        return coord;
      }

 ejemplo random:
int max = 10;
int min = 1;
int range = max - min + 1;
// generate random numbers within 1 to 10
for (int i = 0; i < 10; i++) {
int rand = (int)(Math.random() * range) + min;

o tambien funciona:
int random_coord = (int)Math.floor(Math.random() * (1 - (-1) + 1) + (-1));
coord += random_coord;
coord = (coord > orientacion-1) ? coord-1 : coord;
coord = (coord < 0) ? coord+1 : coord;
return coord;

OTRA FORMA:

enum Destino {
        haciaUnLado,
        haciaElOtro
    } // enum
    //   Set<Destino> posicionesAleatorias = new HashSet<>(posiciones);
    List<Destino> posiciones = Arrays.asList(Destino.values());
    Collections.shuffle(posiciones);
    Destino ord = posiciones.get(0);
            switch (ord) {
        case norte : coord = (); break;
        case sur :  break;
        case este :  break;
        case oeste :  break;
    } // switch
*/