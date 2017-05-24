package it.unical.igpe.tools;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import it.unical.igpe.game.World;
import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Enemy;
import it.unical.igpe.logic.Player;
import it.unical.igpe.logic.Tile;

public class MapRenderer {
	World world;
	OrthographicCamera camera;
	SpriteBatch batch;
	ShapeRenderer sr;
	TextureRegion currentFrame;

	float rotation;
	float stateTime;
	Player player;
	LinkedList<Bullet> bls;

	public MapRenderer(World _world) {
		this.world = _world;
		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, 800, 600);
		this.camera.position.set(world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 0);
		this.batch = new SpriteBatch();
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

		rotation = world.rotation;

		bls = player.getBullets();

		// draw map
		batch.begin();
		for (Tile tile : world.getTiles()) {
			if (tile.getType() == TileType.GROUND)
				batch.draw(Assets.Ground, tile.getBoundingBox().x, tile.getBoundingBox().y);
			else if (tile.getType() == TileType.WALL)
				batch.draw(Assets.Wall, tile.getBoundingBox().x, tile.getBoundingBox().y);
		}
		batch.draw(currentFrame, world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
				64, 1f, 1f, rotation);
		batch.end();

		sr.begin(ShapeType.Filled);
		sr.setColor(Color.RED);
		for (

		Bullet bullet : bls) {
			sr.rect(bullet.getPos().x, bullet.getPos().y, bullet.getBoundingBox().width,
					bullet.getBoundingBox().height);
		}
		sr.setColor(Color.RED);
		for (Enemy e : world.EM.getList()) {
			sr.circle(e.getPos().x, e.getPos().y, 32);
		}
		sr.end();
	}

	public void dispose() {
		batch.dispose();
		sr.dispose();
	}
}
