package de.berlin.tuberlin.adm.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import de.berlin.tuberlin.adm.algorithms.Stopwatch;
import de.berlin.tuberlin.adm.graph.Arc;
import de.berlin.tuberlin.adm.graph.Graph;

public class Input {
	
	private Graph graph;
	private Stopwatch stopwatch;

	// Hier muesste mit einem InputStreamReader ein Datensatz eingelesen werden.
	// Dabei muss zeile fuer zeile Daten uebernommen werden und dann ein graph
	// daraus aufgebaut werden.

	public Input(String path) throws IOException{
		stopwatch = new Stopwatch();
		stopwatch.start();
		int maxCost = Integer.MIN_VALUE;
		//graph = new Graph();
		BufferedReader in = new BufferedReader(new FileReader(path));
		String zeile = null;
		//zeile = in.readLine();
		String[] inputString;
		while ((zeile = in.readLine()) != null) {
			inputString = zeile.split(" ");
			
			if(inputString[0].startsWith("p")){
				graph = new Graph(Integer.parseInt(inputString[2]) ,
									Integer.parseInt(inputString[3]));
			}
			else if(inputString[0].startsWith("n")){			
				//Vertex v = new Vertex(Integer.parseInt(inputString[1]));
				//v.setFlow(Integer.parseInt(inputString[2]));
				//graph.addVertex(v);
				graph.getVertexById(Integer.parseInt(inputString[1])).setFlow(-Integer.parseInt(inputString[2]));

			}
			else if(inputString[0].startsWith("a")){
				Arc a = new Arc(graph.getVertexById(Integer.parseInt(inputString[1])),graph.getVertexById(Integer.parseInt(inputString[2])));
				a.setLow(Integer.parseInt(inputString[3]));
				a.setCap(Integer.parseInt(inputString[4]));
				a.setCost(Integer.parseInt(inputString[5]));
				// add a
				//gehoert quasi zum Initialisieren von Simplex
				a.setFlowX(Integer.parseInt(inputString[3])); //setze Fluss auf min cap
				graph.addArc(a);
				
				if(maxCost< Math.abs(a.getCost()))
					maxCost = Math.abs(a.getCost());
			}

		}
		graph.setMaxCost(maxCost);
		in.close();
		
		stopwatch.stop();
	}

	public Graph getGraph() {
		return graph;
	}
	
	public Stopwatch getStopwatch(){
		return stopwatch;
	}

	
	
	/**
	 * Tests for class Input
	 * @param args
	 */
	public static void main(String[] args){
		
		try {
			Input r = new Input( "src/InputData/test");
			System.out.println("Time for readin ms: " + r.getStopwatch().getElapsedTime());
//			Vertex v = r.getGraph().getVertexById(27);
			System.out.println(r.getGraph().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
