package de.berlin.tuberlin.adm.algorithms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.berlin.tuberlin.adm.graph.Arc;
import de.berlin.tuberlin.adm.graph.Graph;
import de.berlin.tuberlin.adm.graph.Vertex;
import de.berlin.tuberlin.adm.input.Input;

public class SimplexAlgorithm {

	private Graph g;
	private Stopwatch stopwatch;
	public ArrayList<Arc> T;
	public ArrayList<Arc> L;
	public ArrayList<Arc> U;
	
	private int NumberOfNodes;
	
	private int[] p;
	private int[] d;
	private int[] s;
	
	//fuer die oberen Kapazitaeten der neun Kanten
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
		this.augmentieren(this.optimalitaetstest());
		stopwatch.stop();
	}
	
	/**
	 * Step 1: Initalisierung
	 */
	public void initialize(){
		L = (ArrayList<Arc>) g.getArcs(); // Wir schreiben erstmal alle Boegen in L
		T = new ArrayList<Arc>(); 
		U = new ArrayList<Arc>(); //Bleibt erstmal Leer
		
		int M = (int) (1+(0.5 * (g.getVertices().size())) * g.getMaxCost());  // M entsprechend (7.27)
		
		//V' = V vereinigt k
		Vertex k = new Vertex(g.getVertices().size() + 1);
		k.setFlow(0);
		g.addVertex(k);
		
		//A'
		int nettoB = 0;
		for(Vertex v : g.getVertices()){
			if(!v.equals(k)){ //Abfrage damit k nicht mitueberprueft wird
				nettoB = v.getFlow(); // Nettobedarf entsprechend (7.26) 
				for(Arc a : v.getDeltaPlus()){
						nettoB = nettoB + a.getLow();	
				}
				for( Arc a : v.getDeltaMinus()){
						nettoB = nettoB - a.getLow();
						
				}
			
				if(nettoB < 0){ // Hinzufuegen von Boegen 
					Arc a = new Arc(v,k);
					a.setLow(0);
					a.setCap(inf);
//					int M = (int) (1+(0.5 * (g.getVertices().size()-1)) * g.getMaxCost());  // M entsprechend (7.27)
					a.setCost(M);
					a.setFlowX(-nettoB);	//Fluss x bestimmen
					g.addArc(a);
					T.add(a);
				}
				else{
					Arc a = new Arc(k,v);
					a.setLow(0);
					a.setCap(inf);
//					int M = (int) (1+(0.5 * (g.getVertices().size()-1)) * g.getMaxCost()); // M entsprechend (7.27)
					a.setCost(M);
					a.setFlowX(nettoB);		//Fluss x bestimmen
					g.addArc(a);
					T.add(a);
				}
			}
		}
		
		//an i-ter Stelle steht Knoten mit ID i+1
		for( int i=0 ; i< NumberOfNodes ; i++){ // Initialisierung des Baumes mit Wurzel k, alle anderen Knoten Kind von k
			if(i == NumberOfNodes-1){
				p[i] = -1;	//k ist die Wurzel (p = Predecessor)
				d[i] = 1; // d = Depth
				s[i] = 2; // s = successor
			}else{
				p[i] = NumberOfNodes;
				d[i] = 2;
				if( i==0) s[i] = NumberOfNodes;
				else s[i] =i+2;
			}
		}
		
		//Knotenpreise und reduzierte Kosten in T
		k.setPrice(0);
		for( Arc a : k.getDeltaPlus()){
//			a.getHead().setPrice(a.getCost()); // immer M
//			a.setReducedCost(a.getCost() - a.getHead().getPrice()); // immer 0
			a.getHead().setPrice(M); // immer MaxCost
			a.setReducedCost(0);
		}
		for(Arc a : k.getDeltaMinus()){
//			a.getTail().setPrice(-a.getCost()); // immer -M
//			a.setReducedCost(a.getCost() + a.getTail().getPrice()); // immer 0
			a.getTail().setPrice(-M);
			a.setReducedCost(0);
		}
		//reduzierte Kosten in L
		for(Arc a : L){
			a.setReducedCost(a.getCost() + a.getTail().getPrice() - a.getHead().getPrice());
		}
		
	}
	
	private Arc optimalitaetstest(){
		for(Arc a : L){
			if(a.getReducedCost() < 0){
				L.remove(a);
				return a;
			}
		}
		for(Arc a : U){
			if(a.getReducedCost() > 0){
				U.remove(a);
				return a;
			}
		}
		return null;
	}

	
	private void augmentieren(Arc e){
		System.out.println(e.getTail().getId()+" nach "+ e.getHead().getId());
		T.add(e);
		List<Vertex> u = new ArrayList<Vertex>();
		List<Vertex> v = new ArrayList<Vertex>();
		u.add(e.getTail());
		v.add(e.getHead());
		
		int maxC = Integer.MIN_VALUE;
		if(d[u.get(0).getId()-1] > d[v.get(0).getId()-1]){
			maxC = Math.abs(e.getFlowX() - e.getCap());
			int i = 0;
			int j = 0;
			while(!(u.get(i).equals(v.get(j)))){ //Kreis rekonstruieren
				if(this.d[u.get(i).getId()-1] != this.d[v.get(j).getId()-1]){
					u.add(g.getVertexById(p[u.get(i).getId()-1]));
					i++;
				}
				else{
					u.add(g.getVertexById(p[u.get(i).getId()-1]));
					i++;
					v.add(g.getVertexById(p[v.get(j).getId()-1]));
					j++;
				}
			}
			v.remove(v.size()-1); //wird geloescht fuer den Weg	
		}else{
			maxC = Math.abs(e.getFlowX() - e.getCap());
			int i = 0;
			int j = 0;
			while(!(u.get(i).equals(v.get(j)))){ //Kreis rekonstruieren
				if(this.d[u.get(i).getId()-1] != this.d[v.get(j).getId()-1]){
					v.add(g.getVertexById(p[v.get(j).getId()-1]));
					j++;
				}
				else{
					u.add(g.getVertexById(p[u.get(i).getId()-1]));
					i++;
					v.add(g.getVertexById(p[v.get(j).getId()-1]));
					j++;
				}
			}
			v.remove(v.size()-1); //wird geloescht fuer den Weg
		}
		
		
		if(e.getReducedCost() < 0){//e ist aus L
			for(int p = 0; p<v.size()-1; p++){ //maxC finden. Weg von v0 bis vn
				Arc a = v.get(p).getArc(v.get(p+1));
				if(a.getTail().equals(v.get(p))){ //Vorwaertsbogen
					if(maxC > a.getCap() - a.getFlowX()){
						maxC = a.getCap() - a.getFlowX();
					}
				}
				else{ //Rueckwaertsbogen
					if(maxC > a.getFlowX() - a.getLow()){
						maxC = a.getFlowX() - a.getLow();
					}
				}
			}
			for(int p = 0; p<u.size()-1; p++){ //weiterhin maxC finden. Weg von u0 bis un durchlafen
				Arc a = u.get(p).getArc(u.get(p+1));
				if(a.getHead().equals(u.get(p))){
					if(maxC > a.getCap() - a.getFlowX()){
						maxC = a.getCap() - a.getFlowX();
					}
				}
				else{
					if(maxC > a.getFlowX() - a.getLow()){
						maxC = a.getFlowX() - a.getLow();
					}
				}
			}
			Arc lastA = u.get(u.size()-1).getArc(v.get(v.size()-1)); //letzten Weg im Kreis zwischen un und vn
			if(lastA.getHead().equals(u.get(u.size()-1))){
				if(maxC > lastA.getCap() - lastA.getFlowX()){
					maxC = lastA.getCap() - lastA.getFlowX();
				}
			}
			else{
				if(maxC > lastA.getFlowX() - lastA.getLow()){
					maxC = lastA.getFlowX() - lastA.getLow();
				}
			}
			
		}
		else{// e ist aus U
			for(int p = 0; p<v.size()-1; p++){ //maxC finden. Weg von v0 bis vn
				Arc a = v.get(p).getArc(v.get(p+1));
				if(a.getTail().equals(v.get(p))){ //Vorwaertsbogen
					if(maxC > a.getFlowX()- a.getLow()){
						maxC = a.getFlowX()- a.getLow();
					}
				}
				else{ //Rueckwaertsbogen
					if(maxC > a.getCap() - a.getFlowX()){
						maxC = a.getCap() - a.getFlowX();
					}
				}
			}
			for(int p = 0; p<u.size()-1; p++){ //weiterhin maxC finden. Weg von u0 bis un durchlafen
				Arc a = u.get(p).getArc(u.get(p+1));
				if(a.getHead().equals(u.get(p))){
					if(maxC > a.getFlowX() - a.getLow()){
						maxC = a.getFlowX() - a.getLow();
					}
				}
				else{
					if(maxC > a.getCap() - a.getFlowX()){
						maxC = a.getCap() - a.getFlowX();
					}
				}
			}
			Arc lastA = u.get(u.size()-1).getArc(v.get(v.size()-1)); //letzten Weg im Kreis zwischen un und vn
			if(lastA.getHead().equals(u.get(u.size()-1))){
				if(maxC > lastA.getFlowX() - lastA.getLow()){
					maxC = lastA.getFlowX() - lastA.getLow();
				}
			}
			else{
				if(maxC > lastA.getCap() - lastA.getFlowX()){
					maxC = lastA.getCap() - lastA.getFlowX();
				}
			}
		}
		
		System.out.println("max"+maxC);
		
		/**
		 * Augmentieren: 
		 * Nochmal den Kreis durchgehen und schauen obs in U oder L ist.
		 */
		if(e.getReducedCost() < 0){ //e ist aus L
			e.setFlowX(e.getFlowX() + maxC); //Fluss auf e erhoehen
			
			for(int p = 0; p<v.size()-1; p++){ //maxC finden. Weg von v0 bis vn
				Arc a = v.get(p).getArc(v.get(p+1));
				if(a.getTail().equals(v.get(p))){ //Vorwaertsbogen
					a.setFlowX(a.getFlowX()+maxC);
				}
				else{ //Rueckwaertsbogen
					a.setFlowX(a.getFlowX()-maxC);
				}
			}
			for(int p = 0; p<u.size()-1; p++){ //weiterhin maxC finden. Weg von u0 bis un durchlafen
				Arc a = u.get(p).getArc(u.get(p+1));
				if(a.getHead().equals(u.get(p))){
					a.setFlowX(a.getFlowX()+maxC);
				}
				else{
					a.setFlowX(a.getFlowX()-maxC);
				}
			}
			Arc lastA = u.get(u.size()-1).getArc(v.get(v.size()-1)); //letzten Weg im Kreis zwischen un und vn
			if(lastA.getHead().equals(u.get(u.size()-1))){
				lastA.setFlowX(lastA.getFlowX()+maxC);
			}
			else{
				lastA.setFlowX(lastA.getFlowX()-maxC);
			}
			
		}
		else{// e ist aus U
			e.setFlowX(e.getFlowX() - maxC); //Fluss auf e verringern

			for(int p = 0; p<v.size()-1; p++){ //maxC finden. Weg von v0 bis vn
				Arc a = v.get(p).getArc(v.get(p+1));
				if(a.getTail().equals(v.get(p))){ //Vorwaertsbogen
					a.setFlowX(a.getFlowX()-maxC);
				}
				else{ //Rueckwaertsbogen
					a.setFlowX(a.getFlowX()+maxC);
				}
			}
			for(int p = 0; p<u.size()-1; p++){ //weiterhin maxC finden. Weg von u0 bis un durchlafen
				Arc a = u.get(p).getArc(u.get(p+1));
				if(a.getHead().equals(u.get(p))){
					a.setFlowX(a.getFlowX()-maxC);
				}
				else{
					a.setFlowX(a.getFlowX()+maxC);
				}
			}
			Arc lastA = u.get(u.size()-1).getArc(v.get(v.size()-1)); //letzten Weg im Kreis zwischen un und vn
			if(lastA.getHead().equals(u.get(u.size()-1))){
				lastA.setFlowX(lastA.getFlowX()-maxC);
			}
			else{
				lastA.setFlowX(lastA.getFlowX()+maxC);
			}
		}
		
		
		
		/*
		 * Baumloesung aktualisieren
		 */
		Arc f = this.findF(u, v, e);
		
		T.remove(f);
		T.add(e);
		
		L.remove(e);
		if(f.getFlowX() == f.getLow())
			L.add(f);
		
		U.remove(e);
		if(f.getFlowX() == f.getCap())
			U.add(f);
		
		//p,d und s anpassen und Knotenpreise
	}

	private Arc findF(List<Vertex> u, List<Vertex> v, Arc e){
		for(int i =0; i< v.size()-1;i++){
			Arc a = v.get(i).getArc(v.get(i+1));
			if(a.getFlowX()==a.getCap() || a.getFlowX() == a.getLow())
				return a;
		}
		Arc lastA = u.get(u.size()-1).getArc(v.get(v.size()-1)); //letzten Weg im Kreis zwischen un und vn
		if(lastA.getFlowX()==lastA.getCap() || lastA.getFlowX() == lastA.getLow())
			return lastA;			
		
		for(int i = u.size(); i>0;i--){
			Arc a = u.get(i-1).getArc(u.get(i));
			if(a.getFlowX()==a.getCap() || a.getFlowX() == a.getLow())
				return a;
		}
		
		return e;
	}
	
	
	private void updateKnotenpreise(){
		
	}
	
	private void updateS(){
		
	}
	
	private void updateP(){
		
	}
	
	private void updateD(){
		
	}
	/*
	 * ToDo: 
	 * 1. Initalisierung ein wenig fehlt noch, aber fast fertig. kann schonmal auf fehler ueberprueft werden.
	 * 2. Berechnung der Knotenpreise 
	 * 3. Optimalitaetstest 
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
	
	public Graph getGraph() {
		return g;
	}
	
	public static void main(String[] args) {
		
		try {
			Input r = new Input( "src/InputData/test1");
			SimplexAlgorithm sim = new SimplexAlgorithm(r.getGraph());
			System.out.println(sim.getGraph().toString());

			
			//sim.initialize();
			sim.startOptimierung();
			
			System.out.println(sim.getGraph().toString());
//			sim.startOptimierung();
//			System.out.println(sim.getGraph().toString());
//			System.out.println("Time for readin ms: " + r.getStopwatch().getElapsedTime());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
