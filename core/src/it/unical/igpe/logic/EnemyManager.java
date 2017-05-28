package it.unical.igpe.logic;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.ai.MapAi;
import it.unical.igpe.game.World;


public class EnemyManager {
	LinkedList<Enemy> ens;
	public LinkedList<Bullet> bls;
	private World world;
	private float currentUpdate = 0;
	private MapAi map;

	public EnemyManager(World _world) {
		world = _world;
		ens = new LinkedList<Enemy>();
		ens.add(new Enemy(new Vector2(500, 500), world.getPlayer()));
		map = new MapAi(world);
	}

	public void update(float delta) {
		if (currentUpdate > 1f) {
			currentUpdate = 0;
			Iterator<Enemy> iter = ens.iterator();
			while (iter.hasNext()) {
				Enemy e = iter.next();
				System.out.println(e.toString());
				map.init(e);
				map.calculatePath();
				e.setPath(map.getPath());
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
