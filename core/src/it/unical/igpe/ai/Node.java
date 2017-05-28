package it.unical.igpe.ai;

public class Node {
	private boolean start;
	private boolean wall;
	private boolean target;
	private int fx;
	private int fy;
	private Node parent;
	private boolean onPath;
	private Node left, right, up, down;
	private int distance;
	private int factor;
	private boolean used;
	
	public Node(int x, int y) {
		start = false;
		wall = false;
		target = false;
		fx = x;
		fy = y;
	}
	
	@Override
	public String toString() {
		return "[" + fx + " " + fy + " start " + start + " wall " + wall + " target " + target + "]";
	}
	
	public void clear() {
		start = false;
		target = false;
		wall = false;
	}
	
	public Node traceBack() {
		onPath = true;
		return parent;
	}
	
	public void calcDistance(Node target) {
		setDistance(Math.abs(fx - target.fx) + Math.abs(fy - target.fy));
	}

	public void setDirections(Node l, Node r, Node u, Node d) {
		left = l;
		right = r;
		up = u;
		down = d;
	}
	
	public void used() {
		used = true;
	}
	
	public int getFx() {
		return fx;
	}
	
	public int getFy() {
		return fy;
	}
	
	public Node getUp() {
		return up;
	}
	
	public Node getDown() {
		return down;
	}
	
	public Node getLeft() {
		return left;
	}
	
	public Node getRight() {
		return right;
	}
	
	public void setParent(Node n) {
		parent = n;		
	}
	
	public Node getParent() {
		return parent;
	}
	
	public boolean isTarget() {
		return target;
	}
	
	public boolean isWall() {
		return wall;
	}
	
	public boolean isStart() {
		return start;
	}
	
	public int getFactor() {
		return factor;
	}
	
	public void setFactor(int x) {
		factor = x;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setWall(boolean bool) {
		wall = bool;
	}
	
	public void setStart(boolean bool) {
		start = bool;
	}
	
	public void setTarget(boolean bool) {
		target = bool;
	}
	
	public boolean isPath() {
		return onPath;
	}
}
