# SegundaPruebaCodegym
proyecto Isla. Es un autómata celular.
Letra del problema al final de este Readme.
Observaciones:
Este programa utiliza dos arraList distintos para representar animales por un lado y plantas por otro.
En ocasiones unifica los dos arrays en otro de nombre seres, casteado, de modo que los campos propios
de las clases Animal y Planta, no quedan en ese array, porque son clases hijas de Ser.
Al comienzo le da al programador la posibilidad de colocar los seres en pantalla todos dentro de un recuadro,
o colocarlos por separado en lineas diagonales distintas.
Los nombres de los seres son letras mayúsculas para los animales, y minúsculas para las plantas, pero al irse
creando hijjos, los nombres pueden exceder el abecedario.

## El proyecto final del módulo 2

Consiste en construir un autómata celular. Dicho autómata deberá cumplir los siguientes requerimientos:

1. Deberá contar con un tablero de dos dimensiones de tamaño fijo.
2. A cada elemento del tablero se le llamará celda.
3. En un estado inicial, cada una de las celdas de un tablero caerá en exactamente uno de los siguientes tres estados:
    1. Contiene un animal
    2. Contiene una planta
    3. Está vacía
4. Se deberá representar el paso de cada unidad de tiempo de alguna manera visible hacia el espectador.
   Se deja a consideración del programador dicha decisión.
5. Cada planta tendrá un nivel de energía. Dicho nivel de energía avanzará conforme avanza el tiempo
   hasta llegar a un máximo.
6. Cada animal tendrá un nivel de energía y una edad.
7. Los animales se irán moviendo por cada unidad de tiempo hacia una celda vecina aleatoria.
8. Las plantas no se moverán a lo largo del juego.
9. Una vez que el animal se ha movido, este puede caer en uno de los siguientes casos:
    1. Se mueva hacia una celda vacía: se resta una unidad de energía al animal y no sucede nada más.
    2. Se mueve hacia una celda que contiene una planta: se resta una unidad de energía al animal,
       posteriormente el animal come de esa planta, por lo tanto absorbe una cantidad de energía de ella.
       Esa misma cantidad de energía se le resta a la energía de la planta.
    3. Se mueve hacia una celda en la cual coincide con otro animal: se resta una unidad de energía
       al animal, posteriormente los animales se reproducen, esto significa que se creará una cría en
       una celda vecina vacía con un nivel fijo de energía determinado por el programador.
       Después de una reproducción se le restará un cantidad fija de energía a ambos animales.
       En el caso de que no exista una celda disponible para la cría, no se lleva a cabo la reproducción.
10. Un animal muere cuando se alcanza al menos una de las siguientes situaciones:
    1. Se ha terminado la energía del animal.
    2. Ha alcanzado su edad máxima.
11. Cada planta aumentará su nivel de energía por cada unidad de tiempo.
12. Cuando un animal o planta han alcanzado su nivel máximo de energía no pasa nada adicional,
    simplemente ya no sigue creciendo y su energía se mantiene en el máximo.
13. El autómata deberá validar que todo movimiento o creación de animales se realiza dentro del tablero,
    sin rebasar los bordes de este.
14. El programador es libre de decidir si el autómata se definirá con la vecindad de Moore o la vecindad
    de Von Neumann. Sin embargo se recomienda iniciar con esta última ya que resultará en una
    implementación más simple.
15. El programador es libre de decidir si el tablero será finito, periódico o infinito. Sin embargo
    se sugiere iniciar implementando un tablero finito ya que es la opción más simple. En un tablero
    finito, las celdas vecinas solo incluyen las que existen dentro del tablero y no se considerarán otras.
16. Es necesario que exista una clase llamada *Configuracion*. En ella se podrán determinar diferentes
    parámetros para experimentar cómo se comporta el autómata en diversos escenarios.
17. En cada unidad de tiempo se deberán obtener y almacenar en un archivo de texto csv las siguientes
    estadísticas sobre el autómata, separadas por punt y coma ; ****:
    1. Número de plantas
    2. Número de animales
    3. Número de nacimientos
    4. Número de muertes
    5. Eventos

    Ejemplo de archivo de texto:

    Tiempo;Animales;Plantas;Nacimientos;Muertes;Eventos
    1;10;20;0;0;tablero inicial
    2;11;18;1;0;Nacimiento en celda [2,4] / Muere planta en [1,3] / Muere planta en [0,1]
    3;10;18;0;1;Muere animal en [7,1]

    -------------------------------------------------------------
