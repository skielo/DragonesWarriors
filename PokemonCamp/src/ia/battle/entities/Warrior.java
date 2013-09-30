package ia.battle.entities;

import ia.battle.camp.ConfigurationManager;
import ia.battle.camp.FieldCell;
import ia.exceptions.RuleException;

import java.util.ArrayList;

public abstract class Warrior {

	private String name;
	private int health, defense, strength, speed, range;
	private FieldCell position;

	public Warrior(String name, int health, int defense, int strength,
			int speed, int range) throws RuleException {

		this.name = name;
		this.health = health;
		this.defense = defense;
		this.strength = strength;
		this.speed = speed;
		this.range = range;

		int sum = this.health + this.defense + this.strength + this.speed
				+ this.range;

		if (sum > ConfigurationManager.getInstance().getMaxPointsPerWarrior())
			throw new RuleException();

		if (this.health <= 0)
			throw new RuleException();

		if (this.defense <= 0)
			throw new RuleException();

		if (this.strength <= 0)
			throw new RuleException();

		if (this.speed <= 0)
			throw new RuleException();

		if (this.range <= 0)
			throw new RuleException();

	}

	public String getName() {

		return name;
	}

	public final int getHealth() {
		return health;
	}

	public final int getDefense() {
		return defense;
	}

	public final int getStrength() {
		return strength;
	}

	public final int getSpeed() {
		return speed;
	}

	public final int getRange() {
		return range;
	}


	public FieldCell getPosition() {
		return position;
	}

	public void setPosition(FieldCell position) throws RuleException {
		
		//TODO: Validar que la invocacion provenga del BattleField
		
//		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
//		if (!stackTraceElements[1].getClassName().equals(BattleField.class.getName()))
//			throw new RuleException();
		
		
		this.position = position;
	}
	
	public abstract Action playTurn(long tick, int actionNumber);

}
