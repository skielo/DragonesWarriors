package ia.battle.gui;

import ia.battle.camp.BattleField;

import javax.swing.JFrame;

public class Frame extends JFrame {

	private FieldBoard tablero;

	public Frame(BattleField battleField, int offset_x, int offset_y) {

		tablero = new FieldBoard(battleField, offset_x, offset_y);
		setTitle("Battle Camp");
		setContentPane(tablero);
		// setLocation(400, 0);
		setResizable(false);
	}

	public FieldBoard getTablero() {
		return tablero;
	}
}