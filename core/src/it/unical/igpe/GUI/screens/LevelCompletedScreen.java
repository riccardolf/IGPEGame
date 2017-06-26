package it.unical.igpe.GUI.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.unical.igpe.GUI.Assets;
import it.unical.igpe.GUI.SoundManager;
import it.unical.igpe.game.IGPEGame;

public class LevelCompletedScreen implements Screen {
	private Texture levelCompleted;
	private Texture GameOver;
	private SpriteBatch batch;
	private float time = 0;
	public boolean gameOver;
	
	@Override
	public void show() {
		levelCompleted = new Texture(Gdx.files.internal("levelcomplete.png"));
		GameOver = new Texture(Gdx.files.internal("GameOver.jpg"));
		batch = new SpriteBatch();
		SoundManager.manager.get(SoundManager.GameMusic, Music.class).stop();
		SoundManager.manager.get(SoundManager.FootStep, Music.class).stop();
		SoundManager.manager.get(SoundManager.MenuMusic, Music.class).play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(gameOver) {
			batch.getProjectionMatrix().setToOrtho2D(0, 0, 480, 360);
			batch.begin();
			batch.draw(GameOver, 0, 0);
			batch.end();
		}
		else {
			batch.getProjectionMatrix().setToOrtho2D(0, 0, 550, 300);
			batch.begin();
			batch.draw(levelCompleted, 0, 0);
			batch.end();
		}
		
		time += delta;
		if (time > 1) {
			if (Gdx.input.isKeyPressed(Keys.ANY_KEY) || Gdx.input.justTouched()) {
				time = 0;
				Assets.manager.clear();
				IGPEGame.game.setScreen(ScreenManager.MMS);
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		this.show();
	}


	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
