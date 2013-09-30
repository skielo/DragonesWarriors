package ew.battle.entities;

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
	
	public boolean IsCellBlocked(int x, int y){
		return (this.cells[x][y] == 0);
	}
	
	/*Genera el calculo del valor F sumando G + H
	 * 
	 */
	public double CalculateFValue(){
		return this.CalculateGValue() + this.CalcularHValue();
	}
	
	/*TODO: Generar el calculo del valor G
	 * 
	 */
	private double CalculateGValue(){
		double retval = 0;
		return retval;
	}
	
	/*TODO: Generar el calculo del valor H
	 * utilizando el metodo Manhatan
	 */
	private double CalcularHValue(){
		double retval = 0;
		return retval;
	}
}
