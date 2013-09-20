package ia.battle.camp;

public class WarriorData {
	private FieldCell fieldCell;
	private int health;
	private String name;

	WarriorData(FieldCell fieldCell, int health, String name) {
		this.fieldCell = fieldCell;
		this.health = health;
		this.name = name;
	}

	public FieldCell getFieldCell() {
		return fieldCell;
	}

	public int getHealth() {
		return health;
	}

	public String getName() {
		return name;
	}

}
