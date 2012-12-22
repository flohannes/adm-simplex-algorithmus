package de.berlin.tuberlin.adm.algorithms;

import de.berlin.tuberlin.adm.graph.Graph;
import de.berlin.tuberlin.adm.graph.Vertex;

public class SimplexAlgorithm {

	private Graph g;
	private Stopwatch stopwatch;

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
		//V' = V vereinigt k
		Vertex k = new Vertex(g.getVertices().size() + 1);
		k.setFlow(0);
		g.addVertex(k);
		
		//A'
		
	}

	/*
	 * ToDo: 
	 * 1. Initalisierung 
	 * 2. Berechnung der Knotenpreise 
	 * 3. Optimalitätstest 
	 * 4. Pricing 
	 * 5. Augmentieren 
	 * 6. Update
	 * 
	 * am besten alles in jeweils eigene Methoden schreiben und von
	 * startOptimierung aufrufen lassen. für besser berechnungen sollten zB
	 * methoden in der graphenklasse hinzugefügt werden. (wird sicherlich
	 * hilfreich sein)
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
