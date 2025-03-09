package isla;

public class Tablero {
    public static final char CHAR_NULO = '.';
    private static final Celda[][] tablero = new Celda[Ajustes.ANCHO_TABLERO][Ajustes.ALTO_TABLERO];

    static void cargarloVacio() {
        for (int x = 0; x < Ajustes.ANCHO_TABLERO; x++)
            for (int y = 0; y < Ajustes.ALTO_TABLERO; y++) {
                Celda celda = new Celda(x,y);
                tablero[x][y] = celda;
            } // for
    } // cargarlo

    static private void agregar (Ser ser) {
        int x = ser.getX();
        int y = ser.getY();
        tablero[x][y].agregarSerVivo(ser);
        tablero[x][y].agregarSimbolo();
    } // method

    static void colocarLosSeres()  {
        cargarloVacio();
        agregarLosAnimales();
        agregarLasPlantas();
        limpiarPantalla();
        mostrarlo();
    } // method

    static void mostrarlo() {  // REESCRIBIRLO CON toString
        System.out.println();
        // es necesario que primero se impriman las columnas
        for (int y = 0; y < Ajustes.ALTO_TABLERO; y++) {
            for (int x = 0; x < Ajustes.ANCHO_TABLERO; x++) { // se imprime de a caracter
                Celda celda = tablero[x][y];
                System.out.print(celda.getSimbolo());
            } // for x
            System.out.println();  // cambio de fila
        } // for y
        System.out.println();  // linea luego del tablero
    } // method

    static void agregarLosAnimales () {
        if (!GestorPoblacion.animales.isEmpty())
            for (Animal animal : GestorPoblacion.animales) {
                agregar(animal);
            } // for
        else Salida.evento("no hay animales");
    }  // method

    static void agregarLasPlantas() {  // CAMBIAR A HASHSET
        if (!GestorPoblacion.plantas.isEmpty())
            for (Planta planta : GestorPoblacion.plantas) {
                agregar(planta);
            } // for
        else Salida.evento("no hay plantas");
    }  // method

    private static void limpiarPantalla() {
        // FUNCIONA SOLO EN TERMINAL, y a medias
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
} // class