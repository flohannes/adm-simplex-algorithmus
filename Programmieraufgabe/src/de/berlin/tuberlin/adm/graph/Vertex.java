package de.berlin.tuberlin.adm.graph;

import java.util.Vector;

public class Vertex {

//	private Vector<Arc> arcs;
	private int id;
	private int flow;
	
	public Vertex(int id){
//		arcs = new Vector<Arc>();
		this.id = id;
	}

//	public Vector<Arc> getArcs() {
//		return arcs;
//	}
//
//	public void setArcs(Vector<Arc> arcs) {
//		this.arcs = arcs;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFlow() {
		return flow;
	}

	public void setFlow(int flow) {
		this.flow = flow;
	}
	
	public String toString(){
		return "n " + this.getId()+ " " + this.getFlow();
	}
}
