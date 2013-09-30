package ia.battle.entities;

import ia.exceptions.RuleException;

public abstract class WarriorManager {
	
	private int count;
	
	public abstract Warrior getNextWarrior() throws RuleException;

	public final Warrior getNewWarrior() throws RuleException {
		count++;
		return this.getNextWarrior();
	}

	public int getCount() {
		return count;
	}
}
