package de.berlin.tuberlin.adm.graph;

public class Vertex {

	private int id;
	private int flow;
	
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
	
	public String toString(){
		return "n " + this.getId()+ " " + this.getFlow();
	}
}
