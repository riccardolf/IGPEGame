package it.unical.igpe.game;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.ai.EnemyManager;
import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Enemy;
import it.unical.igpe.logic.Lootable;
import it.unical.igpe.logic.Player;
import it.unical.igpe.logic.Tile;
import it.unical.igpe.tools.Assets;
import it.unical.igpe.tools.GameConfig;
import it.unical.igpe.tools.LootableType;
import it.unical.igpe.tools.TileType;
import it.unical.igpe.tools.Updatable;
import it.unical.igpe.tools.MapManager;

public class World implements Updatable {
	public static boolean finished = false;
	public static int keyCollected;

	public Player player;
	private LinkedList<Bullet> bls;
	private static LinkedList<Tile> tiles;
	private static LinkedList<Lootable> lootables;
	public LinkedList<Enemy> ens;
	public EnemyManager EM;
	public Vector2 dir;
	private MapManager manager;

	public World(String path) {
		player = new Player(new Vector2(), this, null);
		tiles = new LinkedList<Tile>();
		lootables = new LinkedList<Lootable>();
		ens = new LinkedList<Enemy>();
		bls = new LinkedList<Bullet>();
		keyCollected = 0;

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
				else if (manager.map[x][y] == 2)
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.ENDLEVEL));
				else if (manager.map[x][y] == 11 || manager.map[x][y] == 12) { // Enemy
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
					Enemy e = new Enemy(new Vector2(x * 64, y * 64));
					e.addPlayer(player);
					ens.add(e);
				} else if (manager.map[x][y] == 10) { // Player
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
					player.setPos(new Vector2(x * 64, y * 64));
				} else if (manager.map[x][y] == 6) { // Yellow Key
					lootables.add(new Lootable(new Vector2(x * 64, y * 64), LootableType.KEYY));
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				} else if (manager.map[x][y] == 7) { // Red Key
					lootables.add(new Lootable(new Vector2(x * 64, y * 64), LootableType.KEYR));
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				} else if (manager.map[x][y] == 8) { // Blue Key
					lootables.add(new Lootable(new Vector2(x * 64, y * 64), LootableType.KEYB));
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				} else if (manager.map[x][y] == 9) { // Green Key
					lootables.add(new Lootable(new Vector2(x * 64, y * 64), LootableType.KEYG));
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				} else if (manager.map[x][y] == 5) { // HealthPack
					lootables.add(new Lootable(new Vector2(x * 64, y * 64), LootableType.HEALTPACK));
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				} else if (manager.map[x][y] == 13) { // Trap
					lootables.add(new Lootable(new Vector2(x * 64, y * 64), LootableType.TRAP));
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				} else if (manager.map[x][y] == 4) { // Trap
					lootables.add(new Lootable(new Vector2(x * 64, y * 64), LootableType.AMMOPACK));
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
				}
			}
		dir = new Vector2();
		EM = new EnemyManager(this);
	}

	@SuppressWarnings("static-access")
	public void update(float delta) {
		player.state = Player.PLAYER_STATE_IDLE;

		if (player.isReloading(delta) && player.hasAmmo())
			player.state = Player.PLAYER_STATE_RELOADING;

		if (player.isShooting(delta))
			player.state = Player.PLAYER_STATE_SHOOTING;
		
		// Enemies
		EM.update(delta);
		if (!bls.isEmpty()) {
			boolean removed = false;
			ListIterator<Bullet> it = bls.listIterator();
			while (it.hasNext()) {
				ListIterator<Enemy> iter = EM.getList().listIterator();
				Bullet b = it.next();
				b.update(delta);
				while (iter.hasNext()) {
					Enemy e = iter.next();
					if (b.getBoundingBox().intersects(e.getBoundingBox()) && e.Alive() && b.getID() == "player") {
						it.remove();
						e.hit(b.getHP());
						removed = true;
					}
				}
				if (removed)
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
					Assets.manager.get(Assets.HealthRestored, Sound.class).play(GameConfig.SOUND_VOLUME);
					itl.remove();
				} else if (l.getType() == LootableType.AMMOPACK) {
					if(player.pistol.canAdd() || player.shotgun.canAdd() || player.rifle.canAdd()) {
						player.pistol.addAmmo(15);
						player.shotgun.addAmmo(6);
						player.rifle.addAmmo(5);
						itl.remove();
					}
				} else if (l.getType() == LootableType.TRAP && l.closed == false) {
					player.setHP(player.getHP() - 50);
					Assets.manager.get(Assets.TrapClosing, Sound.class).play(GameConfig.SOUND_VOLUME);
					l.closed = true;
				} else if (l.getType() == LootableType.KEYY || l.getType() == LootableType.KEYR
						|| l.getType() == LootableType.KEYG || l.getType() == LootableType.KEYB) {
					this.keyCollected++;
					itl.remove();
				}
			}
		}

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

	public static boolean isDoorUnlocked() {
		if (keyCollected == 4) {
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