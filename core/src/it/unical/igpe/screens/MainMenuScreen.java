package it.unical.igpe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import it.unical.igpe.game.IGPEGame;

public class MainMenuScreen implements Screen {
	private IGPEGame game;
	
	private SpriteBatch batch;
	Stage stage;
	private Table table;
	private Label title;
	private TextButton singleButton;
	private TextButton multiButton;
	private TextButton optionButton;
	private TextButton quitButton;
	
	public MainMenuScreen(IGPEGame _game) {
		game = _game;
	}
	
	@Override
	public void show() {		
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 900, 506);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		title = new Label("NOT ANOTHER TOP DOWN SHOOTER 3", IGPEGame.skinsoldier);
		
		singleButton = new TextButton("SinglePlayer", IGPEGame.skinsoldier);
		singleButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(ScreenManager.LCS);
			}
		});
		
		multiButton = new TextButton("MultiPlayer", IGPEGame.skinsoldier);
		multiButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(ScreenManager.MS);
			}
		});
		
		optionButton = new TextButton("Option", IGPEGame.skinsoldier);
		optionButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(ScreenManager.OS);
			}
		});
		
		quitButton = new TextButton("Quit", IGPEGame.skinsoldier);
		quitButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
		table.add(title);
		table.row();
		table.add(singleButton);
		table.row();
		table.add(multiButton);
		table.row();
		table.add(optionButton);
		table.row();
		table.add(quitButton);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(IGPEGame.background, 0, 0);
		batch.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void pause() {}
	
	@Override
	public void resume() {}
}
