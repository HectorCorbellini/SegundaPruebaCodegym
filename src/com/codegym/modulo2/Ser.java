package com.codegym.modulo2;

public class Ser {
    private int x, y;
    private char dibujo = ' ';
    private int energia = Ajustes.energiaInicialDeSeres;
    static final int energiaIntercambiada = Ajustes.energiaIntercambiadaEntrePlantaYAnimal;

    public Ser (int x, int y, char dibujo) {
        this.x = x; // puede agregarse validacion
        this.y = y;
        this.dibujo = dibujo;
    } // constructor

    public Ser (int x, int y) {
        this.x = x; // puede agregarse validacion
        this.y = y;
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

    public static void consecuenciasDeUnMovimiento(Animal animalQueLlegoAEstaCelda) {
        // al haberse movido un animal hay que controlar las consecuencias
        consecuenciasParaPlantas(animalQueLlegoAEstaCelda);
        consecuenciasParaAnimales(animalQueLlegoAEstaCelda);
    } // method

    static void consecuenciasParaPlantas(Animal animalQueLlegoAqui) {
        boolean alliTambienHayUnaPlanta;
        for (Planta p : Lista.plantas) {
            alliTambienHayUnaPlanta = (p.getX() == animalQueLlegoAqui.getX()
                    && p.getY() == animalQueLlegoAqui.getY());
            if (alliTambienHayUnaPlanta)  {
                comerPlanta(p, animalQueLlegoAqui);
                break; // solo puede haber una sola planta alli
            }  // if
        }  // for
    } // method
    static void consecuenciasParaAnimales(Animal animalQueLlegoAqui) {
        boolean alliTambienHayOtroAnimal;
        if (animalQueLlegoAqui.getEdad() >= Ajustes.edadReproductivaAnimal)  {
            for (Animal a: Lista.animales) {
                alliTambienHayOtroAnimal = (a.getX() == animalQueLlegoAqui.getX()
                        && a.getY() == animalQueLlegoAqui.getY())
                        && (a.getDibujo() != animalQueLlegoAqui.getDibujo());
                boolean tambienEsMayorDeEdad = a.getEdad() >= Ajustes.edadReproductivaAnimal;
                if (alliTambienHayOtroAnimal && tambienEsMayorDeEdad) {
                    aparearseAnimales(animalQueLlegoAqui);
                    a.setEnergia(a.getEnergia() - 1);
                    animalQueLlegoAqui.setEnergia(animalQueLlegoAqui.getEnergia() - 1);
                    break; // no me hago cargo de si hay mas animales en esa celda
                }  // if
            }  // for
        }  // if
    } // method

} // class