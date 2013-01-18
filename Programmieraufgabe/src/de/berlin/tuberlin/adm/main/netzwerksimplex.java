package de.berlin.tuberlin.adm.main;

import java.io.IOException;

import de.berlin.tuberlin.adm.algorithms.SimplexAlgorithm;
import de.berlin.tuberlin.adm.graph.Vertex;
import de.berlin.tuberlin.adm.input.Input;
import de.berlin.tuberlin.adm.output.Output;

public class netzwerksimplex {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		//args[0] ist Eingabedatei
		//mit Input sollen die Daten eingelesen werden und ein Graph zur�ck gegeben werden
		Input input = new Input("src/InputData/gte_bad.200");
		System.out.println("Time in ms: " + input.getStopwatch().getElapsedTime());

		
		//Ausf�hren von der Klasse SimplexAlgorithmus. ein Graph soll eingegeben werden und berechnet werden.
		System.out.println(input.getGraph().toString());
		SimplexAlgorithm simplex = new SimplexAlgorithm(input.getGraph());
		simplex.startOptimierung();
		System.out.println(input.getGraph().toString());

		System.out.println("Time in ms: " + simplex.getStopwatch().getElapsedTime());

		//args[1] ist Ausgabedatei und bekommt von SimplexAlgorithmus das Ergebnis, welches in eine Ausgabedatei geschrieben werden soll.
		Output output = new Output("src/OutputData", simplex.getGraph());
	}

}
