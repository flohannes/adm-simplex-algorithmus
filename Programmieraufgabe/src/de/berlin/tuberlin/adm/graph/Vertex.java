package de.berlin.tuberlin.adm.graph;

import java.util.Vector;

public class Vertex {

	private Vector<Arc> arcs;
	private int id;
	private double flow;
	
	public Vertex(int id){
		arcs = new Vector<Arc>();
		this.id = id;
	}

	public Vector<Arc> getArcs() {
		return arcs;
	}

	public void setArcs(Vector<Arc> arcs) {
		this.arcs = arcs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getFlow() {
		return flow;
	}

	public void setFlow(double flow) {
		this.flow = flow;
	}
}
