package dr.battle.entities;

import java.util.ArrayList;

import ia.battle.camp.FieldCell;
import ia.battle.camp.Move;

public class DragonWarriorMove extends Move {

	ArrayList<FieldCell> steps = new ArrayList<>();
	
	@Override
	public ArrayList<FieldCell> move() {
		return steps;
	}
	
	void setSteps(ArrayList<FieldCell> steps) {
		this.steps = steps;
	}

}
