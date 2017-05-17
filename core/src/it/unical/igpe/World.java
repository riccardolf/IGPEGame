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
import it.unical.igpe.entity.Player;
import it.unical.igpe.entity.Wall;

public class World {
	private Player player;
	private LinkedList<Bullet> bls;
	private LinkedList<Wall> wls;
	private LinkedList<Enemy> ens;
	private int clipSize;
	private int clipAct;
	private int[][] map;
	public float rotation;
	private Rectangle box;
	TileLayer layer;

	@SuppressWarnings("static-access")
	public World() {
		player = new Player(new Vector2(100, 100));
		Enemy enemy1 = new Enemy(new Vector2(600, 600));
		Enemy enemy2 = new Enemy(new Vector2(700, 700));
		Enemy enemy3 = new Enemy(new Vector2(200, 200));
		wls = new LinkedList<Wall>();
		ens = new LinkedList<Enemy>();
		bls = player.getBullets();
		ens.add(enemy1);
		ens.add(enemy2);
		ens.add(enemy3);
		clipSize = 10;
		clipAct = 0;

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
		// Angle for player and bullets
		rotation = calculateAngle((float) Gdx.input.getX(), (float) Gdx.input.getY());

		// Movements and Collisions of the player
		if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
			box = new Rectangle(player.getBoundingBox().x - GameConfig.MOVESPEED,
					player.getBoundingBox().y + GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveUpLeft();
		} else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			box = new Rectangle(player.getBoundingBox().x + GameConfig.MOVESPEED,
					player.getBoundingBox().y + GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveUpRight();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
			box = new Rectangle(player.getBoundingBox().x - GameConfig.MOVESPEED,
					player.getBoundingBox().y - GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveDownLeft();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			box = new Rectangle(player.getBoundingBox().x + GameConfig.MOVESPEED,
					player.getBoundingBox().y - GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveDownRight();
		} else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			box = new Rectangle(player.getBoundingBox().x, player.getBoundingBox().y + GameConfig.MOVESPEED,
					player.getBoundingBox().width, player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveUp();
		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			box = new Rectangle(player.getBoundingBox().x - GameConfig.MOVESPEED, player.getBoundingBox().y,
					player.getBoundingBox().width, player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveLeft();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			box = new Rectangle(player.getBoundingBox().x, player.getBoundingBox().y - GameConfig.MOVESPEED,
					player.getBoundingBox().width, player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveDown();
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			box = new Rectangle(player.getBoundingBox().x + GameConfig.MOVESPEED, player.getBoundingBox().y,
					player.getBoundingBox().width, player.getBoundingBox().height);
			if (!checkCollisionWall(box))
				player.MoveRight();
		}

		// TODO: weapon's class

		// Fire and Reloading action of the player
		if (Gdx.input.justTouched()) {
			player.fire(rotation + 90f);
			clipSize++;
		}
		// TODO: Clipsize of specified weapon
		if (Gdx.input.isKeyPressed(Input.Keys.R) || clipAct > clipSize) {
			player.reload();
			clipAct = 0;
		}

		player.isReloading(delta);

		// Bullet update and Collision

		bls = player.getBullets();
		Iterator<Bullet> it = bls.listIterator();
		while (it.hasNext()) {
			Bullet b = it.next();
			if (checkCollisionWall(b.getBoundingBox())) {
				it.remove();
			} else {
				b.update();
			}
		}

		// Updating player's bullets
		player.setBullets(bls);

		// TODO: Enemy's AI
		// for (Enemy e : ens) { e.findPathToTarget(player.getPos()); }
	}

	public float calculateAngle(float x, float y) {
		return (float) Math.toDegrees((Math.PI / 2 - Math.atan2(GameConfig.HEIGHT / 2 - y, GameConfig.WIDTH / 2 - x)));
	}

	public boolean checkCollisionWall(Rectangle _box) {
		for (Wall wall : wls) {
			if (Math.sqrt(Math.pow((_box.x - wall.getBoundingBox().x), 2)
					+ Math.pow(_box.y - wall.getBoundingBox().y, 2)) < 128) {
				if (_box.intersects(wall.getBoundingBox())) {
					System.out.println("Collision");
					return true;
				}
			}
		}
		return false;
	}

	public Player getPlayer() {
		return player;
	}

	public LinkedList<Enemy> getEnemy() {
		return ens;
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
