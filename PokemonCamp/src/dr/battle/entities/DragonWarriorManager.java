package dr.battle.entities;

import ia.battle.camp.Warrior;
import ia.battle.camp.WarriorManager;
import ia.exceptions.RuleException;

public class DragonWarriorManager extends WarriorManager {

	@Override
	public Warrior getNextWarrior() throws RuleException {
		DragonWarrior ew = new DragonWarrior("Alejandro Magno", 10, 10, 10, 10, 10);
		return ew;
	}

}
