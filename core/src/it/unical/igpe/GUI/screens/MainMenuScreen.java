package it.unical.igpe.GUI.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import it.unical.igpe.GUI.SoundManager;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.utils.GameConfig;

public class MainMenuScreen implements Screen {
	private SpriteBatch batch;
	Stage stage;
	private Table table;
	private Label title;
	private TextButton singleButton;
	private TextButton multiButton;
	private TextButton optionButton;
	private TextButton quitButton;
	
	public MainMenuScreen() {
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 900, 506);
		
		stage = new Stage();
		
		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		title = new Label(GameConfig.GAMENAME, IGPEGame.skinsoldier);
		
		singleButton = new TextButton("SinglePlayer", IGPEGame.skinsoldier);
		singleButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				IGPEGame.game.setScreen(ScreenManager.LCS);
			}
		});
		
		multiButton = new TextButton("MultiPlayer", IGPEGame.skinsoldier);
		multiButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				IGPEGame.game.setScreen(ScreenManager.MS);
			}
		});
		
		optionButton = new TextButton("Options", IGPEGame.skinsoldier);
		optionButton.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				IGPEGame.game.setScreen(ScreenManager.OS);
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
	public void show() {
		Gdx.input.setInputProcessor(stage);
		SoundManager.manager.get(SoundManager.MenuMusic, Music.class).setVolume(GameConfig.MUSIC_VOLUME);
		SoundManager.manager.get(SoundManager.MenuMusic, Music.class).setLooping(true);
		SoundManager.manager.get(SoundManager.MenuMusic, Music.class).play();
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
	public void hide() {}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void pause() {}
	
	@Override
	public void resume() {}
}
