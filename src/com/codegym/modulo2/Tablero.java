package com.codegym.modulo2;

public class Tablero {
    public static final char CHAR_NULO = '.';
    static final Celda[][] tablero = new Celda[Ajustes.largoTablero][Ajustes.altoTablero];

    static void cargarloVacio() {  // REESCRIBIRLO CON toString REHECHO
        for (int x = 0; x < Ajustes.largoTablero; x++)
            for (int y = 0; y < Ajustes.altoTablero; y++) {
                Celda celda = new Celda(x,y);
                tablero[x][y] = celda;
            } // for
    } // cargarlo

    static void agregar (Ser ser) {
        int x = ser.getX();
        int y = ser.getY();
        tablero[x][y].setSerVivo(ser);
 //       es lo mismo que decir:  Celda celda = tablero[x][y];
 //         celda.setSerVivo(ser);  tablero[x][y] = celda;
    } // agregar

    static void mostrarlo() {
        System.out.println();
        // es necesario que primero se impriman las columnas
        for (int y = 0; y < Ajustes.altoTablero; y++) {
            for (int x = 0; x < Ajustes.largoTablero; x++) { // se imprime de a caracter
                Celda celda = tablero[x][y];
                int cantSeresCelda = celda.getCantidadDeSeresAqui();
                char dibujo = (cantSeresCelda > 0) ?
                        celda.getDibujoDePrimerSerVivo() : Tablero.CHAR_NULO;
                        //  celda.seresVivos.get(0) : Tablero.CHAR_NULO;
                // Añadir casos 2 3 4
                System.out.print(dibujo);
            } // for x
            System.out.println();  // cambio de fila
        } // for y
        System.out.println();  // linea luego del tablero
    } // printTablero

} // class