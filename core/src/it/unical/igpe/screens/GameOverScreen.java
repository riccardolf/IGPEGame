package it.unical.igpe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.unical.igpe.game.IGPEGame;

public class GameOverScreen implements Screen {
	private IGPEGame game;
	private Texture gameOver;
	private SpriteBatch batch;
	private float time = 0;
	
	public GameOverScreen (IGPEGame _game) {
		game = _game;
	}

	@Override
	public void show() {
		gameOver = new Texture(Gdx.files.internal("GameOver.jpg"));
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 480, 360);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(gameOver, 0, 0);
		batch.end();
		
		time += delta;
		if (time > 1) {
			if (Gdx.input.isKeyPressed(Keys.ANY_KEY) || Gdx.input.justTouched()) {
				game.setScreen(new MainMenuScreen(game));
			}
		}
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		gameOver.dispose();
	}
	
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void hide() {}
}
