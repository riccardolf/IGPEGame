package it.unical.igpe.ai;

import java.awt.Rectangle;
import java.util.Iterator;
import java.util.LinkedList;

import it.unical.igpe.MapUtils.World;
import it.unical.igpe.logic.AbstractDynamicObject;
import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Enemy;
import it.unical.igpe.logic.Tile;
import it.unical.igpe.net.MultiplayerWorld;
import it.unical.igpe.utils.GameConfig;
import it.unical.igpe.utils.TileType;
import it.unical.igpe.utils.Updatable;

public class EnemyManager implements Updatable {
	static LinkedList<Enemy> ens;
	public LinkedList<Bullet> bls;
	private World world;
	private MultiplayerWorld worldMP;
	private Astar astar;
	private boolean[][] map;

	public EnemyManager(World _world) {
		world = _world;
		ens = new LinkedList<Enemy>();
		ens = world.ens;
		map = new boolean[64][64];
		for (Tile tile : world.getTiles())
			if (tile.getType() == TileType.WALL)
				map[(int) (tile.getPos().x / GameConfig.TILEDIM)][(int) (tile.getPos().y / GameConfig.TILEDIM)] = true;

		astar = new Astar(64, 64) {
			protected boolean isValid(int x, int y) {
				return !map[x][y];
			}
		};
	}

	public EnemyManager(MultiplayerWorld _world) {
		worldMP = _world;
		ens = new LinkedList<Enemy>();
		for (AbstractDynamicObject o : worldMP.getEntities()) {
			if (o instanceof Enemy)
				ens.add((Enemy) o);
		}
		map = new boolean[64][64];
		for (Tile tile : world.getTiles())
			if (tile.getType() == TileType.WALL)
				map[(int) (tile.getPos().x / GameConfig.TILEDIM)][(int) (tile.getPos().y / GameConfig.TILEDIM)] = true;

		astar = new Astar(64, 64) {
			protected boolean isValid(int x, int y) {
				return !map[x][y];
			}
		};
	}

	public void update(float delta) {
		Iterator<Enemy> iter = ens.iterator();
		while (iter.hasNext()) {
			Enemy e = iter.next();
			if (!e.Alive())
				continue;
			if ((e.targetx / GameConfig.TILEDIM < GameConfig.TILEDIM && e.targetx / GameConfig.TILEDIM > 0
					&& e.targety / GameConfig.TILEDIM > 0 && e.targety / GameConfig.TILEDIM < GameConfig.TILEDIM)
					&& astar.isValid(e.targetx / GameConfig.TILEDIM, e.targety / GameConfig.TILEDIM))
				e.setPath(astar.getPath(e.startx / GameConfig.TILEDIM, e.starty / GameConfig.TILEDIM,
						e.targetx / GameConfig.TILEDIM, e.targety / GameConfig.TILEDIM));
			if (e.canShoot) {
				world.addBullet(e.fire());
			}
			e.update(delta);
		}
	}

	/**
	 * Check enemies collisions
	 * 
	 * @param _box
	 * @param act
	 * @return true if collide otherwise false
	 */
	public static boolean collisionsEnemy(Rectangle _box, Enemy act) {
		for (Enemy e : ens) {
			if (e.Alive() && e != act && e.getBoundingBox().intersects(_box))
				return true;
		}
		return false;
	}

	public LinkedList<Enemy> getEnemies() {
		return ens;
	}

	public void add(Enemy e) {
		ens.add(e);
	}

	public LinkedList<Enemy> getList() {
		return ens;
	}

}
