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

import it.unical.igpe.HUD.HUD;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.game.World;
import it.unical.igpe.logic.Bullet;
import it.unical.igpe.logic.Enemy;
import it.unical.igpe.logic.EnemyManager;
import it.unical.igpe.logic.Player;
import it.unical.igpe.logic.Wall;
import it.unical.igpe.tools.Assets;
import it.unical.igpe.tools.TileLayer;

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
	EnemyManager EM;
	ShapeRenderer sr;
	BitmapFont font;
	HUD hud;

	public GameScreen(IGPEGame _game, World _world) {

		this.game = _game;
		this.world = _world;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		
		hud = new HUD();

		stateTime = 0f;
		rotation = world.rotation;

		player = world.getPlayer();
		EM = world.EM;

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
		//TODO: StateManager
		switch(world.state) {
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

		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		for (Bullet bullet : bls) {
			sr.rect(bullet.getPos().x, bullet.getPos().y, bullet.getBoundingBox().width,
					bullet.getBoundingBox().height);
		}
		sr.setColor(Color.RED);
		for (Enemy e : EM.getList()) {
			sr.circle(e.getPos().x, e.getPos().y, 32);
		}
		sr.end();

		hud.stage.draw();
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
		hud.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
