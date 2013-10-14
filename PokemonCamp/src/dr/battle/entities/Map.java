package dr.battle.entities;

import java.util.*;

import ia.battle.camp.BattleField;
import ia.battle.camp.ConfigurationManager;
import ia.battle.camp.FieldCell;
import ia.battle.camp.FieldCellType;
import ia.exceptions.OutOfMapException;


public class Map {
	private int[][] cells;
	private int height, width;
	private static Map instance;
	
	private Map(){
		this.initCells();
	}
	
	public static Map getInstance(){
		if(instance == null)
			instance = new Map();
		return instance;
	}
	
	private void initCells() {
		height = ConfigurationManager.getInstance().getMapHeight();
		width = ConfigurationManager.getInstance().getMapWidth();
		FieldCell fc = null;
		cells = new int[width][height];
		
		for (int i = 0; i < width; i++){
			for (int j = 0; j < height; j++) {
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

	public List<Node> generarNodos(Node parent){
		int x=0, y=0;
		Node nodo = null;
		List<Node> retval = new Vector<>();
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
			y=parent.getY()-1;
			if(this.isCellNotOutOfMap(x, y) && this.isCellNotBlocked(x, y)){
				nodo = new Node(x, y);
				nodo.setParent(parent);
				retval.add(nodo);
			}
			// centro
			if(i!=2){
				y=parent.getY();
				if(this.isCellNotOutOfMap(x, y) && this.isCellNotBlocked(x, y)){
					nodo = new Node(x, y);
					nodo.setParent(parent);
					retval.add(nodo);
				}
			}
			//abajo
			y=parent.getY()+1;
			if(this.isCellNotOutOfMap(x, y) && this.isCellNotBlocked(x, y)){
				nodo = new Node(x, y);
				nodo.setParent(parent);
				retval.add(nodo);
			}
		}

		return retval;
	}
	/*
	 * Determina si una celda en particular definida por X e Y
	 * se encuentra bloqueda por el mapa (tiene un obstaculo)
	 * esto la define intransitable
	 */
	public boolean isCellNotBlocked(int x, int y){
		return (this.cells[x][y] == 1);
	}
	
	/*
	 * Determina si la posicion definida por X e Y se encuentra dentro 
	 * del mapa.
	 */
	public boolean isCellNotOutOfMap(int x, int y){
		return (x>=0 && y>=0 && x<width && y<height);
	}
	
	/*Genera el calculo del valor F sumando G + H
	 * 
	 */
	public void calculateFValue(Node origen, Node nodo, Node destino){
		nodo.setG(this.calculateGValue(origen.getX(), origen.getY(), nodo.getX(), nodo.getY()));
		nodo.setH(this.calcularHValue(nodo.getX(), nodo.getY(), destino.getX(), destino.getY()));
	}
	
	/* Pasos:
	 * Encuentra el valor G definiendo para los movimientos en Horizontal y vertical
	 * un valor de 10 y 14 para los movimientos en diagonal
	 */
	private double calculateGValue(int x, int y, int xdestino, int ydestino){
		double retval = 10;
		int resultX = xdestino-x;
		int resultY = ydestino-y;
		
		if((resultX==-1 && resultY==-1) || 
			(resultX==1 && resultY==1) || 
				(resultX==-1 && resultY==1) || 
					(resultX==1 && resultY==-1))
			retval=14;
		return retval;
	}
	
	/* Pasos:
	 * Se calcula el valor H utilizando el metodo Manhatan.
	 * 
	 */
	private double calcularHValue(int x, int y, int xdestino, int ydestino){
		double difX, difY;
		difX = x-xdestino;
		difY = y-ydestino;
		difX = difX>=0?difX:difX*-1;
		difY = difY>=0?difY:difY*-1;
		return (difX+difY)*10;
	}
}
