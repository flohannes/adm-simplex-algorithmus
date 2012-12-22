package de.berlin.tuberlin.adm.graph;

public class Arc {

	private Vertex head;
	private Vertex tail;
	private int cost;
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
	
	public String toString(){
		return "a "+ this.tail.getId() + " " + this.head.getId() + " " + this.getLow() + " " + this.getCap() + " " + this.getCost();
	}

}
