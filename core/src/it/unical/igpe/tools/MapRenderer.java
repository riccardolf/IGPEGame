package it.unical.igpe.tools;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import it.unical.igpe.game.World;
import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Enemy;
import it.unical.igpe.logic.Lootable;
import it.unical.igpe.logic.Player;
import it.unical.igpe.logic.Tile;

public class MapRenderer {
	private World world;
	private OrthographicCamera camera;
	public Viewport viewport;
	private SpriteBatch batch;
	private ShapeRenderer sr;
	private float stateTime;

	public MapRenderer(World _world) {
		this.world = _world;
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(true, GameConfig.WIDTH, GameConfig.HEIGHT);
		this.viewport = new ExtendViewport(GameConfig.WIDTH, GameConfig.HEIGHT, camera);
		this.batch = new SpriteBatch();
		this.batch.setColor(1, 1, 1, 0.5f);
		this.sr = new ShapeRenderer();
		this.sr.setColor(Color.BLACK);
	}

	public void render(float deltaTime) {
		stateTime += deltaTime;
		batch.setProjectionMatrix(camera.combined);
		sr.setProjectionMatrix(camera.combined);

		camera.position.lerp(new Vector3(world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 0),
				0.3f);
		camera.update();
		Assets.manager.get(Assets.FootStep, Music.class).setVolume(GameConfig.SOUND_VOLUME);
		Assets.manager.get(Assets.FootStep, Music.class).setLooping(true);

		// FootStep sound
		if (world.getPlayer().state == Player.PLAYER_STATE_RUNNING)
			Assets.manager.get(Assets.FootStep, Music.class).play();
		else
			Assets.manager.get(Assets.FootStep, Music.class).pause();

		batch.begin();
		
		// Drawing Tile
		for (Tile tile : world.getTiles()) {
			if (tile.getType() == TileType.GROUND)
				batch.draw(Assets.manager.get(Assets.Ground, Texture.class), tile.getBoundingBox().x,
						tile.getBoundingBox().y);
			else if (tile.getType() == TileType.WALL)
				batch.draw(Assets.manager.get(Assets.Wall, Texture.class), tile.getBoundingBox().x,
						tile.getBoundingBox().y);
			else if (tile.getType() == TileType.ENDLEVEL) {
				if (!World.isDoorUnlocked())
					batch.draw(Assets.manager.get(Assets.StairClosed, Texture.class), tile.getBoundingBox().x,
							tile.getBoundingBox().y);
				else
					batch.draw(Assets.manager.get(Assets.Stair, Texture.class), tile.getBoundingBox().x,
							tile.getBoundingBox().y);
			}
		}
		
		//Drawing loot
		for (Lootable loot : world.getLootables()) {
			if (loot.getType() == LootableType.HEALTPACK)
				batch.draw(Assets.manager.get(Assets.HealthPack, Texture.class), loot.getBoundingBox().x,
						loot.getBoundingBox().y);
			else if (loot.getType() == LootableType.TRAP) {
				if (loot.closed)
					batch.draw(Assets.manager.get(Assets.TrapClosed, Texture.class), loot.getBoundingBox().x,
							loot.getBoundingBox().y);
				else
					batch.draw(Assets.manager.get(Assets.TrapOpen, Texture.class), loot.getBoundingBox().x,
							loot.getBoundingBox().y);
			} else if (loot.getType() == LootableType.KEYY) {
				batch.draw(Assets.manager.get(Assets.KeyY, Texture.class), loot.getBoundingBox().x,
						loot.getBoundingBox().y);
			} else if (loot.getType() == LootableType.KEYR) {
				batch.draw(Assets.manager.get(Assets.KeyR, Texture.class), loot.getBoundingBox().x,
						loot.getBoundingBox().y);
			} else if (loot.getType() == LootableType.KEYG) {
				batch.draw(Assets.manager.get(Assets.KeyG, Texture.class), loot.getBoundingBox().x,
						loot.getBoundingBox().y);
			} else if (loot.getType() == LootableType.KEYB) {
				batch.draw(Assets.manager.get(Assets.KeyB, Texture.class), loot.getBoundingBox().x,
						loot.getBoundingBox().y);
			} else if(loot.getType() == LootableType.AMMOPACK) {
				batch.draw(Assets.manager.get(Assets.AmmoBox, Texture.class), loot.getBoundingBox().x,
						loot.getBoundingBox().y);
			}
		}

		batch.setColor(1, 1, 1, 1);

		// Draw Enemies
		for (Enemy e : world.EM.getList()) {
			if (e.Alive()) {
				batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_SRC_ALPHA);
				batch.draw(Assets.manager.get(Assets.Light, Texture.class), e.getBoundingBox().x - 320 + 32,
						e.getBoundingBox().y - 320 + 32, 640, 640);
				batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
				batch.draw(Assets.Enemy, e.getPos().x, e.getPos().y, 32, 32, 64, 64, 1f, 1f, e.angle);
			} else
				batch.draw(Assets.manager.get(Assets.Skull, Texture.class), e.getPos().x, e.getPos().y, 48, 48);
		}
		
		//Draw Player
		if (world.getPlayer().getActWeapon() == "pistol") {
			if (world.getPlayer().state == Player.PLAYER_STATE_IDLE)
				batch.draw(Assets.idlePistolAnimation.getKeyFrame(stateTime, true), world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
						64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.PLAYER_STATE_RELOADING)
				batch.draw(Assets.reloadingPistolAnimation.getKeyFrame(stateTime, true), world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
						64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.PLAYER_STATE_RUNNING)
				batch.draw(Assets.runningPistolAnimation.getKeyFrame(stateTime, true), world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
						64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.PLAYER_STATE_SHOOTING)
				batch.draw(Assets.shootingPistolAnimation.getKeyFrame(stateTime, true), world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
						64, 1f, 1f, world.getPlayer().angle);
		} else if (world.getPlayer().getActWeapon() == "shotgun") {
			if (world.getPlayer().state == Player.PLAYER_STATE_IDLE)
				batch.draw(Assets.idleShotgunAnimation.getKeyFrame(stateTime, true), world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
						64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.PLAYER_STATE_RELOADING)
				batch.draw(Assets.reloadingShotgunAnimation.getKeyFrame(stateTime, true), world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
						64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.PLAYER_STATE_RUNNING)
				batch.draw(Assets.runningShotgunAnimation.getKeyFrame(stateTime, true), world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
						64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.PLAYER_STATE_SHOOTING)
				batch.draw(Assets.shootingShotgunAnimation.getKeyFrame(stateTime, true), world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
						64, 1f, 1f, world.getPlayer().angle);
		} else if (world.getPlayer().getActWeapon() == "rifle") {
			if (world.getPlayer().state == Player.PLAYER_STATE_IDLE)
				batch.draw(Assets.idleRifleAnimation.getKeyFrame(stateTime, true), world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
						64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.PLAYER_STATE_RELOADING)
				batch.draw(Assets.reloadingRifleAnimation.getKeyFrame(stateTime, true), world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
						64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.PLAYER_STATE_RUNNING)
				batch.draw(Assets.runningRifleAnimation.getKeyFrame(stateTime, true), world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
						64, 1f, 1f, world.getPlayer().angle);
			else if (world.getPlayer().state == Player.PLAYER_STATE_SHOOTING)
				batch.draw(Assets.shootingRifleAnimation.getKeyFrame(stateTime, true), world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
						64, 1f, 1f, world.getPlayer().angle);
		}
		batch.setColor(1, 1, 1, 0.5f);
		batch.end();
		
		// Draw Bullets
		sr.begin(ShapeType.Filled);
		
		for (Bullet bullet : world.getBls()) {
			sr.circle(bullet.getBoundingBox().x, bullet.getBoundingBox().y, 4);
		}
		sr.end();
		
	}

	public void dispose() {
		batch.dispose();
	}
}
