package it.unical.igpe.GUI.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import it.unical.igpe.GUI.Assets;
import it.unical.igpe.game.IGPEGame;

public class LevelCompletedScreen implements Screen {
	private IGPEGame game;
	private SpriteBatch batch;
	private Stage stage;
	private Table table;
	private Label labelK;
	private Label labelLC;
	private Label labelGO;
	
	private float time = 0;
	public boolean gameOver;
	public int kills;
	
	public LevelCompletedScreen(IGPEGame game) {
		this.game = game;
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 800);

		stage = new Stage();
		
		labelK = new Label("You killed " + kills + " enemies", IGPEGame.skinsoldier);
		labelLC = new Label("LEVEL COMPLETED", IGPEGame.skinsoldier);
		labelGO = new Label("GAMEOVER", IGPEGame.skinsoldier);
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		game.soundManager.GameMusic.stop();
		game.soundManager.FootStep.stop();
		game.soundManager.MenuMusic.play();
		
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		if(gameOver)
			table.add(labelGO);
		else
			table.add(labelLC);
		
		table.row();
		table.add(labelK);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
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
