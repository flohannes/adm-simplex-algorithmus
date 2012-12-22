package de.berlin.tuberlin.adm.graph;

public class Arc {

	private Vertex head;
	private Vertex tail;
	private int cost;
	private int low;
	
	public Arc (Vertex head, Vertex tail){
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

}
