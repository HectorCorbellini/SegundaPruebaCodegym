package com.codegym.modulo2;

public class Pantalla {
    public static void limpieza()
    {  // FUNCIONA SOLO EN TERMINAL, y a medias
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
/*
OTRAS POSIBLES LIMPIEZAS
public class Pantalla {
final static String ESC = "\033[";
public static void ClearScreen()
{
System.out.print(ESC + "2J");
}
} // class

package com.intellij.execution;
import com.intellij.execution.ui.RunContentManager;
// import com.intellij.execution.ui

public static void limpiarConsola() {
getConsoleView().clear();
} // limpiarConsola

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public static void limpiarConsola() {
try {
Robot robot = new Robot();
robot.keyPress(KeyEvent.VK_ALT);
robot.keyPress(KeyEvent.VK_SHIFT);
robot.keyPress(KeyEvent.VK_1);
robot.keyRelease(KeyEvent.VK_ALT);
robot.keyRelease(KeyEvent.VK_SHIFT);
robot.keyRelease(KeyEvent.VK_1);
} catch (AWTException ex) {
ex.printStackTrace(System.err); }
} // limpiarConsola

public void limpiarImagen() {
PARA USAR EN UN MEDIO QUE NO SEA INTELLIJ
try {
final String os = System.getProperty("os.name");
System.out.println(os);
if (os.contains("Windows")) {
Runtime.getRuntime().exec("cls");
}
else {
Runtime.getRuntime().exec("clear");
}
} // try
catch (final Exception e) {
e.printStackTrace(); }
} // limpiarImagen*/
