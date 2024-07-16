package com.codegym.modulo2;

import java.io.BufferedWriter;
/*
cd "CODEGYM linux"/JuegoIsla/out/artifacts/juegoisla_jar/
java -jar JuegoVidaMio.jar
*/
public class Main {
	public static void main(String[] args) throws Exception {
  // Tablero.cargarloVacio();
		Lista.crearListasDeSeres();
  // 	Lista.agregarLosSeresAlTablero();
		BufferedWriter buffParaGuardar = Salida.iniciarCSV();
		int veces = 0;
		boolean vidaEquilibrada = true;
		String estadistica;
		while (veces < Ajustes.maxTiempo && vidaEquilibrada) {
		    Tablero.cargarloVacio();
		    Lista.agregarLosSeresAlTablero();
		  Pantalla.limpieza();
			 Tablero.mostrarlo();
			estadistica = devolverEstadistica(veces, Lista.animales.size(), Lista.plantas.size());
			Salida.guardarArchivoCSV(buffParaGuardar, estadistica);
			Ajustes.transcurreUnMomento();
			vidaEquilibrada = recrearVida();
			veces++;
		} // while
		buffParaGuardar.close();
	} // main

	static String devolverEstadistica(int ciclo, int animales, int plantas) {
		// llevarlo a mostrar archivo CSV
		System.out.println("Tiempo;Animales;Plantas;Nacimientos;Muertes;Eventos");
		System.out.println("  " + ciclo + "  " + animales + "  " + plantas + "  "
						   + Salida.nacimientos + "  " + Salida.muertes + "  " + Salida.eventos);
		String datos = ciclo + ";" + animales + ";" + plantas +
					   ";" + Salida.nacimientos + ";" + Salida.muertes + ";" + Salida.eventos;
		Salida.reiniciarEventos();
		Salida.reiniciarVariables();
		return datos;
	}  // method

	static boolean recrearVida()  {
		boolean hayVidaSuficiente = true;
		boolean hayDemasiadaVida = false;
		hayVidaSuficiente = moverVida();
		if (hayVidaSuficiente)
			actualizarVidaAEsteMomento();
		else
			Salida.evento("no hay vida suficiente");
		int topeDeAnimales = (Ajustes.largoTablero * Ajustes.altoTablero) - Lista.plantas.size();
		if (!(Lista.animales.size() < topeDeAnimales)) {
			hayDemasiadaVida = true;
			Salida.evento("vida supera el tablero");
		}  // if
		return hayVidaSuficiente && !hayDemasiadaVida;
	}  // method

	static boolean moverVida() {
		boolean hayVidaSuficiente;
		if (Lista.animales.size() > 0) {
			hayVidaSuficiente = true;
			moverUnaCeldaTodosLosAnimales();
		} // if
		else
			hayVidaSuficiente = false;
		return hayVidaSuficiente;
	}  // method
    
	static void actualizarVidaAEsteMomento() {
		Lista.aumentarUnidadEdadDeAnimales();
		Lista.matarAnimalesViejos();
		if (Lista.animales.size() > 0)
			Lista.matarAnimalesDesenergizados();
		Lista.matarPlantasDesenergizadas();
		if (Lista.plantas.size() > 0)
			Lista.aumentarUnidadEnergiaDePlantas();
	//	Tablero.cargarlo();  // seria cargrloVacio
		Lista.agregarLosSeresAlTablero();
	}  // method

	static void moverUnaCeldaTodosLosAnimales() {
		for (int lugarEnLista = 0; lugarEnLista < Lista.animales.size(); lugarEnLista++) {
			Animal animal = Animal.moverUnAnimal(lugarEnLista);
			if (animal.getEnergia() > 0) {
				Lista.consecuenciasDeUnMovimiento(animal);
				animal.setEnergia(animal.getEnergia() - 1);
			}  // if
		} // end for
	} // method

} // class
