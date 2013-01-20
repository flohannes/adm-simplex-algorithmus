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

	// fuer die oberen Kapazitaeten der neun Kanten
	final int inf = Integer.MAX_VALUE;

	public SimplexAlgorithm(Graph g) {
		this.g = g;
		this.NumberOfNodes = g.getVertices().size() + 1; // mit Knoten k
		this.p = new int[NumberOfNodes];
		this.d = new int[NumberOfNodes];
		this.s = new int[NumberOfNodes];

		this.stopwatch = new Stopwatch();
	}

	public void startOptimierung() {
		stopwatch.start();
		initialize();
		// Hier kommen die restlichen Methoden hin
		// ...
		for(int i = 0; i < 10; i++){
			Arc e = this.optimalitaetstest();
			System.out.println(this.toString());
			if(e==null){
				System.out.println("Anzahl Augmentierungsschritte:" + (i)); //Anzahl Augmentierungsschritte
				break;
			}
			this.augmentieren(e);	
		}
		stopwatch.stop();
	}

	/**
	 * Step 1: Initalisierung
	 */
	public void initialize() {
		L = new ArrayList<Arc>();
		for(Arc a : g.getArcs()){
			L.add(a);
		}
		
		
		//L = (ArrayList<Arc>) g.getArcs(); // Wir schreiben erstmal alle Boegen
											// in L
		T = new ArrayList<Arc>();
		U = new ArrayList<Arc>(); // Bleibt erstmal Leer

		int M = (int) (1 + (0.5 * (g.getVertices().size())) * g.getMaxCost()); // M
																				// entsprechend
																				// (7.27)

		// V' = V vereinigt k
		Vertex k = new Vertex(g.getVertices().size() + 1);
		k.setFlow(0);
		g.addVertex(k);

		// A'
		int nettoB = 0;
		for (Vertex v : g.getVertices()) {
			if (!v.equals(k)) { // Abfrage damit k nicht mitueberprueft wird
				nettoB = v.getFlow(); // Nettobedarf entsprechend (7.26)
				for (Arc a : v.getDeltaPlus()) {
					nettoB = nettoB + a.getLow();
				}
				for (Arc a : v.getDeltaMinus()) {
					nettoB = nettoB - a.getLow();

				}

				if (nettoB < 0) { // Hinzufuegen von Boegen
					Arc a = new Arc(v, k);
					a.setLow(0);
					a.setCap(inf);
					// int M = (int) (1+(0.5 * (g.getVertices().size()-1)) *
					// g.getMaxCost()); // M entsprechend (7.27)
					a.setCost(M);
					a.setFlowX(-nettoB); // Fluss x bestimmen
					g.addArc(a);
					T.add(a);
				} else {
					Arc a = new Arc(k, v);
					a.setLow(0);
					a.setCap(inf);
					// int M = (int) (1+(0.5 * (g.getVertices().size()-1)) *
					// g.getMaxCost()); // M entsprechend (7.27)
					a.setCost(M);
					a.setFlowX(nettoB); // Fluss x bestimmen
					g.addArc(a);
					T.add(a);
				}
			}
		}

		// an i-ter Stelle steht Knoten mit ID i+1
		for (int i = 0; i < NumberOfNodes; i++) { // Initialisierung des Baumes
													// mit Wurzel k, alle
													// anderen Knoten Kind von k
			if (i == NumberOfNodes - 1) {
				p[i] = -1; // k ist die Wurzel (p = Predecessor)
				d[i] = 1; // d = Depth
				s[i] = 1; // s = successor (in preorder)
			} else {
				p[i] = NumberOfNodes;
				d[i] = 2;
				s[i] = i + 2;
			}
		}

		// Knotenpreise und reduzierte Kosten in T
		k.setPrice(0);
		for (Arc a : k.getDeltaPlus()) {
			a.getHead().setPrice(M); // immer MaxCost
			a.setReducedCost(0);	//reduzierte kosten im baum immer 0
		}
		for (Arc a : k.getDeltaMinus()) {
			a.getTail().setPrice(-M); //Knotenpreis
			a.setReducedCost(0);	//reduzierte kosten im baum immer 0
		}
		// reduzierte Kosten in L
		for (Arc a : L) {
			a.setReducedCost(a.getCost() + a.getTail().getPrice()
					- a.getHead().getPrice());
		}
		
		

	}

	private Arc optimalitaetstest() {
		for (Arc a : L) {
			if (a.getReducedCost() < 0) {
				L.remove(a);
				return a;
			}
		}
		for (Arc a : U) {
			if (a.getReducedCost() > 0) {
				U.remove(a);
				return a;
			}
		}
		return null;
	}
	
	/**
	 * Gibt den max Wert zurück, um den auf dieser Kante augmentiert werden kann
	 * @param u erster Knoten, vorderer in der Liste
	 * @param v zweiter Knoten, hinterer in der Liste
	 * @param b true, falls u und v in aus der Liste u, sonst false
	 * @return max Wert
	 */
	private int calcAugValue ( Vertex u , Vertex v , boolean b , boolean isL){
		Arc tmp = u.getArc(v);
		
		if(isL){//e ist in L, d.h. wir wollen Fluss erhöhen(auf Vor-Knoten)
			if (b){//u und v sind aus u
				if(tmp.getHead().equals(u))	//Vorwaertskante
					return tmp.getCap() - tmp.getFlowX();
				else
					return tmp.getFlowX() - tmp.getLow();
					
			}else{ //u und v sind aus v
				if(tmp.getHead().equals(v))	//Vorwaertskante
					return tmp.getCap() - tmp.getFlowX();
				else 
					return tmp.getFlowX() - tmp.getLow();
			}
		}else{//e ist in U, d.h. wir wollen Fluss verringern( auf Vor_Knoten)
			if (b){//u und v sind aus u
				if(tmp.getHead().equals(u))	//Vorwaertskante
					return tmp.getFlowX() - tmp.getLow();
				else
					return tmp.getCap() - tmp.getFlowX();
					
			}else{ //u und v sind aus v
				if(tmp.getHead().equals(v))	//Vorwaertskante
					return tmp.getFlowX() - tmp.getLow();
				else 
					return tmp.getCap() - tmp.getFlowX();
			}
		}
		
	}
	

	private void augmentieren(Arc e) {
		if(e == null){
			System.out.println("e ist null");
		}
		else if(e.getTail() == null){
			System.out.println("e.tail ist null");
				
		}
		else if(e.getHead() == null){
			System.out.println("e.head ist null");
				
		}
		System.out.println(e.getTail().getId() + " nach " + e.getHead().getId());
		T.add(e);
		List<Vertex> u = new ArrayList<Vertex>();
		List<Vertex> v = new ArrayList<Vertex>();
		u.add(e.getTail());
		v.add(e.getHead());

		int maxC;
		boolean e_L;// true, falls e in L ist, false falls e in U
		if(e.getReducedCost()< 0){
			e_L = true;
			maxC = e.getCap() - e.getFlowX();
		}else{
			e_L = false;
			maxC = e.getFlowX() - e.getLow();
		}	
		int help=0;
		if (d[u.get(0).getId() - 1] > d[v.get(0).getId() - 1]) {
			int i = 0;
			int j = 0;
			while (!(u.get(i).equals(v.get(j)))) { // Kreis rekonstruieren
				if (this.d[u.get(i).getId() - 1] != this.d[v.get(j).getId() - 1]) {
					u.add(g.getVertexById(p[u.get(i).getId() - 1]));
					i++;
					
					//aktualisiere maxC
					help = calcAugValue(u.get(i-1), u.get(i), true , e_L);
					if(maxC > help)
						maxC = help;
					
				} else {
					u.add(g.getVertexById(p[u.get(i).getId() - 1]));
					i++;
					v.add(g.getVertexById(p[v.get(j).getId() - 1]));
					j++;
					
					//aktualisiere maxC
					help = calcAugValue(u.get(i-1), u.get(i), true , e_L);
					if(maxC > help)
						maxC = help;
					
					help = calcAugValue(v.get(j-1), v.get(j), false , e_L);
					if(maxC > help)
						maxC = help;
				}
				
				
			}
			v.remove(v.size() - 1); // wird geloescht fuer den Weg
		} else {
			int i = 0;
			int j = 0;
			while (!(u.get(i).equals(v.get(j)))) { // Kreis rekonstruieren
				if (this.d[u.get(i).getId() - 1] != this.d[v.get(j).getId() - 1]) {
					v.add(g.getVertexById(p[v.get(j).getId() - 1]));
					j++;
					
					//aktualisiere maxC
					help = calcAugValue(v.get(j-1), v.get(j), false , e_L);
					if(maxC > help)
						maxC = help;
					
				} else {
					u.add(g.getVertexById(p[u.get(i).getId() - 1]));
					i++;
					v.add(g.getVertexById(p[v.get(j).getId() - 1]));
					j++;
					
					//aktualisiere maxC
					help = calcAugValue(u.get(i-1), u.get(i), true , e_L);
					if(maxC > help)
						maxC = help;
					
					help = calcAugValue(v.get(j-1), v.get(j), false , e_L);
					if(maxC > help)
						maxC = help;
					
				}
			}
			v.remove(v.size() - 1); // wird geloescht fuer den Weg
									//dieser knoten ist bereits in u
		}
		
		
		//System.out.println("Hier ist maxC: "+maxC);

/*		if (e.getReducedCost() < 0) {// e ist aus L
			for (int p = 0; p < v.size() - 1; p++) { // maxC finden. Weg von v0
														// bis vn
				Arc a = v.get(p).getArc(v.get(p + 1));
				if (a.getTail().equals(v.get(p))) { // Vorwaertsbogen
					if (maxC > a.getCap() - a.getFlowX()) {
						maxC = a.getCap() - a.getFlowX();
					}
				} else { // Rueckwaertsbogen
					if (maxC > a.getFlowX() - a.getLow()) {
						maxC = a.getFlowX() - a.getLow();
					}
				}
			}
			for (int p = 0; p < u.size() - 1; p++) { // weiterhin maxC finden.
														// Weg von u0 bis un
														// durchlafen
				Arc a = u.get(p).getArc(u.get(p + 1));
				if (a.getHead().equals(u.get(p))) {
					if (maxC > a.getCap() - a.getFlowX()) {
						maxC = a.getCap() - a.getFlowX();
					}
				} else {
					if (maxC > a.getFlowX() - a.getLow()) {
						maxC = a.getFlowX() - a.getLow();
					}
				}
			}
			Arc lastA = u.get(u.size() - 1).getArc(v.get(v.size() - 1)); // letzten
																		
			if (lastA.getHead().equals(u.get(u.size() - 1))) {
				if (maxC > lastA.getCap() - lastA.getFlowX()) {
					maxC = lastA.getCap() - lastA.getFlowX();
				}
			} else {
				if (maxC > lastA.getFlowX() - lastA.getLow()) {
					maxC = lastA.getFlowX() - lastA.getLow();
				}
			}

		} else {// e ist aus U
			for (int p = 0; p < v.size() - 1; p++) { // maxC finden. Weg von v0
														// bis vn
				Arc a = v.get(p).getArc(v.get(p + 1));
				if (a.getTail().equals(v.get(p))) { // Vorwaertsbogen
					if (maxC > a.getFlowX() - a.getLow()) {
						maxC = a.getFlowX() - a.getLow();
					}
				} else { // Rueckwaertsbogen
					if (maxC > a.getCap() - a.getFlowX()) {
						maxC = a.getCap() - a.getFlowX();
					}
				}
			}
			for (int p = 0; p < u.size() - 1; p++) { // weiterhin maxC finden.
														// Weg von u0 bis un
														// durchlafen
				Arc a = u.get(p).getArc(u.get(p + 1));
				if (a.getHead().equals(u.get(p))) {
					if (maxC > a.getFlowX() - a.getLow()) {
						maxC = a.getFlowX() - a.getLow();
					}
				} else {
					if (maxC > a.getCap() - a.getFlowX()) {
						maxC = a.getCap() - a.getFlowX();
					}
				}
			}
			Arc lastA = u.get(u.size() - 1).getArc(v.get(v.size() - 1)); // letzten

			if (lastA.getHead().equals(u.get(u.size() - 1))) {
				if (maxC > lastA.getFlowX() - lastA.getLow()) {
					maxC = lastA.getFlowX() - lastA.getLow();
				}
			} else {
				if (maxC > lastA.getCap() - lastA.getFlowX()) {
					maxC = lastA.getCap() - lastA.getFlowX();
				}
			}
		}
		System.out.println("max" + maxC);
*/
		/**
		 * Augmentieren: Nochmal den Kreis durchgehen und schauen obs in U oder
		 * L ist.
		 */
		if (e.getReducedCost() < 0) { // e ist aus L
			e.setFlowX(e.getFlowX() + maxC); // Fluss auf e erhoehen

			for (int p = 0; p < v.size() - 1; p++) { // maxC finden. Weg von v0
														// bis vn
				Arc a = v.get(p).getArc(v.get(p + 1));
				if (a.getTail().equals(v.get(p))) { // Vorwaertsbogen
					a.setFlowX(a.getFlowX() + maxC);
				} else { // Rueckwaertsbogen
					a.setFlowX(a.getFlowX() - maxC);
				}
			}
			for (int p = 0; p < u.size() - 1; p++) { // weiterhin maxC finden.
														// Weg von u0 bis un
														// durchlafen
				Arc a = u.get(p).getArc(u.get(p + 1));
				if (a.getHead().equals(u.get(p))) {
					a.setFlowX(a.getFlowX() + maxC);
				} else {
					a.setFlowX(a.getFlowX() - maxC);
				}
			}
			Arc lastA = u.get(u.size() - 1).getArc(v.get(v.size() - 1)); // letzten
																			// Weg
																			// im
																			// Kreis
																			// zwischen
																			// un
																			// und
																			// vn
			if (lastA.getHead().equals(u.get(u.size() - 1))) {
				lastA.setFlowX(lastA.getFlowX() + maxC);
			} else {
				lastA.setFlowX(lastA.getFlowX() - maxC);
			}

		} else {// e ist aus U
			e.setFlowX(e.getFlowX() - maxC); // Fluss auf e verringern

			for (int p = 0; p < v.size() - 1; p++) { // maxC finden. Weg von v0
														// bis vn
				Arc a = v.get(p).getArc(v.get(p + 1));
				if (a.getTail().equals(v.get(p))) { // Vorwaertsbogen
					a.setFlowX(a.getFlowX() - maxC);
				} else { // Rueckwaertsbogen
					a.setFlowX(a.getFlowX() + maxC);
				}
			}
			for (int p = 0; p < u.size() - 1; p++) { // weiterhin maxC finden.
														// Weg von u0 bis un
														// durchlafen
				Arc a = u.get(p).getArc(u.get(p + 1));
				if (a.getHead().equals(u.get(p))) {
					a.setFlowX(a.getFlowX() - maxC);
				} else {
					a.setFlowX(a.getFlowX() + maxC);
				}
			}
			Arc lastA = u.get(u.size() - 1).getArc(v.get(v.size() - 1)); // letzten
																			// Weg
																			// im
																			// Kreis
																			// zwischen
																			// un
																			// und
																			// vn
			if (lastA.getHead().equals(u.get(u.size() - 1))) {
				lastA.setFlowX(lastA.getFlowX() - maxC);
			} else {
				lastA.setFlowX(lastA.getFlowX() + maxC);
			}
		}
		
		/*
		 * Baumloesung aktualisieren
		 */
		Arc f = this.findF(u, v, e);
		System.out.println("leaving arc: "+f.getTail().getId()+" nach "+f.getHead().getId());
		// update Knotenpreise
		if (!e.equals(f)) {
			//this.updateKnotenpreise(e, f); //TODO man muss vielleicht erst s updaten bevor man die knkotenpreise updatet!!!
			T.remove(f);
			//e.setReducedCost(0);
			T.add(e);
			
			L.remove(e);
			if (f.getFlowX() == f.getLow())
				L.add(f);

			U.remove(e);
			if (f.getFlowX() == f.getCap())
				U.add(f);
		
		
			// p,d und s anpassen
			this.updateS(e, f);
			if(f.getuORv() == 'v')
				this.updatePundD(e,f, v);
			else if(f.getuORv() == 'u')
				this.updatePundD(e,f, u);
			
		}
		//muss nach s.update gemacht werden glaub ich
		this.updateKnotenpreise(e, f);
		e.setReducedCost(0);

		for (Arc a : L) {
			a.setReducedCost(a.getCost() + a.getTail().getPrice()
					- a.getHead().getPrice());
		}
		for (Arc a : U) {
			a.setReducedCost(a.getCost() + a.getTail().getPrice()
					- a.getHead().getPrice());
		}
		
		//Knotenpreise ausgeben
		String knotenpreise= "Knotenpreise:\n";
		for( Vertex w : g.getVertices()){
			knotenpreise= knotenpreise + w.getId()+" :  "+ w.getPrice()+"\n";
		}
		System.out.println(knotenpreise);

		
		
		
	//gibt die reduzierten kosten in L aus
	 /* String test ="Red-Cost:\n";
		for(Arc s : L){
			test= test + s.getTail().getId()+" nach "+s.getHead().getId()+ " : "+ s.getReducedCost()+"\n";
		}
		System.out.println(test);
	*/
	}

	/*
	 * Helper fuer augmentieren() Findet Kante in V oder U, bei welcher die
	 * obere oder untere Kapazitaetsgrenze erreicht ist
	 */

	private Arc findF(List<Vertex> u, List<Vertex> v, Arc e) {
		for (int i = 0; i < v.size() - 1; i++) {
			Arc a = v.get(i).getArc(v.get(i + 1));
			if (a.getFlowX() == a.getCap() || a.getFlowX() == a.getLow()) {
				a.setuORv('v');
				return a;
			}
		}
		Arc lastA = u.get(u.size() - 1).getArc(v.get(v.size() - 1)); // letzten
																		// Weg
																		// im
																		// Kreis
																		// zwischen
																		// un
																		// und
																		// vn
		if (lastA.getFlowX() == lastA.getCap()
				|| lastA.getFlowX() == lastA.getLow()){
			lastA.setuORv('v');
			return lastA;
		}

		for (int i = u.size()-1; i > 0; i--) {
			Arc a = u.get(i - 1).getArc(u.get(i));
			if (a.getFlowX() == a.getCap() || a.getFlowX() == a.getLow()) {
				a.setuORv('u');
				return a;
			}
		}

		e.setuORv('e');
		return e;
	}

	/**
	 * @param e
	 *            entering Arc
	 * @param l
	 *            leaving Arc Wir nutzen Algo 2 aus der Uebung vom 21.12.12
	 *            Updated nach Augmentation die Knotenpreise.
	 * 
	 */
	private void updateKnotenpreise(Arc e, Arc l) {
		Vertex k;
		if (d[l.getTail().getId() - 1] > d[l.getHead().getId() - 1]) { // Waehlt
																		// Endknoten
																		// t1,
																		// t2
																		// von l
																		// so,
																		// dass
																		// d(t2)=d(t1)+1,
																		// setzt
																		// dann
																		// k =
																		// t2
			k = l.getTail();
		} else {
			k = l.getHead();
		}
		int tiefe = d[k.getId() - 1];
//TODO ich vermute hier muss man die kosten benutzen, nicht reduzierten kosten
		while (d[k.getId() - 1] >= tiefe) {
			Arc a = this.g.getVertexById(p[k.getId() - 1]).getArc(k);

			if (l.getuORv() == 'u') {
				k.setPrice(k.getPrice() - e.getReducedCost()); // yk = yk -
																// c^-e, falls
																// Bogen e zur
																// Wurzel
																// gerichtet.
			} else if (l.getuORv() == 'v') {
				k.setPrice(k.getPrice() + e.getReducedCost()); // yk = yk -
																// c^-e, falls
																// Bogen e nicht
																// zur Wurzel
																// gerichtet.
			} else {
				k.setPrice(k.getPrice() + e.getReducedCost()); // yk = yk -
																// c^-e, falls
																// Bogen e nicht
																// zur Wurzel
																// gerichtet.
			}

			k = g.getVertexById(s[k.getId() - 1]); // Knoten k wird geupdated
													// auf Nachfolger von k.
		}
	}

	private void updateS(Arc e, Arc l) {
		//Initialisierung
		int a;
		int t2;
		if (d[l.getTail().getId() - 1] > d[l.getHead().getId() - 1]) {
			a = l.getHead().getId();
			t2 = l.getTail().getId();
		}
		else{
			a = l.getTail().getId();
			t2 = l.getHead().getId();
		}
		while(s[a-1] != t2){
			a = g.getVertexById(s[a-1]).getId();
		}
		
		int b;
		int e1;
		int e2;
		int i;
		
		if (l.getuORv() == 'u') {
			b = s[e.getTail().getId()-1];
			e1 = e.getTail().getId();
			e2 = e.getHead().getId();
			i = e2;
		}
		else{
			b = s[e.getHead().getId()-1];
			e1 = e.getHead().getId();
			e2 = e.getTail().getId();
			i = e2;
		}
		
		//2.Schritt: Finde letzten Knoten (bezueglich s) k von S1
		int k = i;
		while(d[s[k-1]-1] > d[i-1]){
			k = s[k-1];
		}
		int r = s[k-1];
		
		updateSTeil2(i,t2,e1, a, r, e2, k, b);
	}
	
	//Methode Update S Schritt 3-7
	private void updateSTeil2(int i, int t2, int e1, int a, int r, int e2, int k, int b){
		while(true){		
			//3.Schritt: Ersetze s duch s*
				if(i==t2){
					if(e1 != a){
						s[a-1] = r;
						s[e1-1] = e2;
						s[k-1] = b;
					}
					else{
						s[e1-1] = e2;
						s[k-1] = r;
					}
				}
				else{
					break;
				}
				
				//4.Schritt
				int j = i;
				i = p[i-1];
				s[k-1] =i;
				
				//5.Schritt: Finde letzten Knoten k in linken Teil von Sk
				k = i;
				while(s[k-1] != j){
					if(d[r-1]>d[i-1]){
						s[k-1] = r;
						while(d[s[k-1]-1]>d[i-1]){
							k = s[k-1];
						}
						r = s[k-1];
					}
					
					//ACHTUNG
//					updateSTeil2(i,t2,e1, a, r, e2, k, b);
				}
		}
	}

	private void updatePundD(Arc e, Arc l, List<Vertex> uORv) {
//		Nur pivot-Weg aendert sich. p=v1,...,vk
		for(int i = 0; i < uORv.size(); i++){
			
				if(i == 0){
					if(l.getuORv() == 'u'){
						p[uORv.get(i).getId()-1] = e.getHead().getId();
						d[uORv.get(i).getId()-1] = d[e.getHead().getId()-1] + 1;
					}
					else{ //l in v
						p[uORv.get(i).getId()-1] = e.getTail().getId();
						d[uORv.get(i).getId()-1] = d[e.getTail().getId()-1] + 1;
					}
				}
				else{
					p[uORv.get(i).getId()-1] = uORv.get(i-1).getId();
					d[uORv.get(i).getId()-1] = d[uORv.get(i-1).getId()-1] + 1;
				}
				
				if( l.getTail().getId() == uORv.get(i).getId() || l.getHead().getId() == uORv.get(i).getId()){
				//if(l.getTail().getId() == p[uORv.get(i).getId()-1] || l.getHead().getId() == p[uORv.get(i).getId()-1] ){
					break;
				}
			
		}
	}

	
	public Stopwatch getStopwatch() {
		return stopwatch;
	}

	public Graph getGraph() {
		return g;
	}
	
	//print out p d s
	public String toString(){
		String pds= "p:\n[";
		for( int i : this.p) pds = pds+ i +" ;";
		//pds = pds.substring(0, pds.length()-1);
		pds = pds+ "]\nd:\n[";
		
		for( int i : this.d) pds = pds+ i+" ;";
		
		pds = pds+ "]\ns:\n[";
		for( int i : this.s) pds = pds+ i+" ;";
		
		pds = pds +"]";

		return pds;
	}

	public static void main(String[] args) {

		try {
			Input r = new Input("src/InputData/test");
			SimplexAlgorithm sim = new SimplexAlgorithm(r.getGraph());
			System.out.println(sim.getGraph().toString());

			// sim.initialize();
			sim.startOptimierung();

			System.out.println(sim.getGraph().toString());
			// sim.startOptimierung();
			// System.out.println(sim.getGraph().toString());
			// System.out.println("Time for readin ms: " +
			// r.getStopwatch().getElapsedTime());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
