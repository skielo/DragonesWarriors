package dr.battle.Algorithm;

import dr.battle.Structure.*;;

public class PathFinder {
	protected Maze maze;

	public class InvalidStartException extends RuntimeException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2930117334854426626L;

		public InvalidStartException() {
		}
	}

	public class InvalidEndException extends RuntimeException {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3778179540439073010L;

		public InvalidEndException() {
		}
	}

	public void mazeCheck(Maze maze) {
		if (maze == null) {
			System.err.println("Invalid Maze to solve");
			throw new NullPointerException();
		}

		if (maze.getStart() == null) {
			System.err.println("Maze has no start.");
			throw new InvalidStartException();
		}

		if (maze.getEnd() == null) {
			System.err.println("Maze has no end.");
			throw new InvalidEndException();
		}

		if (maze.getStart().isTraversable() == false) {
			System.err.println("Maze start is unpassable.");
			throw new InvalidStartException();
		}

		if (maze.getEnd().isTraversable() == false) {
			System.err.println("Maze end is unreachable.");
			throw new InvalidEndException();
		}
	}
}
