package com.codegym.modulo2;
// PROPOSITO DE ESTA CLASE: determinar los marcos de aparición de animales y plantas.
// En total hay 3 marcos, los dos de seres vivos y el marco total del tablero.
// De esa manera el usuario puede ubicar animales y plantas en las zonas que prefiera.
// IMPORTANTE: eligiendo un marco para animales centrado y con un tamaño mas pequeño
// que el marco total, puede emularse un poco el tablero infinito porque
// los animales no se trasladan rápidamente hacia el borde del tablero.

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Entrada {
    //   final int cantCeldasTotales = Ajustes.largoTablero * Ajustes.altoTablero;
    static final Celda priCeldaSeres = new Celda(1,1);
    static final Celda ultCeldaSeres = new Celda(4,8);
    //  final Celda priCeldaPlantas = new Celda(1,1);
    //  final Celda ultCeldaPlantas = new Celda(20,8);
    static int priCeldaX = priCeldaSeres.getX();
    static int priCeldaY = priCeldaSeres.getY();
    static int ultCeldaX = ultCeldaSeres.getX();
    static int ultCeldaY = ultCeldaSeres.getY();
    static final int cantCeldasRectangulo = ((ultCeldaX-priCeldaX) * (ultCeldaY-priCeldaY));
   // final Set<Celda> bolsaDeCeldasDisponibles  = rectanguloDeCeldasLibres();

    // ESTE RECT DEBE SER UN HASH DE CELDAS DONDE CADA CELDA TIENE EL X,Y ASIGNADO
    // POR LA DETERMINACION DE ESYTE RECTANGULO
    // LUEGO CADA SER AL CREARSE SE LLEVA UNA CELD A DE LA BOLSA, LA VA CONSUMIENDO
    // PARA CREAR LA BOLSA DE CELDAS SE PUEDE ITERAR CON FOR X Y FOR Y

    static ArrayList<Ser> asignarCeldasAleatorias(int cantSeres, char dibujoInicial) {
        // AGREGAR VALIDACION
        ArrayList<Ser> seres = new ArrayList<>(cantSeres);
        for (int i = 0; i < cantSeres; i++) {
            Celda celdaDeEsteSer = traerCeldaAleatoriaDelRectangulo();
            int x = celdaDeEsteSer.getX();
            int y = celdaDeEsteSer.getY();
            char dibujoCambiante = dibujoInicial++;
            Ser ser = new Ser(x,y,dibujoCambiante);
            seres.add(ser);
        }  // for animales
        return seres;
    }  // method

    static Celda traerCeldaAleatoriaDelRectangulo() {
        Set<Celda> celdasLibres = new HashSet<>(cantCeldasRectangulo);
        if (Ajustes.cantidadInicialAnimales > cantCeldasRectangulo)
            System.out.println ("fuera de rango!");  // MANEJAR NULL POINTER EXCEPTION
        else   // creacion del rectangulo con parametros
            llenarCeldasConVacio(celdasLibres);
        Iterator<Celda> it = celdasLibres.iterator();
        Celda celda = null;
        if (it.hasNext()) {
            celda = it.next();
            it.remove();  // para que no haya usadas en disponibles
        } // if
        assert celda != null;
        return celda;
    } // method

    static void llenarCeldasConVacio(Set<Celda> celdasLibres)  {
        for (int i = priCeldaX; i <= ultCeldaX; i++) {
            for (int j = priCeldaY; j <= ultCeldaY; j++) {
                celdasLibres.add(new Celda(i,j));
            } // for
        } // for
    } // method

} // class

