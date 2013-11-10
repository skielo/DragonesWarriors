package dr.battle.Structure;

public class Square implements Comparable<Square> {

	private boolean Traversable;
	private int x, y;

	private int distance = Integer.MAX_VALUE;
	private int g_dist, h_dist, f_dist;
	private Square parent;
	private Square previous;

	public Square(int x, int y, boolean traversable) {
		if (x < 0 || y < 0)
			System.err.println("Invalid input: (" + x + ", " + y + ", "
					+ traversable + ")");
		this.x = x;
		this.y = y;
		this.Traversable = traversable;
	}

	public Square(Square sq) {
		this.x = sq.getX();
		this.y = sq.getY();
		this.f_dist = sq.getF_dist();
		this.g_dist = sq.getG_dist();
		this.h_dist = sq.getH_dist();
		this.Traversable = sq.isTraversable();
		this.parent = sq.parent;
	}

	public int getNeighborDist(Square n) {
		if (n == null) {
			System.err.println("Neighbor does not exist");
			throw new NullPointerException();
		}
		if (!n.isTraversable()) {
			System.err.println("Unpassable Neighbor: (" + n.getX() + ", "
					+ n.getY() + ", " + n.isTraversable() + ")");
			return Integer.MAX_VALUE;
		}

		int a = Math.abs(n.getX() - this.x);
		int b = Math.abs(n.getY() - this.y);
		if (a + b > 2) {
			System.err.println("Not a Neighbor: (" + n.getX() + ", " + n.getY()
					+ ", " + n.isTraversable() + ")");
			return Integer.MAX_VALUE;
		}
		if (a + b == 2)
			return 141;
		else
			return 100;
	}

	public int getDistance() {
		return distance;
	}

	public int getF_dist() {
		return f_dist;
	}

	public int getG_dist() {
		return g_dist;
	}

	public int getH_dist() {
		return h_dist;
	}

	public Square getParent() {
		return parent;
	}

	public Square getPrevious() {
		return previous;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isTraversable() {
		return Traversable;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setF_dist(int f_dist) {
		this.f_dist = f_dist;
	}

	public void setG_dist(int g_dist) {
		this.g_dist = g_dist;
	}

	public void setH_dist(int h_dist) {
		this.h_dist = h_dist;
	}

	public void setParent(Square parent) {
		this.parent = parent;
	}

	public void setPrevious(Square previous) {
		this.previous = previous;
	}

	public void setTraversable(boolean traversable) {
		Traversable = traversable;
	}

	@Override
	public int compareTo(Square o) {
		// TODO Auto-generated method stub
		return (this.x == o.x && this.y == o.y)?0:
			(this.x < o.x && this.y <= o.y)||(this.x > o.x && this.y < o.y)?-1:
				(this.x > o.x && this.y >= o.y)||(this.x < o.x && this.y > o.y)?1:0;
	}
	
	
}
