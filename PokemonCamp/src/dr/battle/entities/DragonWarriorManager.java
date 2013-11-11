package dr.battle.entities;

import java.util.Random;

import ia.battle.camp.Warrior;
import ia.battle.camp.WarriorManager;
import ia.exceptions.RuleException;

public class DragonWarriorManager extends WarriorManager {
	int previous=0;
	@Override
	public Warrior getNextWarrior() throws RuleException {
		DragonWarrior ew = null;
		
		Random rd = new Random();
		previous=rd.nextInt(3);
		switch(previous){
		case 0:
			ew = new DragonWarrior("Alejandro Magno", 10, 30, 30, 10, 20);
			previous=1;
			break;
		case 1:
			ew = new DragonWarrior("Julio Cesar", 15, 25, 25, 25, 10);
			previous=2;
			break;
		case 2:
			ew = new DragonWarrior("Napoleon", 10, 10, 25, 30, 25);
			previous=0;
			break;
		}
		
		return ew;
	}

}
