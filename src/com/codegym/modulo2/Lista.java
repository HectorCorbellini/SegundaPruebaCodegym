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
            int x = 0+i;
            int y = 0+i;  // LUEGO TABLEAR Y HOMOGENEIZAR
            char dibujo = (char)(numeroCharAnimal++);
            Animal animal = new Animal(x,y,dibujo);
            animales.add(animal);
        }  // for animales
        numeroCharPlanta = 111; // codigo ascii de letra 'o'
        for (int i = 0; i < Ajustes.cantidadInicialPlantas; i++) {
            Planta planta = new Planta(7+i,2+i, (char)(numeroCharPlanta++));
            plantas.add(planta);
        }  // for plantas
    }  // crearSeres

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

    public static void consecuenciasDeUnMovimiento(Animal animalQueLlegoAEstaCelda) {
    // al haberse movido un animal hay que controlar las consecuencias
        consecuenciasParaPlantas(animalQueLlegoAEstaCelda);
        consecuenciasParaAnimales(animalQueLlegoAEstaCelda);
    } // method

    static void consecuenciasParaPlantas(Animal animalQueLlegoAqui) {
        boolean alliTambienHayUnaPlanta;
            for (Planta p : plantas) {
            alliTambienHayUnaPlanta = (p.getX() == animalQueLlegoAqui.getX()
                    && p.getY() == animalQueLlegoAqui.getY());
            if (alliTambienHayUnaPlanta)  {
                Ser.comerPlanta(p, animalQueLlegoAqui);
                break; // solo puede haber una sola planta alli
            }  // if
        }  // for
    } // method
    static void consecuenciasParaAnimales(Animal animalQueLlegoAqui) {
        boolean alliTambienHayOtroAnimal;
            if (animalQueLlegoAqui.getEdad() >= Ajustes.edadReproductivaAnimal)  {
            for (Animal a: animales) {
                alliTambienHayOtroAnimal = (a.getX() == animalQueLlegoAqui.getX()
                        && a.getY() == animalQueLlegoAqui.getY())
                        && (a.getDibujo() != animalQueLlegoAqui.getDibujo());
                boolean tambienEsMayorDeEdad = a.getEdad() >= Ajustes.edadReproductivaAnimal;
                if (alliTambienHayOtroAnimal && tambienEsMayorDeEdad) {
                    Ser.aparearseAnimales(animalQueLlegoAqui);
                    a.setEnergia(a.getEnergia() - 1);
                    animalQueLlegoAqui.setEnergia(animalQueLlegoAqui.getEnergia() - 1);
                    break; // no me hago cargo de si hay mas animales en esa celda
                }  // if
            }  // for
        }  // if
    } // method
} // class

/*  NOTAS:
static void crearListasDeSeres()
  aqui los dos for se pueden unificar en un método con wildcard, pero si se hace eso
  se dificulta dar independencia de comportamiento a las plantas

*/
