package isla;

import java.util.List;
import java.util.concurrent.*;

class Animal extends Ser {
    private int edad = 0;
    public Animal(int x, int y, char dibujo) {
        super(x, y, dibujo);
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

    static void moverlosTodosUnaCelda() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        
        try {
            List<Future<?>> futures = new CopyOnWriteArrayList<>();
            for (Animal animal : GestorPoblacion.animales) {
                futures.add(executor.submit(() -> {
                    Animal movedAnimal = moverUnAnimal(GestorPoblacion.animales.indexOf(animal));
                    movedAnimal.setEnergia(movedAnimal.getEnergia() - 1);
                    if (movedAnimal.getEnergia() > 0) {
                        consecuenciasDeUnMovimiento(movedAnimal);
                    }
                }));
            }
            for (Future<?> future : futures) {
                future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            Salida.evento("Error en procesamiento paralelo: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    } // method

    static Animal moverUnAnimal(int posicionLista) {
        Animal animalMoviente = GestorPoblacion.getAnimal(posicionLista);
        if (Ajustes.USAR_VECINDAD_MOORE) {
            moverConMoore(animalMoviente);
        } else
            moverConVonNeumann(animalMoviente);
        // Animal is modified in place, no need to set it back
        return animalMoviente;
    } // method

    static void moverConMoore (Animal animal)  {
        int nuevoX = Random.cambiarCoordenada(animal.getX(), Ajustes.ANCHO_TABLERO);
        int nuevoY = Random.cambiarCoordenada(animal.getY(), Ajustes.ALTO_TABLERO);
        // Usar setPosicion para actualizar ambas coordenadas en una sola operación
        // y evitar actualizaciones redundantes en la cuadrícula espacial
        animal.setPosicion(nuevoX, nuevoY);
    } // method

    static void moverConVonNeumann (Animal animal)  {
        boolean cambiarSoloX = Random.arrojarMonedaAlAire();
        int nuevoX = animal.getX();
        int nuevoY = animal.getY();
        
        if (cambiarSoloX)  {
            nuevoX = Random.cambiarCoordenada(animal.getX(), Ajustes.ANCHO_TABLERO);
        } // if
        else  {  // cambiarSoloY
            nuevoY = Random.cambiarCoordenada(animal.getY(), Ajustes.ALTO_TABLERO);
        } // else
        
        // Usar setPosicion para actualizar ambas coordenadas en una sola operación
        animal.setPosicion(nuevoX, nuevoY);
    } // method
    
    public static void consecuenciasDeUnMovimiento(Animal animalQueLlegoAEstaCelda) {
        // al haberse movido un animal hay que controlar las consecuencias
        consecuenciasParaPlantas(animalQueLlegoAEstaCelda);
        consecuenciasParaAnimales(animalQueLlegoAEstaCelda);
    }

    static void consecuenciasParaPlantas(Animal animalQueLlegoAqui) {
        // Usar el sistema de particionamiento espacial para encontrar plantas en la misma posición
        List<Planta> plantasCercanas = GestorPoblacion.encontrarSeresCercanosPorTipo(
                animalQueLlegoAqui.getX(), animalQueLlegoAqui.getY(), 0, Planta.class);
        
        if (!plantasCercanas.isEmpty()) {
            // Solo puede haber una planta en la misma celda
            Ser.comerPlanta(plantasCercanas.get(0), animalQueLlegoAqui);
        }
    }

    static void consecuenciasParaAnimales(Animal animalQueLlegoAqui) {
        if (animalQueLlegoAqui.getEdad() >= Ajustes.EDAD_REPRODUCTIVA)  {
            // Usar el sistema de particionamiento espacial para encontrar animales en la misma posición
            List<Animal> animalesCercanos = GestorPoblacion.encontrarSeresCercanosPorTipo(
                    animalQueLlegoAqui.getX(), animalQueLlegoAqui.getY(), 0, Animal.class);
            
            for (Animal a: animalesCercanos) {
                // Verificar que sea un animal diferente al que llegó
                if (a.getDibujo() != animalQueLlegoAqui.getDibujo() && 
                        a.getEdad() >= Ajustes.EDAD_REPRODUCTIVA) {
                    Ser.aparearseAnimales(animalQueLlegoAqui);
                    a.setEnergia(a.getEnergia() - 1);
                    animalQueLlegoAqui.setEnergia(animalQueLlegoAqui.getEnergia() - 1);
                    break; // no me hago cargo de si hay mas animales en esa celda
                }  // if
            }  // for
        }  // if
    }
}