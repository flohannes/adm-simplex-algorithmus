package de.berlin.tuberlin.adm.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import de.berlin.tuberlin.adm.graph.Arc;
import de.berlin.tuberlin.adm.graph.Graph;
import de.berlin.tuberlin.adm.graph.Vertex;

public class Input {
	
	private Graph graph;
	

	// Hier müsste mit einem InputStreamReader ein Datensatz eingelesen werden.
	// Dabei muss zeile für zeile Daten übernommen werden und dann ein graph
	// daraus aufgebaut werden.

	public Input(String path) throws IOException{
		graph = new Graph();
		BufferedReader in = new BufferedReader(new FileReader(path));
		String zeile = null;
		zeile = in.readLine();
		String[] inputString;
		while ((zeile = in.readLine()) != null) {
			inputString = zeile.split(" ");
			if(inputString[0].startsWith("n")){			
				Vertex v = new Vertex(Integer.parseInt(inputString[1]));
				v.setFlow(Integer.parseInt(inputString[2]));
				graph.addVertex(v);
			}
			else if(inputString[0].startsWith("a")){
				Arc a = new Arc(graph.getVertexById(Integer.parseInt(inputString[1])),graph.getVertexById(Integer.parseInt(inputString[2])));
				a.setLow(Integer.parseInt(inputString[3]));
				a.setCap(Integer.parseInt(inputString[4]));
				a.setCost(Integer.parseInt(inputString[5]));
			}

		}
		in.close();
	}

	public Graph getGraph() {
		return graph;
	}

}
