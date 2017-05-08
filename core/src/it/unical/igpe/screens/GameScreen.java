package it.unical.igpe.screens;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import it.unical.igpe.Assets;
import it.unical.igpe.GameConfig;
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
	int [][] map;
	float stateTime;
	float rotation;
	Player player;
	Bullet bullet;
	Vector2 posP;
	TextureRegion currentFrame;
	LinkedList<Bullet> bls;
	ShapeRenderer sr;
	Enemy enemy;
	LinkedList<Wall> wls;
	
	public GameScreen(IGPEGame _game, World _world) {
		
		this.game = _game;
		this.world = new World();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,600);
		
		stateTime = 0f;
		rotation = 0f;
		
		posP = world.getPosP();
		player = world.getPlayer();
		enemy = world.getEnemy();
		
		wls = world.getWls();
		
		bls = player.getBullets();
		
		batch = new SpriteBatch();
		sr = new ShapeRenderer();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();
		
		camera.position.x = posP.x;
		camera.position.y = posP.y;
		camera.update();
		
		bls = player.getBullets();
		for (Bullet bullet : bls) {
			bullet.update();
		}
		
		batch.setProjectionMatrix(camera.combined);
		sr.setProjectionMatrix(camera.combined);
		
		enemy.findPathToTarget(player.getPos());
		
		currentFrame = Assets.runningAnimation.getKeyFrame(stateTime,true);
		
		rotation = getAngle((float)Gdx.input.getX(), (float) Gdx.input.getY());
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A))
			player.MoveUpLeft();
		else if(Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D))
			player.MoveUpRight();
		else if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A))
			player.MoveDownLeft();
		else if(Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D))
			player.MoveDownRight();
		else if(Gdx.input.isKeyPressed(Input.Keys.W))
			player.MoveUp();
		else if(Gdx.input.isKeyPressed(Input.Keys.A))
			player.MoveLeft();
		else if(Gdx.input.isKeyPressed(Input.Keys.S))
			player.MoveDown();
		else if(Gdx.input.isKeyPressed(Input.Keys.D))
			player.MoveRight();
			
		if(Gdx.input.justTouched()) {
		    player.fire(rotation+90);
		}
		if(Gdx.input.justTouched())
		    player.fire(rotation);

		
		// Direction bound to the player, so the bullet receive from the player the direction
		
		batch.begin();
		
		
		// draw map;
		
		for(int y = 0; y < world.getMap().length; y++) {
			for (int x = 0; x < world.getMap().length; x++) {
				if(world.getMap()[y][x] == 0)
					batch.draw(Assets.Water, x * 32, y * 32);
				else if(world.getMap()[y][x] == 1) {
					batch.draw(Assets.Sand, x * 32, y * 32);
				}
			}
		}
		batch.draw(currentFrame, posP.x, posP.y, 32, 32, 64, 64, 1f, 1f, rotation);
		batch.end();
		
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.BLACK);
		for (Bullet bullet : bls) {
			sr.circle(bullet.getPos().x, bullet.getPos().y, 4);
		}
		sr.circle(enemy.getPos().x, enemy.getPos().y, 15);
		sr.end();
		
		/*for (Wall wall : wls) {
			if(player.handleCollision(wall.getBoundingBox()))
				System.out.println("Collisione");
		}*/
	}
	
	public float getAngle(float x, float y) {
		// angolo tra vettore (1,0) e punto del mouse normalizzato (lunghezza 1)
		return (float) Math.toDegrees((Math.PI / 2 - Math.atan2(GameConfig.HEIGHT / 2 - y, GameConfig.WIDTH / 2 - x)));
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
