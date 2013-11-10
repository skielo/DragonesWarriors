package dr.battle.Algorithm.Heuristic;

import dr.battle.Structure.*;;

public class Diagonal extends HeuristicCalculator {

	@Override
	public int getDistance(Square a, Square b) {
		int x = Math.abs(a.getX() - b.getX());
		int y = Math.abs(a.getY() - b.getY());
		if (x > y)
			return 100 * (x - y) + 141 * y;
		else
			return 100 * (y - x) + 141 * x;
	}

}
