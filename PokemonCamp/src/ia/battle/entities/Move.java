package ia.battle.entities;

import ia.battle.camp.FieldCell;

import java.util.ArrayList;

public abstract class Move extends Action {
	
	public abstract ArrayList<FieldCell> move();

}
