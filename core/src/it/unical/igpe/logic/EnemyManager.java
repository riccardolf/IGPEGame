package it.unical.igpe.logic;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;


public class EnemyManager {
	LinkedList<Enemy> ens;
	Player target;
	public LinkedList<Bullet> bls;
	private Player player;
	private float currentUpdate = 0;

	public EnemyManager(Player _player) {
		ens = new LinkedList<Enemy>();
		player = _player;
		ens.add(new Enemy(new Vector2(500, 500), player));
	}

	public void update(float delta) {
		if (currentUpdate > 1f) {
			currentUpdate = 0;
			Iterator<Enemy> iter = ens.iterator();
			while (iter.hasNext()) {
				Enemy e = iter.next();
				e.toString();
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
