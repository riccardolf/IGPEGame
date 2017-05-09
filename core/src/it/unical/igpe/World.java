package it.unical.igpe;

import java.io.IOException;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
	public float rotation;
	TileLayer layer;
	
	@SuppressWarnings("static-access")
	public World() {
		posP = new Vector2(100,100);
		player = new Player(posP);
		enemy = new Enemy(new Vector2(600,600));
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
					wls.add(new Wall(new Vector2(y * 64, x * 64)));
		
	}
	
	public void updateWorld() {
		rotation =	calculateAngle((float)Gdx.input.getX(), (float) Gdx.input.getY());
		
		if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A))
			player.MoveUpLeft();
		else if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D))
			player.MoveUpRight();
		else if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A))
			player.MoveDownLeft();
		else if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D))
			player.MoveDownRight();
		else if(Gdx.input.isKeyPressed(Input.Keys.W))
			player.MoveUp();
		else if(Gdx.input.isKeyPressed(Input.Keys.A))
			player.MoveLeft();
		else if(Gdx.input.isKeyPressed(Input.Keys.S))
			player.MoveDown();
		else if(Gdx.input.isKeyPressed(Input.Keys.D))
			player.MoveRight();
		
		player.updateBoundingBox();
		
		if(Gdx.input.justTouched())
		    player.fire(rotation+90);

		for (Wall wall : wls)
			if(player.handleCollision(wall.getBoundingBox()))
				System.out.println("Collisione");
		
		bls = player.getBullets();
		for (Bullet bullet : bls) {
			bullet.update();
		}
		
		enemy.findPathToTarget(player.getPos());
	}
	
	public float calculateAngle(float x, float y) {
		return (float) Math.toDegrees((Math.PI / 2 - Math.atan2(GameConfig.HEIGHT / 2 - y, GameConfig.WIDTH / 2 - x)));
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
