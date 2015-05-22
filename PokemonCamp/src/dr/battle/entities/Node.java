package dr.battle.entities;

public class Node {
	private int x,y;
	private Node parent;
	private double g,h;
	private double parentG;
	boolean validator = false;
	public Node(int x, int y){
		this.x=x;
		this.y=y;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public double getH() {
		return h;
	}
	public void setH(double d) {
		this.h = d;
	}
	public double getF() {
		return this.getG()+this.getH();
	}
	/*
	 * El valor de G es el propio del nodo
	 * mas el de su padre.
	 */
	public double getG() {
		return this.getParentG() + g;
	}
	public boolean isNewGBetter(double newParentG){
		return (this.getG()>g+newParentG);
	}
	public void setG(double d) {
		this.g = d;
	}
	
	@Override
	public boolean equals(Object x){
		return ((Node)x).getX()==this.getX()&&((Node)x).getY()==this.getY();
	}
	public double getParentG() {
		return parentG;
	}
	public void setParentG(double parentG) {
		this.parentG = parentG;
	}
}
