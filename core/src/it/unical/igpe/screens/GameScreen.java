package it.unical.igpe.screens;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.Assets;
import it.unical.igpe.IGPEGame;
import it.unical.igpe.TileLayer;
import it.unical.igpe.World;
import it.unical.igpe.entity.Bullet;
import it.unical.igpe.entity.Enemy;
import it.unical.igpe.entity.Player;
import it.unical.igpe.entity.Wall;

public class GameScreen implements Screen {
	World world;
	IGPEGame game;
	OrthographicCamera camera;
	SpriteBatch batch;
	TileLayer layer;
	int[][] map;
	float stateTime;
	float rotation;
	Player player;
	Bullet bullet;
	TextureRegion currentFrame;
	LinkedList<Bullet> bls;
	LinkedList<Enemy> ens;
	ShapeRenderer sr;
	BitmapFont font;
	private Vector2 PointPos;

	public GameScreen(IGPEGame _game, World _world) {

		this.game = _game;
		this.world = _world;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);

		stateTime = 0f;
		rotation = world.rotation;

		player = world.getPlayer();
		ens = world.getEnemy();
		PointPos = new Vector2();

		bls = new LinkedList<Bullet>();

		batch = new SpriteBatch();
		sr = new ShapeRenderer();
		font = new BitmapFont();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();

		camera.position.x = world.getPlayer().getBoundingBox().x;
		camera.position.y = world.getPlayer().getBoundingBox().y;
		camera.update();

		world.updateWorld(delta);

		batch.setProjectionMatrix(camera.combined);
		sr.setProjectionMatrix(camera.combined);

		currentFrame = Assets.runningAnimation.getKeyFrame(stateTime, true);

		rotation = world.rotation;

		bls = player.getBullets();

		// draw map
		batch.begin();
		for (int x = 0; x < world.getMap().length; x++)
			for (int y = 0; y < world.getMap().length; y++)
				if (world.getMap()[x][y] == 0)
					batch.draw(Assets.Ground, x * 64, y * 64);
		for (Wall wall : world.getWls()) {
			batch.draw(Assets.Wall, wall.getPos().x, wall.getPos().y);
		}

		batch.draw(currentFrame, world.getPlayer().getBoundingBox().x, world.getPlayer().getBoundingBox().y, 32, 32, 64,
				64, 1f, 1f, rotation);
		font.setColor(Color.BLACK);
		font.draw(batch, "Bullet Num: " + bls.size(), camera.position.x - 300, camera.position.y - 250);
		batch.end();

		// FIXME: The point should follow the player's gun

		// PointPos.x = (float) (player.getPos().x + (32 * (Math.cos(rotation))
		// - 32 * (Math.sin(rotation))));
		// PointPos.y = (float) (player.getPos().y + (32 * (Math.sin(rotation))
		// + 32 * (Math.cos(rotation))));

		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		for (Bullet bullet : bls) {
			sr.rect(bullet.getPos().x, bullet.getPos().y, bullet.getBoundingBox().width,
					bullet.getBoundingBox().height);
		}
		for (Enemy e : ens) {
			sr.circle(e.getPos().x, e.getPos().y, 16);
		}
		sr.setColor(Color.RED);
		// sr.circle(PointPos.x, PointPos.y, 4);
		sr.end();

	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		sr.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
