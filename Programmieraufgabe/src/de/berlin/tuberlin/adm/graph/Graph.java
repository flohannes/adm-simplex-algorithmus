package de.berlin.tuberlin.adm.graph;

import java.util.ArrayList;

public class Graph {
	
	private ArrayList<Vertex> vertices;
	private ArrayList<Arc> arcs;
	
	
	public Graph(){
		vertices = new ArrayList<Vertex>();
		arcs = new ArrayList<Arc>();
	}
	
	
	//neuer Konstruktor
	public Graph( int NumOfNodes, int NumOfArcs){
		vertices = new ArrayList<Vertex>(NumOfNodes);
		arcs = new ArrayList<Arc>(NumOfArcs);
		
		for( int i=0; i<NumOfNodes ;i++){
			Vertex v = new Vertex( i );
			v.setFlow(0);
			this.addVertex(v);
		}//jetzt brauch man in INput nur noch setFlow für die angegebenen Knoten aufrufen
	}
	
	
	
	
	public Vertex getVertexById(int id){
		for(Vertex v: vertices){
			if(id == v.getId()){
				return v;
			}
		}
		return null;
	}
	
	public void addVertex(Vertex v){
		this.vertices.add(v);
		// so bräuchte man doch bei getVertexById nicht immer die ganze arraylist durchlaufen
		//id = Stelle an der der Knoten gespeichert ist
		//this.vertices.add(v.getId(),v);
	}
	
	public void addArc(Arc a){
		this.arcs.add(a);
		this.vertices.get(a.getTail().getId()).addArcDeltaMinus(a);
		this.vertices.get(a.getHead().getId()).addArcDeltaPlus(a);
	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertex> vertices) {
		this.vertices = vertices;
	}

	public ArrayList<Arc> getArcs() {
		return arcs;
	}

	public void setArcs(ArrayList<Arc> arcs) {
		this.arcs = arcs;
	}

}
