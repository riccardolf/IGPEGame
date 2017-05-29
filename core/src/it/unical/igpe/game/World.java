package it.unical.igpe.game;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.ai.EnemyManager;
import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Enemy;
import it.unical.igpe.logic.Player;
import it.unical.igpe.logic.Tile;
import it.unical.igpe.tools.GameConfig;
import it.unical.igpe.tools.PlayerState;
import it.unical.igpe.tools.TileType;
import it.unical.igpe.tools.WorldLoader;

public class World {
	private Player player;
	private LinkedList<Bullet> bls;
	private LinkedList<Tile> tiles;
	private TileType nextTile;
	private boolean finished = false;
	public LinkedList<Enemy> ens;
	public EnemyManager EM;
	public float rotation;
	public Vector2 dir;
	private Rectangle box;
	WorldLoader loader;
	public PlayerState state;

	@SuppressWarnings("static-access")
	public World() {
		player = new Player(new Vector2(100, 100));

		state = PlayerState.IDLE;
		tiles = new LinkedList<Tile>();

		try {
			loader = loader.FromFile("map.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int x = 0; x < loader.map.length; x++)
			for (int y = 0; y < loader.map.length; y++) {
				if (loader.map[x][y] == 0)
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				else if (loader.map[x][y] == 1)
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.WALL));
				else if (loader.map[x][y] == 2)
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.ENDLEVEL));
			}
		dir = new Vector2();
		EM = new EnemyManager(this);
	}

	public void updateWorld(float delta) {
		if (!(state == PlayerState.RELOADING && player.getReloading()))
			state = PlayerState.IDLE;
		if (player.getReloading())
			state = PlayerState.RELOADING;
		// Angle for player and bullets
		float midX = Gdx.graphics.getWidth() / 2;
		float midY = Gdx.graphics.getHeight() / 2;
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
		dir = new Vector2(mouseX - midX, mouseY - midY);
		dir.rotate90(-1);
		rotation = dir.angle();
		// rotation = calculateAngle((float) Gdx.input.getX(), (float)
		// Gdx.input.getY());

		// Movements and Collisions of the player
		if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x - GameConfig.MOVESPEED,
					player.getBoundingBox().y + GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			nextTile = getNextTile(box);
			if (nextTile == TileType.ENDLEVEL) {
				finished = true;
				player.MoveUpLeft();
			} else if (nextTile != TileType.WALL)
				player.MoveUpLeft();

		} else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x + GameConfig.MOVESPEED,
					player.getBoundingBox().y + GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			nextTile = getNextTile(box);
			if (nextTile == TileType.ENDLEVEL) {
				finished = true;
				player.MoveUpRight();
			} else if (nextTile != TileType.WALL)
				player.MoveUpRight();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x - GameConfig.MOVESPEED,
					player.getBoundingBox().y - GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			nextTile = getNextTile(box);
			if (nextTile == TileType.ENDLEVEL) {
				finished = true;
				player.MoveDownLeft();
			} else if (nextTile != TileType.WALL)
				player.MoveDownLeft();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x + GameConfig.MOVESPEED,
					player.getBoundingBox().y - GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			nextTile = getNextTile(box);
			if (nextTile == TileType.ENDLEVEL) {
				finished = true;
				player.MoveDownRight();
			} else if (nextTile != TileType.WALL)
				player.MoveDownRight();
		} else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x, player.getBoundingBox().y + GameConfig.MOVESPEED,
					player.getBoundingBox().width, player.getBoundingBox().height);
			nextTile = getNextTile(box);
			if (nextTile == TileType.ENDLEVEL) {
				finished = true;
				player.MoveUp();
			} else if (nextTile != TileType.WALL)
				player.MoveUp();
		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x - GameConfig.MOVESPEED, player.getBoundingBox().y,
					player.getBoundingBox().width, player.getBoundingBox().height);
			nextTile = getNextTile(box);
			if (nextTile == TileType.ENDLEVEL) {
				finished = true;
				player.MoveLeft();
			} else if (nextTile != TileType.WALL)
				player.MoveLeft();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x, player.getBoundingBox().y - GameConfig.MOVESPEED,
					player.getBoundingBox().width, player.getBoundingBox().height);
			nextTile = getNextTile(box);
			if (nextTile == TileType.ENDLEVEL) {
				finished = true;
				player.MoveDown();
			} else if (nextTile != TileType.WALL)
				player.MoveDown();
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x + GameConfig.MOVESPEED, player.getBoundingBox().y,
					player.getBoundingBox().width, player.getBoundingBox().height);
			nextTile = getNextTile(box);
			if (nextTile == TileType.ENDLEVEL) {
				finished = true;
				player.MoveRight();
			} else if (nextTile != TileType.WALL)
				player.MoveRight();
		}

		// Fire and Reloading action of the player
		if (Gdx.input.justTouched()) {
			float px = (float) (player.getPos().x + 32);
			float py = (float) (player.getPos().y + 32);

			px -= Math.sin(Math.toRadians((int) rotation));
			py += Math.cos(Math.toRadians((int) rotation));

			player.fire(px, py, rotation + 90f);
			player.checkAmmo();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.R) && !player.getReloading()) {
			player.reload();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			player.setActWeapon("pistol");
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
			player.setActWeapon("shotgun");
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
			player.setActWeapon("rifle");
		}

		player.isReloading(delta);

		// Bullet update and Collision

		bls = player.getBullets();
		Iterator<Bullet> it = bls.listIterator();
		Iterator<Enemy> iter = ((EnemyManager) EM).getList().listIterator();
		while (it.hasNext()) {
			Bullet b = it.next();
			while (iter.hasNext()) {
				Enemy e = iter.next();
				if (b.getBoundingBox().intersects(e.getBoundingBox())) {
					it.remove();
					e.hit(25);
				}
			}
			if (getNextTile(b.getBoundingBox()) == TileType.WALL) {
				it.remove();
			}
			b.update();
		}
		player.setBullets(bls);

		// Enemies
		EM.update(delta);
	}

	public float calculateAngle(float x, float y) {
		return (float) Math.toDegrees((Math.PI / 2 - Math.atan2(GameConfig.HEIGHT / 2 - y, GameConfig.WIDTH / 2 - x)));
	}

	public TileType getNextTile(Rectangle _box) {
		for (Tile tile : tiles) {
			if (Math.sqrt(Math.pow((_box.x - tile.getBoundingBox().x), 2)
					+ Math.pow(_box.y - tile.getBoundingBox().y, 2)) < 128) {
				if (tile.getType() == TileType.WALL && _box.intersects(tile.getBoundingBox()))
					return TileType.WALL;
				else if (tile.getType() == TileType.ENDLEVEL && _box.intersects(tile.getBoundingBox()))
					return TileType.ENDLEVEL;
			}
		}
		return TileType.GROUND;
	}

	public Player getPlayer() {
		return player;
	}

	public LinkedList<Bullet> getBls() {
		return bls;
	}

	public LinkedList<Tile> getTiles() {
		return tiles;
	}

	public boolean isLevelFinished() {
		return finished;
	}
}
