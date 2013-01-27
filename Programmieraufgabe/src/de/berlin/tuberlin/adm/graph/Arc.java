package de.berlin.tuberlin.adm.graph;

public class Arc {

	// Bogen a = (u,v), mit u,v aus V
	private Vertex head; // v aus V
	private Vertex tail; // u aus V
	private int cost; // c(a)
	private int reducedCost; //c^- (a)
	private int low; // l(a)
	private int cap; // u(a)
	private int flowX; // x(a)
	
	private char uORv;
	
	
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
	
	
	public int getFlowX() {
		return flowX;
	}

	public void setFlowX(int flowX) throws IllegalArgumentException{
		if(flowX > this.cap || flowX < this.low){
			throw new IllegalArgumentException("Kapazitaetsgrenzenueberschritten\n"+"von "+tail.getId() +
											" nach "+head.getId()+" "+low+"/"+ cap+"/"+ flowX);
		}
		this.flowX = flowX;
	}
	
	public char getuORv() {
		return uORv;
	}

	public void setuORv(char uORv) {
		this.uORv = uORv;
	}

	public String toString(){
		return "a "+ this.tail.getId() + " " + this.head.getId() + " " + this.getLow() + " " + this.getCap() + " " + this.getCost();
	}

}
