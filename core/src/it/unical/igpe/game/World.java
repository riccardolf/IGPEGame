package it.unical.igpe.game;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.ai.EnemyManager;
import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Enemy;
import it.unical.igpe.logic.Lootable;
import it.unical.igpe.logic.Player;
import it.unical.igpe.logic.Tile;
import it.unical.igpe.tools.GameConfig;
import it.unical.igpe.tools.LootableType;
import it.unical.igpe.tools.PlayerState;
import it.unical.igpe.tools.TileType;
import it.unical.igpe.tools.MapManager;

public class World {
	private Player player;
	private LinkedList<Bullet> bls;
	private static LinkedList<Tile> tiles;
	private static LinkedList<Lootable> lootables;
	private TileType nextTile;
	private boolean finished = false;
	public LinkedList<Enemy> ens;
	public EnemyManager EM;
	public Vector2 dir;
	private Rectangle box;
	private MapManager manager;
	public PlayerState state;

	public World(String path) {
		player = new Player(new Vector2(), this);
		state = PlayerState.IDLE;
		tiles = new LinkedList<Tile>();
		lootables = new LinkedList<Lootable>();
		ens = new LinkedList<Enemy>();
		bls = new LinkedList<Bullet>();

		manager = new MapManager(64, 64);
		try {
			manager.LoadMap(path);
		} catch (IOException e) {
			System.out.println("Map not found");
		}

		for (int x = 0; x < manager.map.length; x++)
			for (int y = 0; y < manager.map.length; y++) {
				if (manager.map[x][y] == 0)
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				else if (manager.map[x][y] == 1)
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.WALL));
				else if (manager.map[x][y] == 9)
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.ENDLEVEL));
				else if (manager.map[x][y] == 18) { // Enemy
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
					ens.add(new Enemy(new Vector2(x * 64, y * 64), player));
				} else if (manager.map[x][y] == 17) { // Player
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
					player.setPos(new Vector2(x * 64, y * 64));
				} else if (manager.map[x][y] == 13) { // Yellow Key
					lootables.add(new Lootable(new Vector2(x * 64, y * 64), LootableType.KEYY));
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				} else if (manager.map[x][y] == 14) { // Red Key
					lootables.add(new Lootable(new Vector2(x * 64, y * 64), LootableType.KEYR));
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				} else if (manager.map[x][y] == 15) { // Blue Key
					lootables.add(new Lootable(new Vector2(x * 64, y * 64), LootableType.KEYB));
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				} else if (manager.map[x][y] == 16) { // Green Key
					lootables.add(new Lootable(new Vector2(x * 64, y * 64), LootableType.KEYG));
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				} else if (manager.map[x][y] == 12) { // HealthPack
					lootables.add(new Lootable(new Vector2(x * 64, y * 64), LootableType.HEALTPACK));
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				} else if (manager.map[x][y] == 22) { // Trap
					lootables.add(new Lootable(new Vector2(x * 64, y * 64), LootableType.TRAP));
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				}
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
		float mouseY = Gdx.input.getY();
		dir = new Vector2(mouseX - midX, mouseY - midY);
		dir.rotate90(-1);
		player.angle = dir.angle();

		// Movements and Collisions of the player
		if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x - GameConfig.MOVESPEED,
					player.getBoundingBox().y - GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			nextTile = getNextTile(box);
			if (nextTile == TileType.ENDLEVEL && isDoorUnlocked()) {
				finished = true;
				player.MoveUpLeft();
			} else if (nextTile != TileType.WALL)
				player.MoveUpLeft();

		} else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x + GameConfig.MOVESPEED,
					player.getBoundingBox().y - GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			nextTile = getNextTile(box);
			if (nextTile == TileType.ENDLEVEL && isDoorUnlocked()) {
				finished = true;
				player.MoveUpRight();
			} else if (nextTile != TileType.WALL)
				player.MoveUpRight();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x - GameConfig.MOVESPEED,
					player.getBoundingBox().y + GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			nextTile = getNextTile(box);
			if (nextTile == TileType.ENDLEVEL && isDoorUnlocked()) {
				finished = true;
				player.MoveDownLeft();
			} else if (nextTile != TileType.WALL)
				player.MoveDownLeft();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x + GameConfig.MOVESPEED,
					player.getBoundingBox().y + GameConfig.MOVESPEED, player.getBoundingBox().width,
					player.getBoundingBox().height);
			nextTile = getNextTile(box);
			if (nextTile == TileType.ENDLEVEL && isDoorUnlocked()) {
				finished = true;
				player.MoveDownRight();
			} else if (nextTile != TileType.WALL)
				player.MoveDownRight();
		} else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x, player.getBoundingBox().y - GameConfig.MOVESPEED,
					player.getBoundingBox().width, player.getBoundingBox().height);
			nextTile = getNextTile(box);
			if (nextTile == TileType.ENDLEVEL && isDoorUnlocked()) {
				System.out.println("FINISHED");
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
			if (nextTile == TileType.ENDLEVEL && isDoorUnlocked()) {
				finished = true;
				player.MoveLeft();
			} else if (nextTile != TileType.WALL)
				player.MoveLeft();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (!player.getReloading())
				state = PlayerState.RUNNING;
			box = new Rectangle(player.getBoundingBox().x, player.getBoundingBox().y + GameConfig.MOVESPEED,
					player.getBoundingBox().width, player.getBoundingBox().height);
			nextTile = getNextTile(box);
			if (nextTile == TileType.ENDLEVEL && isDoorUnlocked()) {
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
			if (nextTile == TileType.ENDLEVEL && isDoorUnlocked()) {
				finished = true;
				player.MoveRight();
			} else if (nextTile != TileType.WALL)
				player.MoveRight();
		}

		// Fire and Reloading action of the player
		if (Gdx.input.justTouched()) {
			player.fire();
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
		
		// Enemies
		EM.update(delta);
		if (!bls.isEmpty()) {
			boolean removed = false;
			ListIterator<Bullet> it = bls.listIterator();
			while (it.hasNext()) {
				ListIterator<Enemy> iter = EM.getList().listIterator();
				Bullet b = it.next();
				b.update();
				while (iter.hasNext()) {
					Enemy e = iter.next();
					if (b.getBoundingBox().intersects(e.getBoundingBox()) && e.Alive() && b.getID() == "player") {
						it.remove();
						e.hit(b.getHP());
						removed = true;
					}
				}
				if(removed)
					continue;
				if (b.getBoundingBox().intersects(player.getBoundingBox()) && b.getID() == "enemy") {
					it.remove();
					player.hit(b.getHP());
					continue;
				}
				if (getNextTile(b.getBoundingBox()) == TileType.WALL) {
					it.remove();
					continue;
				}
			}
		}

		// Checking lootable items
		Iterator<Lootable> itl = lootables.iterator();
		while (itl.hasNext()) {
			Lootable l = itl.next();
			if (l.getBoundingBox().intersects(player.getBoundingBox())) {
				if (l.getType() == LootableType.HEALTPACK && player.getHP() < 100) {
					player.setHP(player.getHP() + 25);
					itl.remove();
					break;
				} else if (l.getType() == LootableType.TRAP && l.closed == false) {
					player.setHP(player.getHP() - 50);
					l.closed = true;
					break;
				} else if (l.getType() == LootableType.KEYY || l.getType() == LootableType.KEYR
						|| l.getType() == LootableType.KEYG || l.getType() == LootableType.KEYB) {
					player.keys++;
					itl.remove();
				}
			}
		}
	}

	public float calculateAngle(float x, float y) {
		return (float) Math.toDegrees((Math.PI / 2 - Math.atan2(GameConfig.HEIGHT / 2 - y, GameConfig.WIDTH / 2 - x)));
	}

	public static TileType getNextTile(Rectangle _box) {
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

	public boolean isDoorUnlocked() {
		if (player.keys == 4) {
			return true;
		}
		return false;
	}

	public Player getPlayer() {
		return player;
	}

	public LinkedList<Bullet> getBls() {
		return bls;
	}

	public void addBullet(Bullet _bullet) {
		bls.add(_bullet);
	}

	public LinkedList<Tile> getTiles() {
		return tiles;
	}

	public LinkedList<Lootable> getLootables() {
		return lootables;
	}

	public boolean isLevelFinished() {
		return finished;
	}

	public boolean isGameOver() {
		if (player.getHP() <= 0)
			return true;
		return false;
	}
}
