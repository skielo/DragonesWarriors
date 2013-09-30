package ia.battle.camp;

public final class ConfigurationManager {

	private static ConfigurationManager instance = new ConfigurationManager();

	public static ConfigurationManager getInstance() {
		return instance;
	}

	public int getMaxPointsPerWarrior() {
		return 100;
	}
	
	public int getMapHeight() {
		return 40;
	}
	
	public int getMapWidth() {
		return 40;
	}
	
	public int getTurnsToShrink() {
		return 5000;
	}
	
	public int getActionsPerTurn() {
		return 3;
	}
}
