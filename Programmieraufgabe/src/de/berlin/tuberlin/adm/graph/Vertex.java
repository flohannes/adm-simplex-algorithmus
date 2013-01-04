package de.berlin.tuberlin.adm.graph;

import java.util.ArrayList;

public class Vertex {

	
	private int id;
	private int flow;  // bi mit i aus V
	private int price; // yi mit i aus V
	private ArrayList<Arc> deltaPlus; //ausgehende Kanten
	private ArrayList<Arc> deltaMinus; //eingehende Kanten
	
	public Vertex(int id){
		this.id = id;
		this.deltaMinus = new ArrayList<Arc>();
		this.deltaPlus = new ArrayList<Arc>();
	}

	public int getId() {
		return id;
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

	public ArrayList<Arc> getDeltaMinus() {
		return deltaMinus;
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
	
	public Arc getArc(Vertex v){
		for(Arc a : this.deltaMinus){
			if(a.getTail().equals(v)){
				return a;
			}
		}
		for(Arc a : this.deltaPlus){
			if(a.getHead().equals(v)){
				return a;
			}
		}
		return null;
	}
	
	public Vertex clone(){
		Vertex v = new Vertex(this.id);
		v.setFlow(this.flow);
		return v;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	
	public String toString(){
		return "n " + this.getId()+ " " + this.getFlow();
	}

	
}
