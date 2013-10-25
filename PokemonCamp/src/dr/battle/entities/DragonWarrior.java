package dr.battle.entities;

import java.util.Random;

import ia.battle.camp.Action;
import ia.battle.camp.ConfigurationManager;
import ia.battle.camp.FieldCell;
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
		int xdestino,ydestino;
		xdestino = random.nextInt(ConfigurationManager.getInstance().getMapWidth());
		ydestino = random.nextInt(ConfigurationManager.getInstance().getMapHeight());
		ewm.setSteps(pathFinder.find(new Node(x, y),new Node(xdestino,ydestino)));
		System.out.println("Yendo de: [" + x + ";" + y + "] a:[" + xdestino + ";" + ydestino + "]");
		System.out.println("----------------");
		for (FieldCell field : ewm.steps) {
			System.out.println("[" + field.getX() + ";" + field.getY() + "]");
		}
		//this.showMap();
		return ewm;
	}
	
    private void showMap() {

        int height = ConfigurationManager.getInstance().getMapHeight();
        int width = ConfigurationManager.getInstance().getMapWidth();

        for (int i = 1; i < height; i++) {
            for (int j = 1; j < width; j++)
                System.out.print(Map.getInstance().getCells()[i][j]);

            System.out.println();
        }

    }

}
