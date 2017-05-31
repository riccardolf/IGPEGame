package it.unical.igpe.ai;

import java.util.Iterator;
import java.util.LinkedList;

import it.unical.igpe.game.World;
import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Enemy;
import it.unical.igpe.logic.Tile;
import it.unical.igpe.tools.TileType;


public class EnemyManager {
	LinkedList<Enemy> ens;
	public LinkedList<Bullet> bls;
	private World world;
	private float currentUpdate = 0;
	private Astar astar;
	private boolean[][] map;

	public EnemyManager(World _world) {
		world = _world;
		ens = new LinkedList<Enemy>();
		ens = world.ens;
		map = new boolean[64][64];
		for (Tile tile : world.getTiles()) {
			if(tile.getType() == TileType.WALL) {
				map[(int) (tile.getPos().y / 64)][(int) (tile.getPos().x / 64)] = true;
			}
		}
		
		for(int i = 0; i < 64; i++) {
			for(int j = 0; j < 64; j++) {
				System.out.print(map[i][j] ? " 1 " : " 0 ");
			}
			System.out.println();
		}
		
		astar = new Astar(64, 64) {
			protected boolean isValid(int x, int y) {
				return !map[x][y];
			}
		};
	}

	public void update(float delta) {
		if (currentUpdate > 0.3f) {
			currentUpdate = 0;
			Iterator<Enemy> iter = ens.iterator();
			while (iter.hasNext()) {
				Enemy e = iter.next();
				int startx = e.getBoundingBox().x + 32;
				int starty = e.getBoundingBox().y + 32;
				int targetx = world.getPlayer().getBoundingBox().x + 32;
				int targety = world.getPlayer().getBoundingBox().y + 32;
				e.setPath(astar.getPath(startx / 64, starty / 64, targetx / 64, targety / 64));
				if (!e.update())
					iter.remove();
			}
		}
		else
			currentUpdate += delta;
	}

	public void add(Enemy e) {
		ens.add(e);
	}

	public LinkedList<Enemy> getList() {
		return ens;
	}
}
