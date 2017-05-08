package it.unical.igpe;

import java.io.IOException;
import java.util.LinkedList;

import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.entity.Bullet;
import it.unical.igpe.entity.Enemy;
import it.unical.igpe.entity.Player;
import it.unical.igpe.entity.Wall;

public class World {
	
	private Player player;
	private Enemy enemy;
	private Vector2 posP;
	private LinkedList<Bullet> bls;
	private LinkedList<Wall> wls;
	private LinkedList<Enemy> ens;
	private int[][] map;
	TileLayer layer;
	
	@SuppressWarnings("static-access")
	public World() {
		posP = new Vector2(100,100);
		player = new Player(posP);
		enemy = new Enemy(posP.scl(3f));
		bls = new LinkedList<Bullet>();
		wls = new LinkedList<Wall>();
		ens = new LinkedList<Enemy>();
		ens.add(enemy);
		
		map = new int [128][128];
		try {
			layer = layer.FromFile("map.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		map = layer.map;
		for(int y = 0; y < map.length; y++)
			for (int x = 0; x < map[y].length; x++)
				if(map[y][x] == 1)
					wls.add(new Wall(new Vector2(y * 32, x * 32)));
		
	}

	public Player getPlayer() {
		return player;
	}

	public Enemy getEnemy() {
		return enemy;
	}

	public Vector2 getPosP() {
		return posP;
	}

	public LinkedList<Bullet> getBls() {
		return bls;
	}

	public LinkedList<Wall> getWls() {
		return wls;
	}

	public LinkedList<Enemy> getEns() {
		return ens;
	}

	public int[][] getMap() {
		return map;
	}
}
