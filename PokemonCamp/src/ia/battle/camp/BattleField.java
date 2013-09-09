package ia.battle.camp;

import ia.battle.entities.Warrior;
import ia.battle.entities.WarriorManager;
import ia.battle.example.AndrewWarriorManager;
import ia.exceptions.OutOfMapException;
import ia.exceptions.RuleException;

import java.util.ArrayList;
import java.util.Random;

public class BattleField {

	private static BattleField instance = new BattleField();

	private WarriorManager wm1, wm2;
	private Warrior currentWarrior, warrior1, warrior2;
	private int warrior1Count, warrior2Count;

	private FieldCell[][] cells;

	private Random random = new Random();

	private BattleField() {

		initCells();
		
		
		
	}

	public static BattleField getInstance() {
		return instance;
	}

	private void initCells() {

		int height = ConfigurationManager.getInstance().getMapHeight();
		int width = ConfigurationManager.getInstance().getMapWidth();

		cells = new FieldCell[height][width];

		for (int i = 1; i < height; i++)
			for (int j = 1; j < width; j++) {
				if (Math.abs(random.nextGaussian()) > 2)
					cells[i][j] = new FieldCell(FieldCellType.BLOCKED, i, j);
				else
					cells[i][j] = new FieldCell(FieldCellType.NORMAL, i, j);

			}
		
		
		//TODO: Ubicar SpecialItems
		
		
	}

	private void showMap() {

		int height = ConfigurationManager.getInstance().getMapHeight();
		int width = ConfigurationManager.getInstance().getMapWidth();

		for (int i = 1; i < height; i++) {
			for (int j = 1; j < width; j++)
				System.out.print(cells[i][j]);

			System.out.println();
		}

	}

	public FieldCell getFieldCell(int x, int y) throws OutOfMapException {

		if (x > cells.length - 1)
			throw new OutOfMapException();

		if (x < 0)
			throw new OutOfMapException();

		if (y > cells[0].length - 1)
			throw new OutOfMapException();

		if (y < 0)
			throw new OutOfMapException();

		return cells[x][y];
	}

	public EnemyData getEnemyPosition() {

		EnemyData enemyData = null;

		// TODO: buscar alrededor del current si esta el enemigo

		return enemyData;

	}
	
	public ArrayList<FieldCell> getSpecialItems() {
		ArrayList<FieldCell> items = new ArrayList<FieldCell>();
		
		
		
		
		
		
		return items;
	}

	private void fight() {

		//TODO:Borrar
		wm1 = new AndrewWarriorManager();
		wm2 = new AndrewWarriorManager();
		
		
		
		warrior1Count = 1;
		warrior2Count = 1;

		// Solicita los warriors

		try {
			warrior1 = wm1.getNextWarrior();
		} catch (RuleException e1) {

			// Pierde el warrior1

			e1.printStackTrace();
		}

		try {
			warrior2 = wm2.getNextWarrior();
		} catch (RuleException e) {

			// Pierde el warrior2

			e.printStackTrace();
		}

		
		//TODO: Los ubica en el mapa (poner en zonas opuestas

		try {
			warrior1.setPosition(cells[random.nextInt(cells.length)][random
					.nextInt(cells[0].length)]);
		} catch (RuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			warrior2.setPosition(cells[random.nextInt(cells.length)][random
					.nextInt(cells[0].length)]);
		} catch (RuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		//TODO: Determinar quien empieza
		currentWarrior = warrior1;

		
		/*
		// TODO: Ciclo de lucha
		do {

			
			
			
			
		} while (true);
*/
	}

	public static void main(String[] args) {

		BattleField.getInstance().showMap();
		
		

		BattleField.getInstance().fight();

	}

}
