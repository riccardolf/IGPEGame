package it.unical.igpe.ai;

import com.badlogic.gdx.utils.BinaryHeap;
import com.badlogic.gdx.utils.BinaryHeap.Node;
import com.badlogic.gdx.utils.IntArray;

public class Astar {
	private final int width, height;
	private final BinaryHeap<PathNode> open;
	private final PathNode[] nodes;
	int runID;
	private final IntArray path = new IntArray();
	private int targetX, targetY;

	public Astar(int width, int height) {
		this.width = width;
		this.height = height;
		open = new BinaryHeap<PathNode>(width * 4, false);
		nodes = new PathNode[width * height];
	}

	/** Returns x,y pairs that are the path from the target to the start. */
	public IntArray getPath(int startX, int startY, int targetX, int targetY) {
		int count = 0;
		this.targetX = targetX;
		this.targetY = targetY;

		path.clear();
		open.clear();

		runID++;
		if (runID < 0)
			runID = 1;

		int index = startY * width + startX;
		PathNode root = nodes[index];
		if (root == null) {
			root = new PathNode(0);
			root.x = startX;
			root.y = startY;
			nodes[index] = root;
		}
		root.parent = null;
		root.pathCost = 0;
		open.add(root, 0);

		int lastColumn = width - 1, lastRow = height - 1;
		@SuppressWarnings("unused")
		int i = 0;
		while (open.size > 0) {
			count++;
			PathNode node = open.pop();
			if (node.x == targetX && node.y == targetY) {
				while (node != root) {
					path.add(node.x);
					path.add(node.y);
					node = node.parent;
				}
				break;
			}
			node.closedID = runID;
			int x = node.x;
			int y = node.y;
			if (x < lastColumn) {
				addNode(node, x + 1, y, 10);
				if (y < lastRow)
					addNode(node, x + 1, y + 1, 14);
				if (y > 0)
					addNode(node, x + 1, y - 1, 14);
			}
			if (x > 0) {
				addNode(node, x - 1, y, 10);
				if (y < lastRow)
					addNode(node, x - 1, y + 1, 14);
				if (y > 0)
					addNode(node, x - 1, y - 1, 14);
			}
			if (y < lastRow)
				addNode(node, x, y + 1, 10);
			if (y > 0)
				addNode(node, x, y - 1, 10);
			i++;
		}
		System.out.println(count);
		return path;
	}

	private void addNode(PathNode parent, int x, int y, int cost) {
		if (!isValid((int) x, (int) y))
			return;

		int pathCost = parent.pathCost + cost;
		int score = pathCost + Math.abs(x - targetX) + Math.abs(y - targetY);

		int index = y * width + x;
		PathNode node = nodes[(int) index];
		if (node != null && node.runID == runID) { // Node already encountered
													// for this run.
			if (node.closedID != runID && pathCost < node.pathCost) {
				// Node isn't closed and new cost is lower.
				// Update the existing node.
				open.setValue(node, score);
				node.parent = parent;
				node.pathCost = pathCost;
			}
		} else {
			// Use node from the cache or create a new one
			if (node == null) {
				node = new PathNode(0);
				node.x = x;
				node.y = y;
				nodes[(int) index] = node;
			}
			open.add(node, score);
			node.runID = runID;
			node.parent = parent;
			node.pathCost = pathCost;
		}
	}

	protected boolean isValid(int x, int y) {
		return true;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	static private class PathNode extends Node {
		int runID, closedID, pathCost;
		int x, y;
		PathNode parent;

		public PathNode(int value) {
			super(value);
		}
	}
}
