package it.unical.igpe.GUI.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import it.unical.igpe.GUI.Assets;
import it.unical.igpe.GUI.SoundManager;
import it.unical.igpe.game.IGPEGame;
import it.unical.igpe.utils.GameConfig;

public class PauseScreen implements Screen{
	private SpriteBatch batch;
	public Stage stage;
	private Table table;
	private Label title;
	private Label music;
	private Label sound;
	private Slider musicVolume;
	private Slider soundVolume;
	private TextButton quitButton;
	private CheckBox fullscreen;
	
	private Texture command;

	@Override
	public void show() {
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 900,506);

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		title = new Label("PAUSE", IGPEGame.skinsoldier);
		music = new Label("MUSIC", IGPEGame.skinsoldier);
		sound = new Label("SOUND EFFECTS", IGPEGame.skinsoldier);
		musicVolume = new Slider(0.0f, 1.0f, 0.1f, false, IGPEGame.skinsoldier);
		musicVolume.setValue(GameConfig.MUSIC_VOLUME);
		musicVolume.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameConfig.MUSIC_VOLUME = musicVolume.getValue();
				IGPEGame.game.setVolume();
			}
		});

		soundVolume = new Slider(0.0f, 1.0f, 0.1f, false, IGPEGame.skinsoldier);
		soundVolume.setValue(GameConfig.SOUND_VOLUME);
		soundVolume.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameConfig.SOUND_VOLUME = soundVolume.getValue();
				IGPEGame.game.setVolume();
			}
		});

		quitButton = new TextButton("Quit", IGPEGame.skinsoldier);
		quitButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				SoundManager.manager.get(SoundManager.GameMusic, Music.class).stop();
				IGPEGame.music.play();
				Assets.manager.clear();
				IGPEGame.game.setScreen(ScreenManager.MMS);
			}
		});
		
		fullscreen = new CheckBox("FullScreen", IGPEGame.skinsoldier);
		fullscreen.setChecked(GameConfig.isFullscreen);
		fullscreen.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if(fullscreen.isChecked()) {
					GameConfig.isFullscreen = true;
					Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
					IGPEGame.game.setFullScreen();
				}
				else {
					GameConfig.isFullscreen = false;
					Gdx.graphics.setWindowedMode(GameConfig.WIDTH, GameConfig.HEIGHT);
					IGPEGame.game.setFullScreen();
				}
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
		table.add(fullscreen);
		table.row();
		table.add(quitButton);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
			IGPEGame.game.setScreen(ScreenManager.GS);

		batch.begin();
		batch.draw(IGPEGame.background, 0, 0);
		command=new Texture(Gdx.files.internal("command.png"));
		batch.draw(command,0,0);
		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		this.show();
	}


	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
	}	
	
	@Override
	public void hide() {
		
	}


	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
