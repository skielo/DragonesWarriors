package dr.battle.Algorithm;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import dr.battle.Structure.*;

public class DijkstraPathFinder extends PathFinder {
	/*
	 * This comparator code taken from:
	 * http://stackoverflow.com/questions/683041
	 * /java-how-do-i-use-a-priorityQueue
	 */
	private Comparator<Square> comparator = new SquareComparator();
	private PriorityQueue<Square> queue = new PriorityQueue<Square>(10,
			comparator);
	// private HashSet<Square> finished = new HashSet<Square>();
	private HashSet<Square> solution = new HashSet<Square>();

	public DijkstraPathFinder(Maze maze) {
		super();
		mazeCheck(maze);

		this.maze = maze;
		for (int i = 0; i < maze.getMap().length; i++) {
			for (int j = 0; j < maze.getMap()[0].length; j++) {
				if (maze.getSquare(i, j).isTraversable()) {
					if (maze.getSquare(i, j) == maze.getStart())
						maze.getSquare(i, j).setDistance(0);
					else
						maze.getSquare(i, j).setDistance(Integer.MAX_VALUE);
					queue.add(maze.getSquare(i, j));
				}
			}
		}

		DijkstraFindPath();
	}

	/*
	 * I used the pseudo code from the following source:
	 * http://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Pseudocode
	 */
	public void DijkstraFindPath() {
		/* some preparation */
		Square end = maze.getEnd();

		/* actual implementation */
		while (!queue.isEmpty()) {
			Square u = queue.poll();

			if (u == end)
				break;
			if (u.getDistance() == Integer.MAX_VALUE) {
				System.err.println("No result found");
				return;
			}

			List<Square> neighbors = maze.findNeighbor(u);
			for (Square neighbor : neighbors) {
				int alt = u.getDistance() + u.getNeighborDist(neighbor);
				if (alt < neighbor.getDistance()) {
					queue.remove(neighbor);
					neighbor.setDistance(alt);
					neighbor.setPrevious(u);
					queue.offer(neighbor);
				}
			}
		}

		if (end.getPrevious() == null) {
			System.err.println("No result found");
			return;
		}
		Square sq = end;
		while (sq != null) {
			solution.add(sq);
			sq = sq.getPrevious();
		}
	}

	public void showResult() {
		int x, y;
		for (Square sq : solution) {
			x = sq.getX();
			y = sq.getY();
			System.out.println("(" + x + ", " + y + ")");
		}
	}

	public class SquareComparator implements Comparator<Square> {
		/**
		 * @param a
		 * @param b
		 *            This part of code adapted from the following example:
		 *            http://stackoverflow
		 *            .com/questions/683041/java-how-do-i-use-a-priorityqueue
		 */

		@Override
		public int compare(Square a, Square b) {
			if (a.getDistance() < b.getDistance())
				return -1;
			if (a.getDistance() > b.getDistance())
				return 1;
			return 0;
		}
	}

	public HashSet<Square> getResult() {
		// TODO Auto-generated method stub
		return solution;
	}
}
