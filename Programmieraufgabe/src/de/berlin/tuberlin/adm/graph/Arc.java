package de.berlin.tuberlin.adm.graph;

public class Arc {

	private Vertex head;
	private Vertex tail;
	private int cost;
	private int reducedCost;
	private int low;
	private int cap;
	
	public Arc (Vertex tail, Vertex head){
		this.head = head;
		this.tail = tail;
	}
	
	public Vertex getHead() {
		return head;
	}

	public void setHead(Vertex head) {
		this.head = head;
	}

	public Vertex getTail() {
		return tail;
	}

	public void setTail(Vertex tail) {
		this.tail = tail;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getLow() {
		return low;
	}

	public void setLow(int low) {
		this.low = low;
	}


	public int getCap() {
		return cap;
	}


	public void setCap(int cap) {
		this.cap = cap;
	}
	
	public boolean equals(Arc a){
		if(this.tail.equals(a.getTail()) && this.head.equals(a.getHead()))
			return true;
		return false;
	}
	
	public Arc clone(){
		Arc a = new Arc(this.tail.clone(), this.head.clone());
		a.setCap(this.cap);
		a.setLow(this.low);
		a.setCost(this.cost);
		return a;
	}
	
	public int getReducedCost() {
		return reducedCost;
	}

	public void setReducedCost(int reducedCost) {
		this.reducedCost = reducedCost;
	}
	
	public String toString(){
		return "a "+ this.tail.getId() + " " + this.head.getId() + " " + this.getLow() + " " + this.getCap() + " " + this.getCost();
	}

	

}
