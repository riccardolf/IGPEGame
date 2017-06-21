package it.unical.igpe.net;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.logic.AbstractGameObject;
import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Enemy;
import it.unical.igpe.logic.Lootable;
import it.unical.igpe.logic.Player;
import it.unical.igpe.logic.Tile;
import it.unical.igpe.net.packet.Packet00Login;
import it.unical.igpe.tools.Assets;
import it.unical.igpe.tools.GameConfig;
import it.unical.igpe.tools.LootableType;
import it.unical.igpe.tools.MapManager;
import it.unical.igpe.tools.TileType;
import it.unical.igpe.tools.Updatable;

public class MultiplayerWorld implements Updatable {
	public static String username;
	public PlayerMP player;
	public ArrayList<AbstractGameObject> entities;
	public static boolean finished = false;
	public static int keyCollected;

	private LinkedList<Bullet> bls;
	private static LinkedList<Tile> tiles;
	private static LinkedList<Lootable> lootables;
	public Vector2 dir;
	private MapManager manager;

	public MultiplayerWorld(String path) {
		tiles = new LinkedList<Tile>();
		lootables = new LinkedList<Lootable>();
		bls = new LinkedList<Bullet>();
		entities = new ArrayList<AbstractGameObject>();
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
				} else if (manager.map[x][y] == 10) { // Player
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
					player = new PlayerMP(new Vector2(x * 64, y * 64), this, username, null, -1);
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
				}
			}
		this.addEntity(player);
		Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.getBoundingBox().x,
				player.getBoundingBox().y);
		if (IGPEGame.game.socketServer != null) {
			IGPEGame.game.socketServer.addConnection((PlayerMP) player, loginPacket);
		}
		loginPacket.writeData(IGPEGame.game.socketClient);
		dir = new Vector2();
	}

	public void update(float delta) {
		player.state = Player.PLAYER_STATE_IDLE;

		if (player.isReloading(delta))
			player.state = Player.PLAYER_STATE_RELOADING;

		player.activeWeapon.lastFired += delta;

		// Enemies
		synchronized (bls) {
			if (!bls.isEmpty()) {
				boolean removed = false;
				ListIterator<Bullet> it = bls.listIterator();
				while (it.hasNext()) {
					ListIterator<AbstractGameObject> iter = entities.listIterator();
					Bullet b = it.next();
					b.update(delta);
					while (iter.hasNext()) {
						AbstractGameObject a = iter.next();
						if (a instanceof Enemy)
							if (b.getBoundingBox().intersects(a.getBoundingBox()) && a.Alive()
									&& b.getID() == "player") {
								it.remove();
								((Enemy) a).hit(b.getHP());
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
					break;
				} else if (l.getType() == LootableType.TRAP && l.closed == false) {
					player.setHP(player.getHP() - 50);
					Assets.manager.get(Assets.TrapClosing, Sound.class).play(GameConfig.SOUND_VOLUME);
					l.closed = true;
					break;
				} else if (l.getType() == LootableType.KEYY || l.getType() == LootableType.KEYR
						|| l.getType() == LootableType.KEYG || l.getType() == LootableType.KEYB) {
					MultiplayerWorld.keyCollected++;
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

	public synchronized void removePlayerMP(String username) {
		int index = 0;
		for (AbstractGameObject e : entities) {
			if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equals(username)) {
				break;
			}
			index++;
		}
		entities.remove(index);
	}

	public int getPlayerMPIndex(String username) {
		int index = 0;
		for (AbstractGameObject e : entities) {
			if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equals(username)) {
				break;
			}
			index++;
		}
		return index;
	}

	public void movePlayer(String username, int x, int y, float angle, int state, int weapon) {
		int index = getPlayerMPIndex(username);
		if (index != getPlayerMPIndex(player.username)) {
			PlayerMP p = (PlayerMP) entities.get(index);
			p.getBoundingBox().x = x;
			p.getBoundingBox().y = y;
			p.angle = angle;
			p.state = state;
			if(weapon == 0)
				p.activeWeapon = p.pistol;
			else if(weapon == 1)
				p.activeWeapon = p.shotgun;
			else if(weapon == 2)
				p.activeWeapon = p.rifle;
		}
	}

	public void fireBullet(String username, int x, int y, float angle) {
		synchronized (bls) {
			this.bls.add(new Bullet(new Vector2(x + 32, y + 32), (float) Math.toRadians(angle + 90f), username, 15));
		}
	}

	public PlayerMP getPlayer() {
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

	public void addEntity(PlayerMP player) {
		synchronized (entities) {
			this.entities.add(player);		
		}
	}

}
