package dr.battle.entities;

import ia.battle.camp.BattleField;
import ia.battle.camp.FieldCell;
import ia.exceptions.OutOfMapException;

import java.util.*;

public class PathFinder {
	private List<Node> openList, closeList;
	private Node start, end, latestLess;
	private Map mapa;
	boolean validator = false;
	public PathFinder(){
		this.openList = new Vector<>();
		this.closeList = new Stack<>();
		//this.mapa = new Map();
	}
	
	public ArrayList<FieldCell> find(Node comienzo, Node fin){
		this.end = fin;
		this.find(comienzo);
		return getMoves();
	}
	
	/* Retorna la lista de de movimientos con el objeto
	 * utilizado por el BattleField
	 * 
	 */
	private ArrayList<FieldCell> getMoves()
	{
		ArrayList<FieldCell> retval = new ArrayList<>();
		Node nodo, father=null;
		while(!((Stack<Node>)this.closeList).empty()){
			nodo = ((Stack<Node>)this.closeList).pop();
			try {
				if(father==null || !this.closeList.contains(end))
					father=nodo;
				if(nodo.equals(this.end) || nodo.equals(father)){
					father = nodo.getParent();
					retval.add(BattleField.getInstance().getFieldCell(nodo.getX(),nodo.getY()));
				}
			} catch (OutOfMapException ex) {
				ex.printStackTrace();
			}
		}

		this.openList = new Vector<>();
		this.closeList = new Stack<>();
		Collections.reverse(retval);
		retval.remove(0);
		return retval;
	} 
	
	/* Pasos:
	 * Agrego el nodo inicial a la lista abierta
	 * Agrego los potenciales caminos
	 * Recorro la lista buscando el nodo con menor F
	 * Vuelvo a invocar la llamada para calcular el camino
	 * Termina cuando el nodo a buscar sea igual al final
	 * 
	 */
	private void find(Node comienzo){
		Node lessFValue=comienzo;

		while(!this.closeList.contains(end) && this.closeList.size()<75){
			if(!this.openList.contains(lessFValue))
				this.openList.add(lessFValue);
			if(latestLess == null)
				latestLess = lessFValue;
			this.agregarNodosAdyasentes(lessFValue);
			lessFValue = this.openList.get(0);
			for (Node nodo : this.openList) {
				if(nodo.getF()<lessFValue.getF() && !latestLess.equals(lessFValue)){
					lessFValue = nodo;
				}
			}
			latestLess = lessFValue;
		}
	}
	
	public Node getEnd() {
		return end;
	}

	public void setEnd(Node end) {
		this.end = end;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		this.start = start;
	}
	
	/* Pasos:
	 * Calculo F, G, H
	 * Agrego el padre a la lista cerrada y lo saco de la abierta
	 */
	private void agregarNodosAdyasentes(Node padre){
		List<Node> adyasentes = mapa.generarNodos(padre);
		for (Node nodo : adyasentes) {
			mapa.calculateFValue(padre, nodo, this.end);
		}
		this.addToListIfNotExist(adyasentes);
		if(!this.closeList.contains(padre))
			((Stack<Node>)this.closeList).addElement(padre);
		this.openList.remove(padre);	
	}
	
	private void addToListIfNotExist(List<Node> nodes){
		Node temp;
		for (Node node : nodes) {
			//Lo agrego solo si no existe en la lista,
			//En caso de que exista deberia verificar que el 
			//valor de G sea menor al nuevo
			if(this.openList.contains(node)){
				temp = this.openList.get(this.openList.indexOf(node));
				if(temp.isNewGBetter(node.getParent().getG())){
					temp.setParent(node.getParent());
					temp.setParentG(node.getG());
					//this.openList.remove(temp);
					//this.openList.add(node);
				}
			}
			else
				this.openList.add(node);
		}
	}
}
