package ia.battle.example;

import ia.battle.camp.BattleField;
import ia.battle.entities.Warrior;
import ia.battle.entities.WarriorManager;
import ia.exceptions.OutOfMapException;
import ia.exceptions.RuleException;

public class AndrewWarriorManager extends WarriorManager {

	@Override
	public Warrior getNextWarrior() throws RuleException {

		AndrewWarrior andrew = new AndrewWarrior("Andrew", 10, 10, 10, 10, 10);
		
//		try {
//			andrew.setPosition(BattleField.getInstance().getFieldCell(0, 0));
//		} catch (OutOfMapException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return andrew;
	}

}
