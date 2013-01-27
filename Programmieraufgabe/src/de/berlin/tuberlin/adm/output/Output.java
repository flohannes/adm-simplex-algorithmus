package de.berlin.tuberlin.adm.output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import de.berlin.tuberlin.adm.graph.Arc;
import de.berlin.tuberlin.adm.graph.Graph;

public class Output {
	
	private final String eol = System.getProperty("line.separator");

	public Output(String path, Graph graph, int gesamtkosten) throws IOException{
		FileWriter fstream;
		fstream = new FileWriter(path + "/Ergebnis" + ".txt");
		BufferedWriter out = new BufferedWriter(fstream);
		
		out.write("Es folgen die Ergebnisse:" + eol);
		out.write(gesamtkosten + eol);
		//minimale Kosten ausgeben
		//weg angeben, zur zeit noch alle Kanten ausgaben:
		for(Arc a : graph.getArcs()){
			if(!(a.getTail() == null || a.getHead() == null || a==null )){
				out.write(a.getTail().getId() + " " + a.getHead().getId() + " " + a.getFlowX() + eol);
			}
		}	
		
		out.close();
	}

}
