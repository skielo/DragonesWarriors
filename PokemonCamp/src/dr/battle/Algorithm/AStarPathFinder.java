package dr.battle.Algorithm;

import ia.battle.camp.BattleField;
import ia.battle.camp.FieldCell;
import ia.exceptions.OutOfMapException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import dr.battle.Structure.*;

public class AStarPathFinder extends PathFinder {

	private HashSet<Square> closedlist = new HashSet<Square>();

	public class SquareComparator implements Comparator<Square> {

		/**
		 * This part of code adapted from the following example: http:
		 * //stackoverflow.com/questions/683041/java-how-do-i-use
		 * -a-priorityqueue
		 * 
		 * @param a
		 * @param b
		 */
		@Override
		public int compare(Square a, Square b) {
			if (a.getF_dist() < b.getF_dist())
				return -1;

			if (a.getF_dist() > b.getF_dist())
				return 1;

			return 0;
		}
	}

	/*
	 * This comparator code taken from:
	 * http://stackoverflow.com/questions/683041
	 * /java-how-do-i-use-a-priorityqueue
	 */
	private Comparator<Square> comparator = new SquareComparator();
	private PriorityQueue<Square> openlist = new PriorityQueue<Square>(10,
			comparator);

	/**
	 * This is a constructor for the algorithm. it generates the solution at the
	 * same time it is created.
	 * 
	 * @param maze
	 *            the maze to be solved
	 */
	public AStarPathFinder(Maze maze) {
		super();
		mazeCheck(maze);
		this.maze = maze;
		findPath();
	}

	/**
	 * For this part, I consulted the pseudo code from:
	 * http://en.wikibooks.org/wiki
	 * /Artificial_Intelligence/Search/Heuristic_search/Astar_Search
	 */
	public void findPath() {
		maze.setHeuristicMode(maze.getHeuristicMode());
		// some declaring just to make things more convenient.
		Square start = maze.getStart();
		Square end = maze.getEnd();

		openlist.add(start);

		while (!closedlist.contains(end)) {
			Square current = openlist.peek();
			if (openlist.size() == 0) {
				closedlist.clear();
				System.err.println("No result found");
				return;
			}
			if (current.equals(end)) { // the Path found.
				closedlist.add(openlist.poll());
				System.out.println("Path found.");
				return;
			} else {
				closedlist.add(openlist.poll());

				List<Square> neighbors = maze.findNeighbor(current);
				for (Square neighbor : neighbors) {
					if (closedlist.contains(neighbor)) {
						testBetter(current, neighbor);
					} else if (openlist.contains(neighbor)) {
						testBetter(current, neighbor);
					} else {
						openlist.add(neighbor);
						neighbor.setParent(current);
						maze.updateAll(neighbor);
					}
				}
			}
		}
	}

	public ArrayList<FieldCell> getResult() {
		return this.getMoves();
	}
	
	private ArrayList<FieldCell> getMoves()
	{
		ArrayList<FieldCell> retval = new ArrayList<>();
		Object[] list = closedlist.toArray();
		ArrayList<Square> tem = new ArrayList<>();
		
		for (int i = 0; i < list.length; i++) {
			tem.add((Square)list[i]);
		}
		//Collections.sort(tem);
		Square temp = tem.get(tem.indexOf(maze.getEnd()));
		this.AddToList(temp, retval);
		/*
		for (Square item : tem) {
			try {
				retval.add(BattleField.getInstance().getFieldCell(item.getX(),item.getY()));
			} catch (OutOfMapException ex) {
				ex.printStackTrace();
			}
		}
		
		for (int i = 0; i < list.length; i++) {
			try {
				retval.add(BattleField.getInstance().getFieldCell(((Square)list[i]).getX(),((Square)list[i]).getY()));
			} catch (OutOfMapException ex) {
				ex.printStackTrace();
			}
		}
		 */
		Collections.reverse(retval);
		//retval.remove(maze.getStart());
		return retval;
	} 
	
	private void AddToList(Square s,ArrayList<FieldCell> retval)
	{
		if(s.compareTo(maze.getStart())==0)
			return;
		try {
			retval.add(BattleField.getInstance().getFieldCell(s.getX(),s.getY()));
			this.AddToList(s.getParent(), retval);
		} catch (OutOfMapException ex) {
			ex.printStackTrace();
		}
	}

	public void showResult() {
		int x, y;
		for (Square sq : closedlist) {
			x = sq.getX();
			y = sq.getY();
			System.out.print("(" + x + ", " + y + ")");
		}
	}

	/**
	 * @param current
	 * @param neighbor
	 *            this method compares if a certain node is better off when it
	 *            changes its parent to current node
	 */

	private void testBetter(Square current, Square neighbor) {
		Square test = new Square(neighbor);
		test.setParent(current);
		maze.update_Gdist(test);
		if (test.getG_dist() < neighbor.getG_dist()) {
			neighbor.setParent(current);
			maze.update_Gdist(neighbor);
			maze.update_Fdist(neighbor);
		}
	}
}
