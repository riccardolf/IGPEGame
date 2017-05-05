package it.unical.igpe.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import it.unical.igpe.Assets;
import it.unical.igpe.GameConfig;
import it.unical.igpe.IGPEGame;
import it.unical.igpe.TileLayer;
import it.unical.igpe.entity.Bullet;
import it.unical.igpe.entity.Player;

public class GameScreen implements Screen {
	
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
	Vector3 touchPos;
	boolean Running;
	boolean Shooting;
	TextureRegion currentFrame;
	
	@SuppressWarnings("static-access")
	public GameScreen(IGPEGame _game) {
		
		this.game = _game;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,600);
		
		stateTime = 0f;
		rotation = 0f;
		
		posP = new Vector2(100, 100);
		player = new Player(posP);
		
		touchPos = new Vector3();
		
		
		map = new int[16][16];
		/*for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				map[i][j] = 1;
			}
		}*/
		// Non sovrascrive la mappa
		try {
			layer = layer.FromFile("map.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		map = layer.map;
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();
		
		camera.position.x = posP.x;
		camera.position.y = posP.y;
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		
		currentFrame = Assets.walkAnimation.getKeyFrame(0);
		
		rotation = getAngle((float)Gdx.input.getX(), (float) Gdx.input.getY());
//		System.out.println(rotation);
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			player.MoveUp();
//			rotation = 0f;
			currentFrame = Assets.walkAnimation.getKeyFrame(stateTime, true);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.MoveLeft();
//			rotation = 90f;
			currentFrame = Assets.walkAnimation.getKeyFrame(stateTime, true);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			player.MoveDown();
//			rotation = 180f;
			currentFrame = Assets.walkAnimation.getKeyFrame(stateTime, true);
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.MoveRight();
//			rotation = 270f;
			currentFrame = Assets.walkAnimation.getKeyFrame(stateTime, true);
		}
		if(Gdx.input.justTouched()) {
		    touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		    //bullet.fire(touchPos);
		    currentFrame = Assets.shootAnimation.getKeyFrame(1);
		}
			
		
		batch.begin();
		
		// draw map;
		for(int y = 0; y < layer.map.length; y++) {
			for (int x = 0; x < layer.map[y].length; x++) {
				if(map[y][x] == 0)
					batch.draw(Assets.Water, x * 32, y * 32);
				else if(map[y][x] == 1)
					batch.draw(Assets.Sand, x * 32, y * 32);
				else if(map[y][x] == 2)
					batch.draw(Assets.Grass, x * 32, y * 32);
				else if(map[y][x] == 3)
					batch.draw(Assets.Wood, x * 32, y * 32);
			}
		}
		
		batch.draw(currentFrame, posP.x, posP.y, 0, 0, 64, 64, 1f, 1f, rotation);
		batch.end();
	}
	
	public float getAngle(float x, float y) {
		// angolo tra vettore (1,0) e punto del mouse normalizzato (lunghezza 1)
	    //float angle = (float) Math.toDegrees(Math.atan2(y, x));
		//y -= GameConfig.HEIGHT;
		//float angle = (float) Math.toDegrees(Math.atan2(y - (float) player.getPos().y, x - (float)player.getPos().x));
		
		/*Vector2 v2 = new Vector2(x,y);
		
		System.out.println(v2.angle());
		
		float angle = v2.angle();
		
		
	    if(angle < 0){
	        angle += 360;
	    }

	    return angle;*/
		
		float angle = 0.0f;
		
		Vector2 center = new Vector2(GameConfig.WIDTH / 2 , GameConfig.HEIGHT / 2);
		Vector2 mouse = new Vector2(x, y);
		//arccos
		float ipot = (float) Math.sqrt(Math.pow(center.x - mouse.x, 2) + Math.pow(center.y - mouse.y, 2));
		float cat = center.x - mouse.x;
		angle = (float) Math.toDegrees(Math.acos(cat/ipot));
		//angle = center.angle(mouse);
		
		System.out.println(angle);
		System.out.println("Center " + center.x+ " " + center.y);
		
		return angle;
		
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
