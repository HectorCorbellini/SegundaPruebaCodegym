package isla;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Celda {
    public static final char CHAR_NULO = '.';
    private int x;
    private int y;
    private char simbolo = CHAR_NULO;
    private final List<Ser> seresVivosEnCelda  = new ArrayList<>(0);

    public Celda(int x, int y) {
        if (x >= 0 && x < Ajustes.ANCHO_TABLERO)
            this.x = x;
        else  {
            System.out.println(" columna no válida ");
            this.x = 0;
        }
        if (y >= 0 && y < Ajustes.ALTO_TABLERO)
            this.y = y;
        else {
            System.out.println(" fila no válida ");
            this.y = 0;
        }
    } // constructor

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public char getSimbolo() {
        return simbolo;
    }
    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }
    public void agregarSerVivo(Ser ser) {
        seresVivosEnCelda.add(ser);
    }
    void agregarSimbolo() {
        switch (cantidadDeSeresAqui()) {
            case 0 -> setSimbolo(CHAR_NULO);
            case 1 -> setSimbolo(traerDibujoDelSerVivo());
            case 2 -> setSimbolo('2');
            case 3 -> setSimbolo('3');
            case 4 -> setSimbolo('4');
            default -> setSimbolo('?'); // mas de 4 seres
        } // switch
    } // method
    int cantidadDeSeresAqui() {
        return seresVivosEnCelda.size();
    }

    public char traerDibujoDelSerVivo() {
        if (seresVivosEnCelda.size() == 1)  {
            Ser unicoSer = seresVivosEnCelda.get(0);
            return unicoSer.getDibujo();
        }  // if
        else  {  System.out.println("Fallo. No hay un único ser aqui.");
            return 'X';  } // else
    } // get

    static Celda encontrarUnaLibreAlrededor(int x, int y) {
        // eleccion aleatoria de celda libre
        HashSet<Celda> celdasPosibles = vecinosVonNeumann(x,y);
        boolean celdaParaHijoEstaLibre = true;
        List<Ser> seres = GestorPoblacion.todosLosSeres();
        for (Celda celdaP : celdasPosibles) {
            for (Ser ser : seres) {               // MEJOR USAR WHILE
                if (celdaP.getX() == ser.getX() && celdaP.getY() == ser.getY()) {
                    celdaParaHijoEstaLibre = false;
                    break;
                } // if
            } // for seres
            if (celdaParaHijoEstaLibre)
                return celdaP;
        } // for celdasPosibles
        return null;
    } // method
    
    /**
     * Reinicia el estado de la celda para su reutilización en el pool de objetos.
     * Limpia la lista de seres vivos y restablece el símbolo al valor por defecto.
     */
    public void reiniciar() {
        seresVivosEnCelda.clear();
        simbolo = CHAR_NULO;
    }

    private static HashSet<Celda> vecinosVonNeumann(int x, int y) {
        // REUSAR ESTE METODO PARA MOVER UN ANIMAL
        CeldaPool pool = CeldaPool.obtenerInstancia();
        HashSet<Celda> vecinosVonNeu = new HashSet<>();
        if (y > 0) vecinosVonNeu.add(pool.obtenerCelda(x,y-1));
        if (y < Ajustes.ALTO_TABLERO-1) vecinosVonNeu.add(pool.obtenerCelda(x,y+1));
        if (x > 0) vecinosVonNeu.add(pool.obtenerCelda(x-1,y));
        if (x < Ajustes.ANCHO_TABLERO-1) vecinosVonNeu.add(pool.obtenerCelda(x+1,y));
        return vecinosVonNeu;
    } // method

} // class