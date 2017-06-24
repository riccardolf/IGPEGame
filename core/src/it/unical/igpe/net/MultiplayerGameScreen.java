package it.unical.igpe.net;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.GUI.Assets;
import it.unical.igpe.GUI.HUD.HUD;
import it.unical.igpe.GUI.screens.ScreenManager;
import it.unical.igpe.MapUtils.World;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.logic.Player;
import it.unical.igpe.net.packet.Packet02Move;
import it.unical.igpe.net.packet.Packet03Fire;
import it.unical.igpe.utils.GameConfig;
import it.unical.igpe.utils.TileType;

public class MultiplayerGameScreen implements Screen {
	// message to server player.pos, player.angle, player.state,
	// player.activeWeapon.ID, initial pos player.fire()
	// message from server other players, enemies
	// draw this things on multiplayer
	MultiplayerWorld world;
	HUD hud;
	MultiplayerWorldRenderer renderer;

	public MultiplayerGameScreen() {
		IGPEGame.game.worldMP = new MultiplayerWorld("map.txt");
		this.world = IGPEGame.game.worldMP;
		this.hud = new HUD();
	}

	@Override
	public void show() {
		this.renderer = new MultiplayerWorldRenderer(world);
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void render(float delta) {
		delta = 0.01f;
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render(delta);
		world.update(delta);
		handleInput(delta);
		hud.render(world.player);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void dispose() {

	}

	private void handleInput(float delta) {
		float midX = Gdx.graphics.getWidth() / 2;
		float midY = Gdx.graphics.getHeight() / 2;
		float mouseX = Gdx.input.getX();
		float mouseY = Gdx.input.getY();
		Vector2 dir = new Vector2(mouseX - midX, mouseY - midY);
		dir.rotate90(-1);
		world.player.angle = dir.angle();

		Rectangle box = new Rectangle();
		// Movements and Collisions of the player
		if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!world.player.isReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (MultiplayerWorld.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				MultiplayerWorld.finished = true;
				world.player.getBoundingBox().x -= GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y -= GameConfig.DIAGONALSPEED * delta;
			} else if (MultiplayerWorld.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().x -= GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y -= GameConfig.DIAGONALSPEED * delta;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!world.player.isReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (MultiplayerWorld.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				MultiplayerWorld.finished = true;
				world.player.getBoundingBox().x += GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y -= GameConfig.DIAGONALSPEED * delta;
			} else if (MultiplayerWorld.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().x += GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y -= GameConfig.DIAGONALSPEED * delta;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!world.player.isReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (MultiplayerWorld.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				MultiplayerWorld.finished = true;
				world.player.getBoundingBox().x -= GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y += GameConfig.DIAGONALSPEED * delta;
			} else if (MultiplayerWorld.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().x -= GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y += GameConfig.DIAGONALSPEED * delta;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!world.player.isReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (MultiplayerWorld.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				MultiplayerWorld.finished = true;
				world.player.getBoundingBox().x += GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y += GameConfig.DIAGONALSPEED * delta;
			} else if (MultiplayerWorld.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().x += GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y += GameConfig.DIAGONALSPEED * delta;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (!world.player.isReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x,
					world.player.getBoundingBox().y - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (MultiplayerWorld.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				MultiplayerWorld.finished = true;
				world.player.getBoundingBox().y -= GameConfig.MOVESPEED * delta;
			} else if (MultiplayerWorld.getNextTile(box) != TileType.WALL)
				world.player.getBoundingBox().y -= GameConfig.MOVESPEED * delta;

		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!world.player.isReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y, world.player.getBoundingBox().width,
					world.player.getBoundingBox().height);
			if (MultiplayerWorld.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				MultiplayerWorld.finished = true;
				world.player.getBoundingBox().x -= GameConfig.MOVESPEED * delta;
			} else if (MultiplayerWorld.getNextTile(box) != TileType.WALL)
				world.player.getBoundingBox().x -= GameConfig.MOVESPEED * delta;
			
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (!world.player.isReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x,
					world.player.getBoundingBox().y + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (MultiplayerWorld.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				MultiplayerWorld.finished = true;
				world.player.getBoundingBox().y += GameConfig.MOVESPEED * delta;
			} else if (MultiplayerWorld.getNextTile(box) != TileType.WALL)
				world.player.getBoundingBox().y += GameConfig.MOVESPEED * delta;

		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!world.player.isReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y, world.player.getBoundingBox().width,
					world.player.getBoundingBox().height);
			if (MultiplayerWorld.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				MultiplayerWorld.finished = true;
				world.player.getBoundingBox().x += GameConfig.MOVESPEED * delta;
			} else if (MultiplayerWorld.getNextTile(box) != TileType.WALL)
				world.player.getBoundingBox().x += GameConfig.MOVESPEED * delta;

		}

		// Fire and Reloading action of the player
		if (Gdx.input.justTouched() && world.player.activeWeapon.lastFired > world.player.activeWeapon.fireRate
				&& world.player.activeWeapon.actClip > 0) {
			Packet03Fire packetFire = new Packet03Fire(world.player.getUsername(), world.player.getBoundingBox().x,
					world.player.getBoundingBox().y, world.player.angle);
			packetFire.writeData(IGPEGame.game.socketClient);
			world.fireBullet(world.player.getUsername(), world.player.getBoundingBox().x,
					world.player.getBoundingBox().y, world.player.angle);
			if (world.player.getActWeapon() == "pistol" && !world.player.isReloading()) {
				world.player.activeWeapon.lastFired = 0;
				Assets.manager.get(Assets.PistolFire, Sound.class).play(GameConfig.SOUND_VOLUME);
				// world.player.fire();
			} else if (world.player.getActWeapon() == "shotgun" && !world.player.isReloading()) {
				world.player.activeWeapon.lastFired = 0;
				Assets.manager.get(Assets.ShotgunFire, Sound.class).play(GameConfig.SOUND_VOLUME);
				// world.player.fire();
			} else if (world.player.getActWeapon() == "rifle" && !world.player.isReloading()) {
				world.player.activeWeapon.lastFired = 0;
				Assets.manager.get(Assets.RifleFire, Sound.class).play(GameConfig.SOUND_VOLUME);
				// world.player.fire();
			}
			if (world.player.checkAmmo()) {
				if (world.player.getActWeapon() == "pistol")
					Assets.manager.get(Assets.PistolReload, Sound.class).play(GameConfig.SOUND_VOLUME);
				else if (world.player.getActWeapon() == "shotgun")
					Assets.manager.get(Assets.ShotgunReload, Sound.class).play(GameConfig.SOUND_VOLUME);
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.R) && world.player.canReload()) {
			world.player.reload();
			if (world.player.getActWeapon() == "pistol") {
				Assets.manager.get(Assets.PistolFire, Sound.class).play(GameConfig.SOUND_VOLUME);
			} else if (world.player.getActWeapon() == "shotgun") {
				Assets.manager.get(Assets.ShotgunReload, Sound.class).play(GameConfig.SOUND_VOLUME);
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			world.player.setActWeapon("pistol");
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
			world.player.setActWeapon("shotgun");
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
			world.player.setActWeapon("rifle");
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
			IGPEGame.game.setScreen(ScreenManager.MPS);

		Packet02Move packetMove;
		if (world.player.activeWeapon.ID == "pistol")
			packetMove = new Packet02Move(world.player.getUsername(), world.player.getBoundingBox().x,
					world.player.getBoundingBox().y, world.player.angle, world.player.state, 0);
		else if (world.player.activeWeapon.ID == "shotgun")
			packetMove = new Packet02Move(world.player.getUsername(), world.player.getBoundingBox().x,
					world.player.getBoundingBox().y, world.player.angle, world.player.state, 1);
		else
			packetMove = new Packet02Move(world.player.getUsername(), world.player.getBoundingBox().x,
					world.player.getBoundingBox().y, world.player.angle, world.player.state, 2);
		packetMove.writeData(IGPEGame.game.socketClient);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

}
