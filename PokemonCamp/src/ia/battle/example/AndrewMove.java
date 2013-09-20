package ia.battle.example;

import java.util.ArrayList;

import ia.battle.camp.FieldCell;
import ia.battle.entities.Move;


public class AndrewMove extends Move {
	
	private ArrayList<FieldCell> steps = new ArrayList<FieldCell>();
	
	@Override
	public ArrayList<FieldCell> move() {
		
		return steps;
	}
	
	void setSteps(ArrayList<FieldCell> steps) {
		this.steps = steps;
	}
	
	
}
