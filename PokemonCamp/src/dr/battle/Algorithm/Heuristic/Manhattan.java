package dr.battle.Algorithm.Heuristic;

import dr.battle.Structure.*;;

public class Manhattan extends HeuristicCalculator {

	/**
	 * The standard heuristic for a square grid is the Manhattan distance. Look
	 * at your cost function and find the minimum cost D for moving from one
	 * space to an adjacent space. The heuristic on a square grid where you can
	 * move in 4 directions should be 100 times the Manhattan distance:
	 */
	@Override
	public int getDistance(Square a, Square b) {
		int x_1 = a.getX();
		int x_2 = b.getX();
		int y_1 = a.getY();
		int y_2 = b.getY();

		return 100 * (Math.abs(x_1 - x_2) + Math.abs(y_1 - y_2));
	}

}
