package it.unical.igpe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.tools.GameConfig;

public class OptionScreen implements Screen {
	private IGPEGame game;

	private SpriteBatch batch;
	private Stage stage;
	private Table table;
	private Label title;
	private Label music;
	private Label sound;
	private Slider musicVolume;
	private Slider soundVolume;
	private TextButton returnButton;

	public OptionScreen(IGPEGame _game) {
		this.game = _game;
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

		title = new Label("OPTIONS", IGPEGame.skinsoldier);
		music = new Label("MUSIC", IGPEGame.skinsoldier);
		sound = new Label("SOUND EFFECTS", IGPEGame.skinsoldier);
		musicVolume = new Slider(0.0f, 1.0f, 0.1f, false, IGPEGame.skinsoldier);
		musicVolume.setValue(GameConfig.MUSIC_VOLUME);
		musicVolume.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameConfig.MUSIC_VOLUME = musicVolume.getValue();
				game.setVolume();
			}
		});

		soundVolume = new Slider(0.0f, 1.0f, 0.1f, false, IGPEGame.skinsoldier);
		soundVolume.setValue(GameConfig.SOUND_VOLUME);
		soundVolume.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameConfig.SOUND_VOLUME = soundVolume.getValue();
				game.setVolume();
			}
		});

		returnButton = new TextButton("Return", IGPEGame.skinsoldier);
		returnButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(ScreenManager.MMS);
			}
		});
		table.add(title);
		table.row();
		table.add(music);
		table.row();
		table.add(musicVolume);
		table.row();
		table.add(sound);
		table.row();
		table.add(soundVolume);
		table.row();
		table.add(returnButton);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
