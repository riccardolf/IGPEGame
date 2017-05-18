package it.unical.igpe;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.entity.Bullet;
import it.unical.igpe.entity.Enemy;
import it.unical.igpe.entity.EnemyManager;
import it.unical.igpe.entity.Player;
import it.unical.igpe.entity.PlayerState;
import it.unical.igpe.entity.Wall;

public class World {
	private Player player;
	private LinkedList<Bullet> bls;
	private LinkedList<Wall> wls;
	public EnemyManager EM;
	private int[][] map;
	public float rotation;
	private Rectangle box;
	TileLayer layer;
	public PlayerState state;

	@SuppressWarnings("static-access")
	public World() {
		player = new Player(new Vector2(100, 100));
		EM = new EnemyManager(player);
		EM.add(new Enemy(new Vector2(300, 300)));
		state = PlayerState.IDLE;
		wls = new LinkedList<Wall>();

		map = new int[96][96];
		try {
			layer = layer.FromFile("map.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		map = layer.map;
		for (int x = 0; x < map.length; x++)
			for (int y = 0; y < map.length; y++)
				if (map[x][y] == 1)
					wls.add(new Wall(new Vector2(x * 64, y * 64)));

	}

	public void updateWorld(float delta) {
		if(!(state == PlayerState.RELOADING && player.getReloading()))
			state = PlayerState.IDLE;
		if (player.getReloading())
			state = PlayerState.RELOADING;
		// Angle for player and bullets
		rotation = calculateAngle((float) Gdx.input.getX(), (float) Gdx.input.getY());

		// Movements and Collisions of the player
		if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x - GameConfig.MOVESPEED,
					player.getBoundingBox().y + GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveUpLeft();
		} else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x + GameConfig.MOVESPEED,
					player.getBoundingBox().y + GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveUpRight();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x - GameConfig.MOVESPEED,
					player.getBoundingBox().y - GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveDownLeft();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x + GameConfig.MOVESPEED,
					player.getBoundingBox().y - GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveDownRight();
		} else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x, player.getBoundingBox().y + GameConfig.MOVESPEED,
					player.getBoundingBox().width, player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveUp();
		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x - GameConfig.MOVESPEED, player.getBoundingBox().y,
					player.getBoundingBox().width, player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveLeft();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x, player.getBoundingBox().y - GameConfig.MOVESPEED,
					player.getBoundingBox().width, player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveDown();
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x + GameConfig.MOVESPEED, player.getBoundingBox().y,
					player.getBoundingBox().width, player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveRight();
		}

		// Fire and Reloading action of the player
		if (Gdx.input.justTouched()) {
			player.fire(rotation + 90f);
			player.checkAmmo();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.R) && !player.getReloading()) {
			player.reload();
		}
		player.isReloading(delta);
	

		// Bullet update and Collision

		bls = player.getBullets();
		Iterator<Bullet> it = bls.listIterator();
		Iterator<Enemy> iter = EM.getList().listIterator();
		while (it.hasNext()) {
			Bullet b = it.next();
			while(iter.hasNext()) {
				Enemy e = iter.next();
				if (checkCollisionWall(b.getBoundingBox())) {
					it.remove();
				}
				else if(b.getBoundingBox().intersects(e.getBoundingBox())) {
					it.remove();
					e.hit(25);
				}
			}
			b.update();
		}
		player.setBullets(bls);
		
		//Enemies
		EM.run();
		EM.checkEnemies();
	}

	public float calculateAngle(float x, float y) {
		return (float) Math.toDegrees((Math.PI / 2 - Math.atan2(GameConfig.HEIGHT / 2 - y, GameConfig.WIDTH / 2 - x)));
	}

	public boolean checkCollisionWall(Rectangle _box) {
		for (Wall wall : wls) {
			if (Math.sqrt(Math.pow((_box.x - wall.getBoundingBox().x), 2)
					+ Math.pow(_box.y - wall.getBoundingBox().y, 2)) < 128) {
				if (_box.intersects(wall.getBoundingBox())) {
					return true;
				}
			}
		}
		return false;
	}

	public Player getPlayer() {
		return player;
	}

	public LinkedList<Bullet> getBls() {
		return bls;
	}

	public LinkedList<Wall> getWls() {
		return wls;
	}

	public int[][] getMap() {
		return map;
	}
}
