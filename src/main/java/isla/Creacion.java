package isla;

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

public class Creacion {
    //   final int cantCeldasTotales = Ajustes.largoTablero * Ajustes.altoTablero;
    static int priCeldaX = Ajustes.ESQUINA_SUPERIOR_IZQUIERDA.getX();
    static int priCeldaY = Ajustes.ESQUINA_SUPERIOR_IZQUIERDA.getY();
    static int ultCeldaX = Ajustes.ESQUINA_INFERIOR_DERECHA.getX();
    static int ultCeldaY = Ajustes.ESQUINA_INFERIOR_DERECHA.getY();
    static final int cantCeldasRectangulo = ((ultCeldaX-priCeldaX) * (ultCeldaY-priCeldaY));
    static Set<Celda> celdasLibres = new HashSet<>(cantCeldasRectangulo);
    static int cantAnimales = Ajustes.POBLACION_INICIAL_ANIMALES;
    static int cantPlantas = Ajustes.POBLACION_INICIAL_PLANTAS;
    static char dibujoCambianteAnimal = 'A';
    static char dibujoCambiantePlanta = 'o';

    static void iniciarListasDeSeres()  {
        // creacion de plantas y animales, se hace solamente una vez
        if (Ajustes.USAR_AREA_RECTANGULAR) {
            iniciarSeresEnRectangulos();
        }  // if
        else iniciarSeresPorLineas();
    }  // method

    static void iniciarSeresEnRectangulos()  {
        if ((cantAnimales + cantPlantas) < (Ajustes.ANCHO_TABLERO * Ajustes.ALTO_TABLERO)) {
            ArrayList<Ser> seres;
            // animales
            seres = asignarCeldasEnRectangulo(cantAnimales,dibujoCambianteAnimal);
            for (Ser ser : seres)
                GestorPoblacion.agregarAnimal(new Animal(ser.getX(),ser.getY(),ser.getDibujo()));
            // plantas
            seres = asignarCeldasEnRectangulo(cantPlantas,dibujoCambiantePlanta);
            for (Ser ser : seres)
                GestorPoblacion.agregarPlanta(new Planta(ser.getX(),ser.getY(),ser.getDibujo()));
        }  // if
        else System.out.println("Seres exceden tablero");
    }   // method

    static void iniciarSeresPorLineas() {
        for (int i = 0; i < Ajustes.POBLACION_INICIAL_ANIMALES; i++) {
            int x = 0+i;
            int y = 0+i;
            Animal animal = new Animal(x,y,dibujoCambianteAnimal++);
            GestorPoblacion.agregarAnimal(animal);
        }  // for animales
        for (int i = 0; i < Ajustes.POBLACION_INICIAL_PLANTAS; i++) {
            int x = 5+i;
            int y = 2+i;
            Planta planta = new Planta(x,y, dibujoCambiantePlanta++);
            GestorPoblacion.agregarPlanta(planta);
        }  // for plantas
    }  // method

    static ArrayList<Ser> asignarCeldasEnRectangulo(int cantSeres, char dibujoInicial) {
        ArrayList<Ser> seres = new ArrayList<>(cantSeres);
        llenarConCeldasVacias(celdasLibres);
        for (int i = 0; i < cantSeres; i++) {
            Celda celdaDeEsteSer = traerCeldaCualquieraDelRectangulo(celdasLibres);
            int x = celdaDeEsteSer.getX();
            int y = celdaDeEsteSer.getY();
            char dibujoCambiante = dibujoInicial++;
            Ser ser = new Ser(x,y,dibujoCambiante);
            seres.add(ser);
        }  // for
        return seres;
    }  // method

    static private Celda traerCeldaCualquieraDelRectangulo(Set<Celda> celdasLibres) {
        Iterator<Celda> it = celdasLibres.iterator();
        Celda celda = null;
        if (it.hasNext()) {
            celda = it.next();
            it.remove();  // para que no haya usadas en disponibles
        } // if
        assert celda != null; // nunca priCelda y ultCelda seran iguales
        return celda;
    } // method

    static private void llenarConCeldasVacias(Set<Celda> celdasLibres)  {
        for (int i = priCeldaX; i <= ultCeldaX; i++) {
            for (int j = priCeldaY; j <= ultCeldaY; j++) {
                celdasLibres.add(new Celda(i,j));
            } // for
        } // for
    } // method

} // class
