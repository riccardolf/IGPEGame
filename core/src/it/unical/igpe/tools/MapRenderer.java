package it.unical.igpe.tools;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import it.unical.igpe.game.World;
import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Enemy;
import it.unical.igpe.logic.Lootable;
import it.unical.igpe.logic.Player;
import it.unical.igpe.logic.Tile;

public class MapRenderer {
	World world;
	OrthographicCamera camera;
	public Viewport viewport;
	SpriteBatch batch;
	ShapeRenderer sr;
	TextureRegion currentFrame;

	float stateTime;
	Player player;
	LinkedList<Bullet> bls;

	public MapRenderer(World _world) {
		this.world = _world;
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(true, GameConfig.WIDTH, GameConfig.HEIGHT);
		this.viewport = new ExtendViewport(GameConfig.WIDTH, GameConfig.HEIGHT, camera);
		this.camera.position.set(world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 0);
		this.batch = new SpriteBatch();
		this.batch.setColor(1, 1, 1, 0.5f);
		this.sr = new ShapeRenderer();

		this.player = world.getPlayer();
		this.bls = world.getPlayer().getBullets();
	}

	public void render(float deltaTime) {
		stateTime += deltaTime;
		batch.setProjectionMatrix(camera.combined);
		sr.setProjectionMatrix(camera.combined);

		camera.position.x = world.getPlayer().getBoundingBox().x;
		camera.position.y = world.getPlayer().getBoundingBox().y;
		camera.update();

		// Rendering different weapons
		if (world.getPlayer().getActWeapon() == "pistol")
			switch (world.state) {
			case IDLE:
				currentFrame = Assets.idlePistolAnimation.getKeyFrame(stateTime, true);
				break;
			case RUNNING:
				currentFrame = Assets.runningPistolAnimation.getKeyFrame(stateTime, true);
				break;
			case RELOADING:
				currentFrame = Assets.reloadingPistolAnimation.getKeyFrame(stateTime, true);
				break;
			}
		else if (world.getPlayer().getActWeapon() == "shotgun")
			switch (world.state) {
			case IDLE:
				currentFrame = Assets.idleShotgunAnimation.getKeyFrame(stateTime, true);
				break;
			case RUNNING:
				currentFrame = Assets.runningShotgunAnimation.getKeyFrame(stateTime, true);
				break;
			case RELOADING:
				currentFrame = Assets.reloadingShotgunAnimation.getKeyFrame(stateTime, true);
				break;
			}
		else if (world.getPlayer().getActWeapon() == "rifle")
			switch (world.state) {
			case IDLE:
				currentFrame = Assets.idleRifleAnimation.getKeyFrame(stateTime, true);
				break;
			case RUNNING:
				currentFrame = Assets.runningRifleAnimation.getKeyFrame(stateTime, true);
				break;
			case RELOADING:
				currentFrame = Assets.reloadingRifleAnimation.getKeyFrame(stateTime, true);
				break;
			}

		bls = player.getBullets();

		// draw map
		batch.begin();
		for (Tile tile : world.getTiles()) {
			if (tile.getType() == TileType.GROUND)
				batch.draw(Assets.Ground, tile.getBoundingBox().x, tile.getBoundingBox().y);
			else if (tile.getType() == TileType.WALL)
				batch.draw(Assets.Wall, tile.getBoundingBox().x, tile.getBoundingBox().y);
			else if (tile.getType() == TileType.ENDLEVEL) {
				if (!world.isDoorUnlocked())
					batch.draw(Assets.StairClosed, tile.getBoundingBox().x, tile.getBoundingBox().y);
				else
					batch.draw(Assets.Stair, tile.getBoundingBox().x, tile.getBoundingBox().y);
			}
		}
		for (Lootable loot : world.getLootables()) {
			if (loot.getType() == LootableType.HEALTPACK)
				batch.draw(Assets.HealthPack, loot.getBoundingBox().x, loot.getBoundingBox().y);
			else if (loot.getType() == LootableType.TRAP) {
				if (loot.closed)
					batch.draw(Assets.TrapClosed, loot.getBoundingBox().x, loot.getBoundingBox().y);
				else
					batch.draw(Assets.TrapOpen, loot.getBoundingBox().x, loot.getBoundingBox().y);
			} else if (loot.getType() == LootableType.KEYY) {
				batch.draw(Assets.KeyY, loot.getBoundingBox().x, loot.getBoundingBox().y);
			} else if (loot.getType() == LootableType.KEYR) {
				batch.draw(Assets.KeyR, loot.getBoundingBox().x, loot.getBoundingBox().y);
			} else if (loot.getType() == LootableType.KEYG) {
				batch.draw(Assets.KeyG, loot.getBoundingBox().x, loot.getBoundingBox().y);
			} else if (loot.getType() == LootableType.KEYB) {
				batch.draw(Assets.KeyB, loot.getBoundingBox().x, loot.getBoundingBox().y);
			}
		}
		batch.setColor(1, 1, 1, 1);
		batch.draw(currentFrame, world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
				64, 1f, 1f, player.angle);
		for (Enemy e : world.EM.getList()) {
			batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_SRC_ALPHA);
			batch.draw(Assets.Light, e.getBoundingBox().x - 320 + 32, e.getBoundingBox().y - 320 + 32, 640, 640);
			batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			if(e.Alive())
				batch.draw(Assets.Enemy, e.getPos().x, e.getPos().y, 32, 32, 64, 64, 1f, 1f, e.angle);
			else
				batch.draw(Assets.Skull, e.getPos().x, e.getPos().y, 48, 48);
		}
		batch.setColor(1, 1, 1, 0.5f);
		batch.end();

		sr.begin(ShapeType.Filled);
		sr.setColor(Color.RED);
		for (Bullet bullet : world.getBls()) {
			sr.rect(bullet.getPos().x, bullet.getPos().y, bullet.getBoundingBox().width,
					bullet.getBoundingBox().height);
		}
		sr.end();

		sr.begin(ShapeType.Line);
		sr.setColor(Color.BLACK);
		for (Enemy e : world.EM.getList()) {
			sr.circle(e.getPos().x + 32, e.getPos().y + 32, GameConfig.ENEMY_RADIUS);
			sr.circle(e.getPos().x + 32, e.getPos().y + 32, GameConfig.ENEMY_SHOOT_RADIUS);
		}

		sr.end();
	}

	public void dispose() {
		batch.dispose();
		sr.dispose();
	}
}
