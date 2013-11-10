package dr.battle.entities;

import java.util.Random;

import ia.battle.camp.Warrior;
import ia.battle.camp.WarriorManager;
import ia.exceptions.RuleException;

public class DragonWarriorManager extends WarriorManager {

	@Override
	public Warrior getNextWarrior() throws RuleException {
		DragonWarrior ew = null;
		int a=0;
		Random rd = new Random(System.currentTimeMillis());
		a=rd.nextInt(3);
		switch(a){
		case 1:
			ew = new DragonWarrior("Alejandro Magno", 10, 30, 30, 10, 20);
			break;
		case 2:
			ew = new DragonWarrior("Rominitt", 25, 25, 10, 25, 5);
			break;
		case 3:
			ew = new DragonWarrior("La Peste", 10, 10, 25, 30, 25);
			break;
		default:
			ew = new DragonWarrior("Cucuos", 15, 30, 40, 5, 10);
			break;
		}
		
		return ew;
	}

}
