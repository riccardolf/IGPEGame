package it.unical.igpe.net.screens;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.GUI.HUD.HUD;
import it.unical.igpe.GUI.screens.ScreenManager;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.logic.Player;
import it.unical.igpe.net.MultiplayerWorld;
import it.unical.igpe.net.MultiplayerWorldRenderer;
import it.unical.igpe.net.packet.Packet02Move;
import it.unical.igpe.utils.GameConfig;
import it.unical.igpe.utils.TileType;

public class MultiplayerGameScreen implements Screen {
	private IGPEGame game;
	MultiplayerWorld world;
	HUD hud;
	MultiplayerWorldRenderer renderer;

	public MultiplayerGameScreen(IGPEGame game) {
		this.game = game;
		IGPEGame.game.worldMP = new MultiplayerWorld("arena.map", false);
		this.world = IGPEGame.game.worldMP;
		this.hud = new HUD();
		this.renderer = new MultiplayerWorldRenderer(world, game);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		game.soundManager.MenuMusic.pause();
		game.soundManager.GameMusic.setVolume(GameConfig.MUSIC_VOLUME);
		game.soundManager.GameMusic.setLooping(true);
		game.soundManager.GameMusic.play();
		game.soundManager.FootStep.setVolume(GameConfig.SOUND_VOLUME);
		game.soundManager.FootStep.setLooping(true);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.update(delta);
		handleInput(delta);
		renderer.render(delta);
		hud.render(world.player);

		if (world.isGameOver())
			IGPEGame.game.setScreen(ScreenManager.MOS);
	}

	@Override
	public void resize(int width, int height) {
		renderer.viewport.update(width, height, true);
	}

	@Override
	public void dispose() {
		hud.dispose();
		renderer.dispose();
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
				world.player.state = Player.STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);

		} else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!world.player.isReloading())
				world.player.state = Player.STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (MultiplayerWorld.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().x += GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y -= GameConfig.DIAGONALSPEED * delta;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!world.player.isReloading())
				world.player.state = Player.STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (MultiplayerWorld.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().x -= GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y += GameConfig.DIAGONALSPEED * delta;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!world.player.isReloading())
				world.player.state = Player.STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (MultiplayerWorld.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().x += GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y += GameConfig.DIAGONALSPEED * delta;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (!world.player.isReloading())
				world.player.state = Player.STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x,
					world.player.getBoundingBox().y - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
		if (MultiplayerWorld.getNextTile(box) != TileType.WALL)
				world.player.getBoundingBox().y -= GameConfig.MOVESPEED * delta;

		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!world.player.isReloading())
				world.player.state = Player.STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y, world.player.getBoundingBox().width,
					world.player.getBoundingBox().height);
			if (MultiplayerWorld.getNextTile(box) != TileType.WALL)
				world.player.getBoundingBox().x -= GameConfig.MOVESPEED * delta;

		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (!world.player.isReloading())
				world.player.state = Player.STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x,
					world.player.getBoundingBox().y + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (MultiplayerWorld.getNextTile(box) != TileType.WALL)
				world.player.getBoundingBox().y += GameConfig.MOVESPEED * delta;

		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!world.player.isReloading())
				world.player.state = Player.STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y, world.player.getBoundingBox().width,
					world.player.getBoundingBox().height);
			if (MultiplayerWorld.getNextTile(box) != TileType.WALL)
				world.player.getBoundingBox().x += GameConfig.MOVESPEED * delta;

		}

		// Fire and Reloading action of the player
		if (Gdx.input.justTouched() && world.player.canShoot()) {
			world.player.fire();
			if (world.player.getActWeapon() == "pistol" && !world.player.isReloading()) {
				game.soundManager.PistolFire.play(GameConfig.SOUND_VOLUME);
			} else if (world.player.getActWeapon() == "shotgun" && !world.player.isReloading()) {
				game.soundManager.ShotgunFire.play(GameConfig.SOUND_VOLUME);
			} else if (world.player.getActWeapon() == "rifle" && !world.player.isReloading()) {
				game.soundManager.RifleFire.play(GameConfig.SOUND_VOLUME);
			}
			if (world.player.checkAmmo()) {
				if (world.player.getActWeapon() == "pistol")
					game.soundManager.PistolReload.play(GameConfig.SOUND_VOLUME);
				else if (world.player.getActWeapon() == "shotgun")
					game.soundManager.ShotgunReload.play(GameConfig.SOUND_VOLUME);
			}
		}

		if ((Gdx.input.isKeyJustPressed(Input.Keys.R) && world.player.canReload()) || world.player.checkAmmo()) {
			world.player.reload();
			if (world.player.getActWeapon() == "pistol") {
				game.soundManager.PistolReload.play(GameConfig.SOUND_VOLUME);
			} else if (world.player.getActWeapon() == "shotgun") {
				game.soundManager.ShotgunReload.play(GameConfig.SOUND_VOLUME);
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
