package ia.battle.entities;

public final class Attack extends Action {
	private Warrior attackedWarrior;
	
	public Attack(Warrior attacWarrior) {
		this.attackedWarrior = attacWarrior;
	}
	
	public Warrior getAttackedWarrior() {
		return attackedWarrior;
	}
}
