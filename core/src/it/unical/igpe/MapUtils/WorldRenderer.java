package it.unical.igpe.MapUtils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import it.unical.igpe.GUI.Assets;
import it.unical.igpe.GUI.SoundManager;
import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Enemy;
import it.unical.igpe.logic.Lootable;
import it.unical.igpe.logic.Player;
import it.unical.igpe.logic.Tile;
import it.unical.igpe.utils.GameConfig;
import it.unical.igpe.utils.LootableType;
import it.unical.igpe.utils.TileType;

public class WorldRenderer {
	private World world;
	public OrthographicCamera camera;
	public Viewport viewport;
	private SpriteBatch batch;
	private ShapeRenderer sr;
	private float stateTime;

	public WorldRenderer(World _world) {
		this.world = _world;
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(true, 800, 800);
		this.viewport = new ExtendViewport(800, 800, camera);
		this.batch = new SpriteBatch();
		this.batch.setColor(1, 1, 1, 0.7f);
		this.sr = new ShapeRenderer();
		this.sr.setColor(Color.BLACK);
	}

	public void render(float deltaTime) {
		stateTime += deltaTime;
		batch.setProjectionMatrix(camera.combined);
		sr.setProjectionMatrix(camera.combined);

		camera.position.x = world.getPlayer().getX();
		camera.position.y = world.getPlayer().getY();
		camera.update();

		if (world.getPlayer().state == Player.STATE_RUNNING) {
			world.getPlayer().timeToNextStep -= deltaTime;
			if (world.getPlayer().timeToNextStep < 0) {
				SoundManager.manager.get(SoundManager.Step, Sound.class).play(GameConfig.SOUND_VOLUME);
				while (world.getPlayer().timeToNextStep < 0)
					world.getPlayer().timeToNextStep += 0.35f;
			}
		} else {
			world.getPlayer().timeToNextStep = 0;
		}

		batch.begin();

		// Drawing Tile
		for (Tile tile : world.getTiles()) {
			batch.draw(Assets.manager.get(Assets.Ground, Texture.class), tile.getX(), tile.getY());
			if (tile.getType() == TileType.WALL)
				batch.draw(Assets.manager.get(Assets.Wall, Texture.class), tile.getX(), tile.getY());
			else if (tile.getType() == TileType.ENDLEVEL) {
				if (!World.isDoorUnlocked())
					batch.draw(Assets.manager.get(Assets.StairClosed, Texture.class), tile.getX(), tile.getY());
				else
					batch.draw(Assets.manager.get(Assets.Stair, Texture.class), tile.getX(), tile.getY());
			}
			else if (tile.getType() == TileType.BOX)
				batch.draw(Assets.manager.get(Assets.Box, Texture.class), tile.getX(), tile.getY());
			else if (tile.getType() == TileType.BARREL)
				batch.draw(Assets.manager.get(Assets.Barrel, Texture.class), tile.getX(), tile.getY());
			else if (tile.getType() == TileType.CACTUS)
				batch.draw(Assets.manager.get(Assets.Cactus, Texture.class), tile.getX(), tile.getY());
			else if (tile.getType() == TileType.PLANT)
				batch.draw(Assets.manager.get(Assets.Plant, Texture.class), tile.getX(), tile.getY());
			else if (tile.getType() == TileType.LOGS)
				batch.draw(Assets.manager.get(Assets.Logs, Texture.class), tile.getX(), tile.getY());
		}

		// Drawing loot
		for (Lootable loot : world.getLootables()) {
			if (loot.getType() == LootableType.HEALTPACK) {
				batch.draw(Assets.manager.get(Assets.HealthPack, Texture.class), loot.getX(), loot.getY());
			}
			else if (loot.getType() == LootableType.TRAP) {
				if (loot.closed)
					batch.draw(Assets.manager.get(Assets.TrapClosed, Texture.class), loot.getX(), loot.getY());
				else
					batch.draw(Assets.manager.get(Assets.TrapOpen, Texture.class), loot.getX(), loot.getY());
			} else if (loot.getType() == LootableType.KEYY) {
				batch.draw(Assets.manager.get(Assets.KeyY, Texture.class), loot.getX(), loot.getY());
			} else if (loot.getType() == LootableType.KEYR) {
				batch.draw(Assets.manager.get(Assets.KeyR, Texture.class), loot.getX(), loot.getY());
			} else if (loot.getType() == LootableType.KEYG) {
				batch.draw(Assets.manager.get(Assets.KeyG, Texture.class), loot.getX(), loot.getY());
			} else if (loot.getType() == LootableType.KEYB) {
				batch.draw(Assets.manager.get(Assets.KeyB, Texture.class), loot.getX(), loot.getY());
			} else if (loot.getType() == LootableType.AMMOPACK) {
				batch.draw(Assets.manager.get(Assets.AmmoBox, Texture.class), loot.getX(), loot.getY());
			}
		}

		// Draw Enemies
		batch.setColor(1, 1, 1, 1);
		for (Enemy e : world.EM.getList()) {
			if (e.Alive()) {
				batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_SRC_ALPHA);
				batch.draw(Assets.manager.get(Assets.Light, Texture.class), e.getX() - 320 + 32, e.getY() - 320 + 32,
						640, 640);
				batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
				if (e.isMoving) {
					batch.draw(Assets.eRunningPistolAnimation.getKeyFrame(stateTime, true), e.getX(), e.getY(), 32, 32, 64,
							64, 1f, 1f, e.angle);
					e.timeToNextStep -= deltaTime;
					if (e.timeToNextStep < 0) {
						float boundary = camera.viewportWidth / 2;
						float xDistance = e.getX() - camera.position.x;
						float distance = camera.position.dst(e.getX(), e.getY(), 0) * Math.signum(xDistance);
						distance = Math.min(boundary, Math.max(distance, -boundary));
						SoundManager.manager.get(SoundManager.Step, Sound.class).play(
								GameConfig.SOUND_VOLUME * (1 - Math.abs(distance) / boundary), 1.0f,
								xDistance / boundary);
						while (e.timeToNextStep < 0)
							e.timeToNextStep += 0.35f;
					}
				} else {
					batch.draw(Assets.eIdlePistolAnimation.getKeyFrame(stateTime, true), e.getX(), e.getY(), 32, 32, 64,
							64, 1f, 1f, e.angle);
					e.timeToNextStep = 0;
				}

			} else
				batch.draw(Assets.Skull, e.getPos().x, e.getPos().y, 32, 32, 32, 32, 1f, 1f, e.angle);
		}

		// Draw Player
		if (world.getPlayer().getActWeapon() == "pistol") {
			if (world.getPlayer().state == Player.STATE_IDLE)
				batch.draw(Assets.idlePistolAnimation.getKeyFrame(stateTime, true), world.getPlayer().getX(),
						world.getPlayer().getY(), 32, 32, 64, 64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.STATE_RELOADING)
				batch.draw(Assets.reloadingPistolAnimation.getKeyFrame(stateTime, true), world.getPlayer().getX(),
						world.getPlayer().getY(), 32, 32, 64, 64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.STATE_RUNNING)
				batch.draw(Assets.runningPistolAnimation.getKeyFrame(stateTime, true), world.getPlayer().getX(),
						world.getPlayer().getY(), 32, 32, 64, 64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.STATE_SHOOTING) {
				batch.draw(Assets.shootingPistolAnimation.getKeyFrame(stateTime), world.getPlayer().getX(),
						world.getPlayer().getY(), 32, 32, 64, 64, 1f, 1f, world.getPlayer().angle);
			}
		} else if (world.getPlayer().getActWeapon() == "shotgun") {
			if (world.getPlayer().state == Player.STATE_IDLE)
				batch.draw(Assets.idleShotgunAnimation.getKeyFrame(stateTime, true), world.getPlayer().getX(),
						world.getPlayer().getY(), 32, 32, 64, 64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.STATE_RELOADING)
				batch.draw(Assets.reloadingShotgunAnimation.getKeyFrame(stateTime, true), world.getPlayer().getX(),
						world.getPlayer().getY(), 32, 32, 64, 64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.STATE_RUNNING)
				batch.draw(Assets.runningShotgunAnimation.getKeyFrame(stateTime, true), world.getPlayer().getX(),
						world.getPlayer().getY(), 32, 32, 64, 64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.STATE_SHOOTING) {
				batch.draw(Assets.shootingShotgunAnimation.getKeyFrame(stateTime), world.getPlayer().getX(),
						world.getPlayer().getY(), 32, 32, 64, 64, 1f, 1f, world.getPlayer().angle);
			}
		} else if (world.getPlayer().getActWeapon() == "rifle") {
			if (world.getPlayer().state == Player.STATE_IDLE)
				batch.draw(Assets.idleRifleAnimation.getKeyFrame(stateTime, true), world.getPlayer().getX(),
						world.getPlayer().getY(), 32, 32, 64, 64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.STATE_RELOADING)
				batch.draw(Assets.reloadingRifleAnimation.getKeyFrame(stateTime, true), world.getPlayer().getX(),
						world.getPlayer().getY(), 32, 32, 64, 64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.STATE_RUNNING)
				batch.draw(Assets.runningRifleAnimation.getKeyFrame(stateTime, true), world.getPlayer().getX(),
						world.getPlayer().getY(), 32, 32, 64, 64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.STATE_SHOOTING) {
				batch.draw(Assets.shootingRifleAnimation.getKeyFrame(stateTime), world.getPlayer().getX(),
						world.getPlayer().getY(), 32, 32, 64, 64, 1f, 1f, world.getPlayer().angle);
			}
		}
		batch.setColor(1, 1, 1, 0.7f);
		batch.end();

		// Draw Bullets
		sr.begin(ShapeType.Filled);
		for (Bullet bullet : world.getBls()) {
			sr.circle(bullet.getX(), bullet.getY(), 4);
		}
		sr.end();

	}

	public void dispose() {
		batch.dispose();
	}
}
