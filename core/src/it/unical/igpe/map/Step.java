package it.unical.igpe.map;

public class Step {
	private int x;
	private int y;

	public Step(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int hashCode() {
		return x * y;
	}

	public boolean equals(Object other) {
		if (other instanceof Step) {
			Step o = (Step) other;
			return (o.x == x) && (o.y == y);
		}
		return false;
	}
}
