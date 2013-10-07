package ew.battle.entities;

import ia.battle.camp.Action;
import ia.battle.camp.Warrior;
import ia.exceptions.RuleException;

public class ElephantWarrior extends Warrior {

	private PathFinder pathFinder=null;
	private boolean invertir = false;
	
	public ElephantWarrior(String name, int health, int defense, int strength,
			int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);
		pathFinder = new PathFinder();
	}

	@Override
	public Action playTurn(long tick, int actionNumber) {
		ElephantWarriorMove ewm = new ElephantWarriorMove();
		int x = this.getPosition().getX();
		int y = this.getPosition().getY();
		
		if(!invertir){
			ewm.setSteps(pathFinder.Find(new Node(x, y), new Node(0,0)));
			invertir=true;
		}
		else{
			ewm.setSteps(pathFinder.Find(new Node(0,0), new Node(x, y)));
			invertir=false;
		}
		
		return ewm;
	}

}
