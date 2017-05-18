package it.unical.igpe.logic;

import java.util.LinkedList;

public class EnemyManager extends Thread {
	LinkedList<Enemy> ens;
	Player target;
	
	public EnemyManager(Player _target) {
		ens = new LinkedList<Enemy>();
		target = _target;
	}
	@Override
	public void run() {
		for (Enemy enemy : ens) {
			enemy.findPathToTarget(target.getPos());
		}
	}
	
	public void checkEnemies() {
		for (Enemy enemy : ens) {
			if(!enemy.Alive())
				ens.remove();
		}
	}
	
	public void add(Enemy _enemy) {
		ens.add(_enemy);
	}
	
	public LinkedList<Enemy> getList() {
		return ens;
	}
	
}
