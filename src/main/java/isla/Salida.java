package isla;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Salida {
    private static BufferedWriter bufferCSV;
    static int muertes = 0;
    static int nacimientos = 0;
    static StringBuilder eventos = new StringBuilder();
// cambiar stringbuilder por stringbuffer con los threads

    static void evento(String evento) {
        eventos.append("/").append(evento).append(" ");
    } // method

    static void iniciarCSV()  {
        String rutaSalida = "/root/my-applications/CODEGYM-TODO/ISLA_2025/CSV.txt";
        try {
            FileWriter writer = new FileWriter(rutaSalida, true);
            bufferCSV = new BufferedWriter(writer);
            bufferCSV.write("Tiempo;Animales;Plantas;Nacimientos;Muertes;Eventos");
            bufferCSV.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e); }
    }  // method

    static void hacerEstadistica()  {
        String estadistica = Ajustes.traerEstadistica(GestorPoblacion.animales.size(), GestorPoblacion.plantas.size());
        guardarArchivoCSV(estadistica);
        reiniciarVariables();
    }  // method

    static void reiniciarVariables() {
        eventos.setLength(0);
        nacimientos = 0;
        muertes = 0;
    } // method

    static void guardarArchivoCSV(String estadistica) {
        try {
            bufferCSV.write(estadistica);  // añado al archivo la linea
            bufferCSV.newLine();
        } catch (IOException e) { throw new RuntimeException(e); }
    }  // method

    static void accionesDeCierre()  {
        try {
            bufferCSV.close();
        } catch (IOException e) {
            throw new RuntimeException(e);  }
        if (Ajustes.momento == Ajustes.DURACION_MAXIMA_SIMULACION) System.out.println("Tiempo agotado.");
        else System.out.println("La vida se desequilibró.");
    }  // method

} // class