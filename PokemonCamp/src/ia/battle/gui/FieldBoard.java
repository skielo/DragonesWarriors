package ia.battle.gui;

import ia.battle.camp.BattleField;
import ia.battle.camp.ConfigurationManager;
import ia.battle.camp.FieldCellType;
import ia.exceptions.OutOfMapException;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class FieldBoard extends JPanel {

	private Image grass = new ImageIcon(getClass().getResource("grass.jpg")).getImage();
	private Image rocks = new ImageIcon(getClass().getResource("rocks.png")).getImage();
	private Image warrior1 = new ImageIcon(getClass().getResource("warrior1.png")).getImage();
	private Image warrior2 = new ImageIcon(getClass().getResource("warrior2.png")).getImage();
	private Image fogOfWar = new ImageIcon(getClass().getResource("fogOfWar.png")).getImage();
	private Image HPorange = new ImageIcon(getClass().getResource("HPorange.png")).getImage();
	private Image HPviolet = new ImageIcon(getClass().getResource("HPviolet.png")).getImage();
	private Image HPempty = new ImageIcon(getClass().getResource("HPempty.png")).getImage();

	private BattleField battleField;
	private int offset_x, offset_y;

	public FieldBoard(BattleField battleField, int offset_x, int offset_y) {

		this.battleField = battleField;
		this.offset_x = offset_x;
		this.offset_y = offset_y;
	}

	public void paint(Graphics g) {
		int i, j;
		super.paint(g);

		int height = ConfigurationManager.getInstance().getMapHeight();
		int width = ConfigurationManager.getInstance().getMapWidth();

		int vision1 = 3;
		int vision2 = 5;

		int health1 = 100;
		int actualHealth1 = 80;

		int health2 = 60;
		int actualHealth2 = 75;

		int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		String nombreW1 = "1";
		String nombreW2 = "2";

		try {
			x1 = battleField.getWarriors().get(0).getPosition().getX();
			y1 = battleField.getWarriors().get(0).getPosition().getY();

			x2 = battleField.getWarriors().get(1).getPosition().getX();
			y2 = battleField.getWarriors().get(1).getPosition().getY();

			nombreW1 = battleField.getWarriors().get(0).getName();
			nombreW2 = battleField.getWarriors().get(1).getName();

		} catch (Exception e) {
			e.printStackTrace();
		}

		Font ft = new Font("Times New Roman", Font.PLAIN, 10);
		g.setFont(ft);
		// dibujo turnos

		for (i = 1; i < height; i++) {// dibuja pasto y piedras

			for (j = 1; j < width; j++) {

				g.drawImage(grass, (i) * 15 + offset_x, (j) * 15 + offset_y, 15, 15, this);

				try {
					if (battleField.getFieldCell(i, j).getFieldCellType() == FieldCellType.BLOCKED) {

						g.drawImage(rocks, (i) * 15 + offset_x, (j) * 15 + offset_y, 15, 15, this);
					}
				} catch (OutOfMapException e) {

					e.printStackTrace();
				}

			}

		}

		// dibuja los guerreros
		g.drawImage(warrior1, (x1) * 15 + offset_x, (y1) * 15 + offset_y, 15, 15, this);
		g.drawImage(warrior2, (x2 * 15) + offset_x, (y2 * 15) + offset_y, 15, 15, this);

		// /dibujar barra de salud jugador 1
		Font f = new Font("Times New Roman", Font.PLAIN, 20);
		g.setFont(f);

		g.setColor(Color.MAGENTA);
		g.drawString(nombreW1, (x1 * 15) - (45 - 7) + offset_x, (y1 * 15) - 25 + offset_y);
		g.drawImage(HPempty, (x1 * 15) - (45 - 7) + offset_x, (y1 * 15) - 25 + offset_y, 90, 5, this);
		g.drawImage(HPviolet, (x1 * 15) - (45 - 7) + offset_x, (y1 * 15) - 25 + offset_y, actualHealth1 * 90 / health1,
				5, this);

		// /dibujar barra de salud jugador 2

		g.setColor(Color.ORANGE);
		g.drawString(nombreW2, (x2 * 15) - (45 - 7) + offset_x, (y2 * 15) - 30 + offset_y);
		g.drawImage(HPempty, (x2 * 15) - (45 - 7) + offset_x, (y2 * 15) - 30 + offset_y, 90, 5, this);
		g.drawImage(HPorange, (x2 * 15) - (45 - 7) + offset_x, (y2 * 15) - 30 + offset_y, actualHealth2 * 90 / health2,
				5, this);

		for (i = 1; i < height; i++) {// dibuja niebla de guerro(?)

			for (j = 1; j < width; j++) {

				if ((Math.abs(i - x1) <= vision1 && Math.abs(j - y1) <= vision1)
						|| (Math.abs(i - x2) <= vision2 && Math.abs(j - y2) <= vision2)) {

				} else {

					g.drawImage(fogOfWar, (i) * 15 + offset_x, (j) * 15 + offset_y, 15, 15, this);
				}

			}
		}

		g.setColor(Color.BLACK);
		g.drawString(+BattleField.getInstance().getTick() + "/" + "10000", +ConfigurationManager.getInstance()
				.getMapHeight() * 15 - 100 + offset_x, +offset_y);
		// dibujar datos de los warriors
		g.drawString("Warrior 1", 34, 100);
		g.drawString("Warrior 2", 900, 100);

	}
}
