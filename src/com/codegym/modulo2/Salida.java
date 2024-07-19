package com.codegym.modulo2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class Salida implements Serializable {  // REVISAAAR!!!!
    static int muertes = 0;
    static int nacimientos = 0;
    static StringBuilder eventos = new StringBuilder();
// cambiar stringbuilder por stringbuffer con los threads

    static void evento(String evento) {
        eventos.append("/").append(evento).append(" ");
    } // method

    static BufferedWriter iniciarCSV()  {
  //      String rutaSalida = "/storage/emulated/0/Documents/CSV.txt";
        String rutaSalida = "/home/uko/Documentos/CSV.txt";
        BufferedWriter buffParaGuardar;
        try {
            FileWriter writer = new FileWriter(rutaSalida, true);
            buffParaGuardar = new BufferedWriter(writer);
            buffParaGuardar.write("Tiempo;Animales;Plantas;Nacimientos;Muertes;Eventos");
            buffParaGuardar.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e); }
        return buffParaGuardar;
    }  // method

    static void hacerEstadistica(int momentos, BufferedWriter bufferCSV)  {
    String estadistica = Ajustes.traerEstadistica(momentos, Lista.animales.size(), Lista.plantas.size());
    guardarArchivoCSV(bufferCSV, estadistica);
    reiniciarVariables();
    }  // method

    static void reiniciarVariables() {
        eventos.setLength(0);
        nacimientos = 0;
        muertes = 0;
    } // method

    static void guardarArchivoCSV(BufferedWriter buffParaGuardar, String estadistica) {
        try {
            buffParaGuardar.write(estadistica);  // añado al archivo la linea
            buffParaGuardar.newLine();
        } catch (IOException e) { throw new RuntimeException(e); }
    }  // method

    static void accionesDeCierre(int momentos, BufferedWriter bufferCSV)  {
        try {
            bufferCSV.close();
        } catch (IOException e) {
            throw new RuntimeException(e);  }
        if (momentos == Ajustes.maxTiempo) System.out.println("Tiempo agotado.");
        else System.out.println("La vida se desequilibró.");
    }  // method

} // class
