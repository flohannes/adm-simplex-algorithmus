package de.berlin.tuberlin.adm.algorithms;

import java.io.IOException;
import java.util.ArrayList;

import de.berlin.tuberlin.adm.graph.Arc;
import de.berlin.tuberlin.adm.graph.Graph;
import de.berlin.tuberlin.adm.graph.Vertex;
import de.berlin.tuberlin.adm.input.Input;

public class SimplexAlgorithm {

	private Graph g;
	private Stopwatch stopwatch;
	private ArrayList<Arc> T;
	private ArrayList<Arc> L;
	private ArrayList<Arc> U;
	
	private int NumberOfNodes;
	
	private int[] p;
	private int[] d;
	private int[] s;
	
	//für die oberen Kapazitäten der neun Kanten
	final int inf = Integer.MAX_VALUE;

	public SimplexAlgorithm(Graph g) {
		this.g = g;
		this.NumberOfNodes = g.getVertices().size() +1; //mit Knoten k
		this.p = new int[NumberOfNodes];
		this.d = new int[NumberOfNodes];
		this.s = new int[NumberOfNodes];

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
		L = (ArrayList<Arc>) g.getArcs().clone();
		T = new ArrayList<Arc>();
		U = new ArrayList<Arc>(); //Bleibt erstmal Leer
		
		//V' = V vereinigt k
		Vertex k = new Vertex(g.getVertices().size() + 1);
		k.setFlow(0);
		g.addVertex(k);
		
		//A'
		int nettoB = 0;
		int maxCost = Integer.MIN_VALUE;
		for(Vertex v : g.getVertices()){
			if(!v.equals(k)){ //Abfrage damit k nicht mitüberprüft wird
				nettoB = v.getFlow();
				for(Arc a : v.getDeltaPlus()){
						nettoB = nettoB + a.getLow();
						if(maxCost<a.getCost())
							maxCost = a.getCost();	
				}
				for( Arc a : v.getDeltaMinus()){
						nettoB = nettoB - a.getLow();
						if(maxCost<a.getCost())
							maxCost = a.getCost();	
				}
			
				if(nettoB < 0){
					Arc a = new Arc(v,k);
					a.setLow(0);
					a.setCap(inf);
					int M = (int) (1+(0.5 * (g.getVertices().size()-1)) * maxCost);
					a.setCost(M);
					a.setFlowX(-nettoB);	//Fluss x bestimmen
					g.addArc(a);
					T.add(a.clone());
				}
				else{
					Arc a = new Arc(k,v);
					a.setLow(0);
					a.setCap(inf);
					int M = (int) (1+(0.5 * (g.getVertices().size()-1)) * maxCost);
					a.setCost(M);
					a.setFlowX(nettoB);		//Fluss x bestimmen
					g.addArc(a);
					T.add(a.clone());
				}
			}
		}
		
		//an i-ter Stelle steht Knoten mit ID i+1
		for( int i=0 ; i< NumberOfNodes ; i++){
			if(i == NumberOfNodes-1){
				p[i] = -1;	//k ist die Wurzel
				d[i] = 1;
				s[i] = 2;
			}else{
				p[i] = NumberOfNodes;
				d[i] = 2;
				if( i==0) s[i] = NumberOfNodes;
				else s[i] =i+2;
			}
		}
		
		//Knotenpreise und reduzierte Kosten bestimmen
	}

	
	/*
	 * ToDo: 
	 * 1. Initalisierung ein wenig fehlt noch, aber fast fertig. kann schonmal auf fehler überprüft werden.
	 * 2. Berechnung der Knotenpreise 
	 * 3. Optimalitätstest 
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
	
	public Graph getG() {
		return g;
	}
	
	public static void main(String[] args) {
		
		try {
			Input r = new Input( "src/InputData/test");
//			System.out.println("Time for readin ms: " + r.getStopwatch().getElapsedTime());
			SimplexAlgorithm sim = new SimplexAlgorithm(r.getGraph());
			sim.initialize();
			System.out.println(sim.getG().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
