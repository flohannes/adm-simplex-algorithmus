package de.berlin.tuberlin.adm.main;

import java.io.IOException;

import de.berlin.tuberlin.adm.algorithms.SimplexAlgorithm;
import de.berlin.tuberlin.adm.graph.Vertex;
import de.berlin.tuberlin.adm.input.Input;

public class netzwerksimplex {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		//args[0] ist Eingabedatei
		//mit Input sollen die Daten eingelesen werden und ein Graph zurück gegeben werden
		Input input = new Input("src/InputData/gte_bad.200");
		
		
		//Ausführen von der Klasse SimplexAlgorithmus. ein Graph soll eingegeben werden und berechnet werden.
//		SimplexAlgorithm simplex = new SimplexAlgorithm(input.getGraph());
//		System.out.println("Time in ms: " + simplex.getStopwatch().getElapsedTime());
		
		//args[1] ist Ausgabedatei und bekommt von SimplexAlgorithmus das Ergebnis, welches in eine Ausgabedatei geschrieben werden soll.
	}

}
