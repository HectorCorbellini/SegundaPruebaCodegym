package com.codegym.modulo2;

import java.io.BufferedWriter;

/*
cd "CODEGYM linux"/JuegoIsla/out/artifacts/juegoisla_jar/
java -jar JuegoVidaMio.jar
*/
public class Main {
	public static void main(String[] args)  {
		Creacion.iniciarListasDeSeres();
		BufferedWriter bufferCSV = Salida.iniciarCSV();
		int momentos = 1;
		boolean vidaEquilibrada = true;
		while (momentos < Ajustes.maxTiempo && vidaEquilibrada) {
		    Tablero.colocarLosSeres();
			Salida.hacerEstadistica(momentos, bufferCSV);
			Ajustes.transcurreEsteMomento();
			vidaEquilibrada = Vida.recrearla();
			momentos++;
		} // while
		Salida.accionesDeCierre(momentos, bufferCSV);
	} // main

} // class
