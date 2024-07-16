package com.codegym.modulo2;

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

    public static Animal moverUnAnimal(int posicionLista) {
        Animal animalMoviente = Lista.getAnimal(posicionLista);
        int viejoX = animalMoviente.getX();
        int viejoY = animalMoviente.getY();
        int nuevoX = viejoX;
        int nuevoY = viejoY;
        if (Ajustes.moverConVecindadMoore)  {
            while (nuevoX == viejoX && nuevoY == viejoY) {
                nuevoX = Random.cambiarCoordenadaMoore(viejoX,Ajustes.largoTablero);
                nuevoY = Random.cambiarCoordenadaMoore(viejoY,Ajustes.altoTablero);
            } // while
        } // if
        else {  // vecindad VonNeumann. Cambia una sola coordenada.
            boolean cambiarSoloX = Random.arrojarMonedaAlAire();
            if (cambiarSoloX)
                nuevoX = cambiarCoordenadaVonNeumann(viejoX, Ajustes.largoTablero);
            else  // cambiarSoloY
                nuevoY = cambiarCoordenadaVonNeumann(viejoY, Ajustes.altoTablero);
        } // else
        animalMoviente.setX(nuevoX);
        animalMoviente.setY(nuevoY);
        Lista.setAnimal(posicionLista, animalMoviente);
        return animalMoviente;
    } // method

    static int cambiarCoordenadaVonNeumann(int coord, int tope) {
        boolean coordDebeAumentar = Random.arrojarMonedaAlAire();
        if (coordDebeAumentar) coord++; else coord--;
        if (coord > tope-1) coord = coord-2;  // rebote contra bordes
        else if (coord < 0) coord = coord+2;
        return coord;
    } // method

} // class
