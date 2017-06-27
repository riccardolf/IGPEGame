package it.unical.igpe.net;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.GUI.SoundManager;
import it.unical.igpe.MapUtils.MapManager;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.logic.AbstractDynamicObject;
import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Lootable;
import it.unical.igpe.logic.Player;
import it.unical.igpe.logic.Tile;
import it.unical.igpe.net.packet.Packet00Login;
import it.unical.igpe.net.packet.Packet04Loot;
import it.unical.igpe.utils.GameConfig;
import it.unical.igpe.utils.LootableType;
import it.unical.igpe.utils.TileType;
import it.unical.igpe.utils.Updatable;

public class MultiplayerWorld implements Updatable {
	public static String username;
	public static boolean finished = false;
	public static int keyCollected;
	public PlayerMP player;
	public List<AbstractDynamicObject> entities;

	private LinkedList<Bullet> bls;
	private static LinkedList<Tile> tiles;
	private static LinkedList<Lootable> lootables;
	public Vector2 dir;
	private MapManager manager;
	public boolean isServer = false;
	
	public MultiplayerWorld(String path, boolean isServer) {
		this.isServer = isServer;
		tiles = new LinkedList<Tile>();
		lootables = new LinkedList<Lootable>();
		bls = new LinkedList<Bullet>();
		entities = new ArrayList<AbstractDynamicObject>();
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
				} else if (manager.map[x][y] == 4) { // AmmoPack
					lootables.add(new Lootable(new Vector2(x * GameConfig.TILEDIM, y * GameConfig.TILEDIM),
							LootableType.AMMOPACK));
					tiles.add(new Tile(new Vector2(x * GameConfig.TILEDIM, y * GameConfig.TILEDIM), TileType.GROUND));
				}
			}
		if(!isServer) {
			this.addEntity(player);
			Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.getBoundingBox().x,
					player.getBoundingBox().y);
			// If the client has started a server, add it has a connection
			if (IGPEGame.game.socketServer != null) {
				IGPEGame.game.socketServer.addConnection((PlayerMP) player, loginPacket);
			}
			loginPacket.writeData(IGPEGame.game.socketClient);
		}
	}

	public void update(float delta) {
		
		player.state = Player.STATE_IDLE;

		if (player.isReloading(delta))
			player.state = Player.STATE_RELOADING;

		player.activeWeapon.lastFired += delta;

		// Bullet collisions
		synchronized (bls) {
			if (!bls.isEmpty()) {
				boolean removed = false;
				ListIterator<Bullet> it = bls.listIterator();
				while (it.hasNext()) {
					ListIterator<AbstractDynamicObject> iter = entities.listIterator();
					Bullet b = it.next();
					b.update(delta);
					while (iter.hasNext()) {
						AbstractDynamicObject a = iter.next();
						if (b.getBoundingBox().intersects(a.getBoundingBox()) && a.Alive()
								&& b.getID() != ((PlayerMP) a).getUsername()) {
							it.remove();
							removed = true;
						}
					}
					if (removed)
						continue;
					if (getNextTile(b.getBoundingBox()) == TileType.WALL)
						it.remove();
				}
			}
		}

		// TODO: Packet for server closed
		// TODO: Packet for loot
		// Checking lootable items
		Iterator<Lootable> itl = lootables.iterator();
		while (itl.hasNext()) {
			Lootable l = itl.next();
			if (l.getBoundingBox().intersects(player.getBoundingBox())) {
				if (l.getType() == LootableType.HEALTPACK && player.getHP() < 100) {
					player.setHP(player.getHP() + 25);
					SoundManager.manager.get(SoundManager.HealthRestored, Sound.class).play(GameConfig.SOUND_VOLUME);
					Packet04Loot packetLoot = new Packet04Loot(l.getBoundingBox().x, l.getBoundingBox().y);
					packetLoot.writeData(IGPEGame.game.socketClient);
				} else if (l.getType() == LootableType.AMMOPACK) {
					if (player.pistol.canAdd() || player.shotgun.canAdd() || player.rifle.canAdd()) {
						player.pistol.addAmmo(15);
						player.shotgun.addAmmo(6);
						player.rifle.addAmmo(5);
						Packet04Loot packetLoot = new Packet04Loot(l.getBoundingBox().x, l.getBoundingBox().y);
						packetLoot.writeData(IGPEGame.game.socketClient);
					}
				} else if (l.getType() == LootableType.TRAP && l.closed == false) {
					player.setHP(player.getHP() - 50);
					SoundManager.manager.get(SoundManager.TrapClosing, Sound.class).play(GameConfig.SOUND_VOLUME);
					Packet04Loot packetLoot = new Packet04Loot(l.getBoundingBox().x, l.getBoundingBox().y);
					packetLoot.writeData(IGPEGame.game.socketClient);
				} else if (l.getType() == LootableType.KEYY || l.getType() == LootableType.KEYR
						|| l.getType() == LootableType.KEYG || l.getType() == LootableType.KEYB) {
					MultiplayerWorld.keyCollected++;
					Packet04Loot packetLoot = new Packet04Loot(l.getBoundingBox().x, l.getBoundingBox().y);
					packetLoot.writeData(IGPEGame.game.socketClient);
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
		for (AbstractDynamicObject e : entities) {
			if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equals(username)) {
				break;
			}
			index++;
		}
		this.getEntities().remove(index);
	}

	public int getPlayerMPIndex(String username) {
		int index = 0;
		for (AbstractDynamicObject e : this.getEntities()) {
			if (e instanceof PlayerMP && ((PlayerMP) e).getUsername().equals(username)) {
				return index;
			}
			index++;
		}
		return 0;
	}

	public void movePlayer(String username, int x, int y, float angle, int state, int weapon) {
		int index = getPlayerMPIndex(username);
		if (index != getPlayerMPIndex(player.username)) {
			PlayerMP p = (PlayerMP) this.getEntities().get(index);
			p.getBoundingBox().x = x;
			p.getBoundingBox().y = y;
			p.angle = angle;
			p.state = state;
			if (weapon == 0)
				p.activeWeapon = p.pistol;
			else if (weapon == 1)
				p.activeWeapon = p.shotgun;
			else if (weapon == 2)
				p.activeWeapon = p.rifle;
		}
	}

	public void fireBullet(String username, int x, int y, float angle) {
		synchronized (bls) {
			float x2 = (float) (16 * Math.cos(Math.toRadians(angle)) - 16 * Math.sin(Math.toRadians(angle)));
			float y2 = (float) (16 * Math.sin(Math.toRadians(angle)) + 16 * Math.cos(Math.toRadians(angle)));
			this.bls.add(new Bullet(new Vector2(x + 32 + x2, y + 32 + y2), (float) Math.toRadians(angle + 90f),
					username, 15));
		}
	}

	public synchronized void removeLoot(int x, int y) {
		Iterator<Lootable> iter = lootables.iterator();
		while (iter.hasNext()) {
			Lootable l = iter.next();
			if (l.getBoundingBox().x == x && l.getBoundingBox().y == y) {
				if (l.getType() == LootableType.TRAP)
					l.closed = true;
				else
					iter.remove();
				break;
			}
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

	public synchronized void addEntity(AbstractDynamicObject player) {
		this.getEntities().add(player);
	}

	public synchronized List<AbstractDynamicObject> getEntities() {
		return this.entities;
	}
}
