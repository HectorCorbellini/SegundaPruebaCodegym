package com.codegym.modulo2;

public class Tablero {
    public static final char CHAR_NULO = '.';
    static final Celda[][] tablero = new Celda[Ajustes.largoTablero][Ajustes.altoTablero];

    static void cargarloVacio() {
        for (int x = 0; x < Ajustes.largoTablero; x++)
            for (int y = 0; y < Ajustes.altoTablero; y++) {
                Celda celda = new Celda(x,y);
                tablero[x][y] = celda;
            } // for
    } // cargarlo

    static void agregar (Ser ser) {
        int x = ser.getX();
        int y = ser.getY();
        tablero[x][y].agregarSerVivo(ser);
        tablero[x][y].agregarSimbolo();
    } // method

    static void colocarLosSeres()  {
        cargarloVacio();
		agregarLosAnimales();
		agregarLasPlantas();
        Pantalla.limpieza();
		mostrarlo();
    } // method

    static void mostrarlo() {  // REESCRIBIRLO CON toString
        System.out.println();
        // es necesario que primero se impriman las columnas
        for (int y = 0; y < Ajustes.altoTablero; y++) {
            for (int x = 0; x < Ajustes.largoTablero; x++) { // se imprime de a caracter
                Celda celda = tablero[x][y];
                System.out.print(celda.getSimbolo());
            } // for x
            System.out.println();  // cambio de fila
        } // for y
        System.out.println();  // linea luego del tablero
    } // method

    static void agregarLosAnimales () {
        if (Lista.animales.size() > 0)
            for (Animal animal : Lista.animales) {
                agregar(animal);
            } // for
        else Salida.evento("no hay animales");
    }  // method

    static void agregarLasPlantas() {  // CAMBIAR A HASHSET
        if (Lista.plantas.size() > 0)
            for (Planta planta : Lista.plantas) {
                agregar(planta);
            } // for
        else Salida.evento("no hay plantas");
    }  // method

} // class