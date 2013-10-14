package dr.battle.entities;

import ia.battle.camp.BattleField;
import ia.battle.camp.FieldCell;
import ia.exceptions.OutOfMapException;

import java.util.*;

public class PathFinder {
	private List<Node> openList, closeList;
	private Node start, end;
	
	public PathFinder(){
		this.openList = new Vector<>();
		this.closeList = new Vector<>();
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
		
		for (Node nodo : this.closeList) {
			try {			
				retval.add(BattleField.getInstance().getFieldCell(nodo.getX(),nodo.getY()));
			} catch (OutOfMapException ex) {
				ex.printStackTrace();
			}
		}
		this.openList.clear();
		this.closeList.clear();
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
		Node lessFValue;
		if(end.equals(comienzo)){
			this.closeList.add(comienzo);
			return;
		}
		if(!this.openList.contains(comienzo))
			this.openList.add(comienzo);
		this.agregarNodosAdyasentes(comienzo);
		lessFValue = this.openList.get(0);
		for (Node nodo : this.openList) {
			if(nodo.getF()<lessFValue.getF()){
				lessFValue = nodo;
			}
		}
		this.find(lessFValue);
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
		this.addToListIfNotExist(Map.getInstance().generarNodos(padre));
		for (Node nodo : this.openList) {
			Map.getInstance().calculateFValue(padre, nodo, this.end);
		}
		this.closeList.add(padre);
		this.openList.remove(padre);	
	}
	
	private void addToListIfNotExist(List<Node> nodes){
		Collection<Node> copy = new LinkedList<Node>(nodes);
        copy.removeAll(this.openList);
        this.openList.addAll(copy);
	}
}
