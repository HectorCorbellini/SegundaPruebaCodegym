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
        for (int i = 0; i < Lista.animales.size(); i++) {
            Animal animal = moverUnAnimal(i);
            animal.setEnergia(animal.getEnergia() - 1);
            if (animal.getEnergia() > 0) // si llega muerto no tiene poder de cambio
                Animal.consecuenciasDeUnMovimiento(animal);
        } // end for
    } // method

    static Animal moverUnAnimal(int posicionLista) {
        Animal animalMoviente = Lista.getAnimal(posicionLista);
        if (Ajustes.moverConVecindadMoore) {
            moverConMoore(animalMoviente);
        } else
            moverConVonNeumann(animalMoviente);
        Lista.setAnimal(posicionLista, animalMoviente);
        return animalMoviente;
    } // method

    static void moverConMoore (Animal animal)  {
        int nuevoX = Random.cambiarCoordenada(animal.getX(), Ajustes.largoTablero);
        int nuevoY = Random.cambiarCoordenada(animal.getY(), Ajustes.altoTablero);
        animal.setX(nuevoX);
        animal.setY(nuevoY);
    } // method

    static void moverConVonNeumann (Animal animal)  {
        boolean cambiarSoloX = Random.arrojarMonedaAlAire();
        if (cambiarSoloX)  {
            animal.setX(Random.cambiarCoordenada(animal.getX(), Ajustes.largoTablero));
        } // if
        else  {  // cambiarSoloY
            animal.setY(Random.cambiarCoordenada(animal.getY(), Ajustes.altoTablero));
        } // else
    } // method

} // class