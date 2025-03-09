package isla;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Gestiona las poblaciones de animales y plantas en el ecosistema.
 * Mantiene las listas de seres vivos y controla su ciclo de vida.
 */
public class GestorPoblacion {
    static ArrayList<Animal> animales = new ArrayList<>(Ajustes.POBLACION_INICIAL_ANIMALES);
    static ArrayList<Planta> plantas = new ArrayList<>(Ajustes.POBLACION_INICIAL_PLANTAS);

    public static Animal getAnimal(int posicion)  {
        return animales.get(posicion);
    }  // method

    public static void agregarAnimal(Animal animal)  {
        animales.add(animal);
    }  // method

    public static void agregarPlanta(Planta planta)  {
        plantas.add(planta);
    }  // method

    static void aumentarUnidadEnergiaDePlantas()  {
        for (Planta planta : plantas) {
            int energiaDePlanta = planta.getEnergia();
            if (energiaDePlanta <= Ajustes.ENERGIA_MAXIMA_PLANTA)
                planta.setEnergia(energiaDePlanta+1);
        }  // for
    }  // method

    static void eliminarAnimalesMuertos()  {
        Iterator<Animal> it = animales.iterator();
        Animal animal;
        while (it.hasNext()) {
            animal = it.next();
            if (animal.getEdad() >= Ajustes.EDAD_MAXIMA) {
                it.remove();
                Salida.muertes++;
            } // if
        } // while
    } // method

    static void eliminarPlantasMuertas()  {
        Iterator<Planta> it = plantas.iterator();
        Planta planta;
        while (it.hasNext()) {
            planta = it.next();
            if (planta.getEnergia() <= 0) {
                it.remove();
                Salida.muertes++;
            } // if
        } // while
    } // method
    static void aumentarUnidadEdadDeAnimales() {
        for (Animal animal : animales) {
            animal.setEdad(animal.getEdad() + 1);
        }
    }

    static void eliminarAnimalesDesenergizados() {
        Iterator<Animal> it = animales.iterator();
        Animal animal;
        while (it.hasNext()) {
            animal = it.next();
            if (animal.getEnergia() <= 0) {
                it.remove();
                Salida.muertes++;
            }
        }
    }

    static HashSet<Ser> todosLosSeres() {
        HashSet<Ser> seres = new HashSet<>();
        seres.addAll(animales);
        seres.addAll(plantas);
        return seres;
    }
} // class
