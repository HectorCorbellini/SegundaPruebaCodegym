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
                nuevoX = Random.cambiarCoordenadaMoore(Ajustes.largoTablero, viejoX);
                nuevoY = Random.cambiarCoordenadaMoore(Ajustes.altoTablero, viejoY);
            } // while
        } // if
        else {  // vecindad VonNeumann
            // en VonNeumann cambia una sola de las coordenadas
            while (nuevoX == viejoX && nuevoY == viejoY) {
                boolean cambiarSoloX = Random.arrojarMonedaAlAire();
                if (cambiarSoloX) {
                    boolean elNuevoXdebeAumentar = Random.arrojarMonedaAlAire();
                    if (elNuevoXdebeAumentar) nuevoX++; else nuevoX--;
                    if (nuevoX > Ajustes.largoTablero-1 || nuevoX < 0)
                        nuevoX = viejoX;  // rebote contra bordes
                } // if
                else  { // cambiarSoloY
                    boolean elNuevoYdebeAumentar = Random.arrojarMonedaAlAire();
                    if (elNuevoYdebeAumentar) nuevoY++; else nuevoY--;
                    if (nuevoY > Ajustes.altoTablero-1 || nuevoY < 0)
                        nuevoY = viejoY;  // rebote contra bordes
                } // else
       // Moore   nuevoX = Random.cambiarCoordenadaVonNeu(viejoX);
         //       if (nuevoX > Ajustes.largoTablero-1 || nuevoX < 0) nuevoX = viejoX;
        //        nuevoY = Random.cambiarCoordenadaVonNeu(viejoY);
         //       if (nuevoY > Ajustes.altoTablero-1 || nuevoY < 0) nuevoY = viejoY;
            } // while
        } // else
        animalMoviente.setX(nuevoX);
        animalMoviente.setY(nuevoY);
        Lista.setAnimal(posicionLista, animalMoviente);
        return animalMoviente;
    } // method

} // class

/*
else {  // vecindad VonNeumann
        // en VonNeumann cambia una sola de las coordenadas
        while (nuevoX == viejoX && nuevoY == viejoY) {  // tiene REBOTE
        boolean cambiarSoloX = Random.arrojarMonedaAlAire();
        if (cambiarSoloX) {
        nuevoX = Random.cambiarCoordenadaVonNeu(viejoX);
        if (nuevoX > Ajustes.largoTablero-1 || nuevoX < 0) nuevoX = viejoX;
        } // if
        else  { // cambiarSoloY
        nuevoY = Random.cambiarCoordenadaVonNeu(viejoY);
        if (nuevoY > Ajustes.altoTablero-1 || nuevoY < 0) nuevoY = viejoY;
        }*/
