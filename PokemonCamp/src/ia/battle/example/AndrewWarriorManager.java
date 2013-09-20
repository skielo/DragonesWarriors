package ia.battle.example;

import ia.battle.entities.Warrior;
import ia.battle.entities.WarriorManager;
import ia.exceptions.RuleException;

public class AndrewWarriorManager extends WarriorManager {

	@Override
	public Warrior getNextWarrior() throws RuleException {

		AndrewWarrior andrew = new AndrewWarrior("Andrew", 10, 10, 10, 10, 10);
		
		
		return andrew;
	}

}
