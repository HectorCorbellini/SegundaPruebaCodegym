package isla;

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
        for (int i = 0; i < GestorPoblacion.animales.size(); i++) {
            Animal animal = moverUnAnimal(i);
            animal.setEnergia(animal.getEnergia() - 1);
            if (animal.getEnergia() > 0) // si llega muerto no tiene poder de cambio
                Animal.consecuenciasDeUnMovimiento(animal);
        } // end for
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
        animal.setX(nuevoX);
        animal.setY(nuevoY);
    } // method

    static void moverConVonNeumann (Animal animal)  {
        boolean cambiarSoloX = Random.arrojarMonedaAlAire();
        if (cambiarSoloX)  {
            animal.setX(Random.cambiarCoordenada(animal.getX(), Ajustes.ANCHO_TABLERO));
        } // if
        else  {  // cambiarSoloY
            animal.setY(Random.cambiarCoordenada(animal.getY(), Ajustes.ALTO_TABLERO));
        } // else
    } // method
    
    public static void consecuenciasDeUnMovimiento(Animal animalQueLlegoAEstaCelda) {
        // al haberse movido un animal hay que controlar las consecuencias
        consecuenciasParaPlantas(animalQueLlegoAEstaCelda);
        consecuenciasParaAnimales(animalQueLlegoAEstaCelda);
    }

    static void consecuenciasParaPlantas(Animal animalQueLlegoAqui) {
        boolean alliTambienHayUnaPlanta;
        for (Planta p : GestorPoblacion.plantas) {
            alliTambienHayUnaPlanta = (p.getX() == animalQueLlegoAqui.getX()
                    && p.getY() == animalQueLlegoAqui.getY());
            if (alliTambienHayUnaPlanta)  {
                Ser.comerPlanta(p, animalQueLlegoAqui);
                break; // solo puede haber una sola planta alli
            }  // if
        }  // for
    }

    static void consecuenciasParaAnimales(Animal animalQueLlegoAqui) {
        boolean alliTambienHayOtroAnimal;
        if (animalQueLlegoAqui.getEdad() >= Ajustes.EDAD_REPRODUCTIVA)  {
            for (Animal a: GestorPoblacion.animales) {
                alliTambienHayOtroAnimal = (a.getX() == animalQueLlegoAqui.getX()
                        && a.getY() == animalQueLlegoAqui.getY())
                        && (a.getDibujo() != animalQueLlegoAqui.getDibujo());
                boolean tambienEsMayorDeEdad = a.getEdad() >= Ajustes.EDAD_REPRODUCTIVA;
                if (alliTambienHayOtroAnimal && tambienEsMayorDeEdad) {
                    Ser.aparearseAnimales(animalQueLlegoAqui);
                    a.setEnergia(a.getEnergia() - 1);
                    animalQueLlegoAqui.setEnergia(animalQueLlegoAqui.getEnergia() - 1);
                    break; // no me hago cargo de si hay mas animales en esa celda
                }  // if
            }  // for
        }  // if
    }
}