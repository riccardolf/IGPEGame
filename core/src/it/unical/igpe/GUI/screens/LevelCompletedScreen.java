package it.unical.igpe.GUI.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import it.unical.igpe.GUI.Assets;
import it.unical.igpe.GUI.SoundManager;
import it.unical.igpe.game.IGPEGame;

public class LevelCompletedScreen implements Screen {
	private Texture levelCompleted;
	private Texture GameOver;
	private SpriteBatch batch;
	private Stage stage;
	private Table table;
	private Label label;
	
	private float time = 0;
	public boolean gameOver;
	public int kills;
	
	public LevelCompletedScreen() {
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 600, 600);

		levelCompleted = new Texture(Gdx.files.internal("levelcomplete.png"));
		GameOver = new Texture(Gdx.files.internal("GameOver.jpg"));
		
		stage = new Stage();
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		label = new Label("You killed " + kills + " enemies", IGPEGame.skinsoldier);
		table.add(label);
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		SoundManager.manager.get(SoundManager.GameMusic, Music.class).stop();
		SoundManager.manager.get(SoundManager.FootStep, Music.class).stop();
		SoundManager.manager.get(SoundManager.MenuMusic, Music.class).play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		if(gameOver)
			batch.draw(GameOver, 0, 0);
		else
			batch.draw(levelCompleted, 0, 0);
		
		batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		
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
		stage.getViewport().update(width, height);
	}


	@Override
	public void dispose() {
		batch.dispose();
		stage.dispose();
		GameOver.dispose();
		levelCompleted.dispose();
	}

	@Override
	public void pause() {
	}
	
	@Override
	public void resume() {
	}
	
	@Override
	public void hide() {
	}
}
