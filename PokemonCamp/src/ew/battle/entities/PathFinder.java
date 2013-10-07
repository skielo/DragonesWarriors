package ew.battle.entities;

import ia.battle.camp.BattleField;
import ia.battle.camp.FieldCell;
import ia.exceptions.OutOfMapException;

import java.util.ArrayList;

public class PathFinder {
	private Map mapa = null;
	private ArrayList<Node> openList, closeList;
	private Node start, end;
	
	public PathFinder(){
		this.mapa = new Map();
		this.openList = new ArrayList<>();
		this.closeList = new ArrayList<>();
	}
	
	public ArrayList<FieldCell> Find(Node comienzo, Node fin){
		this.end = fin;
		this.Find(comienzo);
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
	private void Find(Node comienzo){
		Node lessFValue;
		if(end==comienzo){
			return;
		}
		this.openList.add(comienzo);
		this.AgregarNodosAdyasentes(comienzo);
		lessFValue = comienzo;
		for (Node nodo : this.openList) {
			if(nodo.getF()<lessFValue.getF()){
				lessFValue = nodo;
			}
		}
		this.Find(lessFValue);
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
	private void AgregarNodosAdyasentes(Node padre){
		this.openList.addAll(this.mapa.GenerarNodos(padre));
		for (Node nodo : this.openList) {
			this.mapa.CalculateFValue(padre, nodo, this.end);
		}
		this.closeList.add(padre);
		this.openList.remove(padre);	
	}
}
