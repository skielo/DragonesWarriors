package dr.battle.Structure;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import dr.battle.Algorithm.Heuristic.*;

/**
 * This is the underlying structure used to implement various kinds of maze.
 * 
 * @author dai13
 * @param map
 *            - the two-dimensional array that holds the maze
 * @param start
 *            - the starting point of the maze
 * @param end
 *            - the ending point of the maze
 */

public class Maze {

	private Square[][] map;
	private Square start;
	private Square end;
	private int heuristicMode = 1;
	private HeuristicCalculator dc;

	/* constructors */

	/**
	 * This is a constructor not initially setting start and end user should
	 * later manually set the start and end, otherwise the system would throw an
	 * exception. If the map is only an empty pointer, this constructor
	 * automatically creates each Square and set them to be viable when run.
	 * 
	 * @param map
	 */
	public Maze(Square[][] map) {
		super();
		this.map = map;

		/* if the map is empty, create a new Square object for each coordinate */
		if (map[0][0] == null) {
			for (int i = 0; i < map.length; i++)
				for (int j = 0; j < map[0].length; j++) {
					map[i][j] = new Square(i, j, true);
				}
		}
	}

	/**
	 * This is the Maze class constructor that takes in a square[][] map as the
	 * maze and the starting and ending point of the maze. one thing to note is
	 * that, this constructor automatically creates each Square and set them to
	 * be viable when run.
	 * 
	 * @param map
	 *            the underlying 2 dimensional array of Squares
	 * @param startx
	 *            x coordinate of Start
	 * @param starty
	 *            y coordinate of Start
	 * @param endx
	 *            x coordinate of End
	 * @param endy
	 *            y coordinate of End
	 */
	public Maze(Square[][] map, int startx, int starty, int endx, int endy) {
		this(map);
		this.start = map[startx][starty];
		this.end = map[endx][endy];
	}

	/**
	 * This constructor takes in the name of an image file and convert that into
	 * the Maze structure. user would also have to set the start and end along
	 * with the filename.
	 * 
	 * @param fname
	 * @return true if the image is created successfully, false if not.
	 */
	public Maze(String fname) {
		Square[][] myMap = ImageParser(fname);
		if (myMap != null)
			this.map = myMap;
		else
			System.err.println("Aborted because of image parsing error.");
	}

	/* helper functions */

	/**
	 * This function takes in a string path, and check to see if this file is a
	 * valid image file. Then the function parse the image into a maze.
	 * 
	 * http://www.exampledepot.com/egs/javax.imageio/BasicImageRead.html
	 * https://wiki.engr.illinois.edu/display/cs242fa12/Assignment+1.2
	 * 
	 * @param path
	 * @return map[][] of the image
	 */
	private Square[][] ImageParser(String path) {
		BufferedImage image;
		File file = new File(path);
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.err.println("Cannot read " + path + ": " + e.getMessage());
			return null;
		}

		// if no error occur

		int height = image.getHeight();
		int width = image.getWidth();

		Square[][] myMap = new Square[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				myMap[i][j] = new Square(i, j, true);
				boolean isPassable = judgeTraversability(image, i, j);
				if (!isPassable)
					myMap[i][j].setTraversable(false);
			}
		}

		return myMap;
	}

	/**
	 * this function judges the traversability of the specific node. returns
	 * true if traversable, false otherwise.
	 * 
	 * @param i
	 *            row of the square
	 * @param j
	 *            column of the square
	 * @return traversability
	 */
	private boolean judgeTraversability(BufferedImage image, int i, int j) {
		Color c = new Color(image.getRGB(j, i));
		int distance = distanceFromWhite(c);
		if (distance < 33)
			return false;
		return true;
	}

	/**
	 * This functions calculate Color c's color distance from white color
	 * 
	 * @param c
	 *            this is the color we are going to compare with white
	 * @return the distance from Color c to white
	 */
	private int distanceFromWhite(Color c) {
		return calculateColorDistance(Color.white, c);
	}

	/**
	 * This functions calculates the distance between two colors.
	 * 
	 * @param c1
	 *            color 1 to be compared
	 * @param c2
	 *            color 2 to be compared
	 * @return the distance between color 1 and color 2
	 */
	private int calculateColorDistance(Color c1, Color c2) {
		int r1 = c1.getRed();
		int g1 = c1.getGreen();
		int b1 = c1.getBlue();

		int r2 = c2.getRed();
		int g2 = c2.getGreen();
		int b2 = c2.getBlue();

		return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
	}

	/**
	 * @param i
	 *            the row of the square
	 * @param j
	 *            the column of the square
	 * 
	 *            returns true if the provided coordinate is out-of-bound
	 */
	private boolean checkBounds(int i, int j) {
		return i < 0 || i >= map.length || j < 0 || j >= map[0].length;
	}

	/**
	 * 
	 * @param square
	 *            the target square that we are trying to find its neighbors
	 * 
	 *            This function finds all the neighboring walkable squares and
	 *            add them to an ArrayList.
	 */
	public ArrayList<Square> findNeighbor(Square square) {
		if (square == null) {
			System.err.println("This is a NULL Square");
			return null;
		} else {
			ArrayList<Square> neighbors = new ArrayList<Square>();
			int x = square.getX();
			int y = square.getY();

			for (int i = x - 1; i <= x + 1; i++) {
				for (int j = y - 1; j <= y + 1; j++) {
					if (i == x && j == y)
						continue;
					if (checkBounds(i, j))
						continue;
					if (!((Square) this.getSquare(i, j)).isTraversable())
						continue;

					neighbors.add(this.getSquare(i, j));
				}
			}
			return neighbors;
		}
	}

	/**
	 * set the end point to the maze
	 * 
	 * @param end
	 */
	public void setEnd(Square end) {
		this.end = end;
	}

	/**
	 * set the start point for the maze
	 * 
	 * @param start
	 */
	public void setStart(Square start) {
		this.start = start;
	}

	/**
	 * get the start point of the maze
	 * 
	 * @return start - start point of the maze
	 */
	public Square getStart() {
		return start;
	}

	/**
	 * get the end point
	 * 
	 * @return end - end the end point of the maze
	 */
	public Square getEnd() {
		return end;
	}

	public Square[][] getMap() {
		return map;
	}

	/**
	 * get the height of the maze
	 * 
	 * @return rows
	 */
	public int getHeight() {
		if (map != null)
			return map.length;
		else
			return 0;
	}

	/**
	 * get the column number of the maze
	 * 
	 * @return column
	 */
	public int getWidth() {
		if (getHeight() == 0)
			return 0;
		if (map[0] == null)
			return 0;
		return map[0].length;
	}

	/**
	 * this function returns the (i, j)th square on
	 * 
	 * @param i
	 *            the row of the square
	 * @param j
	 *            the column of the square
	 * @return the demanded square
	 */
	public Square getSquare(int i, int j) {
		if (!checkBounds(i, j)) {
			return map[i][j];
		} else {
			System.out.println("Trying to get Invalid Square: (" + i + ", " + j
					+ ").");
			return null;
		}
	}

	/* Special functions used only by AStar Algorithm */

	/**
	 * return the heuristic distance between two squares. this should be called
	 * after dc has been assigned a specific method.
	 * 
	 * @param a
	 *            square a
	 * @param b
	 *            square b
	 * @return the heuristic distance between a and b
	 */
	public int getHeuristicDistance(Square a, Square b) {
		return dc.getDistance(a, b);
	}

	/**
	 * update the F distance of the specific square
	 * 
	 * @param sq
	 *            the target square
	 */
	public void update_Fdist(Square sq) {
		sq.setF_dist(sq.getG_dist() + sq.getH_dist());
	}

	/**
	 * update the square's G distance
	 * 
	 * @param sq
	 *            the target square
	 */
	public void update_Gdist(Square sq) {
		if (sq == start)
			return;

		Square parent = sq.getParent();
		int value = Math.abs(parent.getX() - sq.getX())
				+ Math.abs(parent.getY() - sq.getY());
		int dist;
		if (value == 1)
			dist = 100;
		else
			dist = 141;

		sq.setG_dist(parent.getG_dist() + dist);
	}

	/**
	 * update the square's heuristic distance
	 * 
	 * @param sq
	 *            target square
	 */
	public void update_Hdist(Square sq) {
		if (sq == this.end)
			return;
		sq.setH_dist(getHeuristicDistance(sq, end));
	}

	/**
	 * update a specific square's H,G,F distances
	 * 
	 * @param square
	 *            the target Square to be updated
	 */
	public void updateAll(Square square) {
		update_Hdist(square);
		update_Gdist(square);
		update_Fdist(square);
	}

	/**
	 * get the current Maze's preferred heuristicMode
	 * 
	 * @return heuristicMode
	 */
	public int getHeuristicMode() {
		return heuristicMode;
	}

	/**
	 * set the heutisticMode for the Maze. Also load the preferred distance
	 * calculation method to dc
	 * 
	 * @param heuristicMode
	 *            the preferred heurtisticMode
	 */
	public void setHeuristicMode(int heuristicMode) {
		this.heuristicMode = heuristicMode;
		switch (heuristicMode) {
		case 1:
			dc = new Manhattan();
			break;
		case 2:
			dc = new Diagonal();
			break;
		default:
			dc = new Manhattan();
		}
	}

}
