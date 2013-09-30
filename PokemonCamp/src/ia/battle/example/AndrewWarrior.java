package ia.battle.example;

import ia.battle.camp.BattleField;
import ia.battle.camp.ConfigurationManager;
import ia.battle.camp.FieldCell;
import ia.battle.camp.FieldCellType;
import ia.battle.entities.Action;
import ia.battle.entities.Warrior;
import ia.exceptions.OutOfMapException;
import ia.exceptions.RuleException;

import java.util.ArrayList;

public class AndrewWarrior extends Warrior {

	public AndrewWarrior(String name, int health, int defense, int strength, int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);

	}

	private boolean horizontalDirection;

	@Override
	public Action playTurn(long tick, int actionNumber) {

		AndrewMove am = new AndrewMove();

		ArrayList<FieldCell> stepsToDo = new ArrayList<FieldCell>();

		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		FieldCell fc = null;

		do {
			if (horizontalDirection) {

				if (x > 1) {
					x--;
				} else
					horizontalDirection = !horizontalDirection;

			} else {

				if (x < ConfigurationManager.getInstance().getMapWidth() - 1) {
					x++;
				} else {
					horizontalDirection = !horizontalDirection;
				}
			}

			try {
				fc = BattleField.getInstance().getFieldCell(x, y);
			} catch (OutOfMapException e1) {
				e1.printStackTrace();
			}

			if (fc.getFieldCellType() == FieldCellType.BLOCKED)
				horizontalDirection = !horizontalDirection;
			
		} while (fc.getFieldCellType() == FieldCellType.BLOCKED);

		stepsToDo.add(fc);

		am.setSteps(stepsToDo);

		return am;
	}

}
