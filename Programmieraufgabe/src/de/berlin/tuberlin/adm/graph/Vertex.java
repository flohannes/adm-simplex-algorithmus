package de.berlin.tuberlin.adm.graph;

import java.util.ArrayList;

public class Vertex {

	private int id;
	private int flow;
	private ArrayList<Arc> deltaPlus;
	private ArrayList<Arc> deltaMinus;
	
	public Vertex(int id){
		this.id = id;
	}

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
	
	public ArrayList<Arc> getDeltaPlus() {
		return deltaPlus;
	}

	public void setDeltaPlus(ArrayList<Arc> deltaPlus) {
		this.deltaPlus = deltaPlus;
	}

	public ArrayList<Arc> getDeltaMinus() {
		return deltaMinus;
	}

	public void setDeltaMinus(ArrayList<Arc> deltaMinus) {
		this.deltaMinus = deltaMinus;
	}
	
	public void addArcDeltaPlus(Arc a){
		deltaPlus.add(a);
	}
	
	public void addArcDeltaMinus(Arc a){
		deltaMinus.add(a);
	}

	public boolean equals(Vertex v){
		if(this.getId() == v.getId())
			return true;
		return false;
	}
	
	public Vertex clone(){
		Vertex v = new Vertex(this.id);
		v.setFlow(this.flow);
		return v;
	}
	
	public String toString(){
		return "n " + this.getId()+ " " + this.getFlow();
	}
}
