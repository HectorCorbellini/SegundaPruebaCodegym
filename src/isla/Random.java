package isla;

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