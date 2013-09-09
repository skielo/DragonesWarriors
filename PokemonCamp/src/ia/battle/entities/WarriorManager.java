package ia.battle.entities;

import ia.exceptions.RuleException;

public abstract class WarriorManager {

	public abstract Warrior getNextWarrior() throws RuleException;
	
	
}
