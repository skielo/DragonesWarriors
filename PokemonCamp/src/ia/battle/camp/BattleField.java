package ia.battle.camp;

import ia.battle.entities.Action;
import ia.battle.entities.Attack;
import ia.battle.entities.Move;
import ia.battle.entities.Skip;
import ia.battle.entities.Suicide;
import ia.battle.entities.Warrior;
import ia.battle.entities.WarriorManager;
import ia.battle.example.AndrewWarriorManager;
import ia.battle.gui.Frame;
import ia.exceptions.OutOfMapException;
import ia.exceptions.RuleException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JFrame;

public class BattleField {

	private static BattleField instance = new BattleField();

	private long tick;

	private WarriorManager wm1, wm2;
	private WarriorWrapper currentWarriorWrapper, warriorWrapper1, warriorWrapper2;
	private HashMap<Warrior, WarriorWrapper> warriors;

	private FieldCell[][] cells;

	private Random random = new Random();

	private Frame frame;

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

		// TODO: Ubicar SpecialItems

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

	/**
	 * 
	 * @return
	 */
	public WarriorData getEnemyData() {

		WarriorData enemyData;

		if (currentWarriorWrapper == warriorWrapper1)
			enemyData = new WarriorData(warriorWrapper2.getWarrior().getPosition(), warriorWrapper2.getWarrior()
					.getHealth(), warriorWrapper2.getWarrior().getName());
		else
			enemyData = new WarriorData(warriorWrapper1.getWarrior().getPosition(), warriorWrapper1.getWarrior()
					.getHealth(), warriorWrapper1.getWarrior().getName());

		return enemyData;
	}

	/**
	 * Este metodo es para uso interno del framework. Su uso es ilegal.
	 * 
	 * @return
	 * @throws RuleException
	 */
	public ArrayList<Warrior> getWarriors() throws RuleException {

		ArrayList<Warrior> warriors = new ArrayList<Warrior>();

		warriors.add(warriorWrapper1.getWarrior());
		warriors.add(warriorWrapper2.getWarrior());

		return warriors;
	}

	/**
	 * Devuelve si el warrior esta en el rango de ataque del warrior actual.
	 * 
	 * @param warrior
	 * @return
	 */
	public boolean isWarriorInRange(Warrior warrior) {
		// TODO: Implementar

		return true;
	}

	public ArrayList<FieldCell> getSpecialItems() {
		ArrayList<FieldCell> items = new ArrayList<FieldCell>();

		return items;
	}

	private void fight() {

		tick = 0;
		int actionPerTurns = ConfigurationManager.getInstance().getActionsPerTurn();

		if (frame != null) {
			frame.dispose();
			frame = null;
		}

		frame = new Frame(this, 137, 50);
		frame.setSize(1024, 768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// TODO:Borrar
		wm1 = new AndrewWarriorManager();
		wm2 = new AndrewWarriorManager();

		// Solicita los warriors
		try {
			warriorWrapper1 = new WarriorWrapper(wm1.getNextWarrior());
		} catch (RuleException e1) {

			// Pierde el warrior1

			e1.printStackTrace();
		}

		try {
			warriorWrapper2 = new WarriorWrapper(wm2.getNextWarrior());
		} catch (RuleException e) {

			// Pierde el warrior2

			e.printStackTrace();
		}

		warriors = new HashMap<Warrior, WarriorWrapper>();
		warriors.put(warriorWrapper1.getWarrior(), warriorWrapper1);
		warriors.put(warriorWrapper2.getWarrior(), warriorWrapper2);

		// TODO: Los ubica en el mapa (poner en zonas opuestas

		try {
			warriorWrapper1.getWarrior().setPosition(
					cells[random.nextInt(cells.length)][random.nextInt(cells[0].length)]);
		} catch (RuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			warriorWrapper2.getWarrior().setPosition(
					cells[random.nextInt(cells.length)][random.nextInt(cells[0].length)]);
		} catch (RuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Who start the fight
		if (random.nextInt(2) == 0) {
			currentWarriorWrapper = warriorWrapper1;
		} else {
			currentWarriorWrapper = warriorWrapper2;
		}

		Action currentWarriorAction = null;

		do {

			tick++;
			if (tick % 2 == 0) {
				currentWarriorWrapper = warriorWrapper1;
			} else {
				currentWarriorWrapper = warriorWrapper2;
			}

			currentWarriorWrapper.startTurn();

			for (int i = 0; i < actionPerTurns; i++) {
				
				currentWarriorAction = currentWarriorWrapper.getWarrior().playTurn(tick, i);

				if (currentWarriorAction instanceof Move) {
					executeMoveAction((Move) currentWarriorAction);
				} else if (currentWarriorAction instanceof Attack) {
					executeAttackAction((Attack) currentWarriorAction);
				} else if (currentWarriorAction instanceof Skip) {
					executeSkipAction();
				} else if (currentWarriorAction instanceof Suicide) {
					executeSuicideAction();
				}

				frame.repaint();
			}
			

			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

		} while (true);

	}

	private void executeSkipAction() {

	}

	private void executeSuicideAction() {

	}

	private void executeAttackAction(Attack attack) {

		// int damage;
		//
		// if (defendingWarrior.getDefense() >= currentWarrior.getStrength()) {
		//
		// damage = (int) (Math.random() * 2);
		//
		// } else {
		//
		// damage = (int) Math.abs(random.nextGaussian()
		// * (Math.abs(defendingWarrior.getDefense() -
		// currentWarrior.getStrength()) / 2))
		// + Math.abs(defendingWarrior.getDefense() -
		// currentWarrior.getStrength()) / 2;
		//
		// }
		//
		// System.out.println(damage);
		// if (defendingWarrior.getActualHealth() < Math.abs(damage)) {
		//
		// defendingWarrior.setActualHealth(0);
		//
		// } else {
		// defendingWarrior.setActualHealth(defendingWarrior.getActualHealth() -
		// (damage));
		// }

	}

	private void executeMoveAction(Move action) {

		ArrayList<FieldCell> currentWarriorActionsMoveCells;
		currentWarriorActionsMoveCells = action.move();

		for (FieldCell fieldCell : currentWarriorActionsMoveCells) {

			currentWarriorWrapper.doStep();

			if (currentWarriorWrapper.getSteps() > currentWarriorWrapper.getWarrior().getSpeed())
				return;

			FieldCell nueva_pos = fieldCell;

			try {
				if (nueva_pos.getX() > 0
						&& nueva_pos.getX() < ConfigurationManager.getInstance().getMapHeight()
						&& nueva_pos.getY() > 0
						&& nueva_pos.getY() < ConfigurationManager.getInstance().getMapWidth()
						&& (getFieldCell(nueva_pos.getX(), nueva_pos.getY())).getFieldCellType() == FieldCellType.NORMAL) {
					try {

						currentWarriorWrapper.getWarrior().setPosition(nueva_pos);

					} catch (RuleException e) {
						e.printStackTrace();
					}
				}
			} catch (OutOfMapException e) {
				e.printStackTrace();
			}
		}
	}

	Warrior getWarrior1() {
		return this.warriorWrapper1.getWarrior();
	}

	Warrior getWarrior2() {
		return this.warriorWrapper2.getWarrior();
	}

	public long getTick() {
		return this.tick;
	}

	public static void main(String[] args) {

		BattleField.getInstance().showMap();

		BattleField.getInstance().fight();

	}

}
