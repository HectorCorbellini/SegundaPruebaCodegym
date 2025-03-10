package isla;

/**
 * Interface for objects that can be reset and reused in the memory pool.
 */
public interface Resettable {
    /**
     * Reinicia el estado del objeto para su reutilización.
     */
    void reiniciar();
}
