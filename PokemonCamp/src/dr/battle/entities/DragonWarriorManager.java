package dr.battle.entities;

import java.util.Random;

import ia.battle.camp.Warrior;
import ia.battle.camp.WarriorManager;
import ia.exceptions.RuleException;

public class DragonWarriorManager extends WarriorManager {
	int previous=0;
	int count=0;
	boolean validator = false;
	@Override
	public Warrior getNextWarrior() throws RuleException {
		DragonWarrior ew = null;
		this.count++;
		Random rd = new Random();
		previous=rd.nextInt(3);
		switch(previous){
		case 0:
			ew = new DragonWarrior("Alejandro Magno "+this.count, 10, 30, 30, 10, 20);
			break;
		case 1:
			ew = new DragonWarrior("Julio Cesar "+this.count, 15, 25, 25, 25, 10);
			break;
		case 2:
			ew = new DragonWarrior("Napoleon "+this.count, 10, 10, 25, 30, 25);
			break;
		}
		
		return ew;
	}

}
