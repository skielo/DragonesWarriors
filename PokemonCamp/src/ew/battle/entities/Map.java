package ew.battle.entities;

import java.util.ArrayList;

import ia.battle.camp.BattleField;
import ia.battle.camp.ConfigurationManager;
import ia.battle.camp.FieldCell;
import ia.battle.camp.FieldCellType;
import ia.exceptions.OutOfMapException;


public class Map {
	private int[][] cells;

	public Map(){
		this.initCells();
	}
	
	private void initCells() {
		int height = ConfigurationManager.getInstance().getMapHeight();
		int width = ConfigurationManager.getInstance().getMapWidth();
		FieldCell fc = null;
		cells = new int[height][width];
		
		for (int i = 1; i < height; i++){
			for (int j = 1; j < width; j++) {
				try {
					fc = BattleField.getInstance().getFieldCell(i, j);
				} catch (OutOfMapException ex) {
					ex.printStackTrace();
				}
				if (fc.getFieldCellType() == FieldCellType.BLOCKED)
					cells[i][j] = 0;
				else
					cells[i][j] = 1;
			}
		}
	}

	public ArrayList<Node> GenerarNodos(Node parent){
		int x=0, y=0;
		Node nodo = null;
		ArrayList<Node> retval = new ArrayList<>();
		for(int i=1; i<=3;i++){
			switch(i){
			case 1:
				x=parent.getX()-1;
				break;
			case 2:
				x=parent.getX();
				break;
			case 3:
				x=parent.getX()+1;
				break;
			}
			//arriba
			y=parent.getY()+1;
			nodo = new Node(x, y);
			nodo.setParent(parent);
			retval.add(nodo);
			// centro
			if(i!=2){
				y=parent.getY();
				nodo = new Node(x, y);
				nodo.setParent(parent);
				retval.add(nodo);	
			}
			//abajo
			y=parent.getY()-1;
			nodo = new Node(x, y);
			nodo.setParent(parent);
			retval.add(nodo);
		}

		return retval;
	}
	
	public boolean IsCellBlocked(int x, int y){
		return (this.cells[x][y] == 0);
	}
	
	/*Genera el calculo del valor F sumando G + H
	 * 
	 */
	public void CalculateFValue(Node nodo, Node destino){
		nodo.setG(this.CalculateGValue(nodo.getX(), nodo.getY(), destino.getX(), destino.getY()));
		nodo.setH(this.CalcularHValue(nodo.getX(), nodo.getY(), destino.getX(), destino.getY()));
	}
	
	/*TODO: Generar el calculo del valor G
	 * 
	 */
	private double CalculateGValue(int x, int y, int xdestino, int ydestino){
		double retval = 0;
		return retval;
	}
	
	/*TODO: Generar el calculo del valor H
	 * utilizando el metodo Manhatan
	 */
	private double CalcularHValue(int x, int y, int xdestino, int ydestino){
		double retval = 0;
		return retval;
	}
}
