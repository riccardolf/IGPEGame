package it.unical.igpe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import it.unical.igpe.game.IGPEGame;

public class LoadingScreen implements Screen {
	private IGPEGame game;
	private Skin skin;
	private TextureAtlas atlas;
	private Texture mainMenu;
	private SpriteBatch batch;
	public Stage stage;
	private Table table;
	private ProgressBar loadingBar;
	private Label loading;
	
	public LoadingScreen(IGPEGame _game) {
		this.game = _game;
	}
	
	@Override
	public void show() {
		mainMenu = new Texture(Gdx.files.internal("MainMenu.jpg"));
		atlas = new TextureAtlas(Gdx.files.internal("skin/starsoldier/star-soldier-ui.atlas"));
		skin = new Skin(Gdx.files.internal("skin/starsoldier/star-soldier-ui.json"), atlas);

		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 900, 506);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		loading = new Label("LOADING...", skin);
		
		loadingBar = new ProgressBar(0.0f, 1.0f, 0.1f, true, skin);
		loadingBar.setValue(0);
		table.add(loading);
		table.row();
		table.add(loadingBar);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		batch.draw(mainMenu, 0, 0);
		batch.end();
		
		loadingBar.setValue(1f);
		
		if(loadingBar.getValue() == 1f)
			game.setScreen(ScreenManager.GS);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
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

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		mainMenu.dispose();
		atlas.dispose();
	}
	
}
