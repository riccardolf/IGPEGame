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
	IGPEGame game;
	HUD hud;
	MapRenderer renderer;

	public GameScreen(IGPEGame _game, World _world) {
		this.game = _game;
		this.world = _world;
	}

	@Override
	public void show() {
		this.renderer = new MapRenderer(world);
		this.hud = new HUD();
		Gdx.input.setInputProcessor(null);
		IGPEGame.music.pause();
		Assets.manager.get(Assets.GameMusic, Music.class).setLooping(true);
		Assets.manager.get(Assets.GameMusic, Music.class).play();
		// Gdx.graphics.setCursor(Gdx.graphics.newCursor(Assets.manager.get(Assets.Crosshair,
		// Pixmap.class), 32, 32));
	}

	@Override
	public void render(float delta) {
		delta = 0.01f;
		handleInput();
		world.update(delta);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render(delta);
		hud.render(world.getPlayer());

		Assets.manager.get(Assets.GameMusic, Music.class).setVolume(GameConfig.MUSIC_VOLUME);
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
			game.setScreen(ScreenManager.PS);
		if (world.isLevelFinished()) {
			game.setScreen(ScreenManager.LCompletedS);
		} else if (world.isGameOver()) {
			ScreenManager.LCompletedS.gameOver = true;
			game.setScreen(ScreenManager.LCompletedS);
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

	private void handleInput() {
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
			box = new Rectangle(world.player.getBoundingBox().x - GameConfig.MOVESPEED,
					world.player.getBoundingBox().y - GameConfig.MOVESPEED, world.player.getBoundingBox().width,
					world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.MoveUpLeft();
			} else if (World.getNextTile(box) != TileType.WALL)
				world.player.MoveUpLeft();

		} else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!world.player.getReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x + GameConfig.MOVESPEED,
					world.player.getBoundingBox().y - GameConfig.MOVESPEED, world.player.getBoundingBox().width,
					world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.MoveUpRight();
			} else if (World.getNextTile(box) != TileType.WALL)
				world.player.MoveUpRight();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!world.player.getReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x - GameConfig.MOVESPEED,
					world.player.getBoundingBox().y + GameConfig.MOVESPEED, world.player.getBoundingBox().width,
					world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.MoveDownLeft();
			} else if (World.getNextTile(box) != TileType.WALL)
				world.player.MoveDownLeft();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!world.player.getReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x + GameConfig.MOVESPEED,
					world.player.getBoundingBox().y + GameConfig.MOVESPEED, world.player.getBoundingBox().width,
					world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.MoveDownRight();
			} else if (World.getNextTile(box) != TileType.WALL)
				world.player.MoveDownRight();
		} else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (!world.player.getReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x, world.player.getBoundingBox().y - GameConfig.MOVESPEED,
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.MoveUp();
			} else if (World.getNextTile(box) != TileType.WALL)
				world.player.MoveUp();
		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!world.player.getReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x - GameConfig.MOVESPEED, world.player.getBoundingBox().y,
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.MoveLeft();
			} else if (World.getNextTile(box) != TileType.WALL)
				world.player.MoveLeft();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (!world.player.getReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x, world.player.getBoundingBox().y + GameConfig.MOVESPEED,
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.MoveDown();
			} else if (World.getNextTile(box) != TileType.WALL)
				world.player.MoveDown();
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (!world.player.getReloading())
				world.player.state = Player.PLAYER_STATE_RUNNING;
			box = new Rectangle(world.player.getBoundingBox().x + GameConfig.MOVESPEED, world.player.getBoundingBox().y,
					world.player.getBoundingBox().width, world.player.getBoundingBox().height);
			if (World.getNextTile(box) == TileType.ENDLEVEL && World.isDoorUnlocked()) {
				World.finished = true;
				world.player.MoveRight();
			} else if (World.getNextTile(box) != TileType.WALL)
				world.player.MoveRight();
		}

		// Fire and Reloading action of the player
		if (Gdx.input.justTouched() && world.player.activeWeapon.lastFired > world.player.activeWeapon.fireRate
				&& world.player.activeWeapon.actClip > 0) {
			if (world.player.getActWeapon() == "pistol" && !world.player.isReloading()) {
				world.player.activeWeapon.lastFired = 0;
				Assets.manager.get(Assets.PistolFire, Sound.class).play(GameConfig.SOUND_VOLUME);
				world.player.fire();
			} else if (world.player.getActWeapon() == "shotgun" && !world.player.isReloading()) {
				world.player.activeWeapon.lastFired = 0;
				Assets.manager.get(Assets.ShotgunFire, Sound.class).play(GameConfig.SOUND_VOLUME);
				world.player.fire();
			} else if (world.player.getActWeapon() == "rifle" && !world.player.isReloading()) {
				world.player.activeWeapon.lastFired = 0;
				Assets.manager.get(Assets.RifleFire, Sound.class).play(GameConfig.SOUND_VOLUME);
				world.player.fire();
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
