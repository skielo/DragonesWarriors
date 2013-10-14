package dr.battle.entities;

import java.util.Random;

import ia.battle.camp.Action;
import ia.battle.camp.ConfigurationManager;
import ia.battle.camp.Warrior;
import ia.exceptions.RuleException;

public class DragonWarrior extends Warrior {

	private PathFinder pathFinder=null;
	private DragonWarriorMove pendingMoves = new DragonWarriorMove();
	private Random random = new Random();
	
	public DragonWarrior(String name, int health, int defense, int strength,
			int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);
		pathFinder = new PathFinder();
	}

	@Override
	public Action playTurn(long tick, int actionNumber) {
		DragonWarriorMove ewm = new DragonWarriorMove();
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();

		ewm.setSteps(pathFinder.find(new Node(x, y), 
				new Node(random.nextInt(ConfigurationManager.getInstance().getMapWidth()),
				random.nextInt(ConfigurationManager.getInstance().getMapHeight()))));
		return ewm;
	}

}
