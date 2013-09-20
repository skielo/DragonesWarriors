package ia.battle.camp;

import ia.battle.entities.Warrior;

class WarriorWrapper {
	private Warrior warrior;
	private int stepsInTurn;

	WarriorWrapper(Warrior warrior) {
		this.warrior = warrior;
	}

	Warrior getWarrior() {
		return warrior;
	}

	void startTurn() {
		stepsInTurn = 0;

	}

	void doStep() {
		stepsInTurn++;
	}

	public int getSteps() {
		return stepsInTurn;
	}

}
