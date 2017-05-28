package it.unical.igpe.ai;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import it.unical.igpe.game.World;
import it.unical.igpe.logic.Enemy;
import it.unical.igpe.logic.Tile;
import it.unical.igpe.tools.TileType;

public class MapAi {
	private World world;
	private Node[][] nodeList;
	private Node start;
	private Node target;

	public MapAi(World _world) {
		world = _world;
		nodeList = new Node[64][64];
	}

	public void init(Enemy e) {
		resetNodes();
		target = nodeList[world.getPlayer().getBoundingBox().x / 64][world.getPlayer().getBoundingBox().y / 64];
		start = nodeList[e.getBoundingBox().x / 64][e.getBoundingBox().y / 64];
		if(start == target)
			return;
		start.setStart(true);
		target.setTarget(true);
		for (Tile tile : world.getTiles()) {
			if (tile.getType() == TileType.WALL)
				nodeList[tile.getBoundingBox().x / 64][tile.getBoundingBox().y / 64].setWall(true);
		}
	}

	public void resetNodes() {
		target = null;
		start = null;
		for (int i = 0; i < 64; i++) {
			for (int j = 0; j < 64; j++) {
				nodeList[i][j] = new Node(i, j);
			}
		}

		for (int i = 0; i < 64; i++) {
			for (int j = 0; j < 64; j++) {
				int u = j - 1;
				int d = j + 1;
				int l = i - 1;
				int r = i + 1;

				Node up = null, down = null, left = null, right = null;

				if (u >= 0 && u < nodeList[i].length)
					up = nodeList[i][u];
				if (d >= 0 && d < nodeList[i].length)
					down = nodeList[i][d];
				if (l >= 0 && l < nodeList.length)
					left = nodeList[l][j];
				if (r >= 0 && r < nodeList.length)
					right = nodeList[r][j];

				nodeList[i][j].setDirections(left, right, up, down);
			}
		}
	}

	public void calculatePath() {
		boolean calculated = false;
		for (int i = 0; i < 64; i++) {
			for (int j = 0; j < 64; j++) {
				nodeList[i][j].calcDistance(target);
			}
		}

		ArrayList<Node> closed = new ArrayList<Node>();
		ArrayList<Node> open = new ArrayList<Node>();
		open.add(start);
		while (!calculated) {
			for (int i = 0; i < open.size(); i++) {
				if (calculated)
					break;
				Node s = open.get(i);
				Node up = s.getUp();
				Node down = s.getDown();
				Node left = s.getLeft();
				Node right = s.getRight();
				if (up != null && up.isTarget())
					calculated = true;
				else if (down != null && down.isTarget())
					calculated = true;
				else if (left != null && left.isTarget())
					calculated = true;
				else if (right != null && right.isTarget())
					calculated = true;
				else {
					if (up != null && !closed.contains(up) && !up.isWall()) {
						up.setParent(s);
						up.used();
						open.add(up);
					}
					if (down != null && !closed.contains(down) && !down.isWall()) {
						down.setParent(s);
						down.used();
						open.add(down);
					}
					if (left != null && !closed.contains(left) && !left.isWall()) {
						left.setParent(s);
						left.used();
						open.add(left);
					}
					if (right != null && !closed.contains(right) && !right.isWall()) {
						right.setParent(s);
						right.used();
						open.add(right);
					}

					closed.add(s);
				}

				if (calculated) {
					Node n = s.traceBack();
					while (n != null) {
						n = n.traceBack();
					}
				}
			}
		}
	}

	public Array<Vector2> getPath() {
		Array<Vector2> path = new Array<Vector2>();
		for (int i = 0; i < 64; i++) {
			for (int j = 0; j < 64; j++) {
				if (nodeList[i][j].isPath()) {
					Vector2 pos = new Vector2(nodeList[i][j].getFx() * 64, nodeList[i][j].getFy() * 64);
					System.out.println(pos.toString());
					path.add(pos);
				}
			}
		}
		return path;
	}

}
