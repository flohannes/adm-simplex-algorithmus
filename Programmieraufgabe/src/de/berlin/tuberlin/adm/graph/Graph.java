package de.berlin.tuberlin.adm.graph;

import java.util.ArrayList;

public class Graph {
	
	private ArrayList<Vertex> vertices;
	private ArrayList<Arc> arcs;
	
	public Graph(){
		
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
