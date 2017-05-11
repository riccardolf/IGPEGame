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

public class GameScreen implements Screen {
	World world;
	IGPEGame game;
	OrthographicCamera camera;
	SpriteBatch batch;
	TileLayer layer;
	int [][] map;
	float stateTime;
	float rotation;
	Player player;
	Bullet bullet;
	Vector2 posP;
	TextureRegion currentFrame;
	LinkedList<Bullet> bls;
	LinkedList<Enemy> ens;
	ShapeRenderer sr;
	BitmapFont font;
	
	public GameScreen(IGPEGame _game, World _world) {
		
		this.game = _game;
		this.world = _world;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,600);
		
		stateTime = 0f;
		rotation = world.rotation;
		
		posP = world.getPosP();
		player = world.getPlayer();
		ens = world.getEnemy();
		
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
		
		camera.position.x = posP.x;
		camera.position.y = posP.y;
		camera.update();
		
		world.updateWorld();
		
		batch.setProjectionMatrix(camera.combined);
		sr.setProjectionMatrix(camera.combined);
		
		currentFrame = Assets.runningAnimation.getKeyFrame(stateTime,true);
		
		rotation = world.rotation;
		
		bls = player.getBullets();
				
		// draw map
		batch.begin();		
		for(int y = 0; y < world.getMap().length; y++)
			for (int x = 0; x < world.getMap().length; x++) {
				if(world.getMap()[y][x] == 0)
					batch.draw(Assets.Water, x * 64, y * 64);
				else if(world.getMap()[y][x] == 1)
					batch.draw(Assets.Sand, x * 64, y * 64);
			}
		batch.draw(currentFrame, posP.x, posP.y, 32, 32, 64, 64, 1f, 1f, rotation);
		font.setColor(Color.BLACK);
		font.draw(batch, "Bullet Num: " + bls.size(), camera.position.x, camera.position.y);
		batch.end();
		
		
		
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		for (Bullet bullet : bls) {
			sr.circle(bullet.getPos().x, bullet.getPos().y, 4);
		}
		for (Enemy e : ens) {
			sr.circle(e.getPos().x, e.getPos().y, 16);
		}
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
	}

	@Override
	public void pause() {}
	
	@Override
	public void resume() {}
}
