package de.berlin.tuberlin.adm.graph;

import java.util.ArrayList;

public class Graph {
	
	private ArrayList<Vertex> vertices;
	private ArrayList<Arc> arcs;
	private Vertex v;
	private Vertex w;
	
	
	public Graph(){
		
	}
	
	public void addVertex(Vertex v){
		this.vertices.add(v);
	}
	
	public void addArc(Arc a){
		this.arcs.add(a);
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

	public Vertex getV() {
		return v;
	}

	public void setV(Vertex v) {
		this.v = v;
	}

	public Vertex getW() {
		return w;
	}

	public void setW(Vertex w) {
		this.w = w;
	}

}
