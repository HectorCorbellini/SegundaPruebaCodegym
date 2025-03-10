package isla;

public class Ser implements Resettable {
    private int x;
    private int y;
    private char dibujo;
    private int energia = Ajustes.ENERGIA_INICIAL;
    static final int energiaIntercambiada = Ajustes.ENERGIA_TRANSFERIDA;

    public Ser (int x, int y, char dibujo) {
        this.x = x; // puede agregarse validacion
        this.y = y;
        this.dibujo = dibujo;
    } // constructor

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public char getDibujo() {
        return dibujo;
    }

    public void setDibujo(char dibujo) {
        this.dibujo = dibujo;
    }
    public int getEnergia() {
        return energia;
    }
    public void setX(int x) {
        int oldX = this.x;
        this.x = x;
        // Actualizar posición en la cuadrícula espacial
        SpatialGrid.obtenerInstancia().actualizarPosicion(this, oldX, this.y);
    }
    
    public void setY(int y) {
        int oldY = this.y;
        this.y = y;
        // Actualizar posición en la cuadrícula espacial
        SpatialGrid.obtenerInstancia().actualizarPosicion(this, this.x, oldY);
    }
    
    /**
     * Actualiza la posición (x,y) en una sola operación, optimizando la actualización
     * en la cuadrícula espacial.
     * @param x Nueva coordenada X
     * @param y Nueva coordenada Y
     */
    public void setPosicion(int x, int y) {
        int oldX = this.x;
        int oldY = this.y;
        this.x = x;
        this.y = y;
        // Actualizar posición en la cuadrícula espacial
        SpatialGrid.obtenerInstancia().actualizarPosicion(this, oldX, oldY);
    }
    public void setEnergia(int energia) {
        this.energia = energia;
    }

    public static void comerPlanta(Planta planta, Animal animal) {
        int nuevaEnergiaDelAnimal = animal.getEnergia() + energiaIntercambiada;
        if (nuevaEnergiaDelAnimal > Ajustes.ENERGIA_MAXIMA_ANIMAL)
            nuevaEnergiaDelAnimal = Ajustes.ENERGIA_MAXIMA_ANIMAL;
        animal.setEnergia(nuevaEnergiaDelAnimal);
        planta.setEnergia(planta.getEnergia() - energiaIntercambiada);
        Salida.evento(animal.getDibujo()+" masticó "+planta.getDibujo());
    } // method

    public static void aparearseAnimales(Animal unPadre) {
        // unPadre es cualquiera de los dos porque estan en la misma celda
        Celda celdaParaHijo =
                Celda.encontrarUnaLibreAlrededor(unPadre.getX(),unPadre.getY());
        boolean seEncontroCeldaParaHijo = (celdaParaHijo != null);
        if (seEncontroCeldaParaHijo) {
            crearElHijo(celdaParaHijo.getX(),celdaParaHijo.getY(),Creacion.dibujoCambianteAnimal++);
        } // if
        else Salida.evento("encuentro sin hijo");
    } // method

    public static void crearElHijo(int nuevoX, int nuevoY, char nuevoDibujo) {
        Animal nuevoAnimal = new Animal(nuevoX,nuevoY,nuevoDibujo);
        GestorPoblacion.agregarAnimal(nuevoAnimal);
        Salida.evento("hijo "+nuevoDibujo+" creado en ["+nuevoX+","+nuevoY+"]");
        Salida.nacimientos++;
    } // method



    @Override
    public void reiniciar() {
        energia = Ajustes.ENERGIA_INICIAL;
        x = 0;
        y = 0;
        // No reiniciamos dibujo aquí, se establecerá al obtener la entidad del pool
    }
} // class