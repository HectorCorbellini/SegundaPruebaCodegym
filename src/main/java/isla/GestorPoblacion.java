package isla;

import java.util.LinkedList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Gestiona las poblaciones de animales y plantas en el ecosistema.
 * Mantiene las listas de seres vivos y controla su ciclo de vida.
 */
public class GestorPoblacion {
    // Mantener compatibilidad con el código existente
    static List<Animal> animales = new CopyOnWriteArrayList<>();
    static List<Planta> plantas = new CopyOnWriteArrayList<>();
    
    private static final GestorPoblacion instancia = new GestorPoblacion();
    private final List<Ser> seres = new CopyOnWriteArrayList<>();
    
    private GestorPoblacion() {
        // Las listas se inicializan vacías y se llenarán con seres vivos
    }
    
    public static GestorPoblacion obtenerInstancia() {
        return instancia;
    }
    
    public void agregarSer(Ser ser) {
        seres.add(ser);
        if (ser instanceof Animal) {
            animales.add((Animal) ser);
        } else if (ser instanceof Planta) {
            plantas.add((Planta) ser);
        }
        
        // Actualizar la cuadrícula espacial
        SpatialGrid.obtenerInstancia().actualizarPosicion(ser, -1, -1);
    }
    
    public void eliminarSer(Ser ser) {
        seres.remove(ser);
        if (ser instanceof Animal) {
            animales.remove(ser);
        } else if (ser instanceof Planta) {
            plantas.remove(ser);
        }
        
        // Eliminar de la cuadrícula espacial
        SpatialGrid.obtenerInstancia().eliminarEntidad(ser);
    }
    
    public List<Ser> obtenerSeres() {
        return new LinkedList<>(seres);
    }
    
    public int contarEspecies(Class<? extends Ser> tipo) {
        return (int) seres.stream().filter(tipo::isInstance).count();
    }
    
    public boolean existeVida() {
        return !seres.isEmpty();
    }
    
    // Métodos estáticos para mantener compatibilidad
    public static Animal getAnimal(int posicion) {
        return animales.get(posicion);
    }

    public static Animal crearAnimal(int x, int y, char dibujo) {
        Animal animal = MemoryManager.obtenerInstancia().obtenerEntidad(Animal.class, x, y, dibujo);
        agregarAnimal(animal);
        return animal;
    }

    public static void agregarAnimal(Animal animal) {
        instancia.agregarSer(animal);
    }

    public static Planta crearPlanta(int x, int y, char dibujo) {
        Planta planta = MemoryManager.obtenerInstancia().obtenerEntidad(Planta.class, x, y, dibujo);
        agregarPlanta(planta);
        return planta;
    }

    public static void agregarPlanta(Planta planta) {
        instancia.agregarSer(planta);
    }

    static void aumentarUnidadEnergiaDePlantas() {
        // Este método se mantiene por compatibilidad pero ahora usamos el método procesarTodasLasPlantas
        Planta.procesarTodasLasPlantas();
    }

    static void eliminarAnimalesMuertos() {
        // Este método se mantiene por compatibilidad
        // La lógica de muerte por edad ahora está en el método envejecer() de Ser
    }

    static void eliminarPlantasMuertas() {
        // Este método se mantiene por compatibilidad
        // La lógica de muerte por falta de energía ahora está en el método consumirEnergia() de Ser
    }

    static void aumentarUnidadEdadDeAnimales() {
        // Este método se mantiene por compatibilidad
        // El envejecimiento ahora se maneja en el método moverlosTodosUnaCelda() de Animal
    }

    static void eliminarAnimalesDesenergizados() {
        // Este método se mantiene por compatibilidad
        // La lógica de muerte por falta de energía ahora está en el método consumirEnergia() de Ser
    }

    static List<Ser> todosLosSeres() {
        return new LinkedList<>(instancia.seres);
    }
    
    /**
     * Encuentra seres cercanos a una posición dada.
     * @param x Coordenada X
     * @param y Coordenada Y
     * @param radio Radio de búsqueda
     * @return Lista de seres dentro del radio especificado
     */
    public static List<Ser> encontrarSeresCercanos(int x, int y, int radio) {
        return SpatialGrid.obtenerInstancia().encontrarEntidadesCercanas(x, y, radio);
    }
    
    /**
     * Encuentra seres de un tipo específico cercanos a una posición.
     * @param x Coordenada X
     * @param y Coordenada Y
     * @param radio Radio de búsqueda
     * @param tipo Clase de los seres a buscar
     * @return Lista de seres del tipo especificado dentro del radio
     */
    public static <T extends Ser> List<T> encontrarSeresCercanosPorTipo(int x, int y, int radio, Class<T> tipo) {
        return SpatialGrid.obtenerInstancia().encontrarEntidadesPorTipo(x, y, radio, tipo);
    }
    
    /**
     * Elimina un animal del ecosistema
     */
    public static void eliminarAnimal(Animal animal) {
        instancia.eliminarSer(animal);
        MemoryManager.obtenerInstancia().devolverEntidad(animal);
    }
    
    /**
     * Elimina una planta del ecosistema
     */
    public static void eliminarPlanta(Planta planta) {
        instancia.eliminarSer(planta);
        MemoryManager.obtenerInstancia().devolverEntidad(planta);
    }
} // class
