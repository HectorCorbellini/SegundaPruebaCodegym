package com.codegym.modulo2;

import java.util.ArrayList;
import java.util.HashSet;

public class Celda {
    private int x;
    private int y;  // INTENTAR QUITAR ESTOS CAMPOS
    private ArrayList<Ser> seresVivos; // = new ArrayList<>(0); devolver, ver iniciarSeresVivos
    //  private HashSet<int[][]> vecinosMoore = new HashSet();

    public Celda(int x, int y) {
        this.x = x;
        this.y = y;
    } // constructor
    //    this.dibujo = dibujo; no se necesita
    
    private static HashSet<Celda> vecinosVonNeumann(int x, int y) {
        HashSet<Celda> vecinosVonNeu = new HashSet<>();
        if (y > 0) vecinosVonNeu.add(new Celda(x,y-1));
        if (y < Ajustes.altoTablero-1) vecinosVonNeu.add(new Celda(x,y+1));
        if (x > 0) vecinosVonNeu.add(new Celda(x-1,y));
        if (x < Ajustes.largoTablero-1) vecinosVonNeu.add(new Celda(x+1,y));
        return vecinosVonNeu;
    } // method

    public void setSerVivo(Ser ser) {
        seresVivos.add(ser);
    }
    public void iniciarSeresVivos() {
        seresVivos = new ArrayList<>(0);
    }
    public int getCantidadDeSeresAqui() {
        return seresVivos.size();
    }
    public char getDibujoDePrimerSerVivo() {
        if (seresVivos.size() > 0)  {
            Ser primerSer = seresVivos.get(0);
            char esteDibujo = primerSer.getDibujo();
            // el primero es el que más estuvo en esta celda
            return esteDibujo;
        }  // if
        else return Tablero.CHAR_NULO;
    }
//    public char getDibujo() {
//        return dibujo; }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    
    static Celda encontrarUnaLibreAlrededor(int x, int y) {
        HashSet<Celda> celdasPosibles = vecinosVonNeumann(x,y);
        boolean celdaParaHijoEstaLibre = true;
        for (Celda celdaP : celdasPosibles) {
            int i = celdaP.getX(); int j = celdaP.getY();
         // metodo viejo no controlaba animales:
          //  Celda celdaPosible = Tablero.tablero[i][j];
         //   char celdaPosibleDibujo = celdaPosible.getDibujoDePrimerSerVivo();
         //   boolean celdaParaHijoEstaLibre = (int)celdaPosibleDibujo == (int)Tablero.CHAR_NULO;
            for (Animal animal : Lista.animales) {
                if (i == animal.getX() && j == animal.getY()) {
                    celdaParaHijoEstaLibre = false;
                } // if
            } // for animal    
        //  hacer lo mismo para plantas            
            if (celdaParaHijoEstaLibre) {
                return celdaP;
            } // if
        } // for
        return null;
    } // method
} // class
