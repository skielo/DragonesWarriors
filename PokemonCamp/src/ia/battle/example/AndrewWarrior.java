package ia.battle.example;

import java.util.ArrayList;

import ia.battle.entities.Action;
import ia.battle.entities.Warrior;
import ia.exceptions.RuleException;

public class AndrewWarrior extends Warrior {

	public AndrewWarrior(String name, int health, int defense, int strength,
			int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);
		
		
		
	}

	@Override
	public ArrayList<Action> playTurn(long tick) {
		

		
		
		return null;
	}
	
}
