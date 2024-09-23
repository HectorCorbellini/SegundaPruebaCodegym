package isla;

public class Pantalla {
    public static void limpieza()
    {  // FUNCIONA SOLO EN TERMINAL, y a medias
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}