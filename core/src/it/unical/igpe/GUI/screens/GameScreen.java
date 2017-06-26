package it.unical.igpe.GUI.screens;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.GUI.SoundManager;
import it.unical.igpe.GUI.HUD.HUD;
import it.unical.igpe.MapUtils.MapRenderer;
import it.unical.igpe.MapUtils.World;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.logic.Player;
import it.unical.igpe.utils.GameConfig;
import it.unical.igpe.utils.TileType;

public class GameScreen implements Screen {
	World world;
	HUD hud;
	MapRenderer renderer;

	public GameScreen(World _world) {
		this.world = _world;
		this.hud = new HUD(false);
		this.renderer = new MapRenderer(world);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		IGPEGame.music.pause();
		SoundManager.manager.get(SoundManager.GameMusic, Music.class).setVolume(GameConfig.MUSIC_VOLUME);
		SoundManager.manager.get(SoundManager.GameMusic, Music.class).setLooping(true);
		SoundManager.manager.get(SoundManager.GameMusic, Music.class).play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (world.player.isSlowMo(delta))
			delta *= 0.5f;
		
		world.update(delta);
		handleInput(delta);
		renderer.render(delta);
		hud.render(world.getPlayer());

		if (world.isLevelFinished()) {
			IGPEGame.game.setScreen(ScreenManager.LCompletedS);
		} else if (world.isGameOver()) {
			ScreenManager.LCompletedS.gameOver = true;
			IGPEGame.game.setScreen(ScreenManager.LCompletedS);
		}
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

	/**
	 * Handle Inputs from the user
	 * @param delta The time in seconds since the last update
	 */
	private void handleInput(float delta) {
		if (world.player.slowActive)
			delta *= 2f;
		
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
			if (!world.player.isReloading() && !world.player.isShooting())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.getBoundingBox().x -= GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y -= GameConfig.DIAGONALSPEED * delta;
			} else if (World.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().x -= GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y -= GameConfig.DIAGONALSPEED * delta;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!world.player.isReloading() && !world.player.isShooting())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.getBoundingBox().x += GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y -= GameConfig.DIAGONALSPEED * delta;
			} else if (World.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().x += GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y -= GameConfig.DIAGONALSPEED * delta;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!world.player.isReloading() && !world.player.isShooting())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.getBoundingBox().x -= GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y += GameConfig.DIAGONALSPEED * delta;
			} else if (World.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().x -= GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y += GameConfig.DIAGONALSPEED * delta;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!world.player.isReloading() && !world.player.isShooting())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.getBoundingBox().x += GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y += GameConfig.DIAGONALSPEED * delta;
			} else if (World.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().x += GameConfig.DIAGONALSPEED * delta;
				world.player.getBoundingBox().y += GameConfig.DIAGONALSPEED * delta;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (!world.player.isReloading() && !world.player.isShooting())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x,
					world.player.getBoundingBox().y - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.getBoundingBox().y -= GameConfig.MOVESPEED * delta;
			} else if (World.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().y -= GameConfig.MOVESPEED * delta;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!world.player.isReloading() && !world.player.isShooting())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x - (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y, world.player.getBoundingBox().width,
					world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.getBoundingBox().x -= GameConfig.MOVESPEED * delta;
			} else if (World.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().x -= GameConfig.MOVESPEED * delta;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (!world.player.isReloading() && !world.player.isShooting())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x,
					world.player.getBoundingBox().y + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.getBoundingBox().y += GameConfig.MOVESPEED * delta;
			} else if (World.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().y += GameConfig.MOVESPEED * delta;
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!world.player.isReloading() && !world.player.isShooting())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x + (int) (GameConfig.MOVESPEED * delta),
					world.player.getBoundingBox().y, world.player.getBoundingBox().width,
					world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.getBoundingBox().x += GameConfig.MOVESPEED * delta;
			} else if (World.getNextTile(box) != TileType.WALL) {
				world.player.getBoundingBox().x += GameConfig.MOVESPEED * delta;
			}

		}
		
		// Fire and Reloading action of the player
		if (Gdx.input.justTouched() && world.player.canShoot()) {
			if (world.player.getActWeapon() == "pistol") {
				SoundManager.manager.get(SoundManager.PistolFire, Sound.class).play(GameConfig.SOUND_VOLUME);
				world.player.fire();
			} else if (world.player.getActWeapon() == "shotgun") {
				SoundManager.manager.get(SoundManager.ShotgunFire, Sound.class).play(GameConfig.SOUND_VOLUME);
				world.player.fire();
			} else if (world.player.getActWeapon() == "rifle") {
				SoundManager.manager.get(SoundManager.RifleFire, Sound.class).play(GameConfig.SOUND_VOLUME);
				world.player.fire();
			}
		}

		if ((Gdx.input.isKeyJustPressed(Input.Keys.R) && world.player.canReload()) || world.player.checkAmmo()) {
			world.player.reload();
			if (world.player.getActWeapon() == "pistol") {
				SoundManager.manager.get(SoundManager.PistolReload, Sound.class).play(GameConfig.SOUND_VOLUME);
			} else if (world.player.getActWeapon() == "shotgun") {
				SoundManager.manager.get(SoundManager.ShotgunReload, Sound.class).play(GameConfig.SOUND_VOLUME);
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			world.player.setActWeapon("pistol");
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
			world.player.setActWeapon("shotgun");
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
			world.player.setActWeapon("rifle");
		}

		// Slow-Motion Key
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			if (world.player.slowActive)
				world.player.slowActive = false;
			else
				world.player.slowActive = true;
		}
		
		// Escape Key
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
			IGPEGame.game.setScreen(ScreenManager.PS);
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
