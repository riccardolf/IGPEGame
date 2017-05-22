package it.unical.igpe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.game.World;

public class LoadingScreen implements Screen {
	private IGPEGame game;
	
	private Skin skin;
	private TextureAtlas atlas;
	private Texture mainMenu;
	private SpriteBatch batch;
	private Stage stage;
	private Table table;
	private Label title;
	private ProgressBar loadingBar;
	
	public LoadingScreen(IGPEGame _game) {
		game = _game;
	}
	
	@Override
	public void show() {
		mainMenu = new Texture(Gdx.files.internal("MainMenu.jpg"));
		atlas = new TextureAtlas(Gdx.files.internal("skin/star-soldier-ui.atlas"));
		skin = new Skin(Gdx.files.internal("skin/star-soldier-ui.json"), atlas);
		
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 900, 506);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		title = new Label("Loading", skin);
		loadingBar = new ProgressBar(0, 100, 1, false, skin);
		table.add(title);
		table.row();
		
		
		
		stage = new Stage();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		
	}

	
	@Override
	public void resume() {
	}
	
	@Override
	public void hide() {
	}
}
