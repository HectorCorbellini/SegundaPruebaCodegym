package isla;

class Planta extends Ser {
    public Planta(int x, int y, char dibujo) {
        super(x, y, dibujo);
    }
    
    /**
     * Método estático para procesar todas las plantas
     * Aumenta la energía de todas las plantas vivas
     */
    static void procesarTodasLasPlantas() {
        for (Planta planta : GestorPoblacion.plantas) {
            planta.setEnergia(planta.getEnergia() + 1);
            if (planta.getEnergia() > Ajustes.ENERGIA_MAXIMA_PLANTA) {
                planta.setEnergia(Ajustes.ENERGIA_MAXIMA_PLANTA);
            }
        }
    }
}