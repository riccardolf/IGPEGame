package it.unical.igpe.net;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.MapUtils.WorldLoader;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.logic.AbstractDynamicObject;
import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Lootable;
import it.unical.igpe.logic.Player;
import it.unical.igpe.logic.Tile;
import it.unical.igpe.net.packet.Packet00Login;
import it.unical.igpe.net.packet.Packet04Death;
import it.unical.igpe.net.packet.Packet05GameOver;
import it.unical.igpe.net.screens.MultiplayerOverScreen;
import it.unical.igpe.utils.GameConfig;
import it.unical.igpe.utils.TileType;
import it.unical.igpe.utils.Updatable;

public class MultiplayerWorld implements Updatable {
	public static String username;
	public boolean gameOver = false;
	public static int keyCollected;
	public PlayerMP player;
	public List<AbstractDynamicObject> entities;

	private LinkedList<Bullet> bls;
	private static LinkedList<Tile> tiles;
	private static LinkedList<Lootable> lootables;
	private static LinkedList<Vector2> spawnPoints;
	public Vector2 dir;
	private WorldLoader manager;
	public boolean isServer = false;

	public MultiplayerWorld(String path, boolean isServer) {
		this.isServer = isServer;
		tiles = new LinkedList<Tile>();
		lootables = new LinkedList<Lootable>();
		bls = new LinkedList<Bullet>();
		entities = new ArrayList<AbstractDynamicObject>();
		spawnPoints = new LinkedList<Vector2>();
		keyCollected = 0;

		manager = new WorldLoader(32, 32);
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
				else if (manager.map[x][y] == 17) {
					tiles.add(new Tile(new Vector2(x * 64, y * 64), TileType.GROUND));
					spawnPoints.add(new Vector2(x, y));
				} else if (manager.map[x][y] == 12) // Box
					tiles.add(new Tile(new Vector2(x * GameConfig.TILEDIM, y * GameConfig.TILEDIM), TileType.BOX));
				else if (manager.map[x][y] == 13) { // Barrel
					tiles.add(new Tile(new Vector2(x * GameConfig.TILEDIM, y * GameConfig.TILEDIM), TileType.BARREL));
				} else if (manager.map[x][y] == 14) { // Cactus
					tiles.add(new Tile(new Vector2(x * GameConfig.TILEDIM, y * GameConfig.TILEDIM), TileType.CACTUS));
				} else if (manager.map[x][y] == 15) { // Plant
					tiles.add(new Tile(new Vector2(x * GameConfig.TILEDIM, y * GameConfig.TILEDIM), TileType.PLANT));
				} else if (manager.map[x][y] == 16) { // Logs
					tiles.add(new Tile(new Vector2(x * GameConfig.TILEDIM, y * GameConfig.TILEDIM), TileType.LOGS));
				}
			}

		if (!isServer) {
			player = new PlayerMP(randomSpawn(), this, username, null, -1);
			this.addEntity(player);
			Packet00Login loginPacket = new Packet00Login(player.getUsername(), player.getBoundingBox().x,
					player.getBoundingBox().y);
			// If the client has started a server, add it as a connection
			if (IGPEGame.game.socketServer != null) {
				IGPEGame.game.socketServer.addConnection((PlayerMP) player, loginPacket);
			}
			loginPacket.writeData(IGPEGame.game.socketClient);
		}
	}

	public static Vector2 randomSpawn() {
		return spawnPoints.get(new Random().nextInt(spawnPoints.size()));
	}

	public void update(float delta) {

		player.state = Player.STATE_IDLE;

		if (player.isReloading(delta))
			player.state = Player.STATE_RELOADING;

		player.activeWeapon.lastFired += delta;

		String Killer = null;
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
						PlayerMP a = (PlayerMP) iter.next();
						if (!b.getID().equalsIgnoreCase(((PlayerMP) a).getUsername())
								&& b.getBoundingBox().intersects(a.getBoundingBox()) && a.Alive()) {
							it.remove();
							removed = true;
							if (a.getUsername() == this.player.getUsername()) {
								this.player.hit(15); // TODO: DMG
								Killer = b.getID();
							}
						}
					}
					if (removed)
						continue;
					TileType tmp = getNextTile(b.getBoundingBox());
					if (tmp == TileType.WALL || tmp == TileType.BARREL || tmp == TileType.BOX || tmp == TileType.CACTUS
							|| tmp == TileType.LOGS || tmp == TileType.PLANT)
						it.remove();
				}
			}
		}

		if (this.player.getHP() <= 0) {
			Packet04Death packet = new Packet04Death(Killer, this.player.getUsername());
			packet.writeData(IGPEGame.game.socketClient);
		} else if (this.player.kills >= GameConfig.MULTIKILLS) {
			Packet05GameOver packet = new Packet05GameOver(username);
			packet.writeData(IGPEGame.game.socketClient);
		}

		// TODO: Packet for server closed
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

	public void handleDeath(String usernameKiller, String usernameKilled) {
		if (usernameKiller.equalsIgnoreCase(this.player.username))
			this.player.kills++;
		else if (usernameKilled.equalsIgnoreCase(this.player.username)) {
			this.player.deaths++;
			this.player.setHP(100);
			this.player.setPos(randomSpawn());
		}
	}

	public void handleGameOver(String usernameWinner) {
		MultiplayerOverScreen.winner = usernameWinner;
		MultiplayerOverScreen.kills = GameConfig.MULTIKILLS;
		this.gameOver = true;
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

	public synchronized void addEntity(AbstractDynamicObject player) {
		this.getEntities().add(player);
	}

	public synchronized List<AbstractDynamicObject> getEntities() {
		return this.entities;
	}

	public boolean isGameOver() {
		return gameOver;
	}

}
