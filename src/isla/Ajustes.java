package isla;

public class Ajustes {
    static final int maxTiempo = 30;
    static final int largoTablero = 30;
    static final int altoTablero = 10;
    static final boolean iniciarSeresEnAreasRectangulares = true;
    // iniciarSeresPorLineas = ! iniciarSeresEnAreasRectangulares;
    static final Celda primeraCeldaSeres = celdaValidadaDeSeres(2,1);
    static final Celda ultimaCeldaSeres = celdaValidadaDeSeres(5,3);
    // en el rectangulo formado por esas dos celdas se colocan automáticamente los seres vivos
    static int cantidadInicialAnimales = 3;
    static int cantidadInicialPlantas = 5;
    static final int lentitudDeJuego = 1000; // milisegundos
    static int energiaInicialDeSeres = 10;
    //   podría separarse en energiaInicial de Plantas y Animales
    static final int edadReproductivaAnimal = 1;
    static final int edadDeMuerteAnimal = 8;  // CAMBIAR VALOR
    static final int energiaTopePlanta = energiaInicialDeSeres + 4;
    static final int energiaTopeAnimal = energiaInicialDeSeres + 1;
    static final int energiaIntercambiadaEntrePlantaYAnimal = 3;
    static final boolean moverConVecindadMoore = false;

    static Celda celdaValidadaDeSeres (int x, int y) {
        if (x < largoTablero && y < altoTablero)
            return new Celda(x,y);
        else return null;
    } // if


    static String traerEstadistica(int animales, int plantas) {
        // llevarlo a mostrar archivo CSV
        System.out.println("Tiempo;Animales;Plantas;Nacimientos;Muertes;Eventos");
        String datos = Main.momento + ";" + animales + ";" + plantas +
                ";" + Salida.nacimientos + ";" + Salida.muertes + ";" + Salida.eventos;
        System.out.println(datos);
        return datos;
    }  // method

    static void transcurreEsteMomento() {
        // enlentecimiento de la corrida para que la ejecución se pueda ver
        try {
            Thread.sleep(Ajustes.lentitudDeJuego);
        } catch (InterruptedException e) {
            throw new RuntimeException(e); }
    } // method
}  // class