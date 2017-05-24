package it.unical.igpe.logic;

import java.util.Iterator;
import java.util.LinkedList;

public class EnemyManager extends Thread {
	LinkedList<Enemy> ens;
	Player target;
	public LinkedList<Bullet> bls;
	@SuppressWarnings("unused")
	private Player player;

	public EnemyManager(Player _player) {
		ens = new LinkedList<Enemy>();
		player = _player;
	}
	
	public void update() {
		this.run();
	}

	@Override
	public void run() {
		Iterator<Enemy> iter = ens.iterator();
		while (iter.hasNext()) {
			Enemy e = iter.next();
			e.toString();
			if (!e.update())
				iter.remove();
		}
	}

	public LinkedList<Enemy> getList() {
		return ens;
	}

}
