package com.codegym.modulo2;

import java.util.ArrayList;
import java.util.Iterator;

public class Lista {
    static ArrayList<Animal> animales = new ArrayList<>(Ajustes.cantidadInicialAnimales);
    static ArrayList<Planta> plantas = new ArrayList<>(Ajustes.cantidadInicialPlantas);
    static int numeroCharAnimal;
    static int numeroCharPlanta;

    // VER SI verdad se precisan estos geter y seter
    public static Animal getAnimal(int posicion)  {
        return animales.get(posicion);
    }  // getter
    public static void setAnimal (int posicion, Animal animal) {
        animales.set(posicion, animal);
    } // setter

    static void crearListasDeSeres()  {
        // creacion de plantas y animales, se hace solamente una vez
        numeroCharAnimal = 65;  // codigo ascii de letra 'A'
        for (int i = 0; i < Ajustes.cantidadInicialAnimales; i++) {
            int x = 2+i;
            int y = 2+i;  // LUEGO TABLEAR Y HOMOGENEIZAR
            char dibujo = (char)(numeroCharAnimal++);
            Animal animal = new Animal(x,y,dibujo);
            animales.add(animal);
        }  // for animales
        numeroCharPlanta = 111; // codigo ascii de letra 'o'
        for (int i = 0; i < Ajustes.cantidadInicialPlantas; i++) {
            Planta planta = new Planta(9+i,2+i, (char)(numeroCharPlanta++));
            plantas.add(planta);
        }  // for plantas
    }  // crearSeres

    static void agregarLosAnimalesAlTablero () {
        if (animales.size() > 0)
            for (Animal animal : animales) {
                Tablero.agregar(animal);
            } // for
        else Salida.evento("no hay animales");
    }  // method

    static void agregarLasPlantasAlTablero() {
        if (plantas.size() > 0)
            for (Planta planta : plantas) {  // CAMBIAR
                Tablero.agregar(planta);
            } // for
        else Salida.evento("no hay plantas");
    }  // method

    static void aumentarUnidadEdadDeAnimales()  {
        for (Animal animal : animales) {
            animal.setEdad(animal.getEdad()+1);
        }  // for
    }  // method

    static void aumentarUnidadEnergiaDePlantas()  {
        for (Planta planta : plantas) {
            int energiaDePlanta = planta.getEnergia();
            if (energiaDePlanta <= Ajustes.energiaTopePlanta)
                  planta.setEnergia(energiaDePlanta+1);
        }  // for
    }  // method

    static void matarAnimalesViejos()  {
        Iterator<Animal> it = animales.iterator();
        Animal animal;
        while (it.hasNext()) {
            animal = it.next();
            if (animal.getEdad() >= Ajustes.edadDeMuerteAnimal) {
                it.remove();
                Salida.muertes++;
                } // if
            } // while
    }  // method

    static void matarAnimalesDesenergizados()  {
        Iterator<Animal> it = animales.iterator();
        Animal animal;
        while (it.hasNext()) {
            animal = it.next();
            if (animal.getEnergia() <= 0)  {
                it.remove();
                Salida.muertes++;
            } // if
        } // while
    }  // method

    static void matarPlantasDesenergizadas()  {
        Iterator<Planta> it = plantas.iterator();
        Planta planta;
        while (it.hasNext()) {
            planta = it.next();
            if (planta.getEnergia() <= 0) {
                it.remove();
                Salida.muertes++;
            } // if
        } // while
    }  // method

    public static void consecuenciasDeUnMovimiento(Animal animalQueLlegoAEstaCelda)  {
        // al mover un animal hay que controlar consecuencias
        int x = animalQueLlegoAEstaCelda.getX();
        int y = animalQueLlegoAEstaCelda.getY();
        boolean alliTambienHayUnaPlanta;
        for (Planta p : plantas) {
            alliTambienHayUnaPlanta = (p.getX() == x && p.getY() == y);
            if (alliTambienHayUnaPlanta)  {
                Ser.comerPlanta(p, animalQueLlegoAEstaCelda);
                break; // solo puede haber una sola planta alli
            }  // if
        }  // for
        boolean alliTambienHayOtroAnimal;
        if (animalQueLlegoAEstaCelda.getEdad() >= Ajustes.edadReproductivaAnimal)  {
            for (Animal a: animales) {
                alliTambienHayOtroAnimal = (a.getX() == x && a.getY() == y)
                        && (a.getDibujo() != animalQueLlegoAEstaCelda.getDibujo());
                boolean tambienEsMayorDeEdad = a.getEdad() >= Ajustes.edadReproductivaAnimal;
                if (alliTambienHayOtroAnimal && tambienEsMayorDeEdad) {
                    Ser.aparearseAnimales(animalQueLlegoAEstaCelda);
                    a.setEnergia(a.getEnergia() - 1);
                    animalQueLlegoAEstaCelda.setEnergia(animalQueLlegoAEstaCelda.getEnergia() - 1);
                    break; // no me hago cargo de si hay mas animales en esa celda
                }  // if
            }  // for
        }  // if
    } // method

} // class

/*  NOTAS:
static void crearListasDeSeres()  {
  aqui los dos for se pueden unificar en un método con wildcard, pero si se hace eso
  se dificulta dar independencia de comportamiento a las plantas despues
  ver como quedaría ese método en los comentarios

  static void agregarLosSeresAlTablero() {
        if (animales.size() > 0)
            for (Animal animal : animales) {
                Tablero.agregar(animal);
            } // for
        else Salida.evento("no hay animales");
        if (plantas.size() > 0)
            for (Planta planta : plantas) {  // CAMBIAR
                Tablero.agregar(planta);
            } // for
        else Salida.evento("no hay plantas");
    }  // method
*/
