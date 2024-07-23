package com.codegym.modulo2;

class Planta extends Ser {
    public Planta(int x, int y, char dibujo) {
        super(x, y, dibujo);
    } // constructor

    public static void comer(Planta planta, Animal animal) {
        int nuevaEnergiaDelAnimal = animal.getEnergia() + energiaIntercambiada;
        if (nuevaEnergiaDelAnimal > Ajustes.energiaTopeAnimal)
            nuevaEnergiaDelAnimal = Ajustes.energiaTopeAnimal;
        animal.setEnergia(nuevaEnergiaDelAnimal);
        planta.setEnergia(planta.getEnergia() - energiaIntercambiada);
        Salida.evento(animal.getDibujo()+" masticó "+planta.getDibujo());
    } // method

}  // class