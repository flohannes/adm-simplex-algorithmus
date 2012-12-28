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
		//graph = new Graph();
		BufferedReader in = new BufferedReader(new FileReader(path));
		String zeile = null;
		zeile = in.readLine();
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
				//System.out.println(Integer.parseInt(inputString[1]) + ";" + Integer.parseInt(inputString[2]) );
				graph.getVertexById(Integer.parseInt(inputString[1])).setFlow(Integer.parseInt(inputString[2]));

			}
			else if(inputString[0].startsWith("a")){
				Arc a = new Arc(graph.getVertexById(Integer.parseInt(inputString[1])),graph.getVertexById(Integer.parseInt(inputString[2])));
				a.setLow(Integer.parseInt(inputString[3]));
				a.setCap(Integer.parseInt(inputString[4]));
				a.setCost(Integer.parseInt(inputString[5]));
				// add a
				//System.out.println(a.toString());
				graph.addArc(a);
			}

		}
		in.close();
	}

	public Graph getGraph() {
		return graph;
	}

	
	
	public static void main(String[] args){
		
		try {
			Input r = new Input( "src/InputData/test");
//			Vertex v = r.getGraph().getVertexById(27);
			System.out.println(r.getGraph().toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
