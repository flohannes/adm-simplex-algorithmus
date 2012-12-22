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
