package it.unical.igpe.ai;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

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
	private boolean[] map;

	public EnemyManager(World _world) {
		world = _world;
		ens = new LinkedList<Enemy>();
		ens.add(new Enemy(new Vector2(500, 500), world.getPlayer()));
		map = new boolean[16 * 16];
		for (Tile tile : world.getTiles()) {
			if(tile.getType() == TileType.WALL) {
				map[(int) (tile.getPos().x / 64 * 16 + tile.getPos().y / 64)] = true;
			}
		}
		
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
				System.out.print(map[i + j * 16] ? " 1 " : " 0 ");
			}
			System.out.println();
		}
		
		astar = new Astar(16, 16) {
			protected boolean isValid(int x, int y) {
				return !map[x + y * 16];
			}
		};
	}

	public void update(float delta) {
		if (currentUpdate > 1f) {
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
