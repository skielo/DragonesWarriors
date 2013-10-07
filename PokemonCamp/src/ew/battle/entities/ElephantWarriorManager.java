package ew.battle.entities;

import ia.battle.camp.Warrior;
import ia.battle.camp.WarriorManager;
import ia.exceptions.RuleException;

public class ElephantWarriorManager extends WarriorManager {

	@Override
	public Warrior getNextWarrior() throws RuleException {
		ElephantWarrior ew = new ElephantWarrior("Alejandro Magno", 10, 10, 10, 10, 10);
		return ew;
	}

}
