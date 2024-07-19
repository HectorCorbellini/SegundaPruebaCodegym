package com.codegym.modulo2;

public class Ser {
    private int x;
    private int y;
    private final char dibujo;
    private int energia = Ajustes.energiaInicialDeSeres;
    static final int energiaIntercambiada = Ajustes.energiaIntercambiadaEntrePlantaYAnimal;

    public Ser (int x, int y, char dibujo) {
        this.x = x; // puede agregarse validacion
        this.y = y;
        this.dibujo = dibujo;
    } // constructor

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public char getDibujo() {
        return dibujo;
    }
    public int getEnergia() {
        return energia;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public static void comerPlanta(Planta planta, Animal animal) {
        int nuevaEnergiaDelAnimal = animal.getEnergia() + energiaIntercambiada;
        if (nuevaEnergiaDelAnimal > Ajustes.energiaTopeAnimal)
            nuevaEnergiaDelAnimal = Ajustes.energiaTopeAnimal;
        animal.setEnergia(nuevaEnergiaDelAnimal);
        planta.setEnergia(planta.getEnergia() - energiaIntercambiada);
        Salida.evento(animal.getDibujo()+" masticó "+planta.getDibujo());
    } // method

    public static void aparearseAnimales(Animal unPadre) {
        // unPadre es cualquiera de los dos porque estan en la misma celda
        Celda celdaParaHijo =
              Celda.encontrarUnaLibreAlrededor(unPadre.getX(),unPadre.getY());
        boolean seEncontroCeldaParaHijo = (celdaParaHijo != null);
        if (seEncontroCeldaParaHijo) {
            crearElHijo(celdaParaHijo.getX(),celdaParaHijo.getY(),Creacion.dibujoCambianteAnimal++);
            } // if
        else Salida.evento("encuentro sin hijo");
    } // method

    public static void crearElHijo(int nuevoX, int nuevoY, char nuevoDibujo) {
        Animal nuevoAnimal = new Animal(nuevoX,nuevoY,nuevoDibujo);
        Lista.animales.add(nuevoAnimal);
        Salida.evento("hijo "+nuevoDibujo+" creado en ["+nuevoX+","+nuevoY+"]");
        Salida.nacimientos++;
    } // method

} // class