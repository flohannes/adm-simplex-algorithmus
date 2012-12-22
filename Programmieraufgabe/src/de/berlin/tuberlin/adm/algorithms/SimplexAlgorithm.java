package de.berlin.tuberlin.adm.algorithms;

import java.util.ArrayList;

import de.berlin.tuberlin.adm.graph.Arc;
import de.berlin.tuberlin.adm.graph.Graph;
import de.berlin.tuberlin.adm.graph.Vertex;

public class SimplexAlgorithm {

	private Graph g;
	private Stopwatch stopwatch;
	private ArrayList<Arc> T;
	private ArrayList<Arc> L;
	private ArrayList<Arc> U;

	public SimplexAlgorithm(Graph g) {
		this.g = g;
		this.stopwatch = new Stopwatch();
	}

	public void startOptimierung() {
		stopwatch.start();
		initialize();
		//Hier kommen die restlichen Methoden hin
		//...
		
		stopwatch.stop();
	}
	
	/**
	 * Step 1: Initalisierung
	 */
	public void initialize(){
		T = (ArrayList<Arc>) g.getArcs().clone();
		L = new ArrayList<Arc>();
		U = new ArrayList<Arc>(); //Bleibt erstmal Leer
		
		//V' = V vereinigt k
		Vertex k = new Vertex(g.getVertices().size() + 1);
		k.setFlow(0);
		g.addVertex(k);
		
		//A'
		int nettoB = 0;
		int maxCost = Integer.MIN_VALUE;
		for(Vertex v : g.getVertices()){
			if(!v.equals(k)){ //Abfrage damit k nicht mit�berpr�ft wird
				nettoB = v.getFlow();
				for(Arc a : g.getArcs()){
					if(a.getTail().equals(v)){
						nettoB = nettoB + a.getLow();
						if(maxCost<a.getCost())
							maxCost = a.getCost();
					}
					else if(a.getHead().equals(v)){
						nettoB = nettoB - a.getLow();
						if(maxCost<a.getCost())
							maxCost = a.getCost();
					}
				}
			
			
				if(nettoB < 0){
					Arc a = new Arc(k,v);
					a.setLow(0);
					a.setCap(nettoB + 1);
					int M = (int) (1+(0.5 * (g.getVertices().size()-1)) * maxCost);
					a.setCost(M);
					L.add(a.clone());
				}
				else{
					Arc a = new Arc(v,k);
					a.setLow(0);
					a.setCap(nettoB + 1);
					int M = (int) (1+(0.5 * (g.getVertices().size()-1)) * maxCost);
					a.setCost(M);
					L.add(a.clone());
				}
			}
		}
		
		//Fluss x ermitteln
	}

	
	/*
	 * ToDo: 
	 * 1. Initalisierung ein wenig fehlt noch, aber fast fertig. kann schonmal auf fehler �berpr�ft werden.
	 * 2. Berechnung der Knotenpreise 
	 * 3. Optimalit�tstest 
	 * 4. Pricing 
	 * 5. Augmentieren 
	 * 6. Update
	 * 
	 * am besten alles in jeweils eigene Methoden schreiben und von
	 * startOptimierung aufrufen lassen. 
	 * 
	 * Eine Stopwatch Klasse habe ich schon fertig geschrieben. diese kann man
	 * am anfang der berechnung starten und am ende stoppen und dann die Zeit
	 * ausgeben lassen, wie schnell berechnet wurde. Denke, dass ist ein cooler
	 * zusatz.
	 */
	
	public Stopwatch getStopwatch(){
		return stopwatch;
	}
}
