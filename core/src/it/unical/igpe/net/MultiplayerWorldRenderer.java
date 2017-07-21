package it.unical.igpe.net;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import it.unical.igpe.GUI.Assets;
import it.unical.igpe.MapUtils.World;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.logic.AbstractDynamicObject;
import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Lootable;
import it.unical.igpe.logic.Player;
import it.unical.igpe.logic.Tile;
import it.unical.igpe.utils.GameConfig;
import it.unical.igpe.utils.LootableType;
import it.unical.igpe.utils.TileType;

public class MultiplayerWorldRenderer {
	private IGPEGame game;
	private OrthographicCamera camera;
	public Viewport viewport;
	private SpriteBatch batch;
	private ShapeRenderer sr;
	private float stateTime;
	private MultiplayerWorld world;

	public MultiplayerWorldRenderer(MultiplayerWorld world, IGPEGame game) {
		this.game = game;
		this.world = world;
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(true, 800, 800);
		this.camera.position.x = world.player.getBoundingBox().x;
		this.camera.position.y = world.player.getBoundingBox().y;
		this.viewport = new ExtendViewport(800, 800, camera);
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
				0.5f);
		camera.update();

		// Sound from the player
		if (world.getPlayer().state == Player.STATE_RUNNING)
			game.soundManager.FootStep.play();
		else
			game.soundManager.FootStep.pause();

		batch.begin();

		// Drawing tiles
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

		// Drawing loot
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
			} else if (loot.getType() == LootableType.AMMOPACK) {
				batch.draw(Assets.manager.get(Assets.AmmoBox, Texture.class), loot.getBoundingBox().x,
						loot.getBoundingBox().y);
			}
		}

		// Drawing players
		batch.setColor(1, 1, 1, 1);
		Iterator<AbstractDynamicObject> iter = world.entities.iterator();
		while(iter.hasNext()) {
			PlayerMP e = (PlayerMP) iter.next();
			if (e.getActWeapon() == "pistol") {
				if (e.state == Player.STATE_IDLE)
					batch.draw(Assets.idlePistolAnimation.getKeyFrame(stateTime, true), e.getBoundingBox().x,
							e.getBoundingBox().y, 32, 32, 64, 64, 1f, 1f, e.angle);
				else if (e.state == Player.STATE_RELOADING)
					batch.draw(Assets.reloadingPistolAnimation.getKeyFrame(stateTime, true), e.getBoundingBox().x,
							e.getBoundingBox().y, 32, 32, 64, 64, 1f, 1f, e.angle);
				else if (e.state == Player.STATE_RUNNING)
					batch.draw(Assets.runningPistolAnimation.getKeyFrame(stateTime, true), e.getBoundingBox().x,
							e.getBoundingBox().y, 32, 32, 64, 64, 1f, 1f, e.angle);
			} else if (e.getActWeapon() == "shotgun") {
				if (e.state == Player.STATE_IDLE)
					batch.draw(Assets.idleShotgunAnimation.getKeyFrame(stateTime, true), e.getBoundingBox().x,
							e.getBoundingBox().y, 32, 32, 64, 64, 1f, 1f, e.angle);
				else if (e.state == Player.STATE_RELOADING)
					batch.draw(Assets.reloadingShotgunAnimation.getKeyFrame(stateTime, true), e.getBoundingBox().x,
							e.getBoundingBox().y, 32, 32, 64, 64, 1f, 1f, e.angle);
				else if (e.state == Player.STATE_RUNNING)
					batch.draw(Assets.runningShotgunAnimation.getKeyFrame(stateTime, true), e.getBoundingBox().x,
							e.getBoundingBox().y, 32, 32, 64, 64, 1f, 1f, e.angle);
			} else if (e.getActWeapon() == "rifle") {
				if (e.state == Player.STATE_IDLE)
					batch.draw(Assets.idleRifleAnimation.getKeyFrame(stateTime, true), e.getBoundingBox().x,
							e.getBoundingBox().y, 32, 32, 64, 64, 1f, 1f, e.angle);
				else if (e.state == Player.STATE_RELOADING)
					batch.draw(Assets.reloadingRifleAnimation.getKeyFrame(stateTime, true), e.getBoundingBox().x,
							e.getBoundingBox().y, 32, 32, 64, 64, 1f, 1f, e.angle);
				else if (e.state == Player.STATE_RUNNING)
					batch.draw(Assets.runningRifleAnimation.getKeyFrame(stateTime, true), e.getBoundingBox().x,
							e.getBoundingBox().y, 32, 32, 64, 64, 1f, 1f, e.angle);
			}
			if(e.state == Player.STATE_RUNNING) {
				game.soundManager.FootStep.setVolume(GameConfig.SOUND_VOLUME 
						- (camera.position.x - e.getPos().x + camera.position.y - e.getPos().y));
				game.soundManager.FootStep.play();
			}
		}
		batch.setColor(1, 1, 1, 0.5f);
		batch.end();

		// Drawing Bullets
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
