package it.unical.igpe.screens;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.HUD.HUD;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.game.World;
import it.unical.igpe.logic.Player;
import it.unical.igpe.tools.Assets;
import it.unical.igpe.tools.GameConfig;
import it.unical.igpe.tools.MapRenderer;
import it.unical.igpe.tools.TileType;

public class GameScreen implements Screen {
	World world;
	HUD hud;
	MapRenderer renderer;

	public GameScreen(World _world) {
		this.world = _world;
		this.hud = new HUD();
		this.renderer = new MapRenderer(world);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(null);
		IGPEGame.music.pause();
		Assets.manager.get(Assets.GameMusic, Music.class).setLooping(true);
		Assets.manager.get(Assets.GameMusic, Music.class).play();
	}

	@Override
	public void render(float delta) {
		if (world.player.isSlowMo(delta))
			delta *= 0.5f;
		world.update(delta);
		handleInput(delta);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render(delta);
		hud.render(world.getPlayer());

		Assets.manager.get(Assets.GameMusic, Music.class).setVolume(GameConfig.MUSIC_VOLUME);
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
			IGPEGame.game.setScreen(ScreenManager.PS);
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
			if (!world.player.getReloading())
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
			if (!world.player.getReloading())
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
			if (!world.player.getReloading())
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
			if (!world.player.getReloading())
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
			if (!world.player.getReloading())
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
			if (!world.player.getReloading())
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
			if (!world.player.getReloading())
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
			if (!world.player.getReloading())
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
			if (world.player.getActWeapon() == "pistol" && !world.player.isReloading()) {
				Assets.manager.get(Assets.PistolFire, Sound.class).play(GameConfig.SOUND_VOLUME);
				world.player.fire();
			} else if (world.player.getActWeapon() == "shotgun" && !world.player.isReloading()) {
				Assets.manager.get(Assets.ShotgunFire, Sound.class).play(GameConfig.SOUND_VOLUME);
				world.player.fire();
			} else if (world.player.getActWeapon() == "rifle" && !world.player.isReloading()) {
				Assets.manager.get(Assets.RifleFire, Sound.class).play(GameConfig.SOUND_VOLUME);
				world.player.fire();
			}
		}

		if (world.player.checkAmmo()) {
			if (world.player.getActWeapon() == "pistol")
				Assets.manager.get(Assets.PistolReload, Sound.class).play(GameConfig.SOUND_VOLUME);
			else if (world.player.getActWeapon() == "shotgun")
				Assets.manager.get(Assets.ShotgunReload, Sound.class).play(GameConfig.SOUND_VOLUME);
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.R) && world.player.canReload()) {
			world.player.reload();
			if (world.player.getActWeapon() == "pistol") {
				Assets.manager.get(Assets.PistolReload, Sound.class).play(GameConfig.SOUND_VOLUME);
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

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			if(world.player.slowActive)
				world.player.slowActive = false;
			else
				world.player.slowActive = true;
		}
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
