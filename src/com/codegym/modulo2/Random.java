package com.codegym.modulo2;

import java.util.*;

public class Random {
    // AÑADIR randomizacion en la creacion de seres
    
    static boolean arrojarMonedaAlAire() {
        boolean cara = Math.random() < 0.5; 
        // cruz = !cara;
        return (cara) ? true : false;
        } // method
    
    static int cambiarCoordenadaMoore(int coord, int tope) {
        int max = 1; int min = -1;
        int rango = max - min + 1;
        int random_coord = (int)(Math.random() * rango) + min;
        coord += random_coord;
        coord = (coord > tope-1) ? coord-1 : coord;
        coord = (coord < 0) ? coord+1 : coord;
        return coord;
    } // method
} // class

/* Moore tambien podia hacerse parecido a VonNeumann:
    static int cambiarCoordenadaMoore(int coord) {  // int tope ?
        boolean disminuirCoordenada = Math.random() < 0.5;
        // boolean aumentarCoordenada = !disminuirCoordenada;
        return (disminuirCoordenada) ? coord-1 : coord+1;
    clase Animales:
       nuevoX = Random.cambiarCoordenadaMoore(viejoX);
       if (nuevoX > Ajustes.largoTablero-1 || nuevoX < 0) nuevoX = viejoX;
       nuevoY = Random.cambiarCoordenadaMoore(viejoY);
       if (nuevoY > Ajustes.altoTablero-1 || nuevoY < 0) nuevoY = viejoY;
  */
 /*
 ejemplo random:
   int max = 10;
   int min = 1;
   int range = max - min + 1;
   // generate random numbers within 1 to 10
     for (int i = 0; i < 10; i++)
       int rand = (int)(Math.random() * range) + min;
 */