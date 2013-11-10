package dr.battle.Structure;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;

import javax.imageio.ImageIO;

public class Exporter {
	private int height;
	private int width;

	private Maze maze;
	private String path = ".";
	private HashSet<Square> solution;

	private BufferedImage image;

	/**
	 * The basic constructor. the image will be saved to current directory
	 * 
	 * @param solution
	 *            the solution to the maze
	 * @param maze
	 *            the maze to be solved
	 */
	public Exporter(HashSet<Square> solution, Maze maze) {
		super();
		this.solution = solution;
		this.maze = maze;
		this.height = maze.getHeight();
		this.width = maze.getWidth();
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}

	/**
	 * The constructor. In order to generate an image, we need: maze, solution,
	 * and savePath
	 * 
	 * @param solution
	 *            the solution to the maze
	 * @param maze
	 *            the maze
	 * @param path
	 *            the path to save the maze
	 */
	public Exporter(HashSet<Square> solution, Maze maze, String path) {
		this(solution, maze);
		this.path = path;
	}

	public void render() {
		// check sources
		if (maze == null) {
			System.err.println("Maze not specified");
			throw new NullPointerException();
		}

		if (solution == null) {
			System.err.println("Solution not found");
			throw new NullPointerException();
		}

		drawMaze();
		drawSolPath();
		saveToFile();
	}

	private void drawMaze() {
		Square temp;
		Color b = Color.black;
		Color w = Color.white;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				temp = maze.getSquare(i, j);
				if (temp.isTraversable())
					image.setRGB(j, i, b.getRGB());
				else
					image.setRGB(j, i, w.getRGB());
			}
		}
	}

	private void drawSolPath() {
		Color r = Color.red;
		for (Square node : solution) {
			image.setRGB(node.getY(), node.getX(), r.getRGB());
		}
	}

	private void saveToFile() {

		File file = new File("./solution.png");

		/*
		 * http://www.java-tips.org/java-se-tips/javax.imageio/how-to-save-a-
		 * bufferedimage-to-a-png-file.html
		 */
		try {
			ImageIO.write(image, "png", file);
		} catch (Exception e) {
			System.err.println("Error writing to file");
		}
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}
}
